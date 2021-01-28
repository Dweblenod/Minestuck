package com.mraof.minestuck.item;


import com.mraof.minestuck.player.*;
import com.mraof.minestuck.skaianet.SburbConnection;
import com.mraof.minestuck.skaianet.SkaianetHandler;
import com.mraof.minestuck.util.AspectColorHandler;
import com.mraof.minestuck.world.storage.ClientPlayerData;
import com.mraof.minestuck.world.storage.PlayerSavedData;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class GodTierArmorKitItem extends Item
{
	
	public GodTierArmorKitItem(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if(this.isInGroup(group))
		{
			for(EnumAspect aspect : EnumAspect.values())
				for(EnumClass heroClass : EnumClass.values())
				{
					items.add(generateKit(heroClass, aspect));
				}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		
		String heroClass = TextFormatting.OBFUSCATED + "Class" + TextFormatting.RESET;
		String heroAspect = TextFormatting.OBFUSCATED + "Thing" + TextFormatting.RESET;
		
		CompoundNBT nbt = stack.getOrCreateTag();
		
		if(nbt.contains("class"))
		{
			int c = nbt.getInt("class");
			if(c >= 0 && c < EnumClass.values().length)
				heroClass = EnumClass.getClassFromInt(c).getTranslationKey();
		}
		if(nbt.contains("aspect"))
		{
			int a = nbt.getInt("aspect");
			if(a >= 0 && a < EnumAspect.values().length)
				heroAspect = EnumAspect.getAspectFromInt(a).getTranslationKey();
		}
		
		tooltip.add(new TranslationTextComponent("title.format", new TranslationTextComponent("title.format", heroClass, heroAspect)));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		PlayerIdentifier id = IdentifierHandler.encode(playerIn);
		if(playerIn.getServer() != null){
			SburbConnection c = SkaianetHandler.get(worldIn).getPrimaryConnection(id, true).get();
		}
		ItemStack stack = playerIn.getHeldItem(handIn);
		CompoundNBT nbt = stack.getOrCreateTag();
		if(/*c == null || !c.enteredGame() || */ nbt == null) return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
		
		
		CompoundNBT armorNbt = new CompoundNBT();
		armorNbt.putInt("class", nbt.getInt("class"));
		armorNbt.putInt("aspect", nbt.getInt("aspect"));
		Item[] armor = new Item[] {MSItems.GOD_TIER_HOOD, MSItems.GOD_TIER_SHIRT, MSItems.GOD_TIER_PANTS, MSItems.GOD_TIER_SHOES};
		for(Item i : armor)
		{
			ItemStack armorStack = new ItemStack(i);
			armorStack.setTag(armorNbt);
			giveStackToPlayer(armorStack, playerIn, worldIn);
		}
		stack.shrink(1);
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		
		if(entityIn instanceof PlayerEntity)
		{
			CompoundNBT nbt = stack.getOrCreateTag();
			
			PlayerEntity player = (PlayerEntity) entityIn;
			PlayerIdentifier id = IdentifierHandler.encode(player);
			if(player.getServer() != null){
				Optional<SburbConnection> c = SkaianetHandler.get(worldIn).getPrimaryConnection(id, true);
				if(!c.isPresent() || !c.get().hasEntered()) return;
			}
			
			if(!stack.getOrCreateTag().contains("aspect"))
				nbt.putInt("aspect", PlayerSavedData.get(worldIn).getData(id).getTitle().getHeroAspect().ordinal());
			if(!stack.getOrCreateTag().contains("class"))
				nbt.putInt("class", PlayerSavedData.get(worldIn).getData(id).getTitle().getHeroClass().ordinal());
			
			stack.setTag(nbt);
		}
	}
	
	public static int getColor(ItemStack stack, AspectColorHandler.EnumColor color)
	{
		CompoundNBT nbt = stack.getOrCreateTag();
		if(nbt == null) return color == AspectColorHandler.EnumColor.SYMBOL ? 8355711 : 0;
		
		return AspectColorHandler.getAspectColor(nbt.getInt("aspect"), color);
	}
	
	public void giveStackToPlayer(ItemStack stack, PlayerEntity player, World worldIn)
	{
		if(!player.inventory.addItemStackToInventory(stack))
		{
			BlockPos pos = player.getPosition();
			BlockPos dropPos;
			if(!worldIn.getBlockState(pos).isNormalCube(worldIn, pos))
				dropPos = pos;
			else if(!worldIn.getBlockState(pos.up()).isNormalCube(worldIn, pos))
				dropPos = pos.up();
			else dropPos = pos;
			InventoryHelper.spawnItemStack(worldIn, dropPos.getX(), dropPos.getY(), dropPos.getZ(), stack);
		}
	}
	
	public static ItemStack generateKit(EnumClass heroClass, EnumAspect aspect)
	{
		ItemStack stack = new ItemStack(MSItems.GOD_TIER_ARMOR_KIT);
		
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("aspect", aspect == null ? 12 : aspect.ordinal());
		nbt.putInt("class", heroClass.ordinal());
		stack.setTag(nbt);
		
		return stack;
	}
	
	/*
	public static boolean isAvailable(SburbConnection sburbConnection)
	{
		return MinestuckPlayerData.getTitle(sburbConnection.getClientIdentifier()) != null &&
				sburbConnection.hasEntered() && PlayerSavedData.get(sburbConnection.).getData(sburbConnection.getClientIdentifier()).echeladder.getRung() >= 49;
	}
	*/
	
	public static ItemStack generateKit()
	{
		Title clientTitle = ClientPlayerData.getTitle();
		return generateKit(clientTitle == null ? EnumClass.KNIGHT : clientTitle.getHeroClass(), clientTitle == null ? null : clientTitle.getHeroAspect());
	}
	
}