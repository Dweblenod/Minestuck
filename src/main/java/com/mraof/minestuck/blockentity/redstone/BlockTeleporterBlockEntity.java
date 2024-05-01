package com.mraof.minestuck.blockentity.redstone;

import com.mraof.minestuck.blockentity.MSBlockEntityTypes;
import com.mraof.minestuck.util.MSRotationUtil;
import com.mraof.minestuck.util.MSSoundEvents;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockTeleporterBlockEntity extends BlockEntity
{
	@Nonnull
	private BlockPos teleportOffset = new BlockPos(0, 0, 0);
	
	public BlockTeleporterBlockEntity(BlockPos pos, BlockState state)
	{
		super(MSBlockEntityTypes.BLOCK_TELEPORTER.get(), pos, state);
	}
	
	public void handleTeleports()
	{
		if(level == null)
			return;
		
		Direction facingDirection = this.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
		BlockPos abovePos = this.getBlockPos().above();
		BlockState aboveState = level.getBlockState(abovePos);
		
		if(aboveState.isAir())
			return;
		
		PushReaction pushReaction = aboveState.getPistonPushReaction();
		
		if(pushReaction == PushReaction.BLOCK || pushReaction == PushReaction.IGNORE)
			return;
		
		BlockPos offsetMod = teleportOffset.rotate(MSRotationUtil.rotationBetween(Direction.EAST, facingDirection));
		BlockPos destinationPos = new BlockPos(this.getBlockPos().offset(offsetMod));
		
		BlockState destinationState = level.getBlockState(destinationPos);
		if(!isReplaceable(destinationState))
			return;
		
		level.playSound(null, destinationPos, MSSoundEvents.TRANSPORTALIZER_TELEPORT.get(), SoundSource.BLOCKS, 1, 1);
		level.setBlock(destinationPos, aboveState, Block.UPDATE_ALL);
		
		level.playSound(null, abovePos, MSSoundEvents.TRANSPORTALIZER_TELEPORT.get(), SoundSource.BLOCKS, 1, 1);
		level.removeBlock(abovePos, true); //TODO consider whether isMoving = true is appropriate
	}
	
	private static boolean isReplaceable(BlockState state)
	{
		return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
	}
	
	@Nonnull
	public BlockPos getTeleportOffset()
	{
		return this.teleportOffset;
	}
	
	public void setTeleportOffset(BlockPos teleportOffset)
	{
		this.teleportOffset = teleportOffset;
	}
	
	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		
		int offsetX = compound.getInt("offsetX");
		int offsetY = compound.getInt("offsetY");
		int offsetZ = compound.getInt("offsetZ");
		this.teleportOffset = clampPos(offsetX, offsetY, offsetZ);
	}
	
	@Override
	public void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		
		compound.putInt("offsetX", teleportOffset.getX());
		compound.putInt("offsetY", teleportOffset.getY());
		compound.putInt("offsetZ", teleportOffset.getZ());
	}
	
	public static BlockPos clampPos(int x, int y, int z)
	{
		return new BlockPos(Math.min(Math.max(x, -32), 32), Math.min(Math.max(y, -32), 32), Math.min(Math.max(z, -32), 32));
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