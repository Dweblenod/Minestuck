package com.mraof.minestuck.computer.editmode;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristTypes;
import com.mraof.minestuck.api.alchemy.ImmutableGristSet;
import com.mraof.minestuck.block.MSBlocks;
import com.mraof.minestuck.computer.editmode.DeployEntry.AvailabilityConditions;
import com.mraof.minestuck.computer.editmode.DeployEntry.ConditionalItem;
import com.mraof.minestuck.computer.editmode.DeployEntry.ConditionalItem.Condition;
import com.mraof.minestuck.computer.editmode.DeployEntry.DeployGristCost;
import com.mraof.minestuck.computer.editmode.DeployList.EntryLists;
import com.mraof.minestuck.item.MSItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This class will be used to keep track of all deployable
 * items accessible by the server.
 *
 * @author kirderf1
 */
public class DeployListProvider implements DataProvider
{
	private final Map<ResourceLocation, DeployEntry> entries = new HashMap<>();
	
	private final PackOutput output;
	private final String modid;
	
	public DeployListProvider(PackOutput output, String modid)
	{
		this.output = output;
		this.modid = modid;
	}
	
	@Override
	public CompletableFuture<?> run(CachedOutput cache)
	{
		registerItems();
		
		Path outputPath = output.getOutputFolder();
		List<CompletableFuture<?>> futures = new ArrayList<>(entries.size());
		
		for(Map.Entry<ResourceLocation, DeployEntry> entry : entries.entrySet())
		{
			Path entryPath = getPath(outputPath, entry.getKey());
			JsonElement jsonData = DeployEntry.CODEC.encodeStart(JsonOps.INSTANCE, entry.getValue())
					.getOrThrow(false, message -> LOGGER.error("Problem encoding deploy list entry {}: {}", entry.getKey(), message));
			futures.add(DataProvider.saveStable(cache, jsonData, entryPath));
		}
		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
	}
	
	private static Path getPath(Path outputPath, ResourceLocation id)
	{
		return outputPath.resolve("data/" + id.getNamespace() + "/minestuck/deploy_list/" + id.getPath() + ".json");
	}
	
	@Override
	public String getName()
	{
		return "Deploy List";
	}
	
	public void registerItems()
	{
		//Deployables
		registerItem("cruxtruder", new ItemStack(MSBlocks.CRUXTRUDER), GristSet.EMPTY, GristTypes.BUILD.get().amount(100), 0, EntryLists.DEPLOY);
		registerItem("totem_lathe", new ItemStack(MSBlocks.TOTEM_LATHE), GristSet.EMPTY, GristTypes.BUILD.get().amount(100), 0, EntryLists.DEPLOY);
		registerItem("artifact_card", 0, AvailabilityConditions.HAS_NOT_ENTERED, new ConditionalItem(Condition.PUNCHED_ENTRY_ITEM_CARD, new ItemStack(MSItems.GENERIC_OBJECT.get())), new DeployGristCost(DeployGristCost.Condition.NO_SECONDARY, GristSet.EMPTY, GristSet.EMPTY), EntryLists.DEPLOY);
		registerItem("alchemiter", new ItemStack(MSBlocks.ALCHEMITER), GristSet.EMPTY, GristTypes.BUILD.get().amount(100), 0, EntryLists.DEPLOY);
		registerItem("punch_designix", 0, AvailabilityConditions.NONE, new ConditionalItem(Condition.NONE, new ItemStack(MSBlocks.PUNCH_DESIGNIX)), new DeployGristCost(DeployGristCost.Condition.FOUR_BASE_GRIST, GristSet.EMPTY, GristSet.EMPTY), EntryLists.DEPLOY);
		registerItem("portable_cruxtruder", GristTypes.BUILD.get().amount(200), 1, AvailabilityConditions.PORTABLE_MACHINES_ENABLED, new ConditionalItem(Condition.MINI_CRUXTRUDER_WITH_COLOR, new ItemStack(MSItems.GENERIC_OBJECT.get())), EntryLists.DEPLOY);
		registerItem("portable_punch_designix", new ItemStack(MSBlocks.MINI_PUNCH_DESIGNIX), GristTypes.BUILD.get().amount(200), AvailabilityConditions.PORTABLE_MACHINES_ENABLED, 1, EntryLists.DEPLOY);
		registerItem("portable_totem_lathe", new ItemStack(MSBlocks.MINI_TOTEM_LATHE), GristTypes.BUILD.get().amount(200), AvailabilityConditions.PORTABLE_MACHINES_ENABLED, 1, EntryLists.DEPLOY);
		registerItem("portable_alchemiter", new ItemStack(MSBlocks.MINI_ALCHEMITER), GristTypes.BUILD.get().amount(300), AvailabilityConditions.PORTABLE_MACHINES_ENABLED, 1, EntryLists.DEPLOY);
		registerItem("holopad", new ItemStack(MSBlocks.HOLOPAD.get()), GristTypes.BUILD.get().amount(4000), 2, EntryLists.DEPLOY);
		registerItem("intellibeam_laserstation", new ItemStack(MSBlocks.INTELLIBEAM_LASERSTATION.get()), GristTypes.BUILD.get().amount(100000), 2, EntryLists.DEPLOY);
		registerItem("card_punched_card", GristTypes.BUILD.get().amount(25), 0, AvailabilityConditions.DEPLOY_CARD_ENABLED, new ConditionalItem(Condition.PUNCHED_ITEM_CARD, new ItemStack(MSItems.CAPTCHA_CARD.get())), EntryLists.DEPLOY);
		
		//Atheneum
		registerItem("cobblestone", new ItemStack(Blocks.COBBLESTONE), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("mossy_cobblestone", new ItemStack(Blocks.MOSSY_COBBLESTONE), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("smooth_stone", new ItemStack(Blocks.SMOOTH_STONE), GristTypes.BUILD.get().amount(1), 2, EntryLists.ATHENEUM);
		registerItem("stone_bricks", new ItemStack(Blocks.STONE_BRICKS), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("chiseled_stone_bricks", new ItemStack(Blocks.CHISELED_STONE_BRICKS), GristTypes.BUILD.get().amount(1), 2, EntryLists.ATHENEUM);
		registerItem("nether_bricks", new ItemStack(Blocks.NETHER_BRICKS), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.TAR.get().amount(1)), 3, EntryLists.ATHENEUM);
		registerItem("chiseled_nether_bricks", new ItemStack(Blocks.CHISELED_NETHER_BRICKS), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.TAR.get().amount(1)), 4, EntryLists.ATHENEUM);
		registerItem("oak_planks", new ItemStack(Blocks.OAK_PLANKS), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("birch_planks", new ItemStack(Blocks.BIRCH_PLANKS), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("spruce_planks", new ItemStack(Blocks.SPRUCE_PLANKS), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("dark_oak_planks", new ItemStack(Blocks.DARK_OAK_PLANKS), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("acacia_planks", new ItemStack(Blocks.ACACIA_PLANKS), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("jungle_planks", new ItemStack(Blocks.JUNGLE_PLANKS), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("torch", new ItemStack(Blocks.TORCH), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("ladder", new ItemStack(Blocks.LADDER), GristTypes.BUILD.get().amount(16), 0, EntryLists.ATHENEUM);
		registerItem("oak_door", new ItemStack(Blocks.OAK_DOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("birch_door", new ItemStack(Blocks.BIRCH_DOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("spruce_door", new ItemStack(Blocks.SPRUCE_DOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("dark_oak_door", new ItemStack(Blocks.DARK_OAK_DOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("acacia_door", new ItemStack(Blocks.ACACIA_DOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("jungle_door", new ItemStack(Blocks.JUNGLE_DOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("oak_trapdoor", new ItemStack(Blocks.OAK_TRAPDOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("birch_trapdoor", new ItemStack(Blocks.BIRCH_TRAPDOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("spruce_trapdoor", new ItemStack(Blocks.SPRUCE_TRAPDOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("dark_oak_trapdoor", new ItemStack(Blocks.DARK_OAK_TRAPDOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("acacia_trapdoor", new ItemStack(Blocks.ACACIA_TRAPDOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("jungle_trapdoor", new ItemStack(Blocks.JUNGLE_TRAPDOOR), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("glass", new ItemStack(Blocks.GLASS), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("white_stained_glass", new ItemStack(Blocks.WHITE_STAINED_GLASS), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM); //Stained glass and stained glass panes' atheneum costs are given an amount of build grist equal to the number of grist types their normal costs have. To offset farming potential, it has a connection-tier of two.
		registerItem("orange_stained_glass", new ItemStack(Blocks.ORANGE_STAINED_GLASS), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("magenta_stained_glass", new ItemStack(Blocks.MAGENTA_STAINED_GLASS), GristTypes.BUILD.get().amount(4), 2, EntryLists.ATHENEUM);
		registerItem("light_blue_stained_glass", new ItemStack(Blocks.LIGHT_BLUE_STAINED_GLASS), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("yellow_stained_glass", new ItemStack(Blocks.YELLOW_STAINED_GLASS), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("lime_stained_glass", new ItemStack(Blocks.LIME_STAINED_GLASS), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("pink_stained_glass", new ItemStack(Blocks.PINK_STAINED_GLASS), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("gray_stained_glass", new ItemStack(Blocks.GRAY_STAINED_GLASS), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("light_gray_stained_glass", new ItemStack(Blocks.LIGHT_GRAY_STAINED_GLASS), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("cyan_stained_glass", new ItemStack(Blocks.CYAN_STAINED_GLASS), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("purple_stained_glass", new ItemStack(Blocks.PURPLE_STAINED_GLASS), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("blue_stained_glass", new ItemStack(Blocks.BLUE_STAINED_GLASS), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("brown_stained_glass", new ItemStack(Blocks.BROWN_STAINED_GLASS), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("green_stained_glass", new ItemStack(Blocks.GREEN_STAINED_GLASS), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("red_stained_glass", new ItemStack(Blocks.RED_STAINED_GLASS), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("black_stained_glass", new ItemStack(Blocks.BLACK_STAINED_GLASS), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("glass_pane", new ItemStack(Blocks.GLASS_PANE), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("white_stained_glass_pane", new ItemStack(Blocks.WHITE_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("orange_stained_glass_pane", new ItemStack(Blocks.ORANGE_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("magenta_stained_glass_pane", new ItemStack(Blocks.MAGENTA_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(4), 2, EntryLists.ATHENEUM);
		registerItem("light_blue_stained_glass_pane", new ItemStack(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("yellow_stained_glass_pane", new ItemStack(Blocks.YELLOW_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("lime_stained_glass_pane", new ItemStack(Blocks.LIME_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("pink_stained_glass_pane", new ItemStack(Blocks.PINK_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("gray_stained_glass_pane", new ItemStack(Blocks.GRAY_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("light_gray_stained_glass_pane", new ItemStack(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("cyan_stained_glass_pane", new ItemStack(Blocks.CYAN_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("purple_stained_glass_pane", new ItemStack(Blocks.PURPLE_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("blue_stained_glass_pane", new ItemStack(Blocks.BLUE_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("brown_stained_glass_pane", new ItemStack(Blocks.BROWN_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(3), 2, EntryLists.ATHENEUM);
		registerItem("green_stained_glass_pane", new ItemStack(Blocks.GREEN_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("red_stained_glass_pane", new ItemStack(Blocks.RED_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("black_stained_glass_pane", new ItemStack(Blocks.BLACK_STAINED_GLASS_PANE), GristTypes.BUILD.get().amount(2), 2, EntryLists.ATHENEUM);
		registerItem("cobblestone_slab", new ItemStack(Blocks.COBBLESTONE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("mossy_cobblestone_slab", new ItemStack(Blocks.MOSSY_COBBLESTONE_SLAB), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("smooth_stone_slab", new ItemStack(Blocks.SMOOTH_STONE_SLAB), GristTypes.BUILD.get().amount(1), 2, EntryLists.ATHENEUM);
		registerItem("stone_slab", new ItemStack(Blocks.STONE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("stone_brick_slab", new ItemStack(Blocks.STONE_BRICK_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("nether_brick_slab", new ItemStack(Blocks.NETHER_BRICK_SLAB), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.TAR.get().amount(1)), 3, EntryLists.ATHENEUM);
		registerItem("cobblestone_stairs", new ItemStack(Blocks.COBBLESTONE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("mossy_cobblestone_stairs", new ItemStack(Blocks.MOSSY_COBBLESTONE_STAIRS), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("stone_stairs", new ItemStack(Blocks.STONE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("stone_brick_stairs", new ItemStack(Blocks.STONE_BRICK_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("nether_brick_stairs", new ItemStack(Blocks.NETHER_BRICK_STAIRS), GristSet.of(GristTypes.BUILD.get().amount(2), GristTypes.TAR.get().amount(2)), 3, EntryLists.ATHENEUM);
		registerItem("cobblestone_wall", new ItemStack(Blocks.COBBLESTONE_WALL), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("mossy_cobblestone_wall", new ItemStack(Blocks.MOSSY_COBBLESTONE_WALL), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("stone_brick_wall", new ItemStack(Blocks.STONE_BRICK_WALL), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("nether_brick_wall", new ItemStack(Blocks.NETHER_BRICK_WALL), GristSet.of(GristTypes.BUILD.get().amount(2), GristTypes.TAR.get().amount(2)), 3, EntryLists.ATHENEUM);
		registerItem("nether_brick_fence", new ItemStack(Blocks.NETHER_BRICK_FENCE), GristSet.of(GristTypes.BUILD.get().amount(2), GristTypes.TAR.get().amount(2)), 3, EntryLists.ATHENEUM);
		registerItem("oak_slab", new ItemStack(Blocks.OAK_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("birch_slab", new ItemStack(Blocks.BIRCH_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("spruce_slab", new ItemStack(Blocks.SPRUCE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("dark_oak_slab", new ItemStack(Blocks.DARK_OAK_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("acacia_slab", new ItemStack(Blocks.ACACIA_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("jungle_slab", new ItemStack(Blocks.JUNGLE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("oak_stairs", new ItemStack(Blocks.OAK_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("birch_stairs", new ItemStack(Blocks.BIRCH_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("spruce_stairs", new ItemStack(Blocks.SPRUCE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("dark_oak_stairs", new ItemStack(Blocks.DARK_OAK_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("acacia_stairs", new ItemStack(Blocks.ACACIA_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("jungle_stairs", new ItemStack(Blocks.JUNGLE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("oak_fence", new ItemStack(Blocks.OAK_FENCE), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("birch_fence", new ItemStack(Blocks.BIRCH_FENCE), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("spruce_fence", new ItemStack(Blocks.SPRUCE_FENCE), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("acacia_fence", new ItemStack(Blocks.ACACIA_FENCE), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("dark_oak_fence", new ItemStack(Blocks.DARK_OAK_FENCE), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("jungle_fence", new ItemStack(Blocks.JUNGLE_FENCE), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("oak_log", new ItemStack(Blocks.OAK_LOG), GristTypes.BUILD.get().amount(4), 0, EntryLists.ATHENEUM);
		registerItem("birch_log", new ItemStack(Blocks.BIRCH_LOG), GristTypes.BUILD.get().amount(4), 0, EntryLists.ATHENEUM);
		registerItem("spruce_log", new ItemStack(Blocks.SPRUCE_LOG), GristTypes.BUILD.get().amount(4), 0, EntryLists.ATHENEUM);
		registerItem("dark_oak_log", new ItemStack(Blocks.DARK_OAK_LOG), GristTypes.BUILD.get().amount(4), 0, EntryLists.ATHENEUM);
		registerItem("acacia_log", new ItemStack(Blocks.ACACIA_LOG), GristTypes.BUILD.get().amount(4), 0, EntryLists.ATHENEUM);
		registerItem("jungle_log", new ItemStack(Blocks.JUNGLE_LOG), GristTypes.BUILD.get().amount(4), 0, EntryLists.ATHENEUM);
		registerItem("andesite", new ItemStack(Blocks.ANDESITE), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("diorite", new ItemStack(Blocks.DIORITE), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("granite", new ItemStack(Blocks.GRANITE), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("cobbled_deepslate", new ItemStack(Blocks.COBBLED_DEEPSLATE), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("polished_andesite", new ItemStack(Blocks.POLISHED_ANDESITE), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("polished_diorite", new ItemStack(Blocks.POLISHED_DIORITE), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("polished_granite", new ItemStack(Blocks.POLISHED_GRANITE), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("polished_deepslate", new ItemStack(Blocks.POLISHED_DEEPSLATE), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("deepslate_bricks", new ItemStack(Blocks.DEEPSLATE_BRICKS), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("deepslate_tiles", new ItemStack(Blocks.DEEPSLATE_TILES), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("chiseled_deepslate", new ItemStack(Blocks.CHISELED_DEEPSLATE), GristTypes.BUILD.get().amount(1), 2, EntryLists.ATHENEUM);
		registerItem("andesite_slab", new ItemStack(Blocks.ANDESITE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("diorite_slab", new ItemStack(Blocks.DIORITE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("granite_slab", new ItemStack(Blocks.GRANITE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("cobbled_deepslate_slab", new ItemStack(Blocks.COBBLED_DEEPSLATE_SLAB), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("polished_andesite_slab", new ItemStack(Blocks.POLISHED_ANDESITE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("polished_diorite_slab", new ItemStack(Blocks.POLISHED_DIORITE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("polished_granite_slab", new ItemStack(Blocks.POLISHED_GRANITE_SLAB), GristTypes.BUILD.get().amount(1), 0, EntryLists.ATHENEUM);
		registerItem("polished_deepslate_slab", new ItemStack(Blocks.POLISHED_DEEPSLATE_SLAB), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("deepslate_brick_slab", new ItemStack(Blocks.DEEPSLATE_BRICK_SLAB), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("deepslate_tile_slab", new ItemStack(Blocks.DEEPSLATE_TILE_SLAB), GristTypes.BUILD.get().amount(1), 1, EntryLists.ATHENEUM);
		registerItem("andesite_stairs", new ItemStack(Blocks.ANDESITE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("diorite_stairs", new ItemStack(Blocks.DIORITE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("granite_stairs", new ItemStack(Blocks.GRANITE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("cobbled_deepslate_stairs", new ItemStack(Blocks.COBBLED_DEEPSLATE_STAIRS), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("polished_andesite_stairs", new ItemStack(Blocks.POLISHED_ANDESITE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("polished_diorite_stairs", new ItemStack(Blocks.POLISHED_DIORITE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("polished_granite_stairs", new ItemStack(Blocks.POLISHED_GRANITE_STAIRS), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("polished_deepslate_stairs", new ItemStack(Blocks.POLISHED_DEEPSLATE_STAIRS), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("deepslate_brick_stairs", new ItemStack(Blocks.DEEPSLATE_BRICK_STAIRS), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("deepslate_tile_stairs", new ItemStack(Blocks.DEEPSLATE_TILE_STAIRS), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("andesite_wall", new ItemStack(Blocks.ANDESITE_WALL), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("diorite_wall", new ItemStack(Blocks.DIORITE_WALL), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("granite_wall", new ItemStack(Blocks.GRANITE_WALL), GristTypes.BUILD.get().amount(2), 0, EntryLists.ATHENEUM);
		registerItem("cobbled_deepslate_wall", new ItemStack(Blocks.COBBLED_DEEPSLATE_WALL), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("polished_deepslate_wall", new ItemStack(Blocks.POLISHED_DEEPSLATE_WALL), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("deepslate_brick_wall", new ItemStack(Blocks.DEEPSLATE_BRICK_WALL), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("deepslate_tile_wall", new ItemStack(Blocks.DEEPSLATE_TILE_WALL), GristTypes.BUILD.get().amount(2), 1, EntryLists.ATHENEUM);
		registerItem("white_concrete", new ItemStack(Blocks.WHITE_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.CHALK.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("orange_concrete", new ItemStack(Blocks.ORANGE_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.AMBER.get().amount(1), GristTypes.GARNET.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("magenta_concrete", new ItemStack(Blocks.MAGENTA_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.AMETHYST.get().amount(1), GristTypes.GARNET.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("light_blue_concrete", new ItemStack(Blocks.LIGHT_BLUE_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.CHALK.get().amount(1), GristTypes.AMETHYST.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("yellow_concrete", new ItemStack(Blocks.YELLOW_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.AMBER.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("lime_concrete", new ItemStack(Blocks.LIME_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.CHALK.get().amount(1), GristTypes.AMBER.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("pink_concrete", new ItemStack(Blocks.PINK_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.CHALK.get().amount(1), GristTypes.GARNET.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("gray_concrete", new ItemStack(Blocks.GRAY_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.CHALK.get().amount(1), GristTypes.TAR.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("light_gray_concrete", new ItemStack(Blocks.LIGHT_GRAY_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.CHALK.get().amount(1), GristTypes.TAR.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("cyan_concrete", new ItemStack(Blocks.CYAN_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.AMBER.get().amount(1), GristTypes.AMETHYST.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("purple_concrete", new ItemStack(Blocks.PURPLE_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.GARNET.get().amount(1), GristTypes.AMETHYST.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("blue_concrete", new ItemStack(Blocks.BLUE_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.AMETHYST.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("brown_concrete", new ItemStack(Blocks.BROWN_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.AMBER.get().amount(1), GristTypes.IODINE.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("green_concrete", new ItemStack(Blocks.GREEN_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.AMBER.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("red_concrete", new ItemStack(Blocks.RED_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.GARNET.get().amount(1)), 2, EntryLists.ATHENEUM);
		registerItem("black_concrete", new ItemStack(Blocks.BLACK_CONCRETE), GristSet.of(GristTypes.BUILD.get().amount(1), GristTypes.COBALT.get().amount(1), GristTypes.TAR.get().amount(1)), 2, EntryLists.ATHENEUM);
		
	}
	
	public void registerItem(String name, ItemStack stack, ImmutableGristSet cost, int tier, EntryLists entryList)
	{
		registerItem(name, stack, cost, cost, AvailabilityConditions.NONE, tier, entryList);
	}
	
	public void registerItem(String name, ItemStack stack, ImmutableGristSet cost1, ImmutableGristSet cost2, int tier, EntryLists entryList)
	{
		registerItem(name, cost1, cost2, tier, AvailabilityConditions.NONE, new ConditionalItem(Condition.NONE, stack), entryList);
	}
	
	public void registerItem(String name, ItemStack stack, ImmutableGristSet cost, AvailabilityConditions condition, int tier, EntryLists entryList)
	{
		registerItem(name, cost, cost, tier, condition, new ConditionalItem(Condition.NONE, stack), entryList);
	}
	
	/**
	 * Register the specific item as deployable.
	 *
	 * @param stack     The item to be registered.
	 *                  The itemstack can have nbt tags, with the exception of the display tag.
	 * @param cost1     How much it costs the first time deployed.
	 * @param cost2     How much it costs after the first times. Null if only deployable once.
	 *                  First cost will always be used when not in hardmode.
	 * @param tier      The tier of the item; what connection position required in an unfinished chain to deploy.
	 *                  All will be available to all players when the chain is complete.
	 * @param entryList Enum defining which list the item is in. (I.E. Deployables or Atheneum).
	 *                  You cannot directly register items to EntryLists.ALL, as it is simply a list of all entries, regardless of category.
	 */
	public void registerItem(String name, ItemStack stack, ImmutableGristSet cost1, ImmutableGristSet cost2, AvailabilityConditions condition, int tier, EntryLists entryList)
	{
		registerItem(name, cost1, cost2, tier, condition, new ConditionalItem(Condition.NONE, stack), entryList);
	}
	
	public void registerItem(String name, ImmutableGristSet cost, int tier, AvailabilityConditions condition,
							 ConditionalItem item, EntryLists entryList)
	{
		registerItem(name, tier, condition, item, new DeployGristCost(DeployGristCost.Condition.NONE, cost, cost), entryList);
	}
	
	public void registerItem(String name, ImmutableGristSet cost1, ImmutableGristSet cost2, int tier, AvailabilityConditions condition,
							 ConditionalItem item, EntryLists entryList)
	{
		registerItem(name, tier, condition, item, new DeployGristCost(DeployGristCost.Condition.NONE, cost1, cost2), entryList);
	}
	
	public void registerItem(String name, int tier, AvailabilityConditions condition, ConditionalItem item,
							 DeployGristCost grist, EntryLists entryList)
	{
		if(entryList == EntryLists.ALL)
			throw new IllegalArgumentException("Not allowed to add items to allList directly!");
		if(containsEntry(name))
			throw new IllegalStateException("Item stack already added to the deploy list: " + name);
		
		DeployEntry entry = new DeployEntry(name, tier, condition, item, grist, entryList);
		//entryList.getList().add(entry);
		//allList.add(entry);
		add(entry, new ResourceLocation(modid, name));
	}
	
	protected void add(DeployEntry entry, ResourceLocation name)
	{
		entries.put(name, entry);
	}
	
	public boolean containsEntry(String name)
	{
		for(DeployEntry entry : entries.values())
			if(entry.name().equals(name))
				return true;
		return false;
	}
	
	/*public boolean containsEntry(String name, EntryLists entryList)
	{
		return getEntryForName(name, entryList) != null;
	}
	
	public static DeployEntry getEntryForName(String name)
	{
		return getEntryForName(name, EntryLists.ALL);
	}
	
	public static DeployEntry getEntryForName(String name, EntryLists entryList)
	{
		for(DeployEntry entry : entryList.getList())
			if(entry.getName().equals(name))
				return entry;
		return null;
	}*/
}