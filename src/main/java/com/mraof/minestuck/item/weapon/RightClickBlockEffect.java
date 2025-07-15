package com.mraof.minestuck.item.weapon;

import com.mraof.minestuck.effects.CreativeShockEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public interface RightClickBlockEffect extends WeaponEffect
{
	@Override
	float getValue();
	
	InteractionResult onClick(UseOnContext context);
	
	record PlaceFluid(Supplier<Block> fluidBlock, Holder<Item> otherItem) implements RightClickBlockEffect
	{
		@Override
		public float getValue()
		{
			return 0;
		}
		
		@Override
		public InteractionResult onClick(UseOnContext context)
		{
			Player player = context.getPlayer();
			if(creativeShockImpacted(player))
				return InteractionResult.PASS;
			
			Level level = context.getLevel();
			ItemStack itemStack = context.getItemInHand();
			Direction facing = context.getClickedFace();
			BlockPos pos = context.getClickedPos().relative(facing);
			
			BlockState state = level.getBlockState(pos);
			if(state.getBlock() == Blocks.AIR || state.getBlock() == fluidBlock.get())
			{
				if(!level.isClientSide && player != null)
				{
					level.setBlockAndUpdate(pos, fluidBlock.get().defaultBlockState());
					ItemStack newItem = new ItemStack(otherItem, itemStack.getCount(), itemStack.getComponentsPatch());
					player.setItemInHand(context.getHand(), newItem);
					level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1F, 2F);
					player.getCooldowns().addCooldown(otherItem.value(), 5);
				}
				return InteractionResult.SUCCESS;
			}
			return InteractionResult.PASS;
		}
	}
	
	record ScoopBlock(Supplier<Block> validBlock) implements RightClickBlockEffect
	{
		@Override
		public float getValue()
		{
			return 0;
		}
		
		@Override
		public InteractionResult onClick(UseOnContext context)
		{
			Player player = context.getPlayer();
			if(creativeShockImpacted(player))
				return InteractionResult.PASS;
			
			Level level = context.getLevel();
			BlockPos pos = context.getClickedPos();
			Direction facing = context.getClickedFace();
			boolean inside = context.isInside();
			
			BlockState state = level.getBlockState(pos);
			BlockHitResult blockRayTrace = new BlockHitResult(context.getClickLocation(), facing, pos, inside);
			Item lookedAtBlockItem = state.getCloneItemStack(blockRayTrace, level, pos, player).getItem();
			
			if(state.getBlock() == validBlock.get())
			{
				if(!level.isClientSide)
				{
					if(!player.getInventory().add(new ItemStack(lookedAtBlockItem)))
					{
						player.drop(new ItemStack(lookedAtBlockItem), false);
					}
					context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
					level.setBlockAndUpdate(blockRayTrace.getBlockPos(), Blocks.AIR.defaultBlockState());
				}
				level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1F, 1F);
				
				return InteractionResult.SUCCESS;
			}
			
			return InteractionResult.PASS;
		}
	}
	
	/**
	 * Prevents effect from working if the entity is subject to the effects of creative shock
	 */
	default boolean creativeShockImpacted(Player player)
	{
		return player == null || CreativeShockEffect.doesCreativeShockLimit(player, CreativeShockEffect.LIMIT_BLOCK_PLACEMENT_AND_BREAKING);
	}
}
