package com.mraof.minestuck.item.weapon;

import com.google.common.collect.ImmutableList;
import com.mraof.minestuck.item.MSItems;
import com.mraof.minestuck.util.MSSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class FluoriteWeaponItem extends WeaponItem
{
	public FluoriteWeaponItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, float efficiency, MSToolType toolType, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, efficiency, toolType, builder);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		if(playerIn.isSneaking())
		{
			ItemStack newItem = new ItemStack(MSItems.FLUORITE_OCTET, itemStackIn.getCount());
			newItem.setTag(itemStackIn.getTag());

			return new ActionResult<>(ActionResultType.SUCCESS, newItem);
		}
		return new ActionResult<>(ActionResultType.PASS, itemStackIn);
	}
	
	@Override
	public boolean hitEntity(ItemStack itemStack, LivingEntity target, LivingEntity attacker)
	{
		int random1d8 = attacker.getRNG().nextInt(8) + 1;
		ITextComponent message = new TranslationTextComponent(getTranslationKey()+".message."+random1d8);
		message.getStyle().setColor(TextFormatting.DARK_AQUA);
		attacker.sendMessage(message);

		if(!attacker.getEntityWorld().isRemote && random1d8 == 1)
		{
			ItemStack itemStackNew = new ItemStack(MSItems.FLUORITE_WEASEL, 1);
			itemStackNew.setTag(itemStack.getTag());
			if(!attacker.world.isRemote)
			{
				ItemEntity item = new ItemEntity(attacker.world, attacker.posX, attacker.posY, attacker.posZ, itemStackNew);
				attacker.world.addEntity(item);
				item.setPickupDelay(0);
			}
		}
		if(!attacker.getEntityWorld().isRemote && random1d8 == 2)
		{
			ItemStack itemStackNew = new ItemStack(MSItems.FLUORITE_MUSCLEBEAST, 1);
			itemStackNew.setTag(itemStack.getTag());
			if(!attacker.world.isRemote)
			{
				ItemEntity item = new ItemEntity(attacker.world, attacker.posX, attacker.posY, attacker.posZ, itemStackNew);
				attacker.world.addEntity(item);
				item.setPickupDelay(0);
			}
		}
		if(!attacker.getEntityWorld().isRemote && random1d8 == 3)
		{
			ItemStack itemStackNew = new ItemStack(MSItems.FLUORITE_GARROTE, 1);
			itemStackNew.setTag(itemStack.getTag());
			if(!attacker.world.isRemote)
			{
				ItemEntity item = new ItemEntity(attacker.world, attacker.posX, attacker.posY, attacker.posZ, itemStackNew);
				attacker.world.addEntity(item);
				item.setPickupDelay(0);
			}
		}
		if(!attacker.getEntityWorld().isRemote && random1d8 == 4)
		{
			ItemStack itemStackNew = new ItemStack(MSItems.FLUORITE_KNUCKLES, 1);
			itemStackNew.setTag(itemStack.getTag());
			if(!attacker.world.isRemote)
			{
				ItemEntity item = new ItemEntity(attacker.world, attacker.posX, attacker.posY, attacker.posZ, itemStackNew);
				attacker.world.addEntity(item);
				item.setPickupDelay(0);
			}
		}
		if(!attacker.getEntityWorld().isRemote && random1d8 == 5)
		{
			ItemStack itemStackNew = new ItemStack(MSItems.FLUORITE_KNIFE, 1);
			itemStackNew.setTag(itemStack.getTag());
			if(!attacker.world.isRemote)
			{
				ItemEntity item = new ItemEntity(attacker.world, attacker.posX, attacker.posY, attacker.posZ, itemStackNew);
				attacker.world.addEntity(item);
				item.setPickupDelay(0);
			}
		}
		if(!attacker.getEntityWorld().isRemote && random1d8 == 6)
		{
			ItemStack itemStackNew = new ItemStack(MSItems.FLUORITE_MACE, 1);
			itemStackNew.setTag(itemStack.getTag());
			if(!attacker.world.isRemote)
			{
				ItemEntity item = new ItemEntity(attacker.world, attacker.posX, attacker.posY, attacker.posZ, itemStackNew);
				attacker.world.addEntity(item);
				item.setPickupDelay(0);
			}
		}
		if(!attacker.getEntityWorld().isRemote && random1d8 == 7)
		{
			ItemStack itemStackNew = new ItemStack(MSItems.FLUORITE_GUILLOTINE, 1);
			itemStackNew.setTag(itemStack.getTag());
			if(!attacker.world.isRemote)
			{
				ItemEntity item = new ItemEntity(attacker.world, attacker.posX, attacker.posY, attacker.posZ, itemStackNew);
				attacker.world.addEntity(item);
				item.setPickupDelay(0);
			}
		}
		if(!attacker.getEntityWorld().isRemote && random1d8 == 8)
		{
			ItemStack itemStackNew = new ItemStack(MSItems.FLUORITE_SWORD, 1);
			itemStackNew.setTag(itemStack.getTag());
			if(!attacker.world.isRemote)
			{
				ItemEntity item = new ItemEntity(attacker.world, attacker.posX, attacker.posY, attacker.posZ, itemStackNew);
				attacker.world.addEntity(item);
				item.setPickupDelay(0);
			}
		}
		itemStack.damageItem(1, attacker, entity -> entity.sendBreakAnimation(Hand.MAIN_HAND));
		int damage = itemStack.getDamage();
		if(damage != 600) {
			itemStack.shrink(1);
		}
		//ItemStack itemStackOut = attacker.getHeldItem(Hand.MAIN_HAND);
		//return super.hitEntity(itemStackOut, target, attacker);
		return super.hitEntity(itemStack, target, attacker);
	}
}