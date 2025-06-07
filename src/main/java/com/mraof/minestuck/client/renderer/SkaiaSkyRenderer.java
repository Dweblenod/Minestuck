package com.mraof.minestuck.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

public final class SkaiaSkyRenderer
{
	public static void render(Matrix4f modelViewMatrix, ClientLevel level)
	{
		VertexBuffer skyBuffer = createLightSky();
		
		PoseStack poseStack = new PoseStack();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		FogRenderer.levelFogColor();
		RenderSystem.depthMask(false);
		
		poseStack.mulPose(modelViewMatrix);
		
		
		
		//Vec3 vec3 = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), partialTick);
		//float f = (float)vec3.x;
		//float f1 = (float)vec3.y;
		//float f2 = (float)vec3.z;
		FogRenderer.levelFogColor();
		Tesselator tesselator = Tesselator.getInstance();
		RenderSystem.depthMask(false);
		RenderSystem.setShaderColor(0.678F, 0.847F, 0.902F, 1.0F); //light blue
		ShaderInstance shaderinstance = RenderSystem.getShader();
		skyBuffer.bind();
		skyBuffer.drawWithShader(poseStack.last().pose(), modelViewMatrix, shaderinstance);
		VertexBuffer.unbind();
		RenderSystem.enableBlend();
		
		RenderSystem.blendFuncSeparate(
				GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
		);
		/*poseStack.pushPose();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
		poseStack.mulPose(Axis.XP.rotationDegrees(this.level.getTimeOfDay(partialTick) * 360.0F));
		Matrix4f matrix4f1 = posestack.last().pose();
		float f12 = 30.0F;
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, SUN_LOCATION);
		BufferBuilder bufferbuilder1 = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder1.addVertex(matrix4f1, -f12, 100.0F, -f12).setUv(0.0F, 0.0F);
		bufferbuilder1.addVertex(matrix4f1, f12, 100.0F, -f12).setUv(1.0F, 0.0F);
		bufferbuilder1.addVertex(matrix4f1, f12, 100.0F, f12).setUv(1.0F, 1.0F);
		bufferbuilder1.addVertex(matrix4f1, -f12, 100.0F, f12).setUv(0.0F, 1.0F);
		BufferUploader.drawWithShader(bufferbuilder1.buildOrThrow());
		f12 = 20.0F;
		RenderSystem.setShaderTexture(0, MOON_LOCATION);
		int k = this.level.getMoonPhase();
		int l = k % 4;
		int i1 = k / 4 % 2;
		float f13 = (float)(l + 0) / 4.0F;
		float f14 = (float)(i1 + 0) / 2.0F;
		float f15 = (float)(l + 1) / 4.0F;
		float f16 = (float)(i1 + 1) / 2.0F;
		bufferbuilder1 = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder1.addVertex(matrix4f1, -f12, -100.0F, f12).setUv(f15, f16);
		bufferbuilder1.addVertex(matrix4f1, f12, -100.0F, f12).setUv(f13, f16);
		bufferbuilder1.addVertex(matrix4f1, f12, -100.0F, -f12).setUv(f13, f14);
		bufferbuilder1.addVertex(matrix4f1, -f12, -100.0F, -f12).setUv(f15, f14);
		BufferUploader.drawWithShader(bufferbuilder1.buildOrThrow());
		float f10 = this.level.getStarBrightness(partialTick) * f11;
		if (f10 > 0.0F) {
			RenderSystem.setShaderColor(f10, f10, f10, f10);
			FogRenderer.setupNoFog();
			this.starBuffer.bind();
			this.starBuffer.drawWithShader(posestack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
			VertexBuffer.unbind();
			skyFogSetup.run();
		}
		
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
		posestack.popPose();
		RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
		double d0 = this.minecraft.player.getEyePosition(partialTick).y - this.level.getLevelData().getHorizonHeight(this.level);
		if (d0 < 0.0) {
			posestack.pushPose();
			posestack.translate(0.0F, 12.0F, 0.0F);
			this.darkBuffer.bind();
			this.darkBuffer.drawWithShader(posestack.last().pose(), projectionMatrix, shaderinstance);
			VertexBuffer.unbind();
			posestack.popPose();
		}
		
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.depthMask(true);*/
		
		
		
		
		/*RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		
		SessionRenderHelper.drawRotatingVeil(poseStack, level);
		SessionRenderHelper.drawRotatingDerse(poseStack, level, 0.5F);
		SessionRenderHelper.drawRotatingSkaia(poseStack, level, 200.0F);*/
		
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
	}
	
	private static VertexBuffer createLightSky() {
		VertexBuffer skyBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		skyBuffer.bind();
		skyBuffer.upload(buildSkyDisc(Tesselator.getInstance(), 16.0F));
		VertexBuffer.unbind();
		
		return skyBuffer;
	}
	
	private static MeshData buildSkyDisc(Tesselator tesselator, float y) {
		float f = Math.signum(y) * 512.0F;
		float f1 = 512.0F;
		BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
		bufferbuilder.addVertex(0.0F, y, 0.0F);
		
		for (int i = -180; i <= 180; i += 45) {
			bufferbuilder.addVertex(f * Mth.cos((float)i * (float) (Math.PI / 180.0)), y, 512.0F * Mth.sin((float)i * (float) (Math.PI / 180.0)));
		}
		
		return bufferbuilder.buildOrThrow();
	}
}
