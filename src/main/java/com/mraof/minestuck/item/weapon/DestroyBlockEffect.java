package com.mraof.minestuck.item.weapon;

import com.mraof.minestuck.advancements.MSCriteriaTriggers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public interface DestroyBlockEffect extends WeaponEffect
{
	@Override
	float getValue();
	
	void onDestroyBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity);
	
	record DoubleFarm() implements DestroyBlockEffect
	{
		@Override
		public void onDestroyBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity)
		{
			if((state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state)))
				CropBlock.dropResources(state, level, pos);
		}
		
		@Override
		public float getValue()
		{
			return 0;
		}
	}
	
	record ExtraHarvests(boolean melonOverload, float percentage, int maxBonusItems, Supplier<Item> itemDropped, Supplier<Block> harvestedBlock) implements DestroyBlockEffect
	{
		@Override
		public void onDestroyBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity)
		{
			if(state == harvestedBlock.get().defaultBlockState() && !level.isClientSide)
			{
				int harvestCounter = 0;
				for(float i = entity.getRandom().nextFloat(); i <= percentage && harvestCounter < maxBonusItems; i = entity.getRandom().nextFloat())
				{
					ItemStack harvestItemStack = new ItemStack(itemDropped.get(), 1);
					ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), harvestItemStack);
					level.addFreshEntity(item);
					
					harvestCounter++;
				}
				
				if(melonOverload && harvestCounter >= 9)
				{
					entity.sendSystemMessage(Component.translatable(stack.getDescriptionId() + ".message").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));
					
					entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1800, 3));
					entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800, 3));
					entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 3));
					
					if(entity instanceof ServerPlayer player)
						MSCriteriaTriggers.MELON_OVERLOAD.get().trigger(player);
				}
			}
		}
		
		@Override
		public float getValue()
		{
			return 0;
		}
	}
}