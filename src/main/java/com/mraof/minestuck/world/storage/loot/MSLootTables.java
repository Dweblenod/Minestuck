package com.mraof.minestuck.world.storage.loot;

import com.mraof.minestuck.world.storage.loot.conditions.ConsortLootCondition;
import com.mraof.minestuck.world.storage.loot.conditions.LandTypeLootCondition;
import com.mraof.minestuck.world.storage.loot.functions.SetBoondollarCount;
import com.mraof.minestuck.world.storage.loot.functions.SetKeyValues;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryManager;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;

public class MSLootTables
{
	public static final ResourceLocation DUNGEON_LOOT_INJECT = new ResourceLocation("minestuck", "chests/injections/dungeon_inject");
	public static final ResourceLocation FROG_TEMPLE_CHEST = new ResourceLocation("minestuck", "chests/frog_temple");
	public static final ResourceLocation BASIC_MEDIUM_CHEST = new ResourceLocation("minestuck", "chests/medium_basic");
	public static final ResourceLocation TIER_ONE_MEDIUM_CHEST = new ResourceLocation("minestuck", "chests/medium_tier_one");
	public static final ResourceLocation CONSORT_JUNK_REWARD = new ResourceLocation("minestuck", "gameplay/consort_junk");
	public static final ResourceLocation CONSORT_FOOD_STOCK = new ResourceLocation("minestuck", "gameplay/consort_food");
	public static final ResourceLocation CONSORT_GENERAL_STOCK = new ResourceLocation("minestuck", "gameplay/consort_general");
	
	public static void registerLootSerializers()
	{
		LootConditionManager.registerCondition(new LandTypeLootCondition.Serializer());
		LootConditionManager.registerCondition(new ConsortLootCondition.Serializer());
		LootFunctionManager.registerFunction(new SetBoondollarCount.Serializer());
		LootFunctionManager.registerFunction(new SetKeyValues.Serializer());
		LootEntryManager.func_216194_a(new LandTableLootEntry.SerializerImpl());
	}
}