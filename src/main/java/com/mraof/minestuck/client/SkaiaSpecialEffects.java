package com.mraof.minestuck.client;

import com.mraof.minestuck.client.renderer.ProspitSkyRenderer;
import com.mraof.minestuck.client.renderer.SkaiaSkyRenderer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SkaiaSpecialEffects extends DimensionSpecialEffects
{
	public SkaiaSpecialEffects()
	{
		super(300.0F, true, SkyType.NONE, true, false);
	}
	
	@Override
	public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog)
	{
		SkaiaSkyRenderer.render(modelViewMatrix, level);
		return true;
	}
	
	@Nullable
	@Override
	public float[] getSunriseColor(float timeOfDay, float partialTicks)
	{
		return null;
	}
	
	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness)
	{
		return fogColor.multiply(brightness * 0.94F + 0.06F, brightness * 0.94F + 0.06F, brightness * 0.91F + 0.09F);
	}
	
	@Override
	public boolean isFoggyAt(int x, int z)
	{
		return false;
	}
}
