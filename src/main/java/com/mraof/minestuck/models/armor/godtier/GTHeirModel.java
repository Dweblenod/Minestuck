package com.mraof.minestuck.models.armor.godtier;

import com.mraof.minestuck.player.EnumClass;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class GTHeirModel extends GTAbstractModel
{
	
	private final ModelRenderer hood;
	private final ModelRenderer bone;
	
	public GTHeirModel(float size)
	{
		super(size,128, 128, EnumClass.HEIR);
		addColorIgnores(4, 6, 7, 8);
		
		torso.setRotationPoint(0.0F, 0, 0.0F);
		torso.setTextureOffset(90, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.2511F, false);
		
		neck.setRotationPoint(0.0F, 24.0F-24, 0.0F);
		neck.setTextureOffset(106, 0).addBox(-5.0F, -24.0F+24, -3.0F, 10, 1, 1, 0.0F, false);
		neck.setTextureOffset(84, 0).addBox(-5.0F, -24.0F+24, 2.0F, 10, 1, 1, 0.0F, false);
		neck.setTextureOffset(66, 112).addBox(-4.0F, -0.1F, -2.0F, 8, 12, 4, 0.2515F, false);
		
		leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		leftArm.setTextureOffset(0, 60).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm.setTextureOffset(16, 60).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-5.0F, -25.0F+24, -5.0F, 10, 1, 10, 0.0F, false);
		head.setTextureOffset(22, 11).addBox(-5.0F, -33.0F+24, 4.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(0, 11).addBox(-5.0F, -33.0F+24, -5.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(0, 20).addBox(-5.0F, -33.0F+24, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(18, 20).addBox(4.0F, -33.0F+24, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(40, 2).addBox(-5.0F, -34.0F+24, -4.0F, 10, 1, 8, 0.0F, false);
		head.setTextureOffset(0, 36).addBox(-4.0F, -34.0F+24, -5.0F, 8, 1, 1, 0.0F, false);
		head.setTextureOffset(18, 36).addBox(-4.0F, -34.0F+24, 4.0F, 8, 1, 2, 0.0F, false);
		
		hood = new ModelRenderer(this);
		hood.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(hood);
		hood.setTextureOffset(108, 9).addBox(-4.0F, -33.0F+24, 5.0F, 8, 9, 2, 0.0F, false);
		hood.setTextureOffset(108, 20).addBox(-3.0F, -31.0F+24, 4.0F, 6, 8, 4, 0.5F, false);
		hood.setTextureOffset(106, 32).addBox(-3.0F, -29.0F+24, 4.0F, 6, 8, 5, 0.0F, false);
		hood.setTextureOffset(114, 45).addBox(-2.0F, -23.0F+24, 5.5F, 4, 4, 3, 0.5F, false);
		hood.setTextureOffset(112, 52).addBox(-2.0F, -23.0F+24, 5.5F, 4, 8, 4, 0.0F, false);
		hood.setTextureOffset(120, 64).addBox(-1.0F, -15.0F+24, 7.5F, 2, 4, 2, 0.5F, false);
		hood.setTextureOffset(124, 70).addBox(-0.5F, -11.0F+24, 8.0F, 1, 5, 1, 0.5F, false);
		hood.setTextureOffset(122, 76).addBox(-1.0F, -7.2929F+24, 7.2929F, 2, 7, 1, 0.0F, false);
		
		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 0.0F, 8.0F);
		hood.addChild(bone);
		setRotationAngle(bone, -0.7854F, 0.0F, 0.0F);
		bone.setTextureOffset(124, 84).addBox(-0.5F, -0.8071F, -0.6071F, 1, 1, 1, 0.1F, false);
		
		leftFoot.setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftFoot.setTextureOffset(0, 112).addBox(-2f, 0F, -2.0F, 4, 12, 4, 0.2505F, false);
		
		rightFoot.setRotationPoint(1.9f, -12, 0.0F);
		rightFoot.setTextureOffset(16, 112).addBox(-2f, 0F, -2.0F, 4, 12, 4, 0.2505F, false);
		
		leftLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftLeg.setTextureOffset(0, 96).addBox(-2, 0, -2.0F, 4, 12, 4, 0.2505F, false);
		
		rightLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg.setTextureOffset(16, 96).addBox(-2, 0, -2.0F, 4, 12, 4, 0.2505F, false);
		
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}