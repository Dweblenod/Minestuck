package com.mraof.minestuck.models.armor.godtier;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.player.EnumAspect;
import com.mraof.minestuck.player.EnumClass;
import com.mraof.minestuck.util.AspectColorHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public abstract class GTAbstractModel extends BipedModel<LivingEntity>
{
	public final EnumClass heroClass;
	public EnumAspect heroAspect;
	
	public final ModelRenderer head;
	public final ModelRenderer neck;
	public final ModelRenderer torso;
	public final ModelRenderer cape;
	public final ModelRenderer leftArm;
	public final ModelRenderer rightArm;
	public final ModelRenderer skirtFront;
	public final ModelRenderer skirtMiddle;
	public final ModelRenderer skirtBack;
	public final ModelRenderer belt;
	public final ModelRenderer leftLeg;
	public final ModelRenderer rightLeg;
	public final ModelRenderer leftFoot;
	public final ModelRenderer rightFoot;
	
	public final ModelRenderer symbol;
	
	public boolean hideExtras = true;
	public boolean isSneaking = false;
	
	private Float skirtFrontY = null;
	private Float skirtFrontZ = null;
	private Float skirtBackY = null;
	private Float skirtBackZ = null;
	private Float skirtMiddleY = null;
	private Float skirtMiddleZ = null;
	private float scale = 1;
	
	public final ArrayList<Integer> IGNORE_COLORS = new ArrayList<>();
	
	public GTAbstractModel(float modelSize, int textWidth, int textHeight, EnumClass heroClass)
	{
		super(modelSize);
		scale = modelSize;
		this.heroClass = heroClass;
		textureWidth = 12;
		textureHeight = 6;
		
		symbol = new ModelRenderer(this);
		symbol.setRotationPoint(0.0F, 0.0F, 0.0F);
		symbol.setTextureOffset(0,0);
		symbol.addBox(-3f, 2f, -2f, 6, 6, 0, 0.2512F, false);
		
		
		textureWidth = textWidth;
		textureHeight = textHeight;
		
		head = new ModelRenderer(this);
		
		neck = new ModelRenderer(this);
		torso = new ModelRenderer(this);
		cape = new ModelRenderer(this);
		
		leftArm = new ModelRenderer(this);
		rightArm = new ModelRenderer(this);
		
		skirtFront = new ModelRenderer(this);
		skirtMiddle = new ModelRenderer(this);
		skirtBack = new ModelRenderer(this);
		belt = new ModelRenderer(this);
		leftLeg = new ModelRenderer(this);
		rightLeg = new ModelRenderer(this);
		
		leftFoot = new ModelRenderer(this);
		rightFoot = new ModelRenderer(this);
	}
	
	
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		//super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		
		//this.setRotationAngles(e); ???
		matrixStack.push();
		
		
		if (this.isChild)
		{
			float f = 2.0F;
			matrixStack.scale(0.75F, 0.75F, 0.75F);
			matrixStack.translate(0.0F, 16.0F * scale, 0.0F);
			renderHead(matrixStack, buffer, packedLight, packedOverlay);
			matrixStack.pop();
			matrixStack.push();
			matrixStack.scale(0.5F, 0.5F, 0.5F);
			matrixStack.translate(0.0F, 24.0F * scale, 0.0F);
			renderBody(matrixStack, buffer, packedLight, packedOverlay);
		}
		else
		{
			if (isSneaking)
			{
				matrixStack.translate(0.0F, 0.2F, 0.0F);
			}
			
			renderHead(matrixStack, buffer, packedLight, packedOverlay);
			renderBody(matrixStack, buffer, packedLight, packedOverlay);
		}
		
		matrixStack.pop();
	}
	
	private void renderBody(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay)
	{
		AspectColorHandler.AspectColor[] colorSet = AspectColorHandler.getAspectColorSet(heroAspect);
		
		for(int i = 0; i < colorSet.length; i++)
		{
			if(IGNORE_COLORS.contains(i))
				continue;
			
			AspectColorHandler.AspectColor color = colorSet[i];
			ResourceLocation loc = new ResourceLocation(Minestuck.MOD_ID, "textures/models/armor/gt_"+heroClass.toString()+"_layer_"+(i+1)+".png");
			Minecraft.getInstance().getTextureManager().bindTexture(loc);
			
			buffer.color(color.r, color.g, color.b, 1);
			
			this.torso.render(matrixStack, buffer, packedLight, packedOverlay);
			this.neck.render(matrixStack, buffer, packedLight, packedOverlay);
			//renderCape(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			this.cape.render(matrixStack, buffer, packedLight, packedOverlay);
			this.rightArm.render(matrixStack, buffer, packedLight, packedOverlay);
			this.leftArm.render(matrixStack, buffer, packedLight, packedOverlay);
			this.skirtFront.render(matrixStack, buffer, packedLight, packedOverlay);
			this.skirtMiddle.render(matrixStack, buffer, packedLight, packedOverlay);
			this.skirtBack.render(matrixStack, buffer, packedLight, packedOverlay);
			this.belt.render(matrixStack, buffer, packedLight, packedOverlay);
			this.rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			this.leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			this.leftFoot.render(matrixStack, buffer, packedLight, packedOverlay);
			this.rightFoot.render(matrixStack, buffer, packedLight, packedOverlay);
			
			renderExtras(matrixStack, buffer, packedLight, packedOverlay);
		}
		
		buffer.color(1,1,1, 1);
		String aspectName = heroAspect == null ? "default" : heroAspect.toString();
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(Minestuck.MOD_ID, "textures/models/armor/symbol/"+aspectName+".png"));
		this.symbol.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	
	protected void renderExtras(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay)
	{
	}
	
	private void renderHead(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay)
	{
		AspectColorHandler.AspectColor[] colorSet = AspectColorHandler.getAspectColorSet(heroAspect);
		
		for(int i = 0; i < colorSet.length; i++)
		{
			if(IGNORE_COLORS.contains(i))
				continue;
			
			AspectColorHandler.AspectColor color = colorSet[i];
			ResourceLocation loc = new ResourceLocation(Minestuck.MOD_ID, "textures/models/armor/gt_"+heroClass.toString()+"_layer_"+(i+1)+".png");
			Minecraft.getInstance().getTextureManager().bindTexture(loc);
			buffer.color(color.r, color.g, color.b, 1);
			
			this.head.render(matrixStack, buffer, packedLight, packedOverlay);
			renderHeadExtras(matrixStack, buffer, packedLight, packedOverlay);
		}
	}
	
	protected void renderHeadExtras(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay)
	{
	}
	
	@Override
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		
		
		if(skirtFrontY == null)
			skirtFrontY = skirtFront.rotationPointY;
		if(skirtFrontZ == null)
			skirtFrontZ = skirtFront.rotationPointZ;
		if(skirtMiddleY == null)
			skirtMiddleY = skirtMiddle.rotationPointY;
		if(skirtMiddleZ == null)
			skirtMiddleZ = skirtMiddle.rotationPointZ;
		if(skirtBackY == null)
			skirtBackY = skirtBack.rotationPointY;
		if(skirtBackZ == null)
			skirtBackZ = skirtBack.rotationPointZ;
		
		this.skirtFront.rotationPointZ = skirtFrontZ;
		this.skirtFront.rotationPointY = skirtFrontY;
		this.skirtBack.rotationPointZ = skirtBackZ;
		this.skirtBack.rotationPointY = skirtBackY;
		this.skirtMiddle.rotationPointZ = skirtMiddleZ;
		this.skirtMiddle.rotationPointY = skirtMiddleY;
		
		if (entityIn instanceof ArmorStandEntity)
		{
			ArmorStandEntity entityarmorstand = (ArmorStandEntity)entityIn;
			head.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
			head.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
			head.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
			head.setRotationPoint(0.0F, 1.0F, 0.0F);
			torso.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
			torso.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
			torso.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
			leftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
			leftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
			leftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
			rightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
			rightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
			rightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
			leftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
			leftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
			leftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
			leftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
			rightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
			rightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
			rightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
			rightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
			copyModelAngles(torso, neck);
			copyModelAngles(torso, symbol);
			copyModelAngles(torso, cape);
			copyModelAngles(torso, belt);
			copyModelAngles(leftLeg, leftFoot);
			copyModelAngles(rightLeg, rightFoot);
		} else
		{
			if(entityIn instanceof PlayerEntity)
			{
				PlayerRenderer render = (PlayerRenderer) Minecraft.getInstance().getRenderManager().getRenderer((AbstractClientPlayerEntity) entityIn);
				PlayerModel modelPlayer = render.getEntityModel();
				
				copyModelAngles(modelPlayer.bipedHead, head);
				copyModelAngles(modelPlayer.bipedBody, neck);
				copyModelAngles(modelPlayer.bipedBody, torso);
				copyModelAngles(modelPlayer.bipedBody, symbol);
				copyModelAngles(modelPlayer.bipedBody, cape);
				copyModelAngles(modelPlayer.bipedBody, belt);
				copyModelAngles(modelPlayer.bipedLeftArm, leftArm);
				copyModelAngles(modelPlayer.bipedRightArm, rightArm);
				copyModelAngles(modelPlayer.bipedLeftLeg, leftLeg);
				copyModelAngles(modelPlayer.bipedRightLeg, rightLeg);
				copyModelAngles(modelPlayer.bipedLeftLeg, leftFoot);
				copyModelAngles(modelPlayer.bipedRightLeg, rightFoot);
				
			} else
			{
				copyModelAngles(bipedHead, head);
				copyModelAngles(bipedBody, neck);
				copyModelAngles(bipedBody, torso);
				copyModelAngles(bipedBody, cape);
				copyModelAngles(bipedBody, belt);
				copyModelAngles(bipedLeftArm, leftArm);
				copyModelAngles(bipedRightArm, rightArm);
				copyModelAngles(bipedLeftLeg, leftLeg);
				copyModelAngles(bipedRightLeg, rightLeg);
				copyModelAngles(bipedLeftLeg, leftFoot);
				copyModelAngles(bipedRightLeg, rightFoot);
				
			}
			
			cape.rotateAngleX += ((float) Math.sqrt(Math.pow((entityIn.getPosX() - entityIn.prevPosX), 2) + Math.pow(Math.max(0, entityIn.getPosY() - entityIn.prevPosY), 2) + Math.pow((entityIn.getPosZ() - entityIn.prevPosZ), 2)) * limbSwingAmount);
			if (this.isSneak)
			{
				this.skirtFront.rotationPointZ += 4;
				this.skirtFront.rotationPointY -= 2;
				this.skirtBack.rotationPointZ += 4;
				this.skirtBack.rotationPointY -= 2;
				this.skirtMiddle.rotationPointZ += 4;
				this.skirtMiddle.rotationPointY -= 2;
			}
		}
		skirtBack.rotateAngleX = Math.max(0, Math.max(leftLeg.rotateAngleX, rightLeg.rotateAngleX));
		skirtFront.rotateAngleX = Math.min(0, Math.min(leftLeg.rotateAngleX, rightLeg.rotateAngleX));
	}
	
	protected void addColorIgnores(Integer... index)
	{
		for(Integer i : index)
			IGNORE_COLORS.add(i-1);
	}
	
	protected void addColorIgnores(AspectColorHandler.EnumColor... index)
	{
		for(AspectColorHandler.EnumColor i : index)
			IGNORE_COLORS.add(i.ordinal());
	}
	
	public EquipmentSlotType getSkirtSlot()
	{
		return EquipmentSlotType.LEGS;
	}
	public EquipmentSlotType getCapeSlot()
	{
		return EquipmentSlotType.HEAD;
	}
	
	public void addExtraInfo(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot)
	{
	
	}
	
	public static void copyModelAngles(ModelRenderer source, ModelRenderer dest)
	{
		dest.rotateAngleX = source.rotateAngleX;
		dest.rotateAngleY = source.rotateAngleY;
		dest.rotateAngleZ = source.rotateAngleZ;
		dest.rotationPointX = source.rotationPointX;
		dest.rotationPointY = source.rotationPointY;
		dest.rotationPointZ = source.rotationPointZ;
	}
}