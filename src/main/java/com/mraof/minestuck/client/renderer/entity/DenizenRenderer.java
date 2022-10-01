package com.mraof.minestuck.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.*;
import com.mraof.minestuck.client.model.DenizenModel;
import com.mraof.minestuck.entity.DenizenEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
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
	public void render(DenizenEntity denizen, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int packedLightIn)
	{
		//scales up the denizen model
		poseStack.pushPose();
		poseStack.scale(2F, 2F, 2F);
		super.render(denizen, entityYaw, partialTicks, poseStack, renderTypeBuffer, packedLightIn);
		poseStack.popPose();
		
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA); //important for transparency
		
		//float alphaSwing = (float) Math.min(Math.max((Math.sin(denizen.level.getGameTime() / 30F) + 240), 240), 255); //causes the denizen glow to pulse in opacity //TODO turns black and flickers
		float scaleSwing = (float) (Math.sin(denizen.level.getGameTime() / 50F) + 10); //causes the denizen glow to change in size
		
		//TODO with the current version of geckolib, getWorldPosition() is bugged and is not appropriate to use. Once its fixed it's use should be prioritized. https://github.com/bernie-g/geckolib/issues/293
		//modelProvider.getModel(new ResourceLocation("minestuck", "geo/denizen.geo.json")).getBone("head").get().getWorldPosition();
		Quaternion cameraQuaternion = this.entityRenderDispatcher.cameraOrientation();
		Vec3 cameraVec3 = this.entityRenderDispatcher.camera.getPosition();
		Vec3 denizenHeadVec = denizen.getEyePosition().add(denizen.getLookAngle().scale(10));
		Vec3 angledToCamera = cameraVec3.subtract(denizenHeadVec); //creates a point between the camera and the eyes of the denizen that the glow will be positioned at in order to obfuscate the models face
		//Vec3 angledToCamera = denizenHeadVec.subtract(cameraVec3);
		double distanceBetweenEntities = cameraVec3.distanceTo(denizenHeadVec);
		float eyeOffset = (float) (denizen.getEyeY() - denizen.getY());
		poseStack.pushPose();
		//poseStack.translate(denizen.getLookAngle().x * 7, eyeOffset - 4, denizen.getLookAngle().z * 7); //TODO figure out how to make the translation forward always face the camera so as to be projected in a radius in front of the denizens eyes
		poseStack.translate((angledToCamera.x() * 8) / distanceBetweenEntities, angledToCamera.y() / distanceBetweenEntities + eyeOffset, (angledToCamera.z() * 8) / distanceBetweenEntities);
		//poseStack.translate(angledToCamera.x(), angledToCamera.y(), angledToCamera.z());
		poseStack.scale(scaleSwing, scaleSwing, scaleSwing);
		poseStack.mulPose(cameraQuaternion);
		poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		PoseStack.Pose poseStackPose = poseStack.last();
		Matrix4f matrix4f = poseStackPose.pose();
		Matrix3f matrix3f = poseStackPose.normal();
		VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderType.entityTranslucent(DENIZEN_GLOW));
		vertexConsumer.vertex(matrix4f, 0.0F - 0.5F, 0 - 0.25F, 0.0F).color(255, 255, 255, 240).uv(0, 1)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, 1.0F - 0.5F, 0 - 0.25F, 0.0F).color(255, 255, 255, 240).uv(1, 1)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, 1.0F - 0.5F, 1 - 0.25F, 0.0F).color(255, 255, 255, 240).uv(1, 0)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, 0.0F - 0.5F, 1 - 0.25F, 0.0F).color(255, 255, 255, 240).uv(0, 0)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		
		poseStack.popPose();
		
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
	}
}