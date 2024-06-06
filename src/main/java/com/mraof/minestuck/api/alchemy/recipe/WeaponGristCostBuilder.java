package com.mraof.minestuck.api.alchemy.recipe;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.mraof.minestuck.alchemy.recipe.GristCost;
import com.mraof.minestuck.api.alchemy.DefaultImmutableGristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.GristTypes;
import com.mraof.minestuck.item.MSItemTypes;
import com.mraof.minestuck.item.weapon.OnHitEffect;
import com.mraof.minestuck.item.weapon.WeaponItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Used to datagen grist cost for weapons
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class WeaponGristCostBuilder
{
	@Nullable
	private final ResourceLocation defaultName;
	private final Ingredient ingredient;
	private final BuilderType builderType;
	private final ImmutableMap.Builder<GristType, Long> weightBuilder = ImmutableMap.builder();
	private final ImmutableMap.Builder<GristType, Long> outlierBuilder = ImmutableMap.builder();
	@Nullable
	private Integer priority = null;
	
	public enum BuilderType
	{
		VALUE_WEIGHT,
		COUNT_WEIGHT,
		ABSOLUTE
	}
	
	public static WeaponGristCostBuilder of(ItemLike item)
	{
		return of(item, BuilderType.VALUE_WEIGHT);
	}
	
	public static WeaponGristCostBuilder of(ItemLike item, BuilderType builderType)
	{
		return new WeaponGristCostBuilder(BuiltInRegistries.ITEM.getKey(item.asItem()), Ingredient.of(item), builderType);
	}
	
	private WeaponGristCostBuilder(@Nullable ResourceLocation defaultName, Ingredient ingredient, BuilderType builderType)
	{
		this.defaultName = defaultName;
		this.ingredient = ingredient;
		this.builderType = builderType;
	}
	
	public WeaponGristCostBuilder gristWeight(Supplier<GristType> type)
	{
		return gristWeight(type.get(), 1);
	}
	
	public WeaponGristCostBuilder gristWeight(Supplier<GristType> type, long amount)
	{
		return gristWeight(type.get(), amount);
	}
	
	public WeaponGristCostBuilder gristWeight(GristType type, long amount)
	{
		weightBuilder.put(type, amount);
		return this;
	}
	
	public WeaponGristCostBuilder gristOutlier(Supplier<GristType> type, long amount)
	{
		outlierBuilder.put(type.get(), amount);
		return this;
	}
	
	public WeaponGristCostBuilder priority(int priority)
	{
		this.priority = priority;
		return this;
	}
	
	private ImmutableMap<GristType, Long> calculateGrist(Item item)
	{
		ImmutableMap.Builder<GristType, Long> calculatedGrist = ImmutableMap.builder();
		
		ImmutableMap<GristType, Long> gristWeights = weightBuilder.build();
		ImmutableMap<GristType, Long> outlierGristWeights = outlierBuilder.build();
		
		long universalCost = getUniversalCost(item);
		
		Stream<? extends GristType> registeredGristTypes = GristTypes.GRIST_TYPES.getEntries().stream().map(Supplier::get);
		
		//TODO consider printing notification when no grists have been added to give context to crashes when sums end up as 0
		
		if(builderType == BuilderType.VALUE_WEIGHT)
		{
			long weightSum = 0;
			for(Map.Entry<GristType, Long> entry : gristWeights.entrySet())
			{
				weightSum += entry.getValue();
			}
			
			long multiplier = (universalCost - gristSetValue(outlierGristWeights)) / weightSum;
			
			registeredGristTypes.forEach(gristType ->
			{
				if(gristWeights.containsKey(gristType) || outlierGristWeights.containsKey(gristType))
					calculatedGrist.put(gristType, multiplier * gristWeights.getOrDefault(gristType, 0L) * (long) gristType.getRarity() + outlierGristWeights.getOrDefault(gristType, 0L));
			});
		} else if(builderType == BuilderType.COUNT_WEIGHT)
		{
			long weightedGristSum = 0;
			for(Map.Entry<GristType, Long> entry : gristWeights.entrySet())
			{
				weightedGristSum += (long) (entry.getKey().getValue() * entry.getValue());
			}
			
			long multiplier = (universalCost - gristSetValue(outlierGristWeights)) / weightedGristSum;
			
			registeredGristTypes.forEach(gristType ->
			{
				if(gristWeights.containsKey(gristType) || outlierGristWeights.containsKey(gristType))
					calculatedGrist.put(gristType, multiplier * (long) (gristWeights.getOrDefault(gristType, 0L) * gristType.getValue()) * (long) gristType.getRarity() + outlierGristWeights.getOrDefault(gristType, 0L));
			});
		} else
		{
			//long builderUniversalSum = gristSetValue(gristWeights);
			
			//TODO consider accounting for outlier grist, even though they shouldnt be used with this builder type
			
			//TODO throw a warning or error if builderUniversalSum does not match universalCost
			
			return gristWeights;
		}
		
		return calculatedGrist.build();
	}
	
	private static long gristSetValue(ImmutableMap<GristType, Long> grist)
	{
		long value = 0;
		
		for(Map.Entry<GristType, Long> entry : grist.entrySet())
		{
			value += (long) entry.getKey().getValue();
		}
		
		return value;
	}
	
	private static long getUniversalCost(ItemLike itemLike)
	{
		if(itemLike instanceof WeaponItem item)
		{
			ItemStack defaultStack = item.getDefaultInstance();
			
			Multimap<Attribute, AttributeModifier> attributes = item.getAttributeModifiers(EquipmentSlot.MAINHAND, defaultStack);
			List<OnHitEffect> onHitEffects = item.getOnHitEffects();
			Set<ToolAction> toolActions = item.getToolActions();
			Tier itemTier = item.getTier();
			
			double exponent = 0;
			
			exponent += dps(attributes);
			exponent += TIER_CONSTANTS.get(itemTier) / 4D;
			exponent += item.canDisableShield(defaultStack, null, null, null) ? 0.25F : 0F;
			exponent += toolActions.contains(ToolActions.SWORD_SWEEP) ? (float) 1 / 6 : 0F;
			exponent += item.isFireResistant() ? 1 : 0;
			
			for(OnHitEffect effect : onHitEffects)
			{
				exponent += effect.onHit(defaultStack, null, null);
			}
			
			//TODO result of durability does not match how the result you would get with other applications
			//item durability in relation to its tiers default durability
			exponent += (1D + Math.log((double) defaultStack.getMaxDamage() / (double) itemTier.getUses())) / 2D;
			
			return (long) Math.pow(2.5, exponent);
			
			//original formula (each has their own calculation)
			//return 2.5 ^ (dps/2 + material/4 + shield/4 + sweep/6 + knockback/4 + ranged + tool_type/6 + special_prop_total + rarity + durability/2 + fire_resistant);
		}
		
		return 0;
	}
	
	public static final Map<Tier, Double> TIER_CONSTANTS = Map.ofEntries(
			Map.entry(Tiers.WOOD, 0.25),
			Map.entry(Tiers.STONE, 0.4),
			Map.entry(Tiers.IRON, 0.55),
			Map.entry(Tiers.DIAMOND, 0.7),
			Map.entry(Tiers.GOLD, 0.45),
			Map.entry(Tiers.NETHERITE, 0.85),
			Map.entry(MSItemTypes.SBAHJ_TIER, 0.1),
			Map.entry(MSItemTypes.PAPER_TIER, 0.2),
			Map.entry(MSItemTypes.ORGANIC_TIER, 0.3),
			Map.entry(MSItemTypes.MEAT_TIER, 0.3),
			Map.entry(MSItemTypes.CANDY_TIER, 0.25),
			Map.entry(MSItemTypes.BOOK_TIER, 0.35),
			Map.entry(MSItemTypes.CACTUS_TIER, 0.35),
			Map.entry(MSItemTypes.ICE_TIER, 0.55),
			Map.entry(MSItemTypes.POGO_TIER, 0.45),
			Map.entry(MSItemTypes.EMERALD_TIER, 0.7),
			Map.entry(MSItemTypes.PRISMARINE_TIER, 0.6),
			Map.entry(MSItemTypes.CORUNDUM_TIER, 0.65),
			Map.entry(MSItemTypes.REGI_TIER, 0.8),
			Map.entry(MSItemTypes.HORRORTERROR_TIER, 0.7),
			Map.entry(MSItemTypes.URANIUM_TIER, 0.75),
			Map.entry(MSItemTypes.DENIZEN_TIER, 0.9),
			Map.entry(MSItemTypes.ZILLY_TIER, 0.95),
			Map.entry(MSItemTypes.WELSH_TIER, 1.0)
	);
	
	private static float dps(Multimap<Attribute, AttributeModifier> attributes)
	{
		double damage = 1; //the final damage is 1 + damage assigned in code + damage from material (damage in code and damage from material are combined in the attribute)
		for(AttributeModifier modifier : attributes.get(Attributes.ATTACK_DAMAGE))
		{
			damage += modifier.getAmount();
		}
		
		double speed = 4; //speed modifier works as a subtraction
		for(AttributeModifier modifier : attributes.get(Attributes.ATTACK_SPEED))
		{
			speed += modifier.getAmount();
		}
		
		double directDPS = damage * speed;
		
		double pvpAdjustedDPS = (damage * 1.05) + (speed * 0.95);
		
		float completeRecalc = (float) (((pvpAdjustedDPS * 1.5) + (directDPS * 0.5)) / 2);
		
		return completeRecalc / 2;
	}
	
	public void build(RecipeOutput recipeOutput)
	{
		Item item = ingredient.getItems()[0].getItem();
		
		ImmutableMap<GristType, Long> calculatedGrist = calculateGrist(item);
		
		ResourceLocation name = Objects.requireNonNull(defaultName != null ? defaultName : BuiltInRegistries.ITEM.getKey(item));
		build(recipeOutput, name, calculatedGrist);
	}
	
	public void build(RecipeOutput recipeOutput, ResourceLocation id, ImmutableMap<GristType, Long> calculatedGrist)
	{
		recipeOutput.accept(id.withPrefix("grist_costs/"), new GristCost(ingredient, new DefaultImmutableGristSet(calculatedGrist), Optional.ofNullable(priority)), null);
	}
	
	public static float mobEffectInstanceCalculation(MobEffectInstance effectInstance, boolean flipBeneficial)
	{
		//TODO make sure flip actually works
		
		float calculation = 1;
		
		MobEffect effect = effectInstance.getEffect();
		if(EFFECT_CONSTANTS.containsKey(effect))
			calculation *= EFFECT_CONSTANTS.get(effect);
		
		if(!effect.isInstantenous())
			calculation *= ((effectInstance.getDuration() / 100F) + 0.5F);
		
		calculation *= (effectInstance.getAmplifier() / 3F) + 1;
		
		if((effect.isBeneficial() && flipBeneficial) || !effect.isBeneficial())
			calculation = -calculation;
		
		return calculation;
	}
	
	public static final Map<MobEffect, Float> EFFECT_CONSTANTS = Map.ofEntries(
			Map.entry(MobEffects.WATER_BREATHING, 0.25F),
			Map.entry(MobEffects.WITHER, 0.9F),
			Map.entry(MobEffects.MOVEMENT_SLOWDOWN, 0.9F),
			Map.entry(MobEffects.LEVITATION, 0.8F)
	);
}
