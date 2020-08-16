package com.mraof.minestuck.world.gen;

import com.mojang.datafixers.Dynamic;
import com.mraof.minestuck.block.MSBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class ProspitSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public final SurfaceBuilderConfig PROSPIT_CONFIG = new SurfaceBuilderConfig(MSBlocks.GOLD_BRICKS.getDefaultState(), MSBlocks.GOLD_BRICKS.getDefaultState(), MSBlocks.GOLD_BRICKS.getDefaultState());
	public final SurfaceBuilderConfig DARK_GRAY_CHESS_CONFIG = new SurfaceBuilderConfig(MSBlocks.DARK_GRAY_CHESS_DIRT.getDefaultState(), MSBlocks.DARK_GRAY_CHESS_DIRT.getDefaultState(), MSBlocks.DARK_GRAY_CHESS_DIRT.getDefaultState());
	public final SurfaceBuilderConfig BLACK_CHESS_CONFIG = new SurfaceBuilderConfig(MSBlocks.BLACK_CHESS_DIRT.getDefaultState(), MSBlocks.BLACK_CHESS_DIRT.getDefaultState(), MSBlocks.BLACK_CHESS_DIRT.getDefaultState());

	public ProspitSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> deserializer)
	{
		super(deserializer);
	}

	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		int chunkX = Math.floorDiv(x, 48), chunkZ = Math.floorDiv(z, 48);
		if((chunkX + chunkZ) % 2 == 0)
		{
			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, 0, defaultBlock, defaultFluid, seaLevel, seed, PROSPIT_CONFIG);
		} else
		{
			if(noise < -1.0)
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, 0, defaultBlock, defaultFluid, seaLevel, seed, DARK_GRAY_CHESS_CONFIG);
			else
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, 0, defaultBlock, defaultFluid, seaLevel, seed, BLACK_CHESS_CONFIG);
		}
	}
}