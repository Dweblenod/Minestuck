package com.mraof.minestuck.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BellyDancerGarbsModel extends BipedModel<LivingEntity>
{
	
	private final ModelRenderer RightLeg;
	private final ModelRenderer RightLeg_r1;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer Torso;
	private final ModelRenderer Torso_r1;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer Head;
	
	
	public BellyDancerGarbsModel()
	{
		super(1);
		textureWidth = 64;
		textureHeight = 64;
		
		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-1.9F, 0.0F, 0.0F);
		bipedRightLeg.addChild(RightLeg);
		
		
		RightLeg_r1 = new ModelRenderer(this);
		RightLeg_r1.setRotationPoint(1.9F, 12.0F, 0.0F);
		RightLeg.addChild(RightLeg_r1);
		setRotationAngle(RightLeg_r1, 0.0F, 3.1416F, 0.0F);
		RightLeg_r1.setTextureOffset(36, 36).addBox(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		
		bipedRightLeg = new ModelRenderer(this);
		bipedRightLeg.setRotationPoint(0.0F, 12.0F, 0.0F);
		
		/*
		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-1.9F, 0.0F, 0.0F);
		bipedRightLeg.addChild(RightLeg);
		RightLeg.setTextureOffset(24, 16).addBox(-2.175F, 0.2F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		RightLeg.setTextureOffset(48, 0).addBox(-2.325F, 0.2F, -2.0F, 4.0F, 7.0F, 4.0F, 0.2F, false);
		*/
		
		bipedLeftLeg = new ModelRenderer(this);
		bipedLeftLeg.setRotationPoint(0.0F, 12.0F, 0.0F);
		
		
		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(1.9F, 0.0F, 0.0F);
		bipedLeftLeg.addChild(LeftLeg);
		LeftLeg.setTextureOffset(0, 32).addBox(-1.85F, 0.2F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		LeftLeg.setTextureOffset(0, 32).addBox(-1.85F, 0.2F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		LeftLeg.setTextureOffset(40, 16).addBox(-1.725F, 0.2F, -2.0F, 4.0F, 7.0F, 4.0F, 0.2F, false);
		
		bipedBody = new ModelRenderer(this);
		bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		
		Torso = new ModelRenderer(this);
		Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedBody.addChild(Torso);
		Torso.setTextureOffset(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.1F, false);
		Torso.setTextureOffset(0, 48).addBox(-3.45F, 0.0F, -2.0F, 7.0F, 3.0F, 4.0F, 0.2F, false);
		Torso.setTextureOffset(0, 56).addBox(-3.45F, 0.0F, -2.0F, 7.0F, 3.0F, 4.0F, 0.28F, false);
		Torso.setTextureOffset(42, 54).addBox(-2.8F, 3.475F, -2.0F, 7.0F, 7.0F, 4.0F, 0.28F, false);
		Torso.setTextureOffset(24, 53).addBox(-4.25F, -0.125F, -1.925F, 7.0F, 7.0F, 4.0F, 0.28F, false);
		Torso.setTextureOffset(21, 49).addBox(-1.475F, 2.825F, -2.125F, 3.0F, 2.0F, 0.0F, 0.28F, false);
		Torso.setTextureOffset(40, 39).addBox(-3.8F, 11.9F, -2.0F, 8.0F, 3.0F, 4.0F, 0.25F, false);
		Torso.setTextureOffset(40, 46).addBox(-3.8F, 12.2F, -2.0F, 8.0F, 3.0F, 4.0F, 0.26F, false);
		
		Torso_r1 = new ModelRenderer(this);
		Torso_r1.setRotationPoint(1.625F, 7.87F, -2.125F);
		Torso.addChild(Torso_r1);
		setRotationAngle(Torso_r1, 0.0F, 0.0F, 2.7009F);
		Torso_r1.setTextureOffset(21, 49).addBox(-1.5F, -0.37F, 0.0F, 3.0F, 2.0F, 0.0F, 0.28F, false);

		/*Torso_r2 = new ModelRenderer(this);
		Torso_r2.setRotationPoint(0.025F, 4.14F, -2.125F);
		Torso.addChild(Torso_r2);
		setRotationAngle(Torso_r2, 0.0F, 0.0F, -3.1416F);
		Torso_r2.setTextureOffset(21, 49).addBox(-1.5F, 0.7F, 0.0F, 3.0F, 2.0F, 0.0F, 0.28F, false);

		Torso_r3 = new ModelRenderer(this);
		Torso_r3.setRotationPoint(1.625F, 7.87F, -2.125F);
		Torso.addChild(Torso_r3);
		setRotationAngle(Torso_r3, 0.0F, 0.0F, -0.4407F);
		Torso_r3.setTextureOffset(21, 49).addBox(-1.5F, -0.37F, 0.0F, 3.0F, 2.0F, 0.0F, 0.28F, false);

		Torso_r4 = new ModelRenderer(this);
		Torso_r4.setRotationPoint(6.0F, 3.375F, 0.075F);
		Torso.addChild(Torso_r4);
		setRotationAngle(Torso_r4, 0.0F, 3.1416F, 0.0F);
		Torso_r4.setTextureOffset(24, 53).addBox(3.125F, -3.55F, -1.975F, 7.0F, 7.0F, 4.0F, 0.28F, true);*/
		
		bipedRightArm = new ModelRenderer(this);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		
		
		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedRightArm.addChild(RightArm);
		RightArm.setTextureOffset(16, 32).addBox(-3.2F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		
		bipedLeftArm = new ModelRenderer(this);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		
		
		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedLeftArm.addChild(LeftArm);
		LeftArm.setTextureOffset(32, 0).addBox(-0.8F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
		
		bipedHead = new ModelRenderer(this);
		bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		
		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.addChild(Head);
		Head.setTextureOffset(0, 0).addBox(-4.0F, -8.225F, -4.0F, 8.0F, 8.0F, 8.0F, 0.1F, false);
		
		bipedHeadwear = new ModelRenderer(this);
		bipedHeadwear.setRotationPoint(0.0F, 24.0F, 0.0F);
		
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}