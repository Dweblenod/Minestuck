package com.mraof.minestuck.world.lands.title;

import com.mraof.minestuck.player.EnumAspect;
import com.mraof.minestuck.world.biome.LandWrapperBiome;
import com.mraof.minestuck.world.biome.MSBiomes;
import com.mraof.minestuck.world.gen.feature.MSFeatures;
import com.mraof.minestuck.world.gen.feature.structure.blocks.StructureBlockRegistry;
import com.mraof.minestuck.world.lands.terrain.TerrainLandType;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class RabbitsLandType extends TitleLandType
{
	public static final String RABBITS = "minestuck.rabbits";
	public static final String BUNNIES = "minestuck.bunnies";
	
	public RabbitsLandType()
	{
		super(EnumAspect.LIFE);
	}
	
	@Override
	public String[] getNames()
	{
		return new String[] {RABBITS, BUNNIES};
	}
	
	@Override
	public void registerBlocks(StructureBlockRegistry registry)
	{
		registry.setBlockState("structure_wool_2", Blocks.PINK_WOOL.getDefaultState());
		registry.setBlockState("carpet", Blocks.LIGHT_GRAY_CARPET.getDefaultState());
	}
	
	@Override
	public void setBiomeSettings(LandWrapperBiome biome, StructureBlockRegistry blocks)
	{
		if(biome.staticBiome == MSBiomes.LAND_NORMAL)
			biome.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Biome.createDecoratedFeature(MSFeatures.RABBIT_PLACEMENT, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_CHANCE_HEIGHTMAP, new HeightWithChanceConfig(6, 0.2F)));
		if(biome.staticBiome == MSBiomes.LAND_ROUGH)
			biome.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Biome.createDecoratedFeature(MSFeatures.RABBIT_PLACEMENT, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_CHANCE_HEIGHTMAP, new HeightWithChanceConfig(3, 0.2F)));
	}
	
	@Override
	public boolean isAspectCompatible(TerrainLandType aspect)
	{
		StructureBlockRegistry registry = new StructureBlockRegistry();
		aspect.registerBlocks(registry);
		return registry.getBlockState("ocean").getMaterial() != Material.LAVA;
	}
}