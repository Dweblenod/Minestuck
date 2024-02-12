package com.mraof.minestuck.network;

import com.mraof.minestuck.entity.DialogueEntity;
import com.mraof.minestuck.util.Dialogue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DialoguePacket implements MSPacket.PlayToServer
{
	private final Dialogue.Trigger trigger;
	private final int entityID;
	
	public static DialoguePacket createPacket(Dialogue.Trigger trigger, LivingEntity entity)
	{
		return new DialoguePacket(trigger, entity.getId());
	}
	public DialoguePacket(Dialogue.Trigger trigger, int entityID)
	{
		this.trigger = trigger;
		this.entityID = entityID;
	}
	
	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		int type = trigger.getType().ordinal();
		String content = trigger.getContent();
		String contentExtra = trigger.getContentExtra();
		
		buffer.writeInt(type);
		buffer.writeUtf(content, 500);
		buffer.writeUtf(contentExtra, 500);
		buffer.writeInt(entityID);
	}
	
	public static DialoguePacket decode(FriendlyByteBuf buffer)
	{
		Dialogue.Trigger trigger = new Dialogue.Trigger(Dialogue.Trigger.Type.fromInt(buffer.readInt()), buffer.readUtf(500), buffer.readUtf(500));
		return new DialoguePacket(trigger, buffer.readInt());
	}
	
	@Override
	public void execute(ServerPlayer player)
	{
		Entity entity = Minecraft.getInstance().level.getEntity(entityID);
		if(entity instanceof LivingEntity livingEntity && entity instanceof DialogueEntity)
		{
			trigger.testConditions(livingEntity, player);
		}
	}
}