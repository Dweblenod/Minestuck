package com.mraof.minestuck.item.weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record OnHitEffectWrapper(float value, OnHitEffect effect) implements OnHitEffect
{
	@Override
	public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		effect.onHit(stack, target, attacker);
	}
}