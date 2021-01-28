package com.mraof.minestuck.models.armor.godtier;

import com.mraof.minestuck.player.EnumClass;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class GTLordModel extends GTAbstractModel
{
	
	private final ModelRenderer coatRight;
	private final ModelRenderer coatLeft;
	private final ModelRenderer leftSleeve;
	private final ModelRenderer rightSleeve;
	private final ModelRenderer coat;
	private final ModelRenderer hood;
	private final ModelRenderer suspenders;
	
	public GTLordModel(float size)
	{
		super(size,128, 128, EnumClass.LORD);
		addColorIgnores(4, 7, 8);
		
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10, 1, 10, 0.0F, false);
		head.setTextureOffset(22, 11).addBox(-5.0F, -9.0F, 4.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(0, 20).addBox(-5.0F, -9.0F, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(0, 11).addBox(-5.0F, -9.0F, -5.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(18, 20).addBox(4.0F, -9.0F, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(40, 0).addBox(-5.0F, -10.0F, -4.0F, 10, 1, 8, 0.0F, false);
		head.setTextureOffset(18, 36).addBox(-4.0F, -10.0F, 4.0F, 8, 1, 1, 0.0F, false);
		head.setTextureOffset(0, 36).addBox(-4.0F, -10.0F, -5.0F, 8, 1, 1, 0.0F, false);
		
		hood = new ModelRenderer(this);
		hood.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(hood);
		hood.setTextureOffset(110, 10).addBox(-4.0F, -9.0F, 5.0F, 8, 8, 1, 0.5F, false);
		hood.setTextureOffset(108, 19).addBox(-4.0F, -8.5F, 5.5F, 8, 8, 2, 0.0F, false);
		hood.setTextureOffset(104, 29).addBox(-3.0F, -6.5F, 7.0F, 6, 6, 1, 0.5F, false);
		hood.setTextureOffset(112, 36).addBox(-3.0F, -6.0F, 7.5F, 6, 6, 2, 0.0F, false);
		hood.setTextureOffset(118, 44).addBox(-2.0F, -4.0F, 9.0F, 4, 4, 1, 0.5F, false);
		hood.setTextureOffset(116, 49).addBox(-2.0F, -3.5F, 9.5F, 4, 4, 2, 0.0F, false);
		hood.setTextureOffset(122, 55).addBox(-1.0F, -1.5F, 11.0F, 2, 2, 1, 0.5F, false);
		
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.setTextureOffset(32, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.251F, false);
		
		rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm.setTextureOffset(0, 74).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		leftArm.setTextureOffset(0, 74).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightLeg.setTextureOffset(0, 96).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);

		leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftLeg.setTextureOffset(0, 96).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		rightFoot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		leftFoot.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		coatRight = new ModelRenderer(this);
		coatRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
		coatRight.setTextureOffset(96, 93).addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.5F, true);
		
		coatLeft = new ModelRenderer(this);
		coatLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
		coatLeft.setTextureOffset(112, 93).addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.5F, true);
		
		rightSleeve = new ModelRenderer(this);
		rightSleeve.setRotationPoint(-5.5F, 2.0F, 0.0F);
		rightSleeve.setTextureOffset(84, 115).addBox(-3.5F, -2.0F, -2.0F, 4, 9, 4, 0.5F, true);
		rightSleeve.setTextureOffset(84, 110).addBox(-3.5F, 7.0F, -2.0F, 4, 1, 4, 0.75F, true);
		
		leftSleeve = new ModelRenderer(this);
		leftSleeve.setRotationPoint(5.5F, 2.0F, 0.0F);
		leftSleeve.setTextureOffset(84, 115).addBox(-0.5F, -2.0F, -2.0F, 4, 9, 4, 0.5F, false);
		leftSleeve.setTextureOffset(84, 110).addBox(-0.5F, 7.0F, -2.0F, 4, 1, 4, 0.75F, false);
		
		coat = new ModelRenderer(this);
		coat.setRotationPoint(0.0F, 0.0F, 0.0F);
		coat.setTextureOffset(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.5F, false);
		
		suspenders = new ModelRenderer(this);
		suspenders.setRotationPoint(0.0F, 0.0F, 0.0F);
		suspenders.setTextureOffset(56, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.375F, false);
	}
	
	@Override
	public void addExtraInfo(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot)
	{
		coat.showModel = armorSlot == EquipmentSlotType.CHEST && !hideExtras;
		suspenders.showModel = armorSlot == EquipmentSlotType.LEGS && !hideExtras;
		coatLeft.showModel = armorSlot == EquipmentSlotType.CHEST && !hideExtras;
		coatRight.showModel = armorSlot == EquipmentSlotType.CHEST && !hideExtras;
		leftSleeve.showModel = armorSlot == EquipmentSlotType.CHEST && !hideExtras;
		rightSleeve.showModel = armorSlot == EquipmentSlotType.CHEST && !hideExtras;
	}
	
	@Override
	protected void renderExtras(float scale)
	{
		coatRight.render(scale);
		coatLeft.render(scale);
		coat.render(scale);
		leftSleeve.render(scale);
		rightSleeve.render(scale);
		suspenders.render(scale);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		
		copyModelAngles(torso, suspenders);
		copyModelAngles(torso, coat);
		copyModelAngles(leftArm, leftSleeve);
		copyModelAngles(rightArm, rightSleeve);
		copyModelAngles(leftLeg, coatLeft);
		copyModelAngles(rightLeg, coatRight);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}