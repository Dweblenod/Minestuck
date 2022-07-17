package com.mraof.minestuck.data.loot_table;

import com.mraof.minestuck.block.MSBlocks;
import com.mraof.minestuck.entity.consort.EnumConsort;
import com.mraof.minestuck.item.MSItems;
import com.mraof.minestuck.util.MSTags;
import com.mraof.minestuck.world.lands.LandTypes;
import com.mraof.minestuck.world.storage.loot.LandTableLootEntry;
import com.mraof.minestuck.world.storage.loot.MSLootTables;
import com.mraof.minestuck.world.storage.loot.conditions.ConsortLootCondition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.mraof.minestuck.data.loot_table.MSChestLootTables.locationFor;

public class MSGiftLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>
{
	private static final ResourceLocation COLD_CAKE = new ResourceLocation("minestuck", "gameplay/special/cold_cake");
	private static final ResourceLocation HOT_CAKE = new ResourceLocation("minestuck", "gameplay/special/hot_cake");
	
	//Pools in consort general stock
	public static final String ITEM_POOL = "item", BLOCK_POOL = "block";
	public static final String MAIN_POOL = "main", SPECIAL_POOL = "special";
	
	@Override
	public void accept(BiConsumer<ResourceLocation, LootTable.Builder> lootProcessor)
	{
		lootProcessor.accept(COLD_CAKE, LootTable.lootTable()
				.withPool(LootPool.lootPool().name("blue_cake").setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.BLUE_CAKE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name("cold_cake").setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.COLD_CAKE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(HOT_CAKE, LootTable.lootTable()
				.withPool(LootPool.lootPool().name("red_cake").setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.RED_CAKE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name("hot_cake").setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.HOT_CAKE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		
		lootProcessor.accept(MSLootTables.CONSORT_GENERAL_STOCK, LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(4))
						.add(LandTableLootEntry.builder(MSLootTables.CONSORT_GENERAL_STOCK).setPool(ITEM_POOL))
						.add(LootItem.lootTableItem(MSItems.CARVING_TOOL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(MSBlocks.MINI_FROG_STATUE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(MSBlocks.MINI_WIZARD_STATUE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(MSBlocks.MINI_TYPHEUS_STATUE).setWeight(1))
						.add(LootItem.lootTableItem(MSItems.STONE_SLAB).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(MSItems.THRESH_DVD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(MSItems.CREW_POSTER).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(MSItems.SBAHJ_POSTER).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(MSItems.GAMEBRO_MAGAZINE).setWeight(5).when(ConsortLootCondition.builder(EnumConsort.SALAMANDER, EnumConsort.NAKAGATOR)))
						.add(LootItem.lootTableItem(MSItems.GAMEGRL_MAGAZINE).setWeight(5).when(ConsortLootCondition.builder(EnumConsort.TURTLE, EnumConsort.IGUANA)))
						.add(LootItem.lootTableItem(MSItems.ACE_OF_CLUBS).setWeight(2).when(ConsortLootCondition.builder(EnumConsort.SALAMANDER)))
						.add(LootItem.lootTableItem(MSItems.ACE_OF_SPADES).setWeight(2).when(ConsortLootCondition.builder(EnumConsort.NAKAGATOR)))
						.add(LootItem.lootTableItem(MSItems.ACE_OF_HEARTS).setWeight(2).when(ConsortLootCondition.builder(EnumConsort.TURTLE)))
						.add(LootItem.lootTableItem(MSItems.ACE_OF_DIAMONDS).setWeight(2).when(ConsortLootCondition.builder(EnumConsort.IGUANA)))
						.add(LootItem.lootTableItem(MSItems.CLUBS_SUITARANG).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(MSItems.DIAMONDS_SUITARANG).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(MSItems.HEARTS_SUITARANG).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(MSItems.SPADES_SUITARANG).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
						.add(LootItem.lootTableItem(MSItems.MUSIC_DISC_EMISSARY_OF_DANCE).setWeight(1))
						.add(LootItem.lootTableItem(MSItems.MUSIC_DISC_DANCE_STAB_DANCE).setWeight(1))
						.add(LootItem.lootTableItem(MSItems.ELECTRIC_AUTOHARP).setWeight(1))
						.add(LootItem.lootTableItem(MSItems.BATTERY).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,4))))
						.add(LootItem.lootTableItem(MSItems.GRIMOIRE).setWeight(1).when(ConsortLootCondition.builder(EnumConsort.TURTLE)))
						.add(LootItem.lootTableItem(MSItems.CRUMPLY_HAT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))).when(ConsortLootCondition.builder(EnumConsort.SALAMANDER))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(2))
						.add(LandTableLootEntry.builder(MSLootTables.CONSORT_GENERAL_STOCK).setPool(BLOCK_POOL))));
		
		lootProcessor.accept(locationFor(LandTypes.FOREST, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.COPSE_CRUSHER).setWeight(3))
						.add(LootItem.lootTableItem(Items.OAK_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.SPRUCE_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.BIRCH_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.JUNGLE_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.DARK_OAK_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.ACACIA_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.VINE_LOG).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.FLOWERY_VINE_LOG).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.DARK_OAK_LOG).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.OAK_PLANKS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		lootProcessor.accept(locationFor(LandTypes.TAIGA, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.COPSE_CRUSHER).setWeight(3))
						.add(LootItem.lootTableItem(Items.OAK_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.SPRUCE_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.BIRCH_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.JUNGLE_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.DARK_OAK_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.ACACIA_SAPLING).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.SPRUCE_LOG).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.DARK_OAK_LOG).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.OAK_PLANKS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		
		lootProcessor.accept(locationFor(LandTypes.FROST, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.KATANA).setWeight(3))
						.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))
						.add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))
						.add(LootItem.lootTableItem(Items.PRISMARINE_SHARD).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.FROST_BRICKS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.FROST_TILE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.PRISMARINE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.PRISMARINE_BRICKS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.FROST_LOG).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		
		lootProcessor.accept(locationFor(LandTypes.FUNGI, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.REDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		lootProcessor.accept(locationFor(LandTypes.END, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.ELYTRA).setWeight(3))
						.add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.DRAGON_BREATH).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.HEAT, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.FIRE_POKER).setWeight(3))
						.add(LootItem.lootTableItem(Items.QUARTZ).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.BLAZE_POWDER).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))
						.add(LootItem.lootTableItem(Items.NETHER_BRICK).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.CAST_IRON).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.CHISELED_CAST_IRON).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.NETHER_BRICKS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.RED_NETHER_BRICKS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		
		lootProcessor.accept(locationFor(LandTypes.ROCK, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.METAL_BAT).setWeight(3))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.COARSE_STONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.CHISELED_COARSE_STONE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.POLISHED_ANDESITE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.POLISHED_GRANITE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		lootProcessor.accept(locationFor(LandTypes.PETRIFICATION, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.METAL_BAT).setWeight(3))
						.add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.COARSE_STONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.CHISELED_COARSE_STONE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.POLISHED_ANDESITE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.POLISHED_GRANITE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		
		lootProcessor.accept(locationFor(LandTypes.SAND, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.CACTACEAE_CUTLASS).setWeight(3))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.LEATHER).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.CACTUS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.WOODEN_CACTUS).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.SANDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.SMOOTH_SANDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		lootProcessor.accept(locationFor(LandTypes.RED_SAND, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.CACTACEAE_CUTLASS).setWeight(3))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.LEATHER).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.CACTUS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.WOODEN_CACTUS).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.RED_SANDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.SMOOTH_RED_SANDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		lootProcessor.accept(locationFor(LandTypes.LUSH_DESERTS, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.CACTACEAE_CUTLASS).setWeight(3))
						.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.LEATHER).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.CACTUS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.WOODEN_CACTUS).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.SANDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.SMOOTH_SANDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		
		lootProcessor.accept(locationFor(LandTypes.SANDSTONE, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(3))
						.add(LootItem.lootTableItem(Items.REDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.SMOOTH_SANDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.CHISELED_SANDSTONE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.STONE_BRICKS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.CHISELED_STONE_BRICKS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		lootProcessor.accept(locationFor(LandTypes.RED_SANDSTONE, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(3))
						.add(LootItem.lootTableItem(Items.REDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.SMOOTH_RED_SANDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.CHISELED_RED_SANDSTONE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.STONE_BRICKS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.CHISELED_STONE_BRICKS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		
		lootProcessor.accept(locationFor(LandTypes.SHADE, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.POGO_CLUB).setWeight(3))
						.add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.FEATHER).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.FLINT).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.GLOWING_LOG).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.GLOWING_PLANKS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.SHADE_BRICKS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(MSBlocks.SMOOTH_SHADE_STONE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		
		lootProcessor.accept(locationFor(LandTypes.WOOD, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.STONE_AXE).setWeight(3))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
						.add(LootItem.lootTableItem(Items.SLIME_BALL).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))
						.add(LootItem.lootTableItem(Items.CHARCOAL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.OAK_LOG).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.SPRUCE_LOG).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.BIRCH_LOG).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))
						.add(LootItem.lootTableItem(Items.JUNGLE_LOG).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 20))))));
		
		lootProcessor.accept(locationFor(LandTypes.RAINBOW, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.PAPER).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.EGG).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.FLORA, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
						.add(LootItem.lootTableItem(Items.QUARTZ).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.FROGS, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.BUG_NET).setWeight(5)))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.LILY_PAD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))));
		
		lootProcessor.accept(locationFor(LandTypes.WIND, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1)))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.LIGHT, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.GLOWYSTONE_DUST).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.CLOCKWORK, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.CLOCK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.SILENCE, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1)))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.THUNDER, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.IRON_CANE).setWeight(3)))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.PULSE, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1)))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.THOUGHT, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.BUCKETS, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.BUCKET).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.CAKE, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1)))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.SUGAR_CUBE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))))
						.add(LootItem.lootTableItem(MSBlocks.FUCHSIA_CAKE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		
		lootProcessor.accept(locationFor(LandTypes.RABBITS, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.RABBIT_FOOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.MONSTERS, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		lootProcessor.accept(locationFor(LandTypes.UNDEAD, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10)))))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.TOWERS, MSLootTables.CONSORT_GENERAL_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(ITEM_POOL).setRolls(ConstantValue.exactly(1)))
				.withPool(LootPool.lootPool().name(BLOCK_POOL).setRolls(ConstantValue.exactly(1))));
		
		
		lootProcessor.accept(MSLootTables.CONSORT_FOOD_STOCK, LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(5))
						.add(LandTableLootEntry.builder(MSLootTables.CONSORT_FOOD_STOCK).setPool(MAIN_POOL))
						.add(LootItem.lootTableItem(MSItems.GRASSHOPPER).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(UniformGenerator.between(1, 2))
						.add(LandTableLootEntry.builder(MSLootTables.CONSORT_FOOD_STOCK).setPool(SPECIAL_POOL))
						.add(LootItem.lootTableItem(Items.PUMPKIN_PIE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
						.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(1))
						.add(LootItem.lootTableItem(MSItems.CANDY_CORN).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
						.add(TagEntry.expandTag(MSTags.Items.GRIST_CANDY).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))));
		
		//Iguana
		lootProcessor.accept(locationFor(LandTypes.FOREST, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.ONION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CONE_OF_FLIES).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.APPLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CHOCOLATE_BEETLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.COCOA_BEANS).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.COOKIE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(MSBlocks.APPLE_CAKE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.TAIGA, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.ONION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CONE_OF_FLIES).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.APPLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CHOCOLATE_BEETLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.COCOA_BEANS).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.COOKIE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(MSBlocks.APPLE_CAKE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.FROST, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.ONION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CONE_OF_FLIES).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.APPLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CHOCOLATE_BEETLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootTableReference.lootTableReference(COLD_CAKE).setWeight(2)))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.COOKIE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(MSBlocks.APPLE_CAKE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.FLORA, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.ONION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CONE_OF_FLIES).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.APPLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CHOCOLATE_BEETLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.WHEAT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.STRAWBERRY_CHUNK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 12)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.COOKIE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(MSBlocks.APPLE_CAKE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		
		//Salamander
		lootProcessor.accept(locationFor(LandTypes.FUNGI, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CONE_OF_FLIES).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.MUSHROOM_STEW).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.RED_MUSHROOM).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
						.add(LootItem.lootTableItem(Items.BROWN_MUSHROOM).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
						.add(LootItem.lootTableItem(MSItems.FUNGAL_SPORE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 12))))
						.add(LootItem.lootTableItem(MSItems.SPOREO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.POISONOUS_POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.MELON).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))));
		lootProcessor.accept(locationFor(LandTypes.SHADE, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CONE_OF_FLIES).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.MUSHROOM_STEW).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSBlocks.GLOWING_MUSHROOM).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.POISONOUS_POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.MELON).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))));
		lootProcessor.accept(locationFor(LandTypes.WOOD, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CONE_OF_FLIES).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.MUSHROOM_STEW).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.RED_MUSHROOM).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
						.add(LootItem.lootTableItem(Items.BROWN_MUSHROOM).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.POISONOUS_POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.MELON).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))));
		
		//Nakagator
		lootProcessor.accept(locationFor(LandTypes.HEAT, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.ONION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CHOCOLATE_BEETLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.BEETROOT_SOUP).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.BEEF).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.STEAK_SWORD).setWeight(3))
						.add(LootItem.lootTableItem(Items.BEETROOT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootTableReference.lootTableReference(HOT_CAKE).setWeight(2)))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.TAB).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
						.add(LootItem.lootTableItem(MSItems.IRRADIATED_STEAK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.ROCK, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.ONION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CHOCOLATE_BEETLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.BEETROOT_SOUP).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.BEEF).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.STEAK_SWORD).setWeight(3))
						.add(LootItem.lootTableItem(Items.BEETROOT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.ROCK_COOKIE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 12)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.TAB).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
						.add(LootItem.lootTableItem(MSItems.IRRADIATED_STEAK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.PETRIFICATION, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.ONION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CHOCOLATE_BEETLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.BEETROOT_SOUP).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.BEEF).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.STEAK_SWORD).setWeight(3))
						.add(LootItem.lootTableItem(Items.BEETROOT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.ROCK_COOKIE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 12)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.TAB).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
						.add(LootItem.lootTableItem(MSItems.IRRADIATED_STEAK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.END, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.ONION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.CHOCOLATE_BEETLE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.BEETROOT_SOUP).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.BEEF).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.STEAK_SWORD).setWeight(3))
						.add(LootItem.lootTableItem(Items.BEETROOT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.CHORUS_FRUIT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 12)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.TAB).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
						.add(LootItem.lootTableItem(MSItems.IRRADIATED_STEAK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		
		//Turtle
		lootProcessor.accept(locationFor(LandTypes.SAND, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.SALAD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))).apply(SetNbtFunction.setTag(waterNBT())))
						.add(LootItem.lootTableItem(MSItems.DESERT_FRUIT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(15, 20)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.GOLD_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.RED_SAND, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.SALAD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))).apply(SetNbtFunction.setTag(waterNBT())))
						.add(LootItem.lootTableItem(MSItems.DESERT_FRUIT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(15, 20)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.GOLD_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.LUSH_DESERTS, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.SALAD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTION).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))).apply(SetNbtFunction.setTag(waterNBT())))
						.add(LootItem.lootTableItem(MSItems.DESERT_FRUIT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(15, 20)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.GOLD_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.SANDSTONE, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.SALAD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.GOLD_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.RED_SANDSTONE, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.SALAD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.GOLD_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.RAINBOW, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.SALAD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.BREAD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 12)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.GOLD_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		lootProcessor.accept(locationFor(LandTypes.RAIN, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.BUG_ON_A_STICK).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.POTATO).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(MSItems.SALAD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.CARROT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))))
						.add(LootItem.lootTableItem(Items.MELON).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 12)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSBlocks.GOLD_SEEDS).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
						.add(LootItem.lootTableItem(Items.COD).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));
		
		lootProcessor.accept(locationFor(LandTypes.FROGS, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1)))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.WIND, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.COTTON_CANDY_FAYGO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.LIGHT, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.ORANGE_FAYGO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.CLOCKWORK, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.CANDY_APPLE_FAYGO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.SILENCE, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.FAYGO_COLA).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.THUNDER, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.PEACH_FAYGO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.PULSE, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.BEEF_SWORD).setWeight(4)).when(ConsortLootCondition.builder(EnumConsort.NAKAGATOR))
						.add(LootItem.lootTableItem(MSItems.REDPOP_FAYGO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.THOUGHT, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.MOON_MIST_FAYGO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.BUCKETS, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.MILK_BUCKET).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.CAKE, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.SUGAR).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.RABBITS, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.RABBIT_STEW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.MONSTERS, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.GRAPE_FAYGO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		lootProcessor.accept(locationFor(LandTypes.UNDEAD, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.GRAPE_FAYGO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		lootProcessor.accept(locationFor(LandTypes.TOWERS, MSLootTables.CONSORT_FOOD_STOCK), LootTable.lootTable()
				.withPool(LootPool.lootPool().name(MAIN_POOL).setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(MSItems.CREME_SODA_FAYGO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))))
				.withPool(LootPool.lootPool().name(SPECIAL_POOL).setRolls(ConstantValue.exactly(1))));
		
		
		lootProcessor.accept(MSLootTables.CONSORT_JUNK_REWARD, LootTable.lootTable()
				.withPool(LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(Items.OAK_PLANKS).setWeight(2))
						.add(LootItem.lootTableItem(Items.SPRUCE_PLANKS).setWeight(2))
						.add(LootItem.lootTableItem(Items.BIRCH_PLANKS).setWeight(2))
						.add(LootItem.lootTableItem(Items.JUNGLE_PLANKS).setWeight(2))
						.add(LootItem.lootTableItem(Items.DARK_OAK_PLANKS).setWeight(2))
						.add(LootItem.lootTableItem(Items.ACACIA_PLANKS).setWeight(2))
						.add(LootItem.lootTableItem(Items.WOODEN_AXE).setWeight(5))
						.add(LootItem.lootTableItem(Items.WOODEN_PICKAXE).setWeight(5))
						.add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(5))
						.add(LootItem.lootTableItem(Items.WOODEN_SHOVEL).setWeight(5))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(8))
						.add(LootItem.lootTableItem(MSBlocks.GENERIC_OBJECT).setWeight(10))
						.add(LootItem.lootTableItem(Items.POISONOUS_POTATO).setWeight(10))
						.add(LootItem.lootTableItem(Items.RABBIT_HIDE).setWeight(10))
						.add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(10))
						.add(LootItem.lootTableItem(Items.OAK_STAIRS).setWeight(5))
						.add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(5))
						.add(LootItem.lootTableItem(Items.BIRCH_BUTTON).setWeight(5))
						.add(LootItem.lootTableItem(MSItems.SORD).setWeight(10))
						.add(LootItem.lootTableItem(MSItems.CROWBAR).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.05F, 0.7F))))
						.add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.05F, 0.05F))))
						.add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.05F, 0.05F))))
						.add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.05F, 0.05F))))
						.add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.05F, 0.05F))))
						.add(LootItem.lootTableItem(MSItems.SBAHJ_POSTER).setWeight(10))
						.add(LootItem.lootTableItem(MSItems.CREW_POSTER).setWeight(10))
						.add(LootItem.lootTableItem(MSItems.THRESH_DVD).setWeight(10))
						.add(LootItem.lootTableItem(MSItems.ONION).setWeight(5).when(ConsortLootCondition.builder(EnumConsort.NAKAGATOR)))
						.add(LootItem.lootTableItem(MSItems.TAB).setWeight(5).when(ConsortLootCondition.builder(EnumConsort.SALAMANDER)))
						.add(LootItem.lootTableItem(MSItems.JAR_OF_BUGS).setWeight(5).when(ConsortLootCondition.builder(EnumConsort.TURTLE)))
						.add(LootItem.lootTableItem(MSItems.GRASSHOPPER).setWeight(5).when(ConsortLootCondition.builder(EnumConsort.IGUANA)))));
		
	}
	
	private static CompoundTag waterNBT()
	{
		return Objects.requireNonNull(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER).getTag());
	}
}