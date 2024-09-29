package com.mraof.minestuck.computer.editmode;

import com.google.common.collect.ImmutableList;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.skaianet.SburbHandler;
import com.mraof.minestuck.skaianet.SburbPlayerData;
import com.mraof.minestuck.skaianet.SkaianetData;
import com.mraof.minestuck.world.storage.MSExtraData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.BiFunction;

/**
 * This class will be used to keep track of all deployable
 * items accessible by the server.
 *
 * @author kirderf1
 */
@Mod.EventBusSubscriber(modid = Minestuck.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DeployList
{
	private final List<DeployEntry> entries;
	
	public enum EntryLists implements StringRepresentable
	{
		ALL,
		DEPLOY,
		ATHENEUM;
		
		public static final Codec<EntryLists> CODEC = StringRepresentable.fromEnum(EntryLists::values);
		
		@Override
		public String getSerializedName()
		{
			return name().toLowerCase(Locale.ROOT);
		}
	}
	
	public List<DeployEntry> getItemList(MinecraftServer server, SburbPlayerData playerData)
	{
		return getItemList(server, playerData, EntryLists.ALL);
	}
	
	public List<DeployEntry> getItemList(MinecraftServer server, SburbPlayerData playerData, EntryLists entryList)
	{
		int tier = SburbHandler.availableTier(server, playerData.playerId());
		ArrayList<DeployEntry> itemList = new ArrayList<>();
		for(DeployEntry entry : entries.stream().filter(entry -> entry.category() == entryList).toList())
			if(entry.isAvailable(playerData, tier))
				itemList.add(entry);
		
		return itemList;
	}
	
	@Nonnull
	static ItemStack cleanStack(ItemStack stack)
	{
		if(stack.isEmpty())
			return ItemStack.EMPTY;
		stack = stack.copy();
		stack.setCount(1);
		if(stack.hasTag() && stack.getTag().isEmpty())
			stack.setTag(null);
		return stack;
	}
	
	public static boolean containsItemStack(ItemStack stack, SburbPlayerData playerData, Level level)
	{
		return containsItemStack(stack, playerData, level, EntryLists.ALL);
	}
	
	public static boolean containsItemStack(ItemStack stack, SburbPlayerData playerData, Level level, EntryLists entryList)
	{
		return getEntryForItem(stack, playerData, level, entryList) != null;
	}
	
	public static DeployEntry getEntryForItem(ItemStack stack, SburbPlayerData playerData, Level level)
	{
		return getEntryForItem(stack, playerData, level, EntryLists.ALL);
	}
	
	public static DeployEntry getEntryForItem(ItemStack stack, SburbPlayerData playerData, Level level, EntryLists entryList)
	{
		List<DeployEntry> entries = playerData.getDeployList().entries;
		stack = cleanStack(stack);
		for(DeployEntry entry : entries.stream().filter(entry -> entry.category() == entryList).toList())
			if(ItemStack.matches(stack, entry.getItemStack(playerData, level)))
				return entry;
		return null;
	}
	
	public static BiFunction<SburbPlayerData, Level, ItemStack> item(ItemLike item)
	{
		return (playerData, world) -> new ItemStack(item);
	}
	
	static CompoundTag getDeployListTag(MinecraftServer server, SburbPlayerData playerData)
	{
		CompoundTag nbt = new CompoundTag();
		ListTag tagList = new ListTag();
		nbt.put("l", tagList);
		int tier = SburbHandler.availableTier(server, playerData.playerId());
		List<DeployEntry> entries = playerData.getDeployList().entries;
		for(int i = 0; i < entries.size(); i++)
		{
			DeployEntry entry = entries.get(i);
			entry.tryAddDeployTag(playerData, server.getLevel(Level.OVERWORLD), tier, tagList, i);
		}
		return nbt;
	}
	
	private static long lastDay;
	
	@SubscribeEvent
	public static void serverStarting(ServerStartingEvent event)
	{
		lastDay = event.getServer().overworld().getGameTime() / 24000L;
	}
	
	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.END && !MinestuckConfig.SERVER.hardMode.get())
		{
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			long currentDay = server.overworld().getGameTime() / 24000L;
			if(currentDay != lastDay)
			{
				lastDay = currentDay;
				SkaianetData.get(server).allPlayerData().forEach(SburbPlayerData::resetGivenItems);
			}
		}
	}
	
	private DeployList(List<DeployEntry> entries)
	{
		this.entries = entries;
	}
	
	private static DeployList INSTANCE;
	
	public static DeployList getInstance()
	{
		return Objects.requireNonNull(INSTANCE);
	}
	
	@SubscribeEvent
	private static void onResourceReload(AddReloadListenerEvent event)
	{
		event.addListener(new DeployList.Loader());
	}
	
	@SubscribeEvent
	private static void onServerStopped(ServerStoppedEvent event)
	{
		INSTANCE = null;
	}
	
	private static final class Loader extends SimpleJsonResourceReloadListener
	{
		private static final Logger LOGGER = LogManager.getLogger();
		
		Loader()
		{
			super(new GsonBuilder().create(), "minestuck/deploy_list");
		}
		
		@Override
		protected void apply(Map<ResourceLocation, JsonElement> jsonEntries, ResourceManager resourceManager, ProfilerFiller profiler)
		{
			ImmutableList.Builder<DeployEntry> entries = ImmutableList.builder();
			for(Map.Entry<ResourceLocation, JsonElement> entry : jsonEntries.entrySet())
			{
				DeployEntry.CODEC.parse(JsonOps.INSTANCE, entry.getValue())
						.resultOrPartial(message -> LOGGER.error("Couldn't parse deploy list entry {}: {}", entry.getKey(), message))
						.ifPresent(entries::add);
			}
			
			INSTANCE = new DeployList(entries.build());
			LOGGER.info("Loaded {} deploy list entries", INSTANCE.entries.size());
		}
	}
	
	/**
	 * Should be called any time that the conditions of deploy list entries might have changed for players.
	 */
	public static void onConditionsUpdated(MinecraftServer server)
	{
		MSExtraData.get(server).forEach(EditData::sendGivenItemsToEditor);
	}
	
	public static List<ItemStack> getEditmodeTools()
	{
		return Collections.emptyList();
	}
}