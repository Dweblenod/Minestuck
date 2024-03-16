package com.mraof.minestuck.data.dialogue;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.mraof.minestuck.entity.dialogue.Dialogue;
import com.mraof.minestuck.entity.dialogue.DialogueMessage;
import com.mraof.minestuck.entity.dialogue.Trigger;
import com.mraof.minestuck.entity.dialogue.condition.Condition;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class DialogueProvider implements DataProvider
{
	private final Map<ResourceLocation, Dialogue.NodeSelector> dialogues = new HashMap<>();
	
	private final String modId;
	private final String subFolder;
	private final PackOutput output;
	
	public DialogueProvider(String modId, String subFolder, PackOutput output)
	{
		this.modId = modId;
		this.subFolder = subFolder;
		this.output = output;
	}
	
	public ResourceLocation dialogueId(String path)
	{
		return new ResourceLocation(this.modId, this.subFolder + "/" + path);
	}
	
	public ResourceLocation add(String path, DialogueProducer builder)
	{
		return builder.buildAndRegister(dialogueId(path), this::checkAndAdd);
	}
	
	public void add(ResourceLocation id, SimpleDialogueProducer builder)
	{
		builder.buildAndRegister(id, this::checkAndAdd);
	}
	
	private void checkAndAdd(ResourceLocation id, Dialogue.NodeSelector dialogue)
	{
		if(this.dialogues.containsKey(id))
			throw new IllegalArgumentException(id + " was added twice");
		this.dialogues.put(id, dialogue);
	}
	
	/**
	 * A builder that can would create one or more node selectors, with one being considered a starting node.
	 */
	public interface DialogueProducer
	{
		ResourceLocation buildAndRegister(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register);
	}
	
	/**
	 * A version of {@link DialogueProducer} which is expected to register the starting node selector under the given id.
	 */
	public interface SimpleDialogueProducer extends DialogueProducer
	{
		Dialogue.NodeSelector buildSelector(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register);
		
		@Override
		default ResourceLocation buildAndRegister(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register)
		{
			register.accept(id, this.buildSelector(id, register));
			return id;
		}
	}
	
	public interface NodeProducer
	{
		Dialogue.Node buildNode(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register);
	}
	
	public static class NodeSelectorBuilder implements SimpleDialogueProducer
	{
		private final List<Pair<Condition, NodeProducer>> conditionedNodes = new ArrayList<>();
		@Nullable
		private NodeProducer defaultNode;
		
		public NodeSelectorBuilder node(Condition condition, String key, NodeProducer node)
		{
			return node(condition, (id, register) -> node.buildNode(id.withSuffix("." + key), register));
		}
		
		public NodeSelectorBuilder node(Condition condition, NodeProducer node)
		{
			this.conditionedNodes.add(Pair.of(condition, node));
			return this;
		}
		
		public NodeSelectorBuilder defaultNode(String key, NodeProducer node)
		{
			return defaultNode((id, register) -> node.buildNode(id.withSuffix("." + key), register));
		}
		
		public NodeSelectorBuilder defaultNode(NodeProducer node)
		{
			this.defaultNode = node;
			return this;
		}
		
		@Override
		public Dialogue.NodeSelector buildSelector(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register)
		{
			Objects.requireNonNull(this.defaultNode, "Default node must be set");
			return new Dialogue.NodeSelector(this.conditionedNodes.stream().map(pair -> pair.mapSecond(builder -> builder.buildNode(id, register))).toList(),
					this.defaultNode.buildNode(id, register));
		}
	}
	
	public static class NodeBuilder implements SimpleDialogueProducer, NodeProducer
	{
		private final Function<ResourceLocation, DialogueMessage> messageProvider;
		@Nullable
		private Function<ResourceLocation, DialogueMessage> descriptionProvider;
		private String animation = Dialogue.DEFAULT_ANIMATION;
		private ResourceLocation guiPath = Dialogue.DEFAULT_GUI;
		private final List<ResponseBuilder> responses = new ArrayList<>();
		
		public NodeBuilder(DialogueMessage message)
		{
			this.messageProvider = id -> message;
		}
		
		public NodeBuilder(Function<ResourceLocation, DialogueMessage> messageProvider)
		{
			this.messageProvider = messageProvider;
		}
		
		public NodeBuilder description(Function<ResourceLocation, DialogueMessage> provider)
		{
			this.descriptionProvider = provider;
			return this;
		}
		
		public NodeBuilder animation(String animation)
		{
			this.animation = animation;
			return this;
		}
		
		public NodeBuilder gui(ResourceLocation guiPath)
		{
			this.guiPath = guiPath;
			return this;
		}
		
		public NodeBuilder addClosingResponse()
		{
			return addClosingResponse(DOTS);
		}
		
		public NodeBuilder addClosingResponse(DialogueMessage message)
		{
			return addResponse(new ResponseBuilder(message));
		}
		
		public NodeBuilder addClosingResponse(Function<ResourceLocation, DialogueMessage> message)
		{
			return addResponse(new ResponseBuilder(message));
		}
		
		public NodeBuilder next(String key, DialogueProducer dialogueProducer)
		{
			return this.addResponse(new ResponseBuilder(ARROW).nextDialogue(key, dialogueProducer));
		}
		
		public NodeBuilder next(ResourceLocation dialogueId)
		{
			return this.addResponse(new ResponseBuilder(ARROW).nextDialogue(dialogueId));
		}
		
		public NodeBuilder addResponse(ResponseBuilder responseBuilder)
		{
			this.responses.add(responseBuilder);
			return this;
		}
		
		@Override
		public Dialogue.Node buildNode(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register)
		{
			DialogueMessage message = this.messageProvider.apply(id);
			Optional<DialogueMessage> description = this.descriptionProvider != null ? Optional.of(this.descriptionProvider.apply(id)) : Optional.empty();
			return new Dialogue.Node(message, description, this.animation, this.guiPath, this.responses.stream().map(builder -> builder.build(id, register)).toList());
		}
		
		@Override
		public Dialogue.NodeSelector buildSelector(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register)
		{
			return new NodeSelectorBuilder().defaultNode(this)
					.buildSelector(id, register);
		}
	}
	
	public static class ResponseBuilder
	{
		private final Function<ResourceLocation, DialogueMessage> message;
		private final List<Trigger> triggers = new ArrayList<>();
		@Nullable
		private DialogueProducer nextDialogue = null;
		private Condition condition = Condition.AlwaysTrue.INSTANCE;
		private boolean hideIfFailed = true;
		private Function<ResourceLocation, String> failTooltip = id -> null;
		
		ResponseBuilder(DialogueMessage message)
		{
			this.message = id -> message;
		}
		
		ResponseBuilder(Function<ResourceLocation, DialogueMessage> message)
		{
			this.message = message;
		}
		
		public ResponseBuilder nextDialogue(String key, DialogueProducer dialogueProducer)
		{
			this.nextDialogue = (id, register) -> dialogueProducer.buildAndRegister(id.withSuffix("." + key), register);
			return this;
		}
		
		public ResponseBuilder nextDialogue(ResourceLocation nextDialogueId)
		{
			this.nextDialogue = (id, register) -> nextDialogueId;
			return this;
		}
		
		public ResponseBuilder loop()
		{
			this.nextDialogue = (id, register) -> id;
			return this;
		}
		
		public ResponseBuilder condition(Condition condition)
		{
			this.hideIfFailed = true;
			this.condition = condition;
			return this;
		}
		
		public ResponseBuilder visibleCondition(Condition condition)
		{
			this.hideIfFailed = false;
			this.condition = condition;
			return this;
		}
		
		public ResponseBuilder visibleCondition(Function<ResourceLocation, String> failTooltip, Condition condition)
		{
			this.hideIfFailed = false;
			this.condition = condition;
			this.failTooltip = failTooltip;
			return this;
		}
		
		public ResponseBuilder addTrigger(Trigger trigger)
		{
			this.triggers.add(trigger);
			return this;
		}
		
		public Dialogue.Response build(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register)
		{
			Optional<ResourceLocation> nextPath = Optional.ofNullable(this.nextDialogue).map(builder -> builder.buildAndRegister(id, register));
			DialogueMessage message = this.message.apply(id);
			return new Dialogue.Response(message, this.triggers, nextPath, this.condition, this.hideIfFailed, Optional.ofNullable(this.failTooltip.apply(id)));
		}
	}
	
	public static class ChainBuilder implements DialogueProducer, NodeProducer
	{
		private boolean withFolders = false;
		private final List<NodeBuilder> nodes = new ArrayList<>();
		private boolean loop = false;
		
		public ChainBuilder withFolders()
		{
			this.withFolders = true;
			return this;
		}
		
		public ChainBuilder node(NodeBuilder nodeBuilder)
		{
			this.nodes.add(nodeBuilder);
			return this;
		}
		
		public ChainBuilder loop()
		{
			this.loop = true;
			return this;
		}
		
		private <T> T build(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register, BiFunction<NodeBuilder, ResourceLocation, T> startNodeBuilder)
		{
			if(this.nodes.isEmpty())
				throw new IllegalStateException("Nodes must be added to this chain builder");
			
			List<ResourceLocation> ids = IntStream.range(0, this.nodes.size())
					.mapToObj(index -> id.withSuffix((withFolders ? "/" : ".") + (index + 1))).toList();
			
			for(int index = 1; index < this.nodes.size(); index++)
				this.nodes.get(index - 1).next(ids.get(index));
			if(this.loop)
				this.nodes.get(this.nodes.size() - 1).next(ids.get(0));
			
			for(int index = 1; index < this.nodes.size(); index++)
				this.nodes.get(index).buildAndRegister(ids.get(index), register);
			return startNodeBuilder.apply(this.nodes.get(0), ids.get(0));
		}
		
		@Override
		public Dialogue.Node buildNode(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register)
		{
			return this.build(id, register, (startNode, startId) -> startNode.buildNode(startId, register));
		}
		
		@Override
		public ResourceLocation buildAndRegister(ResourceLocation id, BiConsumer<ResourceLocation, Dialogue.NodeSelector> register)
		{
			return this.build(id, register, (startNode, startId) -> startNode.buildAndRegister(startId, register));
		}
	}
	
	public static final DialogueMessage ARROW = new DialogueMessage("minestuck.arrow");
	public static final DialogueMessage DOTS = new DialogueMessage("minestuck.dots");
	
	boolean hasAddedDialogue(ResourceLocation dialogueId)
	{
		return this.dialogues.containsKey(dialogueId);
	}
	
	@Override
	public CompletableFuture<?> run(CachedOutput cache)
	{
		Set<ResourceLocation> missingDialogue = new HashSet<>();
		dialogues.values().forEach(dialogue -> dialogue.visitConnectedDialogue(dialogueId -> {
			if(!hasAddedDialogue(dialogueId))
				missingDialogue.add(dialogueId);
		}));
		if(!missingDialogue.isEmpty())
			throw new IllegalStateException("Some referenced dialogue is missing: " + missingDialogue);
		
		Path outputPath = output.getOutputFolder();
		List<CompletableFuture<?>> futures = new ArrayList<>(dialogues.size());
		
		for(Map.Entry<ResourceLocation, Dialogue.NodeSelector> entry : dialogues.entrySet())
		{
			Path dialoguePath = getPath(outputPath, entry.getKey());
			JsonElement dialogueJson = Dialogue.NodeSelector.CODEC.encodeStart(JsonOps.INSTANCE, entry.getValue()).getOrThrow(false, LOGGER::error);
			futures.add(DataProvider.saveStable(cache, dialogueJson, dialoguePath));
		}
		
		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
	}
	
	private static Path getPath(Path outputPath, ResourceLocation id)
	{
		return outputPath.resolve("data/" + id.getNamespace() + "/minestuck/dialogue/" + id.getPath() + ".json");
	}
	
	@Override
	public String getName()
	{
		return "Dialogues";
	}
}