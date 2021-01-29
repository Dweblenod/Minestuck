package com.mraof.minestuck.models.armor.godtier;

import com.mraof.minestuck.player.EnumClass;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class GTMageModel extends GTAbstractModel
{
	
	private final ModelRenderer hood;
	
	public GTMageModel(float size)
	{
		super(size,128, 128, EnumClass.MAGE);
		addColorIgnores(6, 7, 8);
		
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
		hood.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.addChild(hood);
		hood.setTextureOffset(110, 8).addBox(-4.0F, -32.0F, 4.5F, 8, 7, 1, 0.5F, false);
		hood.setTextureOffset(108, 16).addBox(-4.0F, -32.0F, 5.0F, 8, 7, 2, 0.0F, false);
		hood.setTextureOffset(118, 25).addBox(-2.0F, -30.0F, 6.0F, 4, 4, 1, 1.0F, false);
		hood.setTextureOffset(116, 30).addBox(-2.0F, -30.0F, 7.0F, 4, 4, 2, 0.0F, false);
		hood.setTextureOffset(122, 36).addBox(-1.0F, -28.0F, 8.5F, 2, 2, 1, 0.0F, false);
		hood.setTextureOffset(124, 39).addBox(-0.5F, -27.5F, 9.0F, 1, 1, 1, 0.0F, false);
		
		neck.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.setTextureOffset(84, 0).addBox(-5.0F, 0.0F, -3.0F, 10, 1, 1, 0.0F, false);
		neck.setTextureOffset(106, 0).addBox(-5.0F, 0.0F, 2.0F, 10, 1, 1, 0.0F, false);
		neck.setTextureOffset(94, 96).addBox(-4.0F, -0.1F, -2.0F, 8, 12, 4, 0.252F, false);
		
		cape.setRotationPoint(0.0F, 0.0F, 0.0F);
		cape.setTextureOffset(110, 78).addBox(-4.0F, 1.0F, 2.0F, 8, 8, 1, 0.0F, false);
		cape.setTextureOffset(106, 87).addBox(-5.0F, 9.0F, 2.0F, 10, 7, 1, 0.0F, false);
		cape.setTextureOffset(118, 95).addBox(-5.0F, 16.0F, 2.0F, 4, 2, 1, 0.0F, false);
		cape.setTextureOffset(118, 95).addBox(1.0F, 16.0F, 2.0F, 4, 2, 1, 0.0F, false);
		cape.setTextureOffset(120, 98).addBox(-5.0F, 18.0F, 2.0F, 3, 2, 1, 0.0F, false);
		cape.setTextureOffset(120, 98).addBox(2.0F, 18.0F, 2.0F, 3, 2, 1, 0.0F, false);
		cape.setTextureOffset(122, 101).addBox(-5.0F, 20.0F, 2.0F, 2, 2, 1, 0.0F, false);
		cape.setTextureOffset(122, 101).addBox(3.0F, 20.0F, 2.0F, 2, 2, 1, 0.0F, false);
		cape.setTextureOffset(124, 104).addBox(-5.0F, 22.0F, 2.0F, 1, 1, 1, 0.0F, false);
		cape.setTextureOffset(124, 104).addBox(4.0F, 22.0F, 2.0F, 1, 1, 1, 0.0F, false);
		
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.setTextureOffset(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.251F, false);
		
		rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm.setTextureOffset(0, 74).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		leftArm.setTextureOffset(0, 74).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightLeg.setTextureOffset(0, 96).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2505F, true);
		
		leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftLeg.setTextureOffset(0, 96).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2505F, false);
		
		rightFoot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		leftFoot.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		skirtFront.setRotationPoint(0.0F, 10.0F, -2.252F);
		skirtFront.setTextureOffset(77, 115).addBox(-4.0F, 0.0F, -0F, 8, 7, 0, 0.0F, false);
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