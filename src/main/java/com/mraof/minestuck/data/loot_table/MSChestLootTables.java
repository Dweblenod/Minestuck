package com.mraof.minestuck.data.loot_table;

import com.google.common.collect.Sets;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.block.MSBlocks;
import com.mraof.minestuck.item.MSItems;
import com.mraof.minestuck.util.MSTags;
import com.mraof.minestuck.world.lands.LandTypes;
import com.mraof.minestuck.world.lands.terrain.TerrainLandType;
import com.mraof.minestuck.world.lands.title.TitleLandType;
import com.mraof.minestuck.world.storage.loot.LandTableLootEntry;
import com.mraof.minestuck.world.storage.loot.MSLootTables;
import com.mraof.minestuck.world.storage.loot.functions.SetBoondollarCount;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.conditions.Reference;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetDamage;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("WeakerAccess")
public class MSChestLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>
{
	//Pools in basic medium chest
	public static final String WEAPONS_POOL = "weapons", SUPPLIES_POOL = "supplies", MISC_POOL = "misc", RARE_POOL = "rare";
	
	
	@Override
	public void accept(BiConsumer<ResourceLocation, LootTable.Builder> lootProcessor)
	{
		/*lootProcessor.accept(LootTables.CHESTS_PILLAGER_OUTPOST, LootTable.builder()
				.addLootPool(LootPool.builder().rolls(RandomValueRange.of(5.0F, 15.0F))
						.addEntry(ItemLootEntry.builder(MSItems.CANE).weight(10).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.DEUCE_CLUB).weight(10).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
				));*/
		
		/*lootProcessor.accept(locationForExternal(LootTables.CHESTS_PILLAGER_OUTPOST), LootTable.builder()
				.addLootPool(LootPool.builder().name("minestuck").rolls(ConstantRange.of(5))
						.addEntry(ItemLootEntry.builder(MSItems.BLANK_DISK).weight(5).quality(3).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(MSItems.HASHMAP_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.QUEUE_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.QUEUESTACK_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.SET_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.STACK_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.TREE_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
				));*/
		
		lootProcessor.accept(MSLootTables.FROG_TEMPLE_CHEST, LootTable.builder()
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(RandomValueRange.of(3, 8))
						.addEntry(LandTableLootEntry.builder(MSLootTables.FROG_TEMPLE_CHEST).setPool(SUPPLIES_POOL))
						.addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(2).quality(2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 5))))
						.addEntry(ItemLootEntry.builder(Items.DIAMOND).weight(1).quality(3).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.PUMPKIN).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.WRITABLE_BOOK).weight(2).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.BLANK_DISK).weight(5).quality(3).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(MSItems.RAW_CRUXITE).weight(4).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 7))))
						.addEntry(ItemLootEntry.builder(MSItems.RAW_URANIUM).weight(2).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.BUG_ON_A_STICK).weight(4).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.CHOCOLATE_BEETLE).weight(4).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.FOOD_CAN).weight(4).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.GRASSHOPPER).weight(4).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.CONE_OF_FLIES).weight(4).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.GOLDEN_GRASSHOPPER).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(MSBlocks.MINI_FROG_STATUE).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.CARVING_TOOL).weight(5).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(MSItems.STONE_SLAB).weight(2).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(MSItems.HASHMAP_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.QUEUE_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.QUEUESTACK_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.SET_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.STACK_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))
						.addEntry(ItemLootEntry.builder(MSItems.TREE_MODUS_CARD).weight(1).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))))));
		
		
		lootProcessor.accept(MSLootTables.BASIC_MEDIUM_CHEST, LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(LandTableLootEntry.builder(MSLootTables.BASIC_MEDIUM_CHEST).setPool(WEAPONS_POOL))
						.addEntry(ItemLootEntry.builder(Items.BOW).weight(5).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.STONE_SWORD).weight(5).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.IRON_SWORD).weight(3).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.CLAW_HAMMER).weight(5).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.SICKLE).weight(5).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.KATANA).weight(3).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.LIPSTICK).weight(1).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.SLEDGE_HAMMER).weight(3).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.WOODEN_SPOON).weight(5).quality(-2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.SCYTHE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.KNITTING_NEEDLE).weight(4).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.SHURIKEN).weight(8).quality(-2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.METAL_BAT).weight(4).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.MACE).weight(1).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.JOUSTING_LANCE).weight(2).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.FAN).weight(4).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.LUCERNE_HAMMER).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.FORK).weight(4).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(3))
						.addEntry(LandTableLootEntry.builder(MSLootTables.BASIC_MEDIUM_CHEST).setPool(SUPPLIES_POOL))
						.addEntry(ItemLootEntry.builder(Items.ARROW).weight(15).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 16))))
						.addEntry(ItemLootEntry.builder(Items.BREAD).weight(20).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.WHEAT).weight(15).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.APPLE).weight(15).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.EXPERIENCE_BOTTLE).weight(1).quality(2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_APPLE).weight(1).quality(2))
						.addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.BONE).weight(12).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(MSItems.PHLEGM_GUSHERS).weight(7).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(3, 7)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(RandomValueRange.of(2, 4))
						.addEntry(LandTableLootEntry.builder(MSLootTables.BASIC_MEDIUM_CHEST).setPool(MISC_POOL))
						.addEntry(ItemLootEntry.builder(Items.IRON_INGOT).weight(15).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(10).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.REDSTONE).weight(8).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.ENDER_PEARL).weight(2).quality(2))
						.addEntry(ItemLootEntry.builder(MSItems.FLARP_MANUAL).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.SASSACRE_TEXT).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.WISEGUY).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.TABLESTUCK_MANUAL).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.TILLDEATH_HANDBOOK).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.BINARY_CODE).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.NONBINARY_CODE).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.INK_SQUID_PRO_QUO).weight(8).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.BARBASOL).weight(10).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 8))))
						.addEntry(ItemLootEntry.builder(MSBlocks.GENERIC_OBJECT).weight(10).quality(-1))
						.addEntry(ItemLootEntry.builder(MSItems.RAW_CRUXITE).weight(15).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.RAW_URANIUM).weight(12).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4)))))
				.addLootPool(LootPool.builder().name(RARE_POOL).rolls(RandomValueRange.of(0, 1))
						.addEntry(LandTableLootEntry.builder(MSLootTables.BASIC_MEDIUM_CHEST).setPool(RARE_POOL))
						.addEntry(ItemLootEntry.builder(Items.IRON_INGOT).weight(20).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 5))))
						.addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(15).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(0, 4))))
						.addEntry(ItemLootEntry.builder(Items.DIAMOND).weight(15).quality(2).acceptFunction(SetCount.builder(RandomValueRange.of(0, 2))))
						.addEntry(ItemLootEntry.builder(MSItems.GRIMOIRE).weight(3).quality(1))
						.addEntry(ItemLootEntry.builder(MSBlocks.MINI_WIZARD_STATUE).weight(5).quality(1))
						.addEntry(ItemLootEntry.builder(MSBlocks.CASSETTE_PLAYER).weight(1).quality(3))
						.addEntry(ItemLootEntry.builder(MSBlocks.SENDIFICATOR).weight(1).quality(3))
						.addEntry(ItemLootEntry.builder(MSBlocks.TRANSPORTALIZER).weight(2).quality(2))
						.addEntry(ItemLootEntry.builder(MSItems.DICE).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.CUEBALL).weight(1).quality(4))
						.addEntry(ItemLootEntry.builder(MSBlocks.COMPUTER).weight(1).quality(0))
						.addEntry(ItemLootEntry.builder(MSBlocks.LAPTOP).weight(1).quality(0))
						.addEntry(ItemLootEntry.builder(MSBlocks.CHESSBOARD).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.CIGARETTE_LANCE).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.ACE_OF_CLUBS).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.ACE_OF_DIAMONDS).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.ACE_OF_HEARTS).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.ACE_OF_SPADES).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.ENERGY_CORE).weight(10).quality(0)))
				.addLootPool(LootPool.builder().name("boondollars").rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.SORROW_GUSHERS).weight(7).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(3, 7))))
						.addEntry(ItemLootEntry.builder(MSItems.BOONDOLLARS).weight(10).quality(-1).acceptFunction(SetBoondollarCount.builder(RandomValueRange.of(5, 50))))
						.addEntry(ItemLootEntry.builder(MSItems.BOONDOLLARS).weight(5).quality(0).acceptFunction(SetBoondollarCount.builder(RandomValueRange.of(50, 250))))
						.addEntry(ItemLootEntry.builder(MSItems.BOONDOLLARS).weight(2).quality(0).acceptFunction(SetBoondollarCount.builder(RandomValueRange.of(250, 1000))))));
		
		lootProcessor.accept(locationFor(LandTypes.FOREST, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.CANE).weight(10).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.DEUCE_CLUB).weight(10).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.STONE_AXE).weight(10).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.IRON_AXE).weight(5).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.DIAMOND_AXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.OAK_SAPLING).weight(5).quality(-1))
						.addEntry(ItemLootEntry.builder(Items.SPRUCE_SAPLING).weight(5).quality(-1))
						.addEntry(ItemLootEntry.builder(Items.BIRCH_SAPLING).weight(5).quality(-1))
						.addEntry(ItemLootEntry.builder(Items.OAK_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.SPRUCE_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.BIRCH_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.JUNGLE_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.ACACIA_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.DARK_OAK_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.OAK_PLANKS).weight(3).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.SPRUCE_PLANKS).weight(3).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.BIRCH_PLANKS).weight(3).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.OAK_LEAVES).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 15))))));
		lootProcessor.accept(locationFor(LandTypes.TAIGA, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.CANE).weight(10).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.DEUCE_CLUB).weight(10).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.STONE_AXE).weight(10).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.IRON_AXE).weight(5).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.DIAMOND_AXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.OAK_SAPLING).weight(5).quality(-1))
						.addEntry(ItemLootEntry.builder(Items.SPRUCE_SAPLING).weight(5).quality(-1))
						.addEntry(ItemLootEntry.builder(Items.BIRCH_SAPLING).weight(5).quality(-1))
						.addEntry(ItemLootEntry.builder(Items.OAK_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.SPRUCE_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.BIRCH_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.JUNGLE_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.ACACIA_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.DARK_OAK_LOG).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.OAK_PLANKS).weight(3).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.SPRUCE_PLANKS).weight(3).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.BIRCH_PLANKS).weight(3).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.OAK_LEAVES).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 15))))));
		
		lootProcessor.accept(locationFor(LandTypes.FROST, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.FLINT_AND_STEEL).weight(10).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.0F, 0.25F))))
						.addEntry(ItemLootEntry.builder(Items.COAL).weight(8).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.DIAMOND).weight(1).quality(2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.SNOWBALL).weight(20).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 10))))
						.addEntry(ItemLootEntry.builder(Items.SNOW).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.SNOW_BLOCK).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.SPRUCE_SAPLING).weight(15).quality(-1))));
		
		lootProcessor.accept(locationFor(LandTypes.FUNGI, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.PARADISES_PORTABELLO).weight(3).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.SPOREO).weight(6).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(MSItems.MOREL_MUSHROOM).weight(1).quality(1)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.BROWN_MUSHROOM).weight(8).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(2, 8))))
						.addEntry(ItemLootEntry.builder(Items.RED_MUSHROOM).weight(8).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(2, 7))))
						.addEntry(ItemLootEntry.builder(Items.SLIME_BALL).weight(5).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.FUNGAL_SPORE).weight(10).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(6, 11))))));
		
		lootProcessor.accept(locationFor(LandTypes.HEAT, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.BLACKSMITH_HAMMER).weight(2).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.BISICKLE).weight(2).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.TOO_HOT_TO_HANDLE).weight(2).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.COAL).weight(8).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.BLAZE_POWDER).weight(5).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.BLAZE_ROD).weight(3).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.LAVA_BUCKET).weight(6).quality(0)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.MAGMA_CREAM).weight(5).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.BRICK).weight(10).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 8))))
						.addEntry(ItemLootEntry.builder(Items.NETHERRACK).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.OBSIDIAN).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(MSBlocks.CAST_IRON).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 8))))
						.addEntry(ItemLootEntry.builder(MSBlocks.CHISELED_CAST_IRON).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))));
		
		lootProcessor.accept(locationFor(LandTypes.ROCK, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.STONE_PICKAXE).weight(15).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.IRON_PICKAXE).weight(7).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.DIAMOND_PICKAXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.STONE).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 7))))
						.addEntry(ItemLootEntry.builder(Items.COBBLESTONE).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.GRAVEL).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 7))))
						.addEntry(ItemLootEntry.builder(Items.BRICK).weight(8).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 6))))
						.addEntry(ItemLootEntry.builder(Items.BRICKS).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))));
		lootProcessor.accept(locationFor(LandTypes.PETRIFICATION, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.STONE_PICKAXE).weight(15).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.IRON_PICKAXE).weight(7).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.DIAMOND_PICKAXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.STONE).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 7))))
						.addEntry(ItemLootEntry.builder(Items.COBBLESTONE).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.GRAVEL).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 7))))
						.addEntry(ItemLootEntry.builder(Items.BRICK).weight(8).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 6))))
						.addEntry(ItemLootEntry.builder(Items.BRICKS).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))));
		
		lootProcessor.accept(locationFor(LandTypes.SAND, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.CACTACEAE_CUTLASS).weight(4).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_SWORD).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_AXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(6).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_PICKAXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_SHOVEL).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_HOE).weight(2).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.SAND).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.RED_SAND).weight(5).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.SANDSTONE).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 12))))
						.addEntry(ItemLootEntry.builder(Items.SMOOTH_SANDSTONE).weight(6).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.RED_SANDSTONE).weight(3).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.CACTUS).weight(12).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))));
		lootProcessor.accept(locationFor(LandTypes.LUSH_DESERTS, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.CACTACEAE_CUTLASS).weight(4).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_SWORD).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_AXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(6).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_PICKAXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_SHOVEL).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_HOE).weight(2).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.SAND).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.RED_SAND).weight(5).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.SANDSTONE).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 12))))
						.addEntry(ItemLootEntry.builder(Items.SMOOTH_SANDSTONE).weight(6).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.RED_SANDSTONE).weight(3).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.CACTUS).weight(12).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))));
		lootProcessor.accept(locationFor(LandTypes.RED_SAND, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.CACTACEAE_CUTLASS).weight(4).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_SWORD).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_AXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(6).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_PICKAXE).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_SHOVEL).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.GOLDEN_HOE).weight(2).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.RED_SAND).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.SAND).weight(5).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.RED_SANDSTONE).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 12))))
						.addEntry(ItemLootEntry.builder(Items.SMOOTH_RED_SANDSTONE).weight(6).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.SANDSTONE).weight(3).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.CACTUS).weight(12).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))));
		
		lootProcessor.accept(locationFor(LandTypes.SANDSTONE, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(2).quality(2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.REDSTONE).weight(5).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 7))))
						.addEntry(ItemLootEntry.builder(Items.STONE_PICKAXE).weight(8).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.IRON_PICKAXE).weight(3).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.SANDSTONE).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.SMOOTH_SANDSTONE).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 8))))
						.addEntry(ItemLootEntry.builder(Items.CHISELED_SANDSTONE).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.RED_SANDSTONE).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.SAND).weight(8).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))));
		lootProcessor.accept(locationFor(LandTypes.RED_SANDSTONE, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(2).quality(2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.REDSTONE).weight(5).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 7))))
						.addEntry(ItemLootEntry.builder(Items.STONE_PICKAXE).weight(8).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.IRON_PICKAXE).weight(3).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.RED_SANDSTONE).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.SMOOTH_RED_SANDSTONE).weight(10).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 8))))
						.addEntry(ItemLootEntry.builder(Items.CHISELED_RED_SANDSTONE).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.SANDSTONE).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.RED_SAND).weight(8).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))));
		
		lootProcessor.accept(locationFor(LandTypes.SHADE, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.RED_MUSHROOM).weight(10).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.BROWN_MUSHROOM).weight(10).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.MUSHROOM_STEW).weight(5).quality(1))
						.addEntry(ItemLootEntry.builder(MSItems.OIL_BUCKET).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(Items.TORCH).weight(8).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSBlocks.BLUE_DIRT).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(MSBlocks.GLOWING_LOG).weight(6).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(MSBlocks.GLOWING_MUSHROOM).weight(12).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))));
		
		lootProcessor.accept(locationFor(LandTypes.WOOD, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.POGO_HAMMER).weight(3).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.IRON_INGOT).weight(4).quality(2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.REDSTONE).weight(5).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 7))))
						.addEntry(ItemLootEntry.builder(Items.IRON_AXE).weight(3).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.DARK_OAK_PLANKS).weight(5).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 8))))
						.addEntry(ItemLootEntry.builder(Items.JUNGLE_PLANKS).weight(8).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.OAK_PLANKS).weight(8).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(2, 16))))
						.addEntry(ItemLootEntry.builder(Items.DARK_OAK_LOG).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.JUNGLE_LOG).weight(5).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 8))))
						.addEntry(ItemLootEntry.builder(Items.OAK_LOG).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.DARK_OAK_SAPLING).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.JUNGLE_SAPLING).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.OAK_SAPLING).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(MSBlocks.GLOWING_LOG).weight(4).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.COCOA_BEANS).weight(3).quality(0))));
		
		lootProcessor.accept(locationFor(LandTypes.RAINBOW, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.SHEARS).weight(10).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.LEATHER_CHESTPLATE).weight(2).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.LEATHER_HELMET).weight(2).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.LEATHER_LEGGINGS).weight(2).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.LEATHER_BOOTS).weight(2).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(TagLootEntry.func_216176_b(MSTags.Items.FAYGO).weight(1).quality(0)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(TagLootEntry.func_216176_b(Tags.Items.DYES).weight(1).quality(-3).acceptFunction(SetCount.builder(RandomValueRange.of(1, 8))))
						.addEntry(ItemLootEntry.builder(Items.WHITE_WOOL).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(8, 16))))
						.addEntry(ItemLootEntry.builder(Items.GLASS).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(8, 16))))
						.addEntry(ItemLootEntry.builder(Items.TERRACOTTA).weight(2).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(8, 16))))));
		
		lootProcessor.accept(locationFor(LandTypes.FLORA, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.SICKLE).weight(8).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.SHEARS).weight(10).quality(-1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.DANDELION).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.POPPY).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.BLUE_ORCHID).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.ALLIUM).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.AZURE_BLUET).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.RED_TULIP).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.ORANGE_TULIP).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.WHITE_TULIP).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.PINK_TULIP).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.OXEYE_DAISY).weight(3).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.SUNFLOWER).weight(6).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.LILAC).weight(6).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.ROSE_BUSH).weight(6).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.PEONY).weight(6).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.FERN).weight(11).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(MSBlocks.FLOWERY_MOSSY_COBBLESTONE).weight(9).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(8, 24))))
						.addEntry(ItemLootEntry.builder(Items.MOSSY_COBBLESTONE).weight(9).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(8, 24))))));
		
		lootProcessor.accept(locationFor(LandTypes.END, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.STONE_HOE).weight(5).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.ELYTRA).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(Items.ENDER_PEARL).weight(10).quality(1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))))
						.addEntry(ItemLootEntry.builder(Items.CHORUS_FRUIT).weight(10).quality(-5).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.DRAGON_BREATH).weight(1).quality(1))
						.addEntry(ItemLootEntry.builder(Items.SHULKER_SHELL).weight(1).quality(1))
						.addEntry(ItemLootEntry.builder(Items.END_STONE).weight(8).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 16))))
						.addEntry(ItemLootEntry.builder(Items.END_ROD).weight(5).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(MSBlocks.END_GRASS).weight(5).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 16))))
						.addEntry(ItemLootEntry.builder(MSBlocks.COARSE_END_STONE).weight(8).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 6))))));
		
		lootProcessor.accept(locationFor(LandTypes.RAIN, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.BEACON).weight(1).quality(-2))));
		
		lootProcessor.accept(locationFor(LandTypes.FROGS, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.BUG_NET).weight(6).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.GRASSHOPPER).weight(11).quality(-3).acceptFunction(SetCount.builder(RandomValueRange.of(2, 8))))
						.addEntry(ItemLootEntry.builder(MSItems.GOLDEN_GRASSHOPPER).weight(1).quality(2)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.LILY_PAD).weight(9).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 9))))
						.addEntry(ItemLootEntry.builder(MSBlocks.MINI_FROG_STATUE).weight(5).quality(3))
						.addEntry(ItemLootEntry.builder(MSItems.THRESH_DVD).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.WIND, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.POGO_HAMMER).weight(1).quality(2).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.CREW_POSTER).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.LIGHT, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.TORCH).weight(10).quality(-1)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.GLOWSTONE).weight(8).quality(-1))
						.addEntry(ItemLootEntry.builder(MSItems.DICE).weight(8).quality(2))
						.addEntry(ItemLootEntry.builder(Items.GLOWSTONE_DUST).weight(12).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(2, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.SBAHJ_POSTER).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.CLOCKWORK, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.BISICKLE).weight(5).quality(0)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.CLOCK).weight(10).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
						.addEntry(ItemLootEntry.builder(Items.COMPASS).weight(5).quality(0)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.REPEATER).weight(4).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.SBAHJ_POSTER).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.SILENCE, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.NIGHT_CLUB).weight(1).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.PUMPKIN).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.CREW_POSTER).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.THUNDER, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.CREW_POSTER).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.PULSE, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.BEEF).weight(8).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.REPEATER).weight(2).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.RAZOR_BLADE).weight(4).quality(-1))
						.addEntry(ItemLootEntry.builder(MSItems.THRESH_DVD).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.THOUGHT, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.SPEAR_CANE).weight(1).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.BRAIN_JUICE_BUCKET).weight(8).quality(-1)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSBlocks.THOUGHT_DIRT).weight(15).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(2, 10))))
						.addEntry(ItemLootEntry.builder(MSItems.SBAHJ_POSTER).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.BUCKETS, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.BUCKET).weight(10).quality(0))
						.addEntry(ItemLootEntry.builder(Items.WATER_BUCKET).weight(8).quality(0))
						.addEntry(ItemLootEntry.builder(Items.LAVA_BUCKET).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.OIL_BUCKET).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.BLOOD_BUCKET).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.BRAIN_JUICE_BUCKET).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.WATER_COLORS_BUCKET).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.ENDER_BUCKET).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.OBSIDIAN_BUCKET).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.THRESH_DVD).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.CAKE, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.SILVER_SPOON).weight(5).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F))))
						.addEntry(ItemLootEntry.builder(MSItems.FUDGESICKLE).weight(2).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.CAKE).weight(8).quality(-1))
						.addEntry(ItemLootEntry.builder(MSBlocks.REVERSE_CAKE).weight(1).quality(0))
						.addEntry(ItemLootEntry.builder(MSBlocks.APPLE_CAKE).weight(7).quality(-1))
						.addEntry(ItemLootEntry.builder(MSBlocks.COLD_CAKE).weight(5).quality(-2))
						.addEntry(ItemLootEntry.builder(MSBlocks.HOT_CAKE).weight(5).quality(-2))
						.addEntry(ItemLootEntry.builder(MSBlocks.BLUE_CAKE).weight(3).quality(1))
						.addEntry(ItemLootEntry.builder(MSBlocks.RED_CAKE).weight(3).quality(1))
						.addEntry(ItemLootEntry.builder(MSBlocks.NEGATIVE_CAKE).weight(2).quality(1))
						.addEntry(ItemLootEntry.builder(Items.COOKIE).weight(5).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(2, 5))))
						.addEntry(ItemLootEntry.builder(MSItems.CANDY_CORN).weight(3).quality(0)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.SUGAR).weight(8).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.MILK_BUCKET).weight(5).quality(0))
						.addEntry(ItemLootEntry.builder(Items.COCOA_BEANS).weight(3).quality(0))
						.addEntry(ItemLootEntry.builder(MSItems.THRESH_DVD).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.RABBITS, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1)))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.RABBIT).weight(10).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.COOKED_RABBIT).weight(7).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))
						.addEntry(ItemLootEntry.builder(Items.RABBIT_STEW).weight(4).quality(1))
						.addEntry(ItemLootEntry.builder(Items.CARROT).weight(12).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.RABBIT_HIDE).weight(8).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.RABBIT_FOOT).weight(2).quality(1))
						.addEntry(ItemLootEntry.builder(MSBlocks.GOLD_SEEDS).weight(3).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(MSItems.SBAHJ_POSTER).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.MONSTERS, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.SPIKED_CLUB).weight(2).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.ENDER_PEARL).weight(5).quality(2)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.BONE).weight(12).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.GUNPOWDER).weight(8).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.STRING).weight(10).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(MSItems.CREW_POSTER).weight(4).quality(2))));
		lootProcessor.accept(locationFor(LandTypes.UNDEAD, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.SPIKED_CLUB).weight(2).quality(0).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.ENDER_PEARL).weight(5).quality(2)))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).weight(15).quality(-2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.BONE).weight(12).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.GUNPOWDER).weight(8).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(Items.STRING).weight(10).quality(0).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
						.addEntry(ItemLootEntry.builder(MSItems.CREW_POSTER).weight(4).quality(2))));
		
		lootProcessor.accept(locationFor(LandTypes.TOWERS, MSLootTables.BASIC_MEDIUM_CHEST), LootTable.builder()
				.addLootPool(LootPool.builder().name(WEAPONS_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.IRON_CANE).weight(5).quality(1).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.75F, 1.0F)))))
				.addLootPool(LootPool.builder().name(SUPPLIES_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(Items.LADDER).weight(10).quality(-1).acceptFunction(SetCount.builder(RandomValueRange.of(3, 10)))))
				.addLootPool(LootPool.builder().name(MISC_POOL).rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(MSItems.THRESH_DVD).weight(4).quality(2))));
		
		
	}
	
	public static ResourceLocation locationForExternal(ResourceLocation baseLoot)
	{
		return new ResourceLocation(baseLoot.getNamespace(), baseLoot.getPath() + "/minestuck/");
	}
	
	public static ResourceLocation locationFor(TerrainLandType landAspect, ResourceLocation baseLoot)
	{
		ResourceLocation landName = Objects.requireNonNull(landAspect.getRegistryName());
		return new ResourceLocation(baseLoot.getNamespace(), baseLoot.getPath() + "/terrain/" + landName.toString().replace(':', '/'));
	}
	
	public static ResourceLocation locationFor(TitleLandType landAspect, ResourceLocation baseLoot)
	{
		ResourceLocation landName = Objects.requireNonNull(landAspect.getRegistryName());
		return new ResourceLocation(baseLoot.getNamespace(), baseLoot.getPath() + "/title/" + landName.toString().replace(':', '/'));
	}
	
	/*private static final Set<ResourceLocation> DUNGEON_LOOT_INJECT = Sets.newHashSet(LootTables.CHESTS_PILLAGER_OUTPOST);
	
	@SubscribeEvent
	public static void onInjectLoot(LootTableLoadEvent event) {
		if (DUNGEON_LOOT_INJECT.contains(event.getName())) {
			LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(Minestuck.MOD_ID, "injections/dungeon_inject")).weight(1).quality(0)).name("dungeon_inject").build();
			event.getTable().addPool(pool);
		}
	}*/
	
	/*private static final @Nonnull LootConditionManager[] NO_CONDITIONS = new LootConditionManager[0];
	
	private static final @Nonnull
	Set<ResourceLocation> MC_TABLES = new HashSet<>();
	static {
		MC_TABLES.add(LootTables.CHESTS_SIMPLE_DUNGEON);
	}
	
	private static void injectTables(@Nonnull LootTableLoadEvent evt) {
		if (MC_TABLES.contains(evt.getName())) {
			LootPool lp = new LootPool(new LootEntry[0], NO_CONDITIONS, new RandomValueRange(1, 3), new RandomValueRange(0, 0), Minestuck.MOD_NAME);
			addTable(lp, eio(evt.getName()));
			evt.getTable().addPool(lp);
		}
	}
	
	@SubscribeEvent
	public static void onLootTableLoad(@Nonnull LootTableLoadEvent evt)
	{
		
		switch(PersonalConfig.lootGeneration.get())
		{
			case VANILLA:
				injectTables(evt); // fallthrough on purpose
			case DISABLED:
				return;
			case DEVELOPMENT:
			default:
				break;
		}
		
		LootTable table = evt.getTable();
		
		LootPool lp = new LootPool(new LootEntry[0], NO_CONDITIONS, new RandomValueRange(1, 3), new RandomValueRange(0, 0), Minestuck.MOD_NAME);
		
		if(evt.getName().equals(LootTables.CHESTS_SIMPLE_DUNGEON))
		{
			
			lp.addEntry(createLootEntry(Alloy.DARK_STEEL.getStackIngot(), 1, 3, 0.25F));
			lp.addEntry(createLootEntry(itemConduitProbe.getItemNN(), 0.10F));
			lp.addEntry(createLootEntry(Items.QUARTZ, 3, 16, 0.25F));
			lp.addEntry(createLootEntry(Items.NETHER_WART, 1, 4, 0.20F));
			lp.addEntry(createLootEntry(Items.ENDER_PEARL, 1, 2, 0.30F));
			lp.addEntry(createDarkSteelLootEntry(ModObject.itemDarkSteelSword.getItemNN(), 0.1F));
			lp.addEntry(createDarkSteelLootEntry(ModObject.itemDarkSteelBoots.getItemNN(), 0.1F));
			lp.addEntry(createLootEntry(Material.GEAR_WOOD.getStack(), 1, 2, 0.5F));
			lp.addEntry(createLootCapacitor(0.15F));
			lp.addEntry(createLootCapacitor(0.15F));
			lp.addEntry(createLootCapacitor(0.15F));
		}
	}LootTables
	
	private static void addTable(@Nonnull LootPool pool, @Nonnull ResourceLocation resourceLocation) {
		pool.addEntry(new LootEntryTable(resourceLocation, 1, 1, NO_CONDITIONS, resourceLocation.toString()));
	}
	
	private @Nonnull static LootEntry createLootEntry(@Nonnull Item item, float chance) {
		return createLootEntry(item, 1, 1, chance);
	}
	
	private @Nonnull static LootEntry createLootEntry(@Nonnull Item item, int minSize, int maxSize, float chance) {
		return createLootEntry(item, 0, minSize, maxSize, chance);
	}
	
	private @Nonnull static LootEntry createLootEntry(@Nonnull Item item, int meta, int minStackSize, int maxStackSize, float chance) {
		LootCondition[] chanceCond = new LootCondition[] { new RandomChance(chance) };
		final ResourceLocation registryName = NullHelper.notnull(item.getRegistryName(), ERROR_UNREGISTERED_ITEM);
		if (item.isDamageable()) {
			return new LootEntryItem(item, 1, 1, new LootFunction[] { setCount(minStackSize, maxStackSize), setDamage(item, meta), setEnergy() }, chanceCond,
					registryName.toString() + ":" + meta);
		} else {
			return new LootEntryItem(item, 1, 1, new LootFunction[] { setCount(minStackSize, maxStackSize), setMetadata(meta) }, chanceCond,
					registryName.toString() + ":" + meta);
		}
	}
	
	private @Nonnull static LootEntry createLootEntry(@Nonnull ItemStack stack, int minStackSize, int maxStackSize, float chance) {
		LootCondition[] chanceCond = new LootCondition[] { new RandomChance(chance) };
		final ResourceLocation registryName = NullHelper.notnull(stack.getItem().getRegistryName(), ERROR_UNREGISTERED_ITEM);
		return new LootEntryItem(stack.getItem(), 1, 1, new LootFunction[] { setCount(minStackSize, maxStackSize), setMetadata(stack.getMetadata()) }, chanceCond,
				registryName.toString() + ":" + stack.getMetadata());
	}*/
	
}