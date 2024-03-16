package com.mraof.minestuck.entity.dialogue;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class RandomlySelectableDialogue
{
	private final List<Dialogue.SelectableDialogue> selectableDialogueList;
	
	private RandomlySelectableDialogue(List<Dialogue.SelectableDialogue> selectableDialogueList)
	{
		this.selectableDialogueList = selectableDialogueList;
	}
	
	@Nullable
	private static RandomlySelectableDialogue INSTANCE;
	
	public static RandomlySelectableDialogue instance()
	{
		return Objects.requireNonNull(INSTANCE, "Called instance() before this has loaded!");
	}
	
	public Optional<Dialogue.SelectableDialogue> pickRandomForEntity(LivingEntity entity)
	{
		List<WeightedEntry.Wrapper<Dialogue.SelectableDialogue>> weightedFilteredDialogue = new ArrayList<>();
		selectableDialogueList.forEach(selectable -> {
			if(selectable.condition().test(entity, null))
					weightedFilteredDialogue.add(WeightedEntry.wrap(selectable, selectable.weight()));
		});
		
		return WeightedRandom.getRandomItem(entity.getRandom(), weightedFilteredDialogue)
				.map(WeightedEntry.Wrapper::getData);
	}
	
	@SubscribeEvent
	public static void onResourceReload(AddReloadListenerEvent event)
	{
		event.addListener(new Loader());
	}
	
	@SubscribeEvent
	public static void onServerStopped(ServerStoppedEvent event)
	{
		INSTANCE = null;
	}
	
	private static class Loader extends SimpleJsonResourceReloadListener
	{
		private static final Logger LOGGER = LogManager.getLogger();
		private static final Gson GSON = new GsonBuilder().create();
		
		public Loader()
		{
			super(GSON, "minestuck/selectable_dialogue/consort");
		}
		
		@Override
		protected void apply(Map<ResourceLocation, JsonElement> jsonElements, ResourceManager resourceManager, ProfilerFiller profiler)
		{
			ImmutableList.Builder<Dialogue.SelectableDialogue> listBuilder = ImmutableList.builder();
			
			for(Map.Entry<ResourceLocation, JsonElement> entry : jsonElements.entrySet())
			{
				Dialogue.SelectableDialogue.CODEC.parse(JsonOps.INSTANCE, entry.getValue())
						.resultOrPartial(message -> LOGGER.error("Problem loading selectable dialogue {}: {}", entry.getKey(), message))
						.ifPresent(listBuilder::add);
			}
			
			INSTANCE = new RandomlySelectableDialogue(listBuilder.build());
		}
	}
}
