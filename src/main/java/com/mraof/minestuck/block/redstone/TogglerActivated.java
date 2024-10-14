package com.mraof.minestuck.block.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface TogglerActivated
{
	void triggered(Level level, BlockPos pos);
}
