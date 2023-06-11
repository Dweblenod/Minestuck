package com.mraof.minestuck.data.recipe;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.mraof.minestuck.alchemy.*;
import com.mraof.minestuck.item.crafting.MSRecipeTypes;
import com.mraof.minestuck.item.weapon.WeaponItem;
import com.mraof.minestuck.util.UniversalToolCostUtil;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
public class GristCostRecipeBuilder
{
	private static final Logger LOGGER = LogManager.getLogger();
	
	private final ResourceLocation defaultName;
	private final Ingredient ingredient;
	private final ImmutableMap.Builder<GristType, Long> costBuilder = ImmutableMap.builder();
	private Integer priority = null;
	
	public static GristCostRecipeBuilder of(TagKey<Item> tag)
	{
		ResourceLocation tagId = tag.location();
		return new GristCostRecipeBuilder(new ResourceLocation(tagId.getNamespace(), tagId.getPath() + "_tag"), Ingredient.of(tag));
	}
	
	public static GristCostRecipeBuilder of(ItemLike item)
	{
		return new GristCostRecipeBuilder(ForgeRegistries.ITEMS.getKey(item.asItem()), Ingredient.of(item));
	}
	
	public static GristCostRecipeBuilder of(Ingredient ingredient)
	{
		return new GristCostRecipeBuilder(ingredient);
	}
	
	protected GristCostRecipeBuilder(Ingredient ingredient)
	{
		this(null, ingredient);
	}
	
	protected GristCostRecipeBuilder(ResourceLocation defaultName, Ingredient ingredient)
	{
		this.defaultName = defaultName;
		this.ingredient = ingredient;
	}
	
	public GristCostRecipeBuilder grist(Supplier<GristType> type, long amount)
	{
		return grist(type.get(), amount);
	}
	
	public GristCostRecipeBuilder grist(GristType type, long amount)
	{
		costBuilder.put(type, amount);
		return this;
	}
	
	public GristCostRecipeBuilder gristsFromWeights(MutableGristSet weights)
	{
		return gristsFromWeights(weights, new MutableGristSet());
	}
	
	public GristCostRecipeBuilder gristsFromWeights(MutableGristSet weights, MutableGristSet outliers)
	{
		if(ingredient.getItems()[0].getItem() instanceof WeaponItem weapon)
		{
			ImmutableGristSet immutableOutliers = outliers.asImmutable();
			
			long universalCost = weapon.generateUniversalCost() - UniversalToolCostUtil.universalOutlierCost(immutableOutliers);
			LOGGER.debug(weapon.getDescription().getString() + ": " + universalCost);
			ImmutableGristSet costSet = UniversalToolCostUtil.weightedValue(weights.asImmutable(), universalCost);
			
			addGristSetToBuilder(costSet);
			addGristSetToBuilder(immutableOutliers);
			
			return this;
		} else
			throw new IllegalStateException("Grist cost being built MUST be of a WeaponItem to use universal cost formula.");
	}
	
	public void addGristSetToBuilder(ImmutableGristSet outliers)
	{
		for(GristAmount amount : outliers.asAmounts())
		{
			if(amount.amount() != 0)
				costBuilder.put(amount.type(), amount.amount());
			else
				costBuilder.put(amount.type(), 1L);
		}
	}
	
	public GristCostRecipeBuilder priority(int priority)
	{
		this.priority = priority;
		return this;
	}
	
	public void build(Consumer<FinishedRecipe> recipeSaver)
	{
		ResourceLocation name = Objects.requireNonNull(defaultName != null ? defaultName : ForgeRegistries.ITEMS.getKey(ingredient.getItems()[0].getItem()));
		build(recipeSaver, name);
	}
	
	public void buildFor(Consumer<FinishedRecipe> recipeSaver, String modId)
	{
		ResourceLocation name = Objects.requireNonNull(defaultName != null ? defaultName : ForgeRegistries.ITEMS.getKey(ingredient.getItems()[0].getItem()));
		build(recipeSaver, new ResourceLocation(modId, name.getPath()));
	}
	
	public void build(Consumer<FinishedRecipe> recipeSaver, ResourceLocation id)
	{
		recipeSaver.accept(new Result(new ResourceLocation(id.getNamespace(), "grist_costs/" + id.getPath()), ingredient, new DefaultImmutableGristSet(costBuilder), priority));
	}
	
	public static class Result implements FinishedRecipe
	{
		public final ResourceLocation id;
		public final Ingredient ingredient;
		public final ImmutableGristSet cost;
		public final Integer priority;
		
		public Result(ResourceLocation id, Ingredient ingredient, ImmutableGristSet cost, Integer priority)
		{
			this.id = id;
			this.ingredient = ingredient;
			this.cost = cost;
			this.priority = priority;
		}
		
		@Override
		public void serializeRecipeData(JsonObject jsonObject)
		{
			jsonObject.add("ingredient", ingredient.toJson());
			jsonObject.add("grist_cost", ImmutableGristSet.MAP_CODEC.encodeStart(JsonOps.INSTANCE, cost).getOrThrow(false, LOGGER::error));
			if(priority != null)
				jsonObject.addProperty("priority", priority);
		}
		
		@Override
		public ResourceLocation getId()
		{
			return id;
		}
		
		@Override
		public RecipeSerializer<?> getType()
		{
			return MSRecipeTypes.GRIST_COST.get();
		}
		
		@Nullable
		@Override
		public JsonObject serializeAdvancement()
		{
			return null;
		}
		
		@Nullable
		@Override
		public ResourceLocation getAdvancementId()
		{
			return new ResourceLocation("");
		}
	}
}