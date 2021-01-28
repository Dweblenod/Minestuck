package com.mraof.minestuck.models.armor.godtier;

import com.mraof.minestuck.player.EnumClass;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GTBardModel extends GTAbstractModel
{
	private final ModelRenderer hood;
	private final ModelRenderer cape1;
	private final ModelRenderer cape2;
	private final ModelRenderer cape3;
	private final ModelRenderer cape4;
	private final ModelRenderer codPieceTop;
	private final ModelRenderer codPieceRight;
	private final ModelRenderer codPieceLeft;
	private final ModelRenderer boneRight;
	private final ModelRenderer boneLeft;
	
	public GTBardModel(float size)
	{
		super(size,128, 128, EnumClass.BARD);
		addColorIgnores(8);
		
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
		hood.setRotationPoint(0.0F, -10.0F, 0.0F);
		head.addChild(hood);
		hood.setTextureOffset(96, 9).addBox(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.5F, false);
		hood.setTextureOffset(96, 19).addBox(-4.0F, -3.0F, -4.0F, 8, 3, 8, 0.0F, false);
		hood.setTextureOffset(104, 30).addBox(-3.0F, -4.0F, -3.0F, 6, 1, 6, 0.5F, false);
		hood.setTextureOffset(104, 37).addBox(-3.0F, -6.0F, -3.0F, 6, 3, 6, 0.0F, false);
		hood.setTextureOffset(112, 46).addBox(-2.0F, -7.0F, -2.0F, 4, 1, 4, 0.5F, false);
		hood.setTextureOffset(112, 51).addBox(-2.0F, -9.0F, -2.0F, 4, 3, 4, 0.0F, false);
		hood.setTextureOffset(120, 58).addBox(-1.0F, -9.0F, -1.0F, 2, 1, 2, 0.5F, false);
		hood.setTextureOffset(120, 61).addBox(-1.0F, -10.0F, -1.0F, 2, 1, 2, 0.0F, false);
		
		neck.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.setTextureOffset(84, 0).addBox(-5.0F, 0.0F, -2.75F, 10, 2, 1, 0.0F, false);
		neck.setTextureOffset(106, 0).addBox(-5.0F, 0.0F, 1.75F, 10, 2, 1, 0.0F, false);
		neck.setTextureOffset(79, 112).addBox(-4.0F, -0.1F, -2.0F, 8, 12, 4, 0.252F, false);
		
		cape.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		cape1 = new ModelRenderer(this);
		cape1.setRotationPoint(0.0F, -19.0F, 0.0F);
		cape.addChild(cape1);
		cape1.setTextureOffset(122, 78).addBox(-5.0F, 20.0F, 2.25F, 2, 22, 1, 0.0F, true);
		
		cape2 = new ModelRenderer(this);
		cape2.setRotationPoint(0.0F, -19.0F, 0.0F);
		cape.addChild(cape2);
		cape2.setTextureOffset(116, 78).addBox(-2.35F, 20.0F, 2.25F, 2, 22, 1, 0.0F, true);
		
		cape3 = new ModelRenderer(this);
		cape3.setRotationPoint(0.0F, -19.0F, 0.0F);
		cape.addChild(cape3);
		cape3.setTextureOffset(116, 78).addBox(0.35F, 20.0F, 2.25F, 2, 22, 1, 0.0F, false);
		
		cape4 = new ModelRenderer(this);
		cape4.setRotationPoint(0.0F, -19.0F, 0.0F);
		cape.addChild(cape4);
		cape4.setTextureOffset(122, 78).addBox(3.0F, 20.0F, 2.25F, 2, 22, 1, 0.0F, false);
		
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.setTextureOffset(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.251F, false);
		
		rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm.setTextureOffset(0, 74).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		leftArm.setTextureOffset(0, 74).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		belt.setRotationPoint(0.0F, 0.0F, 0.0F);
		belt.setTextureOffset(55, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.255F, false);
		
		codPieceTop = new ModelRenderer(this);
		codPieceTop.setRotationPoint(0.0F, 0.0F, 0.0F);
		belt.addChild(codPieceTop);
		codPieceTop.setTextureOffset(86, 68).addBox(-5.0F, 9.0F, -3.0F, 10, 1, 6, 0.0F, false);
		
		rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightLeg.setTextureOffset(0, 96).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		codPieceRight = new ModelRenderer(this);
		codPieceRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightLeg.addChild(codPieceRight);
		codPieceRight.setTextureOffset(92, 75).addBox(-3.0F, 1.0F, -3.0F, 6, 1, 6, -0.001F, false);
		
		leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftLeg.setTextureOffset(0, 96).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		codPieceLeft = new ModelRenderer(this);
		codPieceLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftLeg.addChild(codPieceLeft);
		codPieceLeft.setTextureOffset(92, 75).addBox(-3.0F, 1.0F, -3.0F, 6, 1, 6, -0.001F, false);
		
		rightFoot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, true);
		rightFoot.setTextureOffset(18, 125).addBox(-1.5F, 11.0F, -4.0F, 3, 1, 2, 0.251F, true);
		rightFoot.setTextureOffset(18, 123).addBox(-0.5F, 10.5F, -4.5F, 1, 1, 1, 0.5F, true);
		
		boneRight = new ModelRenderer(this);
		boneRight.setRotationPoint(0.0F, 10.0F, -5.0F);
		rightFoot.addChild(boneRight);
		setRotationAngle(boneRight, -0.7854F, 0.0F, 0.0F);
		boneRight.setTextureOffset(18, 120).addBox(-0.5F, -0.5F, -0.5F, 1, 1, 2, 0.0F, true);
		boneRight.setTextureOffset(18, 118).addBox(-0.5F, -1.0F, -1.0F, 1, 1, 1, 0.0F, true);
		
		leftFoot.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		leftFoot.setTextureOffset(18, 125).addBox(-1.5F, 11.0F, -4.0F, 3, 1, 2, 0.251F, false);
		leftFoot.setTextureOffset(18, 123).addBox(-0.5F, 10.5F, -4.5F, 1, 1, 1, 0.5F, false);
		
		boneLeft = new ModelRenderer(this);
		boneLeft.setRotationPoint(0.0F, 10.0F, -5.0F);
		leftFoot.addChild(boneLeft);
		setRotationAngle(boneLeft, -0.7854F, 0.0F, 0.0F);
		boneLeft.setTextureOffset(18, 120).addBox(-0.5F, -0.5F, -0.5F, 1, 1, 2, 0.0F, false);
		boneLeft.setTextureOffset(18, 118).addBox(-0.5F, -1.0F, -1.0F, 1, 1, 1, 0.0F, false);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}