package com.mraof.minestuck.models.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mraof.minestuck.player.EnumClass;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GTKnightModel extends GTAbstractModel
{
	
	public GTKnightModel(float size)
	{
		super(size, 128, 128, EnumClass.KNIGHT);
		
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(34, 34).addBox(4.0F, -9.0F, -4.0F, 1.0F, 8.0F, 8.0F, 0.0F, false);
		head.setTextureOffset(42, 0).addBox(-5.0F, -9.0F, 4.0F, 10.0F, 8.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(50, 9).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 8.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
		head.setTextureOffset(32, 3).addBox(-5.0F, -9.0F, -4.0F, 1.0F, 8.0F, 8.0F, 0.0F, false);
		head.setTextureOffset(0, 11).addBox(-5.0F, -10.0F, -4.0F, 10.0F, 1.0F, 8.0F, 0.0F, false);
		head.setTextureOffset(16, 52).addBox(-4.0F, -10.0F, 4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(34, 50).addBox(-4.0F, -10.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		
		neck.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.setTextureOffset(44, 35).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
		neck.setTextureOffset(44, 38).addBox(-4.0F, 0.0F, 2.2F, 8.0F, 2.0F, 1.0F, 0.0F, false);
		
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.setTextureOffset(18, 20).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.251F, false);
		
		cape.setRotationPoint(0.0F, 0.0F, 0.0F);
		cape.setTextureOffset(0, 20).addBox(-4.0F, 1.0F, 2.0F, 8.0F, 22.0F, 1.0F, 0.0F, false);
		
		leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		leftArm.setTextureOffset(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);
		
		rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm.setTextureOffset(44, 19).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);
		
		leftLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftLeg.setTextureOffset(0, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);
		
		rightLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg.setTextureOffset(18, 36).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);
		
		leftFoot.setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftFoot.setTextureOffset(0, 59).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);
		
		rightFoot.setRotationPoint(1.9F, 12.0F, 0.0F);
		rightFoot.setTextureOffset(16, 59).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.251F, false);
	}

	/*
	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}
	*/
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		neck.render(matrixStack, buffer, packedLight, packedOverlay);
		torso.render(matrixStack, buffer, packedLight, packedOverlay);
		cape.render(matrixStack, buffer, packedLight, packedOverlay);
		leftArm.render(matrixStack, buffer, packedLight, packedOverlay);
		rightArm.render(matrixStack, buffer, packedLight, packedOverlay);
		skirtFront.render(matrixStack, buffer, packedLight, packedOverlay);
		skirtMiddle.render(matrixStack, buffer, packedLight, packedOverlay);
		skirtBack.render(matrixStack, buffer, packedLight, packedOverlay);
		belt.render(matrixStack, buffer, packedLight, packedOverlay);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		leftFoot.render(matrixStack, buffer, packedLight, packedOverlay);
		rightFoot.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}