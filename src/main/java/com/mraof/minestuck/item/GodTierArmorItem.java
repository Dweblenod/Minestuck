package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.models.armor.GTAbstractModel;
import com.mraof.minestuck.player.EnumAspect;
import com.mraof.minestuck.player.EnumClass;
import com.mraof.minestuck.util.GodTierArmorModels;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GodTierArmorItem extends ArmorItem
{
	public GodTierArmorItem(IArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn, Properties properties)
	{
		super(materialIn, equipmentSlotIn, properties);
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack)
	{
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
		
		return new TranslationTextComponent(this.getTranslationKey(stack), new TranslationTextComponent("title.format", heroClass), new TranslationTextComponent("title.format", heroAspect));
	}
	
	
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(getHideExtras(stack))
		{
			if((getHeroClass(stack) == (EnumClass.ROGUE) && getType().equals("hood")))
				tooltip.add(new TranslationTextComponent("item.gtHood.hiddenExtras.rogue"));
			if((getHeroClass(stack) == (EnumClass.LORD) && getType().equals("shirt")))
				tooltip.add(new TranslationTextComponent("item.gtHood.hiddenExtras.rogue"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		EnumClass heroClass = getHeroClass(stack);
		
		if(heroClass == null)
			return super.getArmorTexture(stack, entity, slot, type);
		
		return Minestuck.MOD_ID + ":textures/models/armor/gt_" + heroClass.toString() + ".png";
	}
	
	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, BipedModel _default)
	{
		EnumClass heroClass = getHeroClass(stack);
		EnumAspect heroAspect = getHeroAspect(stack);
		
		if(heroClass == null)
			return super.getArmorModel(entityLiving, stack, armorSlot, _default);
		
		GTAbstractModel model = GodTierArmorModels.models.get(heroClass);
		
		model.heroAspect = heroAspect;
		
		model.head.showModel = armorSlot == EquipmentSlotType.HEAD;
		model.neck.showModel = armorSlot == EquipmentSlotType.HEAD;
		
		model.symbol.showModel = armorSlot == EquipmentSlotType.CHEST;
		model.torso.showModel = armorSlot == EquipmentSlotType.CHEST;
		model.leftArm.showModel = armorSlot == EquipmentSlotType.CHEST;
		model.rightArm.showModel = armorSlot == EquipmentSlotType.CHEST;
		model.cape.showModel = armorSlot == model.getCapeSlot();
		
		model.skirtFront.showModel = armorSlot == model.getSkirtSlot();
		model.skirtMiddle.showModel = model.skirtFront.showModel;
		model.skirtBack.showModel = model.skirtFront.showModel;
		
		model.belt.showModel = armorSlot == EquipmentSlotType.LEGS;
		model.leftLeg.showModel = armorSlot == EquipmentSlotType.LEGS;
		model.rightLeg.showModel = armorSlot == EquipmentSlotType.LEGS;
		
		model.leftFoot.showModel = armorSlot == EquipmentSlotType.FEET;
		model.rightFoot.showModel = armorSlot == EquipmentSlotType.FEET;
		
		model.isSneak = _default.isSneak;
		model.isSitting = _default.isSitting;
		model.isChild = _default.isChild;
		
		model.rightArmPose = _default.rightArmPose;
		model.leftArmPose = _default.leftArmPose;
		
		model.hideExtras = getHideExtras(stack);
		
		model.addExtraInfo(entityLiving, stack, armorSlot);
		
		return model;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if(playerIn.isSneaking() && ((getHeroClass(stack) == (EnumClass.ROGUE) && getType().equals("hood")) || (getHeroClass(stack) == (EnumClass.LORD) && getType().equals("shirt"))))
		{
			setHideExtras(stack, !getHideExtras(stack));
			return new ActionResult<>(ActionResultType.SUCCESS, stack);
		}
		else return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public String getType()
	{
		return this.equals(MSItems.GOD_TIER_HOOD) ? "hood" : this.equals(MSItems.GOD_TIER_SHIRT) ? "shirt" : this.equals(MSItems.GOD_TIER_PANTS) ? "pants" : "shoes";
	}
	
	public static EnumClass getHeroClass(ItemStack stack)
	{
		CompoundNBT nbt = stack.getOrCreateTag();
		if(!nbt.contains("class"))
			return null;
		
		int c = nbt.getInt("class");
		if(c >= 0 && c < EnumClass.values().length)
			return EnumClass.getClassFromInt(c);
		return null;
	}
	
	public static boolean getHideExtras(ItemStack stack)
	{
		CompoundNBT nbt = stack.getOrCreateTag();
		if(nbt == null || !nbt.contains("hideExtras"))
			return false;
		
		return nbt.getBoolean("hideExtras");
	}
	public static void setHideExtras(ItemStack stack, boolean v)
	{
		CompoundNBT nbt = stack.getOrCreateTag();
		
		nbt.putBoolean("hideExtras", v);
	}
	
	public static EnumAspect getHeroAspect(ItemStack stack)
	{
		CompoundNBT nbt = stack.getOrCreateTag();
		if(nbt == null || !nbt.contains("aspect"))
			return null;
		
		int a = nbt.getInt("aspect");
		if(a >= 0 && a < EnumAspect.values().length)
			return EnumAspect.getAspectFromInt(a);
		return null;
	}
}