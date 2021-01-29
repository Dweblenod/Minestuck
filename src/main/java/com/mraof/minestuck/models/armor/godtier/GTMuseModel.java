package com.mraof.minestuck.models.armor.godtier;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mraof.minestuck.player.EnumClass;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class GTMuseModel extends GTAbstractModel
{
	
	private final ModelRenderer hood;
	private final ModelRenderer neckLeft;
	private final ModelRenderer neckRight;
	
	public GTMuseModel(float size)
	{
		super(size,128, 128, EnumClass.MUSE);
		addColorIgnores(6, 7, 8);
		textureWidth = 128;
		textureHeight = 128;
		
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10, 1, 10, 0.0F, false);
		head.setTextureOffset(22, 11).addBox(-5.0F, -9.0F, 4.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(0, 20).addBox(-5.0F, -9.0F, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(0, 11).addBox(-5.0F, -9.0F, -5.0F, 10, 8, 1, 0.0F, false);
		head.setTextureOffset(18, 20).addBox(4.0F, -9.0F, -4.0F, 1, 8, 8, 0.0F, false);
		head.setTextureOffset(40, 0).addBox(-5.0F, -10.0F, -4.0F, 10, 1, 8, 0.0F, false);
		head.setTextureOffset(0, 36).addBox(-5.0F, -10.0F, 4.0F, 10, 1, 1, 0.0F, false);
		head.setTextureOffset(18, 36).addBox(-4.0F, -10.0F, -5.0F, 8, 1, 1, 0.0F, false);
		
		hood = new ModelRenderer(this);
		hood.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(hood);
		hood.setTextureOffset(110, 10).addBox(-4.0F, -9.5F, 5.0F, 8, 8, 1, 0.5F, false);
		hood.setTextureOffset(108, 19).addBox(-4.0F, -10.0F, 5.0F, 8, 8, 3, 0.0F, false);
		hood.setTextureOffset(114, 30).addBox(-3.0F, -9.5F, 7.5F, 6, 7, 1, 0.5F, false);
		hood.setTextureOffset(112, 38).addBox(-3.0F, -9.0F, 8.5F, 6, 7, 2, 0.0F, false);
		hood.setTextureOffset(118, 47).addBox(-2.0F, -8.0F, 10.0F, 4, 5, 1, 0.5F, false);
		hood.setTextureOffset(116, 53).addBox(-2.0F, -7.5F, 10.5F, 4, 5, 2, 0.0F, false);
		hood.setTextureOffset(122, 60).addBox(-1.0F, -6.0F, 12.0F, 2, 2, 1, 0.5F, false);
		hood.setTextureOffset(120, 63).addBox(-1.0F, -5.5F, 12.5F, 2, 2, 2, 0.0F, false);
		
		neck.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.setTextureOffset(102, 0).addBox(-5.0F, 0.0F, 0.0F, 10, 4, 3, 0.0F, false);
		neck.setTextureOffset(104, 96).addBox(-4.0F, 0.25F, -2.5F, 8, 12, 4, 0.251F, false);
		neck.setTextureOffset(104, 80).addBox(-4.0F, -0.1F, -2.0F, 8, 12, 4, 0.2515F, false);
		
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.setTextureOffset(104, 112).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.251F, false);
		
		leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		leftArm.setTextureOffset(0, 56).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm.setTextureOffset(0, 56).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftLeg.setTextureOffset(0, 80).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightLeg.setTextureOffset(0, 80).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		leftFoot.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, true);
		
		rightFoot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightFoot.setTextureOffset(0, 112).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false);
		
		neckLeft = new ModelRenderer(this);
		neckLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
		neckLeft.setTextureOffset(82, 0).addBox(-1.0F, -2.0F, -2.0F, 4, 3, 4, 0.75F, true);
		
		neckRight = new ModelRenderer(this);
		neckRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
		neckRight.setTextureOffset(82, 0).addBox(-3.0F, -2.0F, -2.0F, 4, 3, 4, 0.75F, false);
		
		skirtBack.setRotationPoint(0.0F, 10.0F, 2.252F);
		skirtBack.setTextureOffset(59, 115).addBox(-4.0F, 0.0F, 0.1F, 8, 12, 0, 0.0F, false);
		
		skirtFront.setRotationPoint(0.0F, 10.0F, -2.252F);
		skirtFront.setTextureOffset(77, 115).addBox(-4.0F, 0.0F, -0F, 8, 12, 0, 0.0F, false);
		
		
	}
	
	@Override
	public EquipmentSlotType getSkirtSlot() {
		return EquipmentSlotType.CHEST;
	}
	
	@Override
	public void addExtraInfo(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot)
	{
		neckLeft.showModel = armorSlot == EquipmentSlotType.HEAD;
		neckRight.showModel = armorSlot == EquipmentSlotType.HEAD;
	}
	
	@Override
	protected void renderExtras(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay)
	{
		neckRight.render(matrixStack, buffer, packedLight, packedOverlay);
		neckLeft.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	
	@Override
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		copyModelAngles(leftArm, neckLeft);
		copyModelAngles(rightArm, neckRight);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}