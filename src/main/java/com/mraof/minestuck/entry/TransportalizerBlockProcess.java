package com.mraof.minestuck.entry;

import com.mraof.minestuck.blockentity.TransportalizerBlockEntity;
import com.mraof.minestuck.world.storage.TransportalizerSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TransportalizerBlockProcess implements EntryBlockProcessing
{
	@Override
	public void copyOver(ServerLevel oldLevel, BlockPos oldPos, ServerLevel newLevel, BlockPos newPos, BlockState state, @Nullable BlockEntity oldTE, @Nullable BlockEntity newTE)
	{
		if(oldTE instanceof TransportalizerBlockEntity && newTE instanceof TransportalizerBlockEntity)
		{
			TransportalizerSavedData.get(oldLevel).replace(((TransportalizerBlockEntity) newTE).getId(), GlobalPos.of(oldLevel.dimension(), oldPos), GlobalPos.of(newLevel.dimension(), newPos));
			if(((TransportalizerBlockEntity) oldTE).isActive())
				((TransportalizerBlockEntity) newTE).tryReactivate();
		}
	}
}