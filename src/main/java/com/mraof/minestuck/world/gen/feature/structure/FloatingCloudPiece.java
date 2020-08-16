package com.mraof.minestuck.world.gen.feature.structure;

import com.mraof.minestuck.world.gen.feature.MSStructurePieces;
import com.mraof.minestuck.world.gen.feature.structure.blocks.StructureBlockRegistry;
import com.mraof.minestuck.world.storage.loot.MSLootTables;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class FloatingCloudPiece extends ScatteredStructurePiece
{
	public FloatingCloudPiece(Random random, int minX, int minZ, float skyLight)
	{
		super(MSStructurePieces.FLOATING_CLOUD, random, minX, 100, minZ, 8, 8, 10);
	}

	public FloatingCloudPiece(TemplateManager templates, CompoundNBT nbt)
	{
		super(MSStructurePieces.FLOATING_CLOUD, nbt);
	}
	
	@Override
	public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox boundingBoxIn, ChunkPos chunkPosIn)
	{
		if(!isInsideBounds(worldIn, boundingBoxIn, 0))
			return false;
		
		StructureBlockRegistry blocks = StructureBlockRegistry.getOrDefault(worldIn.getChunkProvider().getChunkGenerator().getSettings());
		BlockState structureBlock = blocks.getBlockState("cloud");
		
		for(int z = 0; z < 8; z++)
			for(int x = 0; x < 7; x++)
				if(x == 0 || x == 6)
				{
				}
		
		for(int x = 1; x < 8; x++)
		{
			buildCloud(structureBlock, x, 8, worldIn, randomIn, boundingBoxIn, 0);
		}
		
		for(int x = 2; x < 8; x++)
		{
			buildCloud(structureBlock, x, 9, worldIn, randomIn, boundingBoxIn, 0);
		}
		return true;
	}
	
	private void buildCloud(BlockState block, int x, int z, IWorld world, Random rand, MutableBoundingBox boundingBox, int minY)
	{
		for(int y = 1; y < 4; y++)
		{
			this.setBlockState(world, block, x, y, z, boundingBox);
		}


	}
}