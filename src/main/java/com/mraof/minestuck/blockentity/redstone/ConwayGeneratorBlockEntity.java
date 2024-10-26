package com.mraof.minestuck.blockentity.redstone;

import com.mojang.datafixers.util.Pair;
import com.mraof.minestuck.block.MSBlocks;
import com.mraof.minestuck.block.redstone.ConwayCellBlock;
import com.mraof.minestuck.block.redstone.ConwayGeneratorBlock;
import com.mraof.minestuck.blockentity.MSBlockEntityTypes;
import com.mraof.minestuck.network.block.ConwayGeneratorSettingsPacket;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConwayGeneratorBlockEntity extends BlockEntity
{
	public static final int GEN_DISTANCE = 16;
	private static final Map<BlockPos, Integer> neighborMap = new HashMap<>();
	private static final Map<Pair<Integer, Integer>, Boolean> startConfiguration = new HashMap<>();
	
	public ConwayGeneratorBlockEntity(BlockPos pos, BlockState state)
	{
		super(MSBlockEntityTypes.CONWAY_GENERATOR.get(), pos, state);
		
		startConfiguration.clear();
		
		for(int x = -GEN_DISTANCE; x < GEN_DISTANCE; x++)
		{
			for(int y = -GEN_DISTANCE; y < GEN_DISTANCE; y++)
			{
				startConfiguration.put(Pair.of(x, y), false);
			}
		}
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, ConwayGeneratorBlockEntity blockEntity)
	{
		//updates only every .25 seconds, the block is not power, and only if the full game board is loaded
		if(level.getGameTime() % 5 != 0 || state.getValue(ConwayGeneratorBlock.POWERED) || !level.isAreaLoaded(pos, GEN_DISTANCE))
			return;
		
		blockEntity.updateBoard();
	}
	
	public void updateBoard()
	{
		if(level == null || level.isClientSide)
			return;
		
		neighborMap.clear();
		
		Iterable<BlockPos> allPos = getAllPos();
		
		//repopulate the map
		allPos.forEach(pos -> neighborMap.put(pos.immutable(), 0));
		
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
	
	private Iterable<BlockPos> getAllPos()
	{
		BlockPos genPos = getBlockPos();
		return BlockPos.betweenClosed(genPos.offset(GEN_DISTANCE, 1, GEN_DISTANCE), genPos.offset(-GEN_DISTANCE, 1, -GEN_DISTANCE));
	}
	
	private void updateNeighbors(BlockPos iteratePos)
	{
		//TODO consider allowing for seamless connection of connected generators
		for(BlockPos neighborPos : BlockPos.betweenClosed(iteratePos.offset(1, 0, 1), iteratePos.offset(-1, 0, -1)))
		{
			//skip if the neighbor is actually the original block or if the neighbor pos is not in the map (implying it is out of bounds)
			if(neighborPos.equals(iteratePos) || !neighborMap.containsKey(neighborPos))
				continue;
			
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
	
	public Map<Pair<Integer, Integer>, Boolean> getStartConfiguration()
	{
		return startConfiguration;
	}
	
	public void handleSettingsPacket(ConwayGeneratorSettingsPacket packet)
	{
		Objects.requireNonNull(this.level);
		
		startConfiguration.clear();
		startConfiguration.putAll(packet.startConfiguration());
		setChanged();
	}
	
	public void resetBoard()
	{
		if(level == null || level.isClientSide())
			return;
		
		getAllPos().forEach(pos -> {
			BlockState state = level.getBlockState(pos);
			
			if(isCell(state))
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		});
		
		for(Map.Entry<Pair<Integer, Integer>, Boolean> entry : startConfiguration.entrySet())
		{
			boolean isLive = entry.getValue();
			
			if(!isLive)
				continue;
			
			int entryXOffset = entry.getKey().getFirst();
			int entryYOffset = entry.getKey().getSecond();
			
			BlockPos entryPos = getBlockPos().offset(entryXOffset, 1, entryYOffset);
			
			BlockState entryState = level.getBlockState(entryPos);
			
			if(entryState.canBeReplaced())
				level.setBlockAndUpdate(entryPos, MSBlocks.CONWAY_CELL.get().defaultBlockState());
		}
	}
	
	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		
		ListTag list = compound.getList("startConfiguration", Tag.TAG_COMPOUND);
		for(int i = 0; i < list.size(); i++)
		{
			CompoundTag entryNbt = list.getCompound(i);
			if(entryNbt.contains("xCoord") && entryNbt.contains("yCoord") && entryNbt.contains("live"))
				startConfiguration.put(Pair.of(entryNbt.getInt("xCoord"), entryNbt.getInt("yCoord")), entryNbt.getBoolean("live"));
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		
		ListTag configTag = new ListTag();
		
		for(Map.Entry<Pair<Integer, Integer>, Boolean> entry : startConfiguration.entrySet())
		{
			CompoundTag entryNbt = new CompoundTag();
			
			entryNbt.putInt("xCoord", entry.getKey().getFirst());
			entryNbt.putInt("yCoord", entry.getKey().getSecond());
			entryNbt.putBoolean("live", entry.getValue());
			
			configTag.add(entryNbt);
		}
		
		compound.put("startConfiguration", configTag);
	}
	
	@Override
	public CompoundTag getUpdateTag()
	{
		return this.saveWithoutMetadata();
	}
	
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket()
	{
		return ClientboundBlockEntityDataPacket.create(this);
	}
}
