package com.mraof.minestuck.blockentity.redstone;

import com.mraof.minestuck.block.MSBlocks;
import com.mraof.minestuck.block.redstone.ConwayCellBlock;
import com.mraof.minestuck.blockentity.MSBlockEntityTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConwayGeneratorBlockEntity extends BlockEntity
{
	public static final int GEN_DISTANCE = 16;
	private static final Map<BlockPos, Integer> neighborMap = new HashMap<>();
	
	public ConwayGeneratorBlockEntity(BlockPos pos, BlockState state)
	{
		super(MSBlockEntityTypes.CONWAY_GENERATOR.get(), pos, state);
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, ConwayGeneratorBlockEntity blockEntity)
	{
		//updates only every .25 seconds and then only if the full game board is loaded
		if(level.getGameTime() % 5 != 0 || !level.isAreaLoaded(pos, GEN_DISTANCE))
			return;
		
		blockEntity.updateBoard();
	}
	
	public void updateBoard()
	{
		if(level == null || level.isClientSide)
			return;
		
		neighborMap.clear();
		
		BlockPos genPos = getBlockPos();
		Iterable<BlockPos> allPos = BlockPos.betweenClosed(genPos.offset(GEN_DISTANCE, 1, GEN_DISTANCE), genPos.offset(-GEN_DISTANCE, 1, -GEN_DISTANCE));
		
		//repopulate the map
		allPos.forEach(pos -> {
			neighborMap.put(pos.immutable(), 0);
		});
		
		for(BlockPos iteratePos : allPos)
		{
			BlockState iterateState = level.getBlockState(iteratePos);
			
			if(isCell(iterateState))
			{
				updateNeighbors(iteratePos);
			}
		}
		
		enactRules();
	}
	
	private void updateNeighbors(BlockPos iteratePos)
	{
		for(BlockPos neighborPos : BlockPos.betweenClosed(iteratePos.offset(1, 0, 1), iteratePos.offset(-1, 0, -1)))
		{
			//skip if the neighbor is actually the original block or if the neighbor pos is not in the map (implying it is out of bounds)
			if(neighborPos.equals(iteratePos) || !neighborMap.containsKey(neighborPos))
				continue;
			
			//TODO update regardless?
			neighborMap.replace(neighborPos, neighborMap.get(neighborPos) + 1);
		}
	}
	
	private void enactRules()
	{
		for(Map.Entry<BlockPos, Integer> entry : neighborMap.entrySet())
		{
			BlockPos entryPos = entry.getKey();
			int entryNeighors = entry.getValue();
			BlockState entryState = level.getBlockState(entryPos);
			
			boolean isCell = isCell(entryState);
			
			//will only live if there are 2-3 neighbors
			if(isCell && (entryNeighors < 2 || entryNeighors > 3))
			{
				level.setBlockAndUpdate(entryPos, Blocks.AIR.defaultBlockState());
				continue;
			}
			
			//"dead" cells become live cells if they have 3 living neighbors and is a replaceable blockstate
			if(entryNeighors == 3 && !isCell && entryState.canBeReplaced())
			{
				level.setBlockAndUpdate(entryPos, MSBlocks.CONWAY_CELL.get().defaultBlockState());
			}
		}
	}
	
	private static boolean isCell(BlockState state)
	{
		//TODO consider using block tag
		return state.getBlock() instanceof ConwayCellBlock;
	}
}
