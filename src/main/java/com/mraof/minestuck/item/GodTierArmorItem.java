package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.models.armor.GTAbstractModel;
import com.mraof.minestuck.models.armor.GodTierAbstractModel;
import com.mraof.minestuck.player.EnumAspect;
import com.mraof.minestuck.player.EnumClass;
import com.mraof.minestuck.util.GodTierArmorModels;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GodTierArmorItem extends ArmorItem
{
	public GodTierArmorItem(IArmorMaterial materialIn, int renderIndexIn, EquipmentSlotType equipmentSlotIn)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String heroClass = TextFormatting.OBFUSCATED + "Class" + TextFormatting.RESET;
		String heroAspect = TextFormatting.OBFUSCATED + "Thing" + TextFormatting.RESET;
		
		if(stack.hasTagCompound())
		{
			NBTTagCompound nbt = stack.getTagCompound();
			
			if(nbt.hasKey("class"))
			{
				int c = nbt.getInteger("class");
				if(c >= 0 && c < EnumClass.values().length)
					heroClass = EnumClass.getClassFromInt(c).getDisplayName();
			}
			if(nbt.hasKey("aspect"))
			{
				int a = nbt.getInteger("aspect");
				if(a >= 0 && a < EnumAspect.values().length)
					heroAspect = EnumAspect.getAspectFromInt(a).getDisplayName();
			}
		}
		return I18n.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", I18n.translateToLocalFormatted("title.format", heroClass, heroAspect)).trim();
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(getHideExtras(stack))
		{
			if((getHeroClass(stack) == (EnumClass.ROGUE) && getType().equals("hood")))
				tooltip.add(I18n.translateToLocal("item.gtHood.hiddenExtras.rogue"));
			if((getHeroClass(stack) == (EnumClass.LORD) && getType().equals("shirt")))
				tooltip.add(I18n.translateToLocal("item.gtShirt.hiddenExtras.lord"));
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
	public BipedModel getArmorModel(EntityLivingBase entityLiving, ItemStack stack, EquipmentSlotType armorSlot, BipedModel _default)
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
		return this.equals(gtHood) ? "hood" : this.equals(gtShirt) ? "shirt" : this.equals(gtPants) ? "pants" : "shoes";
	}
	
	public static EnumClass getHeroClass(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null || !nbt.hasKey("class"))
			return null;
		
		int c = nbt.getInteger("class");
		if(c >= 0 && c < EnumClass.values().length)
			return EnumClass.getClassFromInt(c);
		return null;
	}
	
	public static boolean getHideExtras(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null || !nbt.hasKey("hideExtras"))
			return false;
		
		return nbt.getBoolean("hideExtras");
	}
	public static void setHideExtras(ItemStack stack, boolean v)
	{
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = stack.getTagCompound();
		
		nbt.setBoolean("hideExtras", v);
	}
	
	public static EnumAspect getHeroAspect(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null || !nbt.hasKey("aspect"))
			return null;
		
		int a = nbt.getInteger("aspect");
		if(a >= 0 && a < EnumAspect.values().length)
			return EnumAspect.getAspectFromInt(a);
		return null;
	}
}