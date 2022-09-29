package com.mraof.minestuck.network;

import com.mraof.minestuck.entity.DenizenEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

public class DenizenPacket implements PlayToClientPacket
{
	private final int entityID;
	private final DenizenEntity.Animation animation;
	
	public static DenizenPacket createPacket(DenizenEntity entity, DenizenEntity.Animation animation)
	{
		return new DenizenPacket(entity.getId(), animation);
	}
	
	private DenizenPacket(int entityID, DenizenEntity.Animation animation)
	{
		this.entityID = entityID;
		this.animation = animation;
	}
	
	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		buffer.writeInt(entityID);
		buffer.writeInt(animation.ordinal());
	}
	
	public static DenizenPacket decode(FriendlyByteBuf buffer)
	{
		int entityID = buffer.readInt(); //readInt spits out the values you gave to the PacketBuffer in encode in that order
		DenizenEntity.Animation animation = DenizenEntity.Animation.values()[buffer.readInt()];
		
		return new DenizenPacket(entityID, animation);
	}
	
	@Override
	public void execute()
	{
		Entity entity = Minecraft.getInstance().level.getEntity(entityID);
		if(entity instanceof DenizenEntity)
		{
			((DenizenEntity) entity).setAnimationFromPacket(animation);
		}
	}
}
