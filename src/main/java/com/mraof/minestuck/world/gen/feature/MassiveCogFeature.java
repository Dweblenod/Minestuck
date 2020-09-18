package com.mraof.minestuck.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import com.mraof.minestuck.Minestuck;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Function;

public class MassiveCogFeature extends Feature<NoFeatureConfig>
{
	private static final ResourceLocation STRUCTURE_SMALL_COG = new ResourceLocation(Minestuck.MOD_ID, "small_cog");
	private static final ResourceLocation STRUCTURE_MASSIVE_COG_1 = new ResourceLocation(Minestuck.MOD_ID, "massive_cog_1");
	private static final ResourceLocation STRUCTURE_MASSIVE_COG_2 = new ResourceLocation(Minestuck.MOD_ID, "massive_cog_2");

	public MassiveCogFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}
	
	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
	{
		Rotation rotation = Rotation.randomRotation(rand);
		TemplateManager templates = ((ServerWorld) worldIn.getWorld()).getSaveHandler().getStructureTemplateManager();
		Template template;
		boolean massive = rand.nextInt(20) == 0;
		if(massive)
			template = templates.getTemplateDefaulted(rand.nextBoolean() ? STRUCTURE_MASSIVE_COG_1 : STRUCTURE_MASSIVE_COG_2);
		else
			template = templates.getTemplateDefaulted(STRUCTURE_SMALL_COG);
		
		PlacementSettings settings = new PlacementSettings().setRotation(rotation).setChunk(new ChunkPos(pos)).setRandom(rand).addProcessor(StructureBlockRegistryProcessor.INSTANCE);
		
		BlockPos size = template.transformedSize(rotation);
		int xOffset = rand.nextInt(17 - size.getX()), zOffset = rand.nextInt(17 - size.getZ());
		
		int yMin = Integer.MAX_VALUE;
		for(BlockPos floorPos : BlockPos.getAllInBoxMutable(0, 0, 0, size.getX(), 0, size.getZ()))
		{
			int y = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.getX() + floorPos.getX() + xOffset, pos.getZ() + floorPos.getZ() + zOffset);
			yMin = Math.min(yMin, y);
		}

		int y = Math.max(0, yMin - rand.nextInt(massive ? 4 : 3));
		
		BlockPos structurePos = template.getZeroPositionWithTransform(new BlockPos(pos.getX() + xOffset, y, pos.getZ() + zOffset), Mirror.NONE, rotation);
		template.addBlocksToWorld(worldIn, structurePos, settings);
		
		return true;
	}
}