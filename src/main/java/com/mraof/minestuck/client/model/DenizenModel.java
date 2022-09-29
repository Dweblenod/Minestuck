package com.mraof.minestuck.client.model;

import com.mraof.minestuck.entity.DenizenEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class DenizenModel extends AnimatedGeoModel<DenizenEntity>
{
	@Override
	public ResourceLocation getAnimationFileLocation(DenizenEntity entity) {
		return new ResourceLocation("minestuck", "animations/denizen.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(DenizenEntity entity) {
		return new ResourceLocation("minestuck", "geo/denizen.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(DenizenEntity entity) {
		return new ResourceLocation("minestuck", "textures/entity/denizen.png");
	}
	
	@Override
	public void setLivingAnimations(DenizenEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}