package com.mraof.minestuck.models.armor.godtier;

import com.mraof.minestuck.player.EnumClass;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class GTSeerModel extends GTAbstractModel
{
	
	public GTSeerModel(float size)
	{
		super(size,128, 128, EnumClass.SEER);
		addColorIgnores(6, 7, 8);
		
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.setTextureOffset(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.2515F, false);
		
		skirtBack.setRotationPoint(0.0F, 10.0F, 2.252F);
		skirtBack.setTextureOffset(59, 115).addBox(-4.0F, 0.0F, 0.1F, 8, 12, 0, 0.0F, false);
		
		skirtFront.setRotationPoint(0.0F, 10.0F, -2.252F);
		skirtFront.setTextureOffset(75, 115).addBox(-4.0F, 0.0F, -0F, 8, 12, 0, 0.0F, false);
		
		rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm.setTextureOffset(16, 56).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		leftArm.setTextureOffset(0, 56).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightLeg.setTextureOffset(0, 96).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftLeg.setTextureOffset(0, 80).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		rightFoot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		leftFoot.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10, 1, 10, 0.0F, false);
		head.setTextureOffset(22, 11).addBox(-5.0F, -9.0F, 4.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(0, 20).addBox(-5.0F, -9.0F, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(0, 11).addBox(-5.0F, -9.0F, -5.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(18, 20).addBox(4.0F, -9.0F, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(40, 0).addBox(-5.0F, -10.0F, -4.0F, 10, 1, 8, 0.0F, false);
		head.setTextureOffset(0, 36).addBox(-4.0F, -10.0F, 4.0F, 8, 1, 1, 0.0F, false);
		head.setTextureOffset(18, 36).addBox(-4.0F, -10.0F, -5.0F, 8, 1, 1, 0.0F, false);
		
		neck.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.setTextureOffset(84, 0).addBox(-5.0F, 0.0F, -3.0F, 10, 2, 1, 0.0F, false);
		neck.setTextureOffset(106, 0).addBox(-5.0F, 0.0F, 2.0F, 10, 2, 1, 0.0F, false);
		
		cape.setRotationPoint(0.0F, 0.0F, 0.0F);
		cape.setTextureOffset(110, 102).addBox(-4.0F, 1.0F, 2.0F, 8, 7, 1, 0.0F, false);
		
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public EquipmentSlotType getSkirtSlot()
	{
		return EquipmentSlotType.CHEST;
	}
}