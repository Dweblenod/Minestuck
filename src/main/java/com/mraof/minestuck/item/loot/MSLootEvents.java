package com.mraof.minestuck.item.loot;

import com.google.common.collect.Sets;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.MSItems;
import com.mraof.minestuck.util.MSTags;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Minestuck.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MSLootEvents
{
	private static final Set<ResourceLocation> LOOT_INJECT = Sets.newHashSet(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.DESERT_PYRAMID, BuiltInLootTables.JUNGLE_TEMPLE, BuiltInLootTables.WOODLAND_MANSION, BuiltInLootTables.UNDERWATER_RUIN_BIG, BuiltInLootTables.SPAWN_BONUS_CHEST);
	
	@SubscribeEvent
	public static void onLootLoad(LootTableLoadEvent event) //created using Upgrade Aquatic "LootEvents" and Mystical World "LootHandler" for reference
	{
		if(LOOT_INJECT.contains(event.getName()))
		{
			LootPool pool = LootPool.lootPool().add(LootTableReference.lootTableReference(MSLootTables.BLANK_DISK_DUNGEON_LOOT_INJECT)).name("blank_disk_dungeon_loot_inject").build();
			event.getTable().addPool(pool);
		}
	}
	
	@SubscribeEvent
	public static void addCustomVillagerTrade(VillagerTradesEvent event)
	{
		//TODO test in server setting for bugs
		Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
		
		if(event.getType() == VillagerProfession.CARTOGRAPHER)
		{
			trades.get(1).add((villager, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 8), new ItemStack(Items.COMPASS), createFrogTempleMap(villager), 12, 7, 0.2F));
		}
		
		if(event.getType() == VillagerProfession.LEATHERWORKER)
		{
			trades.get(3).add((villager, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(MSItems.CRUMPLY_HAT.get()), 5, 3, 0.2F));
		}
		
		if(event.getType() == VillagerProfession.MASON)
		{
			trades.get(2).add((villager, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(MSItems.CARVING_TOOL.get()), 4, 2, 0.05F));
			trades.get(2).add((villager, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(MSItems.STONE_TABLET.get()), 6, 2, 0.05F));
		}
		
		if(event.getType() == VillagerProfession.WEAPONSMITH)
		{
			trades.get(2).add((villager, random) -> createEnchantedItemOffer(random, new ItemStack(MSItems.LUCERNE_HAMMER.get()), 4, 3, 1, 0.05F));
		}
		
		if(event.getType() == VillagerProfession.LIBRARIAN)
		{
			ItemStack[] bookTypes = new ItemStack[]{new ItemStack(MSItems.SBURB_CODE.get()), new ItemStack(MSItems.NONBINARY_CODE.get()), new ItemStack(MSItems.BINARY_CODE.get()), new ItemStack(MSItems.TILLDEATH_HANDBOOK.get()), new ItemStack(MSItems.TABLESTUCK_MANUAL.get()), new ItemStack(MSItems.WISEGUY.get()), new ItemStack(MSItems.SASSACRE_TEXT.get()), new ItemStack(MSItems.FLARP_MANUAL.get())};
			trades.get(2).add((villager, random) -> new MerchantOffer(bookTypes[random.nextInt(bookTypes.length)], new ItemStack(Items.EMERALD, 3), 4, 2, 0.05F)); //TODO look into a way to only get this to occur if the librarian is in a Minestuck dimension or is trading with an entered player
		}
	}
	
	/**
	 * Checks for nearby frog temples and creates a map based on an un-generated one if it can be found, seems to cause momentary lag because of this stage at which the map's data is being collected,
	 * uses TreasureMapForEmeralds in {@link VillagerTrades} as a base
	 */
	public static ItemStack createFrogTempleMap(Entity villagerEntity)
	{
		Level level = villagerEntity.level;
		if(level instanceof ServerLevel serverLevel)
		{
			BlockPos templePos = serverLevel.findNearestMapFeature(MSTags.Structures.SCANNER_LOCATED, villagerEntity.blockPosition(), 100, true);
			if(templePos != null)
			{
				ItemStack itemstack = MapItem.create(serverLevel, templePos.getX(), templePos.getZ(), (byte) 2, true, true);
				MapItem.renderBiomePreviewMap(serverLevel, itemstack);
				MapItemSavedData.addTargetDecoration(itemstack, templePos, "+", MapDecoration.Type.RED_X);
				itemstack.setHoverName(new TranslatableComponent("filled_map." + MSTags.Structures.SCANNER_LOCATED.toString().toLowerCase(Locale.ROOT))); //TODO have a proper name show up, either make a translatable text here or in lang file
				
				return itemstack;
			}
		}
		
		return new ItemStack(MSItems.DICE.get());
	}
	
	/**
	 * Creates a enchanted weapon, uses EnchantedItemForEmeraldsTrade in {@link VillagerTrades} as a base
	 */
	public static MerchantOffer createEnchantedItemOffer(Random random, ItemStack weaponStack, int baseEmeraldCost, int maxUses, int villagerXp, float priceMultiplier)
	{
		int emeraldCostMod = 5 + random.nextInt(15);
		EnchantmentHelper.enchantItem(random, weaponStack, emeraldCostMod, false);
		int emeraldCost = Math.min(baseEmeraldCost + emeraldCostMod, 64);
		ItemStack emeraldStack = new ItemStack(Items.EMERALD, emeraldCost);
		return new MerchantOffer(emeraldStack, weaponStack, maxUses, villagerXp, priceMultiplier);
	}
	
	@SubscribeEvent
	public static void addCustomWanderingVillagerTrade(WandererTradesEvent event)
	{
		List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
		//between the two new trades, there is an approximately 25% chance for a blank disk to appear in a wandering trader slot(as of 1.16 with no further modifications to the trades)
		trades.add(1, (villager, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(MSItems.BLANK_DISK.get()), 3, 12, 0.05F));
		trades.add(1, (villager, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(MSItems.BLANK_DISK.get()), 3, 12, 0.05F));
	}
}
