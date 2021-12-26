package com.mraof.minestuck.block.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class PlatformBlock extends Block
{
	public static final BooleanProperty INVISIBLE = BlockStateProperties.ENABLED;
	
	public PlatformBlock(Properties properties)
	{
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(INVISIBLE, false));
	}
	
	/*@Override
	@Override
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) //TODO unsure if occlusion has really changed anything
	{
		return 1.0F;
	}*/
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos)
	{
		return true;
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		if(state.getValue(INVISIBLE))
			return BlockRenderType.INVISIBLE;
		else
			return BlockRenderType.MODEL;
	}
	
	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
	{
		super.tick(state, worldIn, pos, rand);
		worldIn.removeBlock(pos, false);
	}
	
	@Override
	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		worldIn.getBlockTicks().scheduleTick(new BlockPos(pos), this, 10);
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		builder.add(INVISIBLE);
	}
}