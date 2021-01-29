package com.mraof.minestuck.models.armor.godtier;

import com.mraof.minestuck.player.EnumClass;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GTPageModel extends GTAbstractModel
{
	
	private final ModelRenderer hood;
	
	public GTPageModel(float size)
	{
		super(size,128, 128, EnumClass.PAGE);
		addColorIgnores(4, 6, 7, 8);
		
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10, 1, 10, 0.0F, false);
		head.setTextureOffset(22, 11).addBox(-5.0F, -9.0F, 4.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(0, 20).addBox(-5.0F, -9.0F, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(0, 11).addBox(-5.0F, -9.0F, -5.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(18, 20).addBox(4.0F, -9.0F, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(40, 0).addBox(-5.0F, -10.0F, -4.0F, 10, 1, 8, 0.0F, false);
		head.setTextureOffset(18, 36).addBox(-5.0F, -10.0F, 4.0F, 10, 1, 1, 0.0F, false);
		head.setTextureOffset(0, 36).addBox(-4.0F, -10.0F, -5.0F, 8, 1, 1, 0.0F, false);
		
		hood = new ModelRenderer(this);
		hood.setRotationPoint(0.0F, 1.0F, -3.0F);
		head.addChild(hood);
		hood.setTextureOffset(110, 10).addBox(-4.0F, -10.0F, 8.0F, 8, 8, 1, 0.5F, false);
		hood.setTextureOffset(108, 19).addBox(-4.0F, -10.0F, 9.0F, 8, 8, 2, 0.0F, false);
		hood.setTextureOffset(112, 29).addBox(-3.0F, -9.0F, 10.0F, 6, 6, 2, 0.5F, false);
		hood.setTextureOffset(116, 37).addBox(-2.0F, -9.0F, 11.5F, 4, 4, 2, 0.5F, false);
		hood.setTextureOffset(114, 43).addBox(-2.0F, -10.0F, 12.0F, 4, 4, 3, 0.0F, false);
		hood.setTextureOffset(122, 50).addBox(-1.0F, -10.5F, 14.5F, 2, 2, 1, 0.0F, false);
		hood.setTextureOffset(124, 53).addBox(-0.5F, -11.0F, 15.0F, 1, 1, 1, 0.0F, false);
		
		neck.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.setTextureOffset(106, 0).addBox(-5.0F, 0.0F, 2.0F, 10, 2, 1, 0.0F, false);
		neck.setTextureOffset(84, 0).addBox(-5.0F, 0.0F, -3.0F, 10, 2, 1, 0.0F, false);
		neck.setTextureOffset(80, 112).addBox(-4.0F, -0.1F, -2.0F, 8, 12, 4, 0.2515F, false);
		
		belt.setRotationPoint(0.0F, 0.0F, 0.0F);
		belt.setTextureOffset(104, 96).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.2522F, false);
		
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.setTextureOffset(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.251F, false);
		
		cape.setRotationPoint(0.0F, 0.0F, 0.0F);
		cape.setTextureOffset(110, 84).addBox(-4.0F, 1.0F, 2.0F, 8, 11, 1, 0.0F, false);
		
		rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm.setTextureOffset(0, 74).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		leftArm.setTextureOffset(0, 74).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightLeg.setTextureOffset(0, 96).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftLeg.setTextureOffset(0, 96).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		rightFoot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2505F, true);
		
		leftFoot.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2505F, false);
		
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}