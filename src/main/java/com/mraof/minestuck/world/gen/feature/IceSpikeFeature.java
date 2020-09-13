package com.mraof.minestuck.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import com.mraof.minestuck.util.CoordPair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.*;
import java.util.function.Function;

public class IceSpikeFeature extends Feature<NoFeatureConfig>
{
	private final boolean stomps = false;
	
	public IceSpikeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}
	
	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
	{
		int height = rand.nextInt(7) + 15;
		
		//if(worldIn.getBlockState(pos.up(height*2/3)).getMaterial().isLiquid())	//At least 1/3rd of the height should be above the liquid surface
			//return false;
		float plateauSize = 0.1F + rand.nextFloat()*(height/25F);
		BlockState ice = generator.getSettings().setDefaultBlock(Blocks.ICE);getBlockState("structure_primary")
		
		BlockPos nodePos = generateIce(pos.up(height), height, plateauSize, worldIn, rand, ice);
		
		float iceRarity = plateauSize + height/15F + rand.nextFloat()*0.5F - 0.5F;
		
		if(iceRarity > 1F)
		{
			generateSubIce(pos, height, plateauSize, worldIn, rand, ice);
			iceRarity -= 1F;
		}
		if(rand.nextFloat() < iceRarity)
			generateSubIce(pos, height, plateauSize, worldIn, rand, ice);
		
		return true;
	}
	
	private void generateSubIce(BlockPos pos, int heightOld, float plateauOld, IWorld world, Random rand, BlockState ice)
	{
		int height = 5 + rand.nextInt((int) ((heightOld - 6)*0.75));
		BlockPos newPos = pos.add(rand.nextInt(10) - 5, 0, rand.nextInt(10) - 5);
		float plateauSize = rand.nextFloat()*plateauOld*0.75F;
		
		generateIce(newPos, height, plateauSize, world, rand, ice);
	}
	
	private BlockPos generateIce(BlockPos icePos, int height, float plateauSize, IWorld world, Random random, BlockState ice)
	{
		float xSlope = random.nextFloat(), zSlope = random.nextFloat();
		
		Map<CoordPair, Integer> heightMap = new HashMap<>();
		Queue<BlockPos> toProcess = new LinkedList<>();
		Map<BlockPos, BlockState> was = new HashMap<>();
		toProcess.add(icePos);
		toProcess.add(null);
		
		while(!toProcess.isEmpty())	//place the top layer of blocks
		{
			BlockPos pos = toProcess.remove();
			if(pos == null)
				if(toProcess.isEmpty())
					break;
				else
				{
					plateauSize -= 0.25;
					toProcess.add(null);
					continue;
				}

			if(random.nextFloat()*xSlope < plateauSize)
				toProcess.add(pos.west());
			if(random.nextFloat()*(1F - xSlope) < plateauSize)
				toProcess.add(pos.east());
			if(random.nextFloat()*zSlope < plateauSize)
				toProcess.add(pos.north());
			if(random.nextFloat()*(1F - zSlope) < plateauSize)
				toProcess.add(pos.south());
			
			CoordPair xz = new CoordPair(pos.getX(), pos.getZ());
			if(!heightMap.containsKey(xz))
				heightMap.put(xz, pos.getY());
		}
		
		Queue<BlockEntry> toProcess2 = new LinkedList<>();
		int h = 1;
		
		for(CoordPair coord : heightMap.keySet())
			if(checkCoord(coord, heightMap) && !stomps)
				toProcess2.add(new BlockEntry(coord, plateauSize + 0.2F));
		toProcess2.add(null);
		
		while(!toProcess2.isEmpty() && !stomps)
		{
			BlockEntry entry = toProcess2.remove();
			if(entry == null)
			{
				h++;
				if(toProcess2.isEmpty())
					break;
				toProcess2.add(null);
				continue;
			}
			
			if(!checkCoord(entry.pos, heightMap))
				continue;
			if(random.nextFloat() < entry.spreadChance)
			{
				CoordPair coord;
				if(random.nextBoolean())
					coord = random.nextFloat() < xSlope ? entry.pos.east() : entry.pos.west();
				else coord = random.nextFloat() < zSlope ? entry.pos.south() : entry.pos.north();
				entry.spreadChance -= Math.min(0.5F, (2F*h)/height);
				if(!heightMap.containsKey(coord))
				{
					BlockPos pos = new BlockPos(coord.x, icePos.getY() - h, coord.z);
					heightMap.put(coord, icePos.getY() - h);
					if(checkCoord(coord, heightMap))
						toProcess2.add(new BlockEntry(coord, entry.spreadChance));
				}
			} else entry.spreadChance += 0.5F;
			
			if(!world.getBlockState(new BlockPos(entry.pos.x, icePos.getY() - h - 1, entry.pos.z)).equals(ice))
				toProcess2.add(entry);
		}
		
		for(Map.Entry<CoordPair, Integer> entry : heightMap.entrySet())
		{
			BlockPos pos = new BlockPos(entry.getKey().x, entry.getValue(), entry.getKey().z);
			do
			{
				was.put(pos, world.getBlockState(pos));
				world.setBlockState(pos, ice, 2);
				pos = pos.down();
			} while(!world.getBlockState(pos).equals(ice));
		}
		
		CoordPair nodePos = new CoordPair(icePos.getX(), icePos.getZ());
		int maxBlocks = 0;
		for(int i = 0; i < 9 && !stomps; i++)
		{
			CoordPair coords = new CoordPair(icePos.getX() + (i % 3) - 1, icePos.getZ() + i/3 - 1);
			int blockCount = 0;
			for(int i1 = 0; i1 < 4; i1++)
			{
				Integer coordsHeight = heightMap.get(new CoordPair(coords.x + (i1 % 2), coords.z + i1/2));
				if(coordsHeight != null && coordsHeight == icePos.getY())
					blockCount++;
			}
			if(blockCount > maxBlocks)
			{
				nodePos = coords;
				maxBlocks = blockCount;
			}
		}
		
		BlockPos corePosition = new BlockPos(nodePos.x, icePos.getY() + 1, nodePos.z);
		
		if(stomps)
		{
			was.forEach((t, u) -> world.setBlockState(t, u, 2));
		}
		
		return corePosition;
	}
	
	private static boolean checkCoord(CoordPair pair, Map<CoordPair, Integer> map)
	{
		return !map.containsKey(pair.north()) || !map.containsKey(pair.east()) || !map.containsKey(pair.south()) || !map.containsKey(pair.west());
	}
	
	private static class BlockEntry
	{
		private final CoordPair pos;
		private float spreadChance;
		private BlockEntry(CoordPair pos, float spreadChance)
		{
			this.pos = pos;
			this.spreadChance = spreadChance;
		}
	}
}