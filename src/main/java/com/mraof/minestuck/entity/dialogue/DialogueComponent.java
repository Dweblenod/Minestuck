package com.mraof.minestuck.entity.dialogue;

import com.mojang.datafixers.util.Pair;
import com.mraof.minestuck.advancements.MSCriteriaTriggers;
import com.mraof.minestuck.entity.consort.ConsortEntity;
import com.mraof.minestuck.network.DialogueScreenPacket;
import com.mraof.minestuck.network.MSPacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public final class DialogueComponent
{
	private static final Logger LOGGER = LogManager.getLogger();
	
	private final LivingEntity entity;
	@Nullable
	private ResourceLocation activeDialogue;
	private boolean keepOnReset;
	private boolean hasGeneratedOnce = false;
	
	public DialogueComponent(LivingEntity entity)
	{
		this.entity = entity;
	}
	
	public void read(CompoundTag tag)
	{
		if(tag.contains("dialogue_id", CompoundTag.TAG_STRING))
		{
			this.activeDialogue = ResourceLocation.tryParse(tag.getString("dialogue_id"));
			this.keepOnReset = tag.getBoolean("keep_on_reset");
			this.hasGeneratedOnce = true;
		}
		else
			this.hasGeneratedOnce = tag.getBoolean("has_generated");
	}
	
	public CompoundTag write()
	{
		CompoundTag tag = new CompoundTag();
		if(this.activeDialogue != null)
		{
			tag.putString("dialogue_id", this.activeDialogue.toString());
			tag.putBoolean("keep_on_reset", this.keepOnReset);
		}
		
		tag.putBoolean("has_generated", this.hasGeneratedOnce);
		
		return tag;
	}
	
	public boolean hasGeneratedOnce()
	{
		return hasGeneratedOnce;
	}
	
	public void setDialogue(Dialogue.SelectableDialogue selectable)
	{
		this.setDialogue(selectable.dialogueId(), selectable.keepOnReset());
	}
	
	public void setDialogue(ResourceLocation dialogueId, boolean keepOnReset)
	{
		this.hasGeneratedOnce = true;
		this.activeDialogue = dialogueId;
		this.keepOnReset = keepOnReset;
	}
	
	public boolean hasActiveDialogue()
	{
		return this.activeDialogue != null;
	}
	
	public void resetDialogue()
	{
		if(!this.keepOnReset)
			this.activeDialogue = null;
	}
	
	public void startDialogue(ServerPlayer serverPlayer)
	{
		if(this.activeDialogue == null)
			return;
		
		Dialogue dialogue = DialogueManager.getInstance().getDialogue(this.activeDialogue);
		if(dialogue == null)
		{
			LOGGER.warn("Unable to find dialogue with id {}", this.activeDialogue);
			this.activeDialogue = null;
			return;
		}
		
		if(this.entity instanceof ConsortEntity consort)
			MSCriteriaTriggers.CONSORT_TALK.trigger(serverPlayer, this.activeDialogue.toString(), consort);
		
		this.openScreenForDialogue(serverPlayer, dialogue);
	}
	
	public void openScreenForDialogue(ServerPlayer serverPlayer, Dialogue dialogue)
	{
		Pair<Dialogue.DialogueNode, Integer> node = dialogue.nodes().pickNode(this.entity, serverPlayer);
		Dialogue.DialogueData data = node.getFirst().evaluateData(this.entity, serverPlayer);
		Dialogue.NodeReference nodeReference = new Dialogue.NodeReference(dialogue.lookupId(), node.getSecond());
		
		DialogueScreenPacket packet = new DialogueScreenPacket(this.entity.getId(), nodeReference, data);
		MSPacketHandler.sendToPlayer(packet, serverPlayer);
	}
}
