package com.mraof.minestuck.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mraof.minestuck.client.model.DenizenModel;
import com.mraof.minestuck.entity.DenizenEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DenizenRenderer extends GeoEntityRenderer<DenizenEntity>
{
	private static final ResourceLocation DENIZEN_GLOW = new ResourceLocation("minestuck", "textures/entity/denizen_glow.png");
	
	public DenizenRenderer(EntityRendererProvider.Context context)
	{
		super(context, new DenizenModel());
	}
	
	@Override
	public RenderType getRenderType(DenizenEntity animatable, float partialTicks, PoseStack stack,
									MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
									ResourceLocation textureLocation)
	{
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
	
	@Override
	public void render(DenizenEntity pEntity, float entityYaw, float partialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, entityYaw, partialTicks, pMatrixStack, pBuffer, pPackedLight);
		
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		
		//float alphaSwing = (float) Math.min(Math.max((Math.sin(pEntity.level.getGameTime() / 30F) + 240), 240), 255); //causes the denizen glow to pulse in opacity //TODO turns black and flickers
		float scaleSwing = (float) (Math.sin(pEntity.level.getGameTime() / 50F) + 5.5); //causes the denizen glow to change in size
		
		float eyeOffset = (float) (pEntity.getEyeY() - pEntity.getY());
		pMatrixStack.pushPose();
		pMatrixStack.translate(pEntity.getLookAngle().x * 7, eyeOffset - 4, pEntity.getLookAngle().z * 7); //TODO figure out how to make the translation forward always face the camera so as to be projected in a radius in front of the denizens eyes
		pMatrixStack.scale(scaleSwing, scaleSwing, scaleSwing);
		pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		//pMatrixStack.translate(0.0F, 0.0F, 1.2F);
		pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		PoseStack.Pose matrixstack = pMatrixStack.last();
		Matrix4f matrix4f = matrixstack.pose();
		Matrix3f matrix3f = matrixstack.normal();
		VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityTranslucent(DENIZEN_GLOW));
		ivertexbuilder.vertex(matrix4f, 0.0F - 0.5F, 0 - 0.25F, 0.0F).color(255, 255, 255, 240).uv(0, 1)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		ivertexbuilder.vertex(matrix4f, 1.0F - 0.5F, 0 - 0.25F, 0.0F).color(255, 255, 255, 240).uv(1, 1)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		ivertexbuilder.vertex(matrix4f, 1.0F - 0.5F, 1 - 0.25F, 0.0F).color(255, 255, 255, 240).uv(1, 0)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		ivertexbuilder.vertex(matrix4f, 0.0F - 0.5F, 1 - 0.25F, 0.0F).color(255, 255, 255, 240).uv(0, 0)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		
		pMatrixStack.popPose();
		
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
	}
}
/*public class DenizenRenderer<T extends DenizenEntity, M extends EntityModel<T>> extends MobRenderer<T, M>
{
	private static final ResourceLocation DENIZEN_GLOW = new ResourceLocation("minestuck", "textures/block/black_crown_stained_glass.png");
	
	public DenizenRenderer(EntityRendererProvider.Context pContext, M pModel, float pShadowRadius)
	{
		super(pContext, pModel, pShadowRadius);
	}
	
	
	@Override
	public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
		
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		
		float eyeOffset = (float) (pEntity.getEyeY() - pEntity.getY());
		pMatrixStack.pushPose();
		pMatrixStack.translate(0.0F, eyeOffset, 1.2F); //TODO figure out how to make the translation forward always face the camera so as to be projected in a radius in front of the denizens eyes
		pMatrixStack.scale(2F, 2F, 2F);
		
		pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		//pMatrixStack.translate(0.0F, 0.0F, 1.2F);
		pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		PoseStack.Pose matrixstack = pMatrixStack.last();
		Matrix4f matrix4f = matrixstack.pose();
		Matrix3f matrix3f = matrixstack.normal();
		VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityTranslucent(DENIZEN_GLOW));
		ivertexbuilder.vertex(matrix4f, 0.0F - 0.5F, 0 - 0.25F, 0.0F).color(255, 255, 255, 230).uv(0, 1)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		ivertexbuilder.vertex(matrix4f, 1.0F - 0.5F, 0 - 0.25F, 0.0F).color(255, 255, 255, 230).uv(1, 1)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		ivertexbuilder.vertex(matrix4f, 1.0F - 0.5F, 1 - 0.25F, 0.0F).color(255, 255, 255, 230).uv(1, 0)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		ivertexbuilder.vertex(matrix4f, 0.0F - 0.5F, 1 - 0.25F, 0.0F).color(255, 255, 255, 230).uv(0, 0)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		pMatrixStack.popPose();
		
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
	}
	
	@Override
	public ResourceLocation getTextureLocation(T pEntity)
	{
		return null;
	}
}*/