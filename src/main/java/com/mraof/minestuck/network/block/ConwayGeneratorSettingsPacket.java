package com.mraof.minestuck.network.block;

import com.mojang.datafixers.util.Pair;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.block.redstone.ConwayGeneratorBlock;
import com.mraof.minestuck.blockentity.redstone.ConwayGeneratorBlockEntity;
import com.mraof.minestuck.network.MSPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public record ConwayGeneratorSettingsPacket(Map<Pair<Integer, Integer>, Boolean> startConfiguration, BlockPos beBlockPos) implements MSPacket.PlayToServer
{
	public static final ResourceLocation ID = Minestuck.id("conway_generator_settings");
	
	@Override
	public ResourceLocation id()
	{
		return ID;
	}
	
	@Override
	public void write(FriendlyByteBuf buffer)
	{
		buffer.writeBlockPos(beBlockPos);
		
		for(Map.Entry<Pair<Integer, Integer>, Boolean> entry : startConfiguration.entrySet())
		{
			buffer.writeInt(entry.getKey().getFirst());
			buffer.writeInt(entry.getKey().getSecond());
			buffer.writeBoolean(entry.getValue());
		}
	}
	
	public static ConwayGeneratorSettingsPacket read(FriendlyByteBuf buffer)
	{
		BlockPos beBlockPos = buffer.readBlockPos();
		
		Map<Pair<Integer, Integer>, Boolean> startConfiguration = new HashMap<>();
		
		//TODO bad
		for(int i = -ConwayGeneratorBlockEntity.GEN_DISTANCE; i < ConwayGeneratorBlockEntity.GEN_DISTANCE; i++)
		{
			startConfiguration.put(Pair.of(buffer.readInt(), buffer.readInt()), buffer.readBoolean());
		}
		
		return new ConwayGeneratorSettingsPacket(startConfiguration, beBlockPos);
	}
	
	@Override
	public void execute(ServerPlayer player)
	{
		if(!ConwayGeneratorBlock.canInteract(player))
			return;
		
		MSPacket.getAccessibleBlockEntity(player, this.beBlockPos, ConwayGeneratorBlockEntity.class)
				.ifPresent(generator -> generator.handleSettingsPacket(this));
	}
}
