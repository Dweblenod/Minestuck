package com.mraof.minestuck.world.gen;

import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;

import javax.annotation.Nullable;

public class ProspitChunkGenerator extends NoiseChunkGenerator<ProspitGenSettings>
{
	private final OctavesNoiseGenerator depthNoise;

	public ProspitChunkGenerator(IWorld worldIn, BiomeProvider biomeProviderIn, ProspitGenSettings settings)
	{
		super(worldIn, biomeProviderIn, 1, 1, 256, settings, false);
		
		this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, 16);
	}
	
	@Override
	protected double[] getBiomeNoiseColumn(int columnX, int columnZ)
	{
		double depth = this.depthNoise.getValue(columnX * 200, 10.0D, columnZ * 200, 1.0D, 1.0D, true) / 12000.0D + 1.0D;
		
		return new double[]{depth, 0.3};
	}
	
	/**
	 * Generated an offset/modifier to the noise density based on the y-height of the grid element and the values generated by the function above
	 * Uses the same equation as the vanilla overworld
	 */
	@Override
	protected double func_222545_a(double depth, double scale, int height)
	{
		double modifier = ((double)height - (0.1D + depth * 1.5D / 1.1D * 1.1D)) * 1.1D * 1.1D / 1.1D / scale;
		if (modifier < 0.0D)
			modifier *= 1.1D;
		
		return modifier;
	}
	
	/**
	 * The first step of noise generation is to generate a grid of doubles where each double determines the density of blocks inside that grid element
	 * This function generates the densities for a column in this grid
	 * @param noiseColumn Array to be filled with noise densities for this column
	 * @param columnX the x index of the noise column
	 * @param columnZ the z index of the noise column
	 */
	@Override
	protected void fillNoiseColumn(double[] noiseColumn, int columnX, int columnZ)
	{
		double horizontal = 10D;
		double vertical = 2D;
		double horizontal2 = 1.1D;
		double vertical2 = 1.1D;
		int lerpModifier = 1;
		int skyValueTarget = -10;
		this.func_222546_a(noiseColumn, columnX, columnZ, horizontal, vertical, horizontal2, vertical2, lerpModifier, skyValueTarget);
	}
	
	@Override
	public int getGroundHeight()
	{
		return 10;
	}
	
	@Override
	public int getSeaLevel()
	{
		return 8;
	}

	@Override
	public void generateSurface(IChunk chunkIn)
	{
		SharedSeedRandom sharedRandom = new SharedSeedRandom();
		sharedRandom.setBaseChunkSeed(chunkIn.getPos().x, chunkIn.getPos().z);

		int xOffset = chunkIn.getPos().getXStart(), zOffset = chunkIn.getPos().getZStart();

		int x = chunkIn.getPos().getXStart();
		int z = chunkIn.getPos().getZStart();

		if(x == 0 && z == 0)
		{
			//chunkIn.getStructureStarts(Feature );

		}

		//this.makeBedrock(chunkIn, sharedRandom); //remove this for a smooth floor
	}
}
