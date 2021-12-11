package com.mraof.minestuck.block.redstone;

import com.mraof.minestuck.util.ParticlesAroundSolidBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class TimedSolidSwitchBlock extends Block
{
	public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;
	
	private final int tickRate;
	
	public TimedSolidSwitchBlock(Properties properties, int tickRate)
	{
		super(properties);
		this.tickRate = tickRate;
		this.setDefaultState(this.stateContainer.getBaseState().with(POWER, 0));
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		if(!player.isSneaking())
		{
			worldIn.setBlockState(pos, state.with(POWER, state.get(POWER) == 0 ? 15 : 0));
			if(state.get(POWER) > 0)
			{
				worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, 1.2F);
				worldIn.getPendingBlockTicks().scheduleTick(new BlockPos(pos), this, tickRate);
			} else
			{
				worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, 1.2F);
				worldIn.getPendingBlockTicks().scheduleTick(new BlockPos(pos), this, tickRate);
			}
			
			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.PASS;
	}
	
	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
	{
		super.tick(state, worldIn, pos, rand);
		
		if(state.get(POWER) >= 1)
		{
			worldIn.setBlockState(pos, state.with(POWER, state.get(POWER) - 1));
			worldIn.getPendingBlockTicks().scheduleTick(new BlockPos(pos), this, tickRate);
			
			if(worldIn.getBlockState(pos).get(POWER) == 0)
				worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, 1.2F);
			else
				worldIn.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 0.5F, 1.2F);
		}
	}
	
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
		worldIn.getPendingBlockTicks().scheduleTick(new BlockPos(pos), this, tickRate);
	}
	
	@Override
	public boolean canProvidePower(BlockState state)
	{
		return state.get(POWER) != 0;
	}
	
	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
	{
		return blockState.get(POWER);
	}
	
	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side)
	{
		return true;
	}
	
	@Override
	public int getLightValue(BlockState state)
	{
		return state.get(POWER);
	}
	
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(stateIn.get(POWER) > 0)
			ParticlesAroundSolidBlock.spawnParticles(worldIn, pos, () -> RedstoneParticleData.REDSTONE_DUST);
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		super.fillStateContainer(builder);
		builder.add(POWER);
	}
}