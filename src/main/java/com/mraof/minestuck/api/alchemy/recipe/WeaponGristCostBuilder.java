package com.mraof.minestuck.api.alchemy.recipe;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.mraof.minestuck.alchemy.recipe.GristCost;
import com.mraof.minestuck.api.alchemy.DefaultImmutableGristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.GristTypes;
import com.mraof.minestuck.item.weapon.WeaponItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

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
	private final ImmutableMap.Builder<GristType, Long> costBuilder = ImmutableMap.builder();
	@Nullable
	private Integer priority = null;
	
	public static WeaponGristCostBuilder of(TagKey<Item> tag)
	{
		return new WeaponGristCostBuilder(tag.location().withSuffix("_tag"), Ingredient.of(tag));
	}
	
	public static WeaponGristCostBuilder of(ItemLike item)
	{
		return new WeaponGristCostBuilder(BuiltInRegistries.ITEM.getKey(item.asItem()), Ingredient.of(item)).grist(GristTypes.BUILD, calculation(item));
	}
	
	public static WeaponGristCostBuilder of(Ingredient ingredient)
	{
		return new WeaponGristCostBuilder(null, ingredient);
	}
	
	private WeaponGristCostBuilder(@Nullable ResourceLocation defaultName, Ingredient ingredient)
	{
		this.defaultName = defaultName;
		this.ingredient = ingredient;
	}
	
	public WeaponGristCostBuilder grist(Supplier<GristType> type, long amount)
	{
		return grist(type.get(), amount);
	}
	
	public WeaponGristCostBuilder grist(GristType type, long amount)
	{
		costBuilder.put(type, amount);
		return this;
	}
	
	public WeaponGristCostBuilder priority(int priority)
	{
		this.priority = priority;
		return this;
	}
	
	private static long calculation(ItemLike itemLike)
	{
		if(itemLike instanceof WeaponItem item)
		{
			Multimap<Attribute, AttributeModifier> attributes = item.getAttributeModifiers(EquipmentSlot.MAINHAND, ((WeaponItem) itemLike).getDefaultInstance());
			
			return (long) dps(attributes);
			//return 2.5 ^ (dps/2 + material/4 + shield/4 + sweep/6 + knockback/4 + ranged + tool_type/6 + special_prop_total + rarity + durability/2 + fire_resistant);
		}
		
		return 0;
	}
	
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
		
		return (float) (((pvpAdjustedDPS * 1.5) + (directDPS * 0.5)) / 2);
	}
	
	public void build(RecipeOutput recipeOutput)
	{
		ResourceLocation name = Objects.requireNonNull(defaultName != null ? defaultName : BuiltInRegistries.ITEM.getKey(ingredient.getItems()[0].getItem()));
		build(recipeOutput, name);
	}
	
	public void buildFor(RecipeOutput recipeOutput, String modId)
	{
		ResourceLocation name = Objects.requireNonNull(defaultName != null ? defaultName : BuiltInRegistries.ITEM.getKey(ingredient.getItems()[0].getItem()));
		build(recipeOutput, new ResourceLocation(modId, name.getPath()));
	}
	
	public void build(RecipeOutput recipeOutput, ResourceLocation id)
	{
		recipeOutput.accept(id.withPrefix("grist_costs/"), new GristCost(ingredient, new DefaultImmutableGristSet(costBuilder), Optional.ofNullable(priority)), null);
	}
}
