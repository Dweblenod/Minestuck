package com.mraof.minestuck.data.recipe;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.mraof.minestuck.item.crafting.MSRecipeTypes;
import com.mraof.minestuck.item.crafting.alchemy.GristSet;
import com.mraof.minestuck.item.crafting.alchemy.GristType;
import com.mraof.minestuck.item.crafting.alchemy.ImmutableGristSet;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WeaponGristCostRecipeBuilder
{
	private final ResourceLocation defaultName;
	private final Ingredient ingredient;
	private final ImmutableMap.Builder<GristType, Long> costBuilder = ImmutableMap.builder();
	private Integer priority = null;
	
	public static WeaponGristCostRecipeBuilder of(Tag<Item> tag)
	{
		return new WeaponGristCostRecipeBuilder(new ResourceLocation(tag.getId().getNamespace(), tag.getId().getPath()+"_tag"), Ingredient.fromTag(tag));
	}
	
	public static WeaponGristCostRecipeBuilder of(IItemProvider item)
	{
		return new WeaponGristCostRecipeBuilder(item.asItem().getRegistryName(), Ingredient.fromItems(item));
	}
	
	public static WeaponGristCostRecipeBuilder of(Ingredient ingredient)
	{
		return new WeaponGristCostRecipeBuilder(ingredient);
	}
	
	protected WeaponGristCostRecipeBuilder(Ingredient ingredient)
	{
		this(null, ingredient);
	}
	
	protected WeaponGristCostRecipeBuilder(ResourceLocation defaultName, Ingredient ingredient)
	{
		this.defaultName = defaultName;
		this.ingredient = ingredient;
	}
	
	public WeaponGristCostRecipeBuilder grist(Supplier<GristType> type, long ratio, long absoluteAmount)
	{
		return grist(type.get(), ratio, absoluteAmount);
	}
	
	public WeaponGristCostRecipeBuilder grist(GristType type, long ratio, long absoluteAmount)
	{
		if(absoluteAmount == 0)
			costBuilder.put(type, (long)universalValueConverter(universalValueCreator(), ratio));
		else
			costBuilder.put(type, absoluteAmount);
		return this;
	}
	
	public int universalValueCreator()
	{
		//float toolTypes = ingredient.getMatchingStacks()[0].getItem().getDefaultInstance().getToolTypes().size()/10F;
		Multimap<String, AttributeModifier> multimap = ingredient.getMatchingStacks()[0].getAttributeModifiers(EquipmentSlotType.MAINHAND);
		Collection<AttributeModifier> damage = multimap.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
		ingredient.getMatchingStacks()[0].getItem().getDefaultInstance().getAttributeModifiers(EquipmentSlotType.MAINHAND).get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
		.;
		
		float variables = (damageRecalc/2) + (material/4) + (rarity);
		//float variables = (damageRecalc/2) + (material/4) + (shieldBreaking/4) + (sweep/6) + (knockback/4) + (reach/1.2) + (ranged) + (toolTypes/6) + (specialProperty) + (rarity) + (durability/2);
		int universalValue = Math.getExponent(variables);
		return universalValue;
	}
	
	public int universalValueConverter(int universalValue, long ratio)
	{
		int ratioValue = 0;
		return ratioValue;
	}
	
	public WeaponGristCostRecipeBuilder priority(int priority)
	{
		this.priority = priority;
		return this;
	}
	
	public void build(Consumer<IFinishedRecipe> recipeSaver)
	{
		ResourceLocation name = Objects.requireNonNull(defaultName != null ? defaultName : ingredient.getMatchingStacks()[0].getItem().getRegistryName());
		build(recipeSaver, name);
	}
	
	public void buildFor(Consumer<IFinishedRecipe> recipeSaver, String modId)
	{
		ResourceLocation name = Objects.requireNonNull(defaultName != null ? defaultName : ingredient.getMatchingStacks()[0].getItem().getRegistryName());
		build(recipeSaver, new ResourceLocation(modId, name.getPath()));
	}
	
	public void build(Consumer<IFinishedRecipe> recipeSaver, ResourceLocation id)
	{
		recipeSaver.accept(new Result(new ResourceLocation(id.getNamespace(), "grist_costs/"+id.getPath()), ingredient, new ImmutableGristSet(costBuilder), priority));
	}
	
	public static class Result implements IFinishedRecipe
	{
		public final ResourceLocation id;
		public final Ingredient ingredient;
		public final GristSet cost;
		public final Integer priority;
		
		public Result(ResourceLocation id, Ingredient ingredient, GristSet cost, Integer priority)
		{
			this.id = id;
			this.ingredient = ingredient;
			this.cost = cost;
			this.priority = priority;
		}
		
		@Override
		public void serialize(JsonObject jsonObject)
		{
			jsonObject.add("ingredient", ingredient.serialize());
			jsonObject.add("grist_cost", cost.serialize());
			if(priority != null)
				jsonObject.addProperty("priority", priority);
		}
		
		@Override
		public ResourceLocation getID()
		{
			return id;
		}
		
		@Override
		public IRecipeSerializer<?> getSerializer()
		{
			return MSRecipeTypes.GRIST_COST;
		}
		
		@Nullable
		@Override
		public JsonObject getAdvancementJson()
		{
			return null;
		}
		
		@Nullable
		@Override
		public ResourceLocation getAdvancementID()
		{
			return new ResourceLocation("");
		}
	}
}