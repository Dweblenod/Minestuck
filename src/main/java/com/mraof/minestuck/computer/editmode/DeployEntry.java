package com.mraof.minestuck.computer.editmode;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.alchemy.AlchemyHelper;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.ImmutableGristSet;
import com.mraof.minestuck.item.block.MiniCruxtruderItem;
import com.mraof.minestuck.skaianet.SburbHandler;
import com.mraof.minestuck.skaianet.SburbPlayerData;
import com.mraof.minestuck.util.ColorHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Predicate;

public record DeployEntry(String name, int tier, AvailabilityConditions condition, ConditionalItem item,
						  DeployGristCost grist, DeployList.EntryLists category)
{
	public static final Codec<DeployEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("name").forGetter(DeployEntry::name),
			Codec.INT.fieldOf("tier").forGetter(DeployEntry::tier),
			AvailabilityConditions.CODEC.fieldOf("condition").forGetter(DeployEntry::condition),
			ConditionalItem.CODEC.fieldOf("item").forGetter(DeployEntry::item),
			DeployGristCost.CODEC.fieldOf("grist").forGetter(DeployEntry::grist),
			DeployList.EntryLists.CODEC.fieldOf("category").forGetter(DeployEntry::category)
	).apply(instance, DeployEntry::new));
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public boolean isAvailable(SburbPlayerData playerData, int tier)
	{
		return (condition == null || condition.test(playerData)) && this.tier <= tier && getCurrentCost(playerData) != null;
	}
	
	public ItemStack getItemStack(SburbPlayerData playerData, Level level)
	{
		return item.apply(playerData, level).copy();
	}
	
	@Nullable
	public GristSet getCurrentCost(SburbPlayerData playerData)
	{
		boolean usePrimaryCost = !playerData.hasGivenItem(this);
		return grist.condition.apply(playerData, grist.primaryCost, grist.secondaryCost, usePrimaryCost);
	}
	
	void tryAddDeployTag(SburbPlayerData playerData, Level level, int tier, ListTag list, int i)
	{
		if(isAvailable(playerData, tier))
		{
			ItemStack stack = getItemStack(playerData, level);
			GristSet cost = getCurrentCost(playerData);
			CompoundTag tag = new CompoundTag();
			stack.save(tag);
			tag.putInt("i", i);
			tag.put("cost", ImmutableGristSet.LIST_CODEC.encodeStart(NbtOps.INSTANCE, cost.asImmutable()).getOrThrow(false, LOGGER::error));
			tag.putInt("cat", category.ordinal());
			list.add(tag);
		}
	}
	
	public enum AvailabilityConditions implements StringRepresentable
	{
		NONE(data -> true),
		HAS_NOT_ENTERED(data -> !data.hasEntered()),
		PORTABLE_MACHINES_ENABLED(data -> MinestuckConfig.SERVER.portableMachines.get()),
		DEPLOY_CARD_ENABLED(data -> MinestuckConfig.SERVER.deployCard.get());
		
		public static final Codec<AvailabilityConditions> CODEC = StringRepresentable.fromEnum(AvailabilityConditions::values);
		
		private final Predicate<SburbPlayerData> playerDataConsumer;
		
		AvailabilityConditions(Predicate<SburbPlayerData> playerData)
		{
			this.playerDataConsumer = playerData;
		}
		
		public boolean test(SburbPlayerData playerData)
		{
			return playerDataConsumer.test(playerData);
		}
		
		@Override
		public String getSerializedName()
		{
			return name().toLowerCase(Locale.ROOT);
		}
	}
	
	public record ConditionalItem(Condition condition, ItemStack item)
	{
		public static final Codec<ConditionalItem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Condition.CODEC.fieldOf("condition").forGetter(ConditionalItem::condition),
				ItemStack.CODEC.fieldOf("item").forGetter(ConditionalItem::item)
		).apply(instance, ConditionalItem::new));
		
		public ItemStack apply(SburbPlayerData playerData, Level level)
		{
			return condition.apply(playerData, level, item);
		}
		
		public enum Condition implements StringRepresentable
		{
			NONE((data, level, item) -> item),
			PUNCHED_ENTRY_ITEM_CARD((data, level, item) -> AlchemyHelper.createPunchedCard(SburbHandler.getEntryItem(level, data))),
			PUNCHED_ITEM_CARD((data, level, item) -> AlchemyHelper.createPunchedCard(item)),
			MINI_CRUXTRUDER_WITH_COLOR((data, level, item) -> MiniCruxtruderItem.getCruxtruderWithColor(ColorHandler.getColorForPlayer(data.playerId(), level)));
			
			public static final Codec<Condition> CODEC = StringRepresentable.fromEnum(Condition::values);
			
			private final TriFunction<SburbPlayerData, Level, ItemStack, ItemStack> process;
			
			Condition(TriFunction<SburbPlayerData, Level, ItemStack, ItemStack> process)
			{
				this.process = process;
			}
			
			public ItemStack apply(SburbPlayerData playerData, Level level, ItemStack item)
			{
				return process.apply(playerData, level, item);
			}
			
			@Override
			public String getSerializedName()
			{
				return name().toLowerCase(Locale.ROOT);
			}
		}
	}
	
	public record DeployGristCost(Condition condition, ImmutableGristSet primaryCost, ImmutableGristSet secondaryCost)
	{
		public static final Codec<DeployGristCost> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Condition.CODEC.fieldOf("condition").forGetter(DeployGristCost::condition),
				ImmutableGristSet.LIST_CODEC.fieldOf("primary_cost").forGetter(DeployGristCost::primaryCost),
				ImmutableGristSet.LIST_CODEC.fieldOf("secondary_cost").forGetter(DeployGristCost::secondaryCost)
		).apply(instance, DeployGristCost::new));
		
		public enum Condition implements StringRepresentable
		{
			NONE((data, primarySet, secondarySet, usePrimary) -> usePrimary ? primarySet : secondarySet),
			NO_SECONDARY((data, primarySet, secondarySet, usePrimary) -> usePrimary ? primarySet : null),
			FOUR_BASE_GRIST((data, primarySet, secondarySet, usePrimary) -> data.getBaseGrist().amount(4));
			
			public static final Codec<Condition> CODEC = StringRepresentable.fromEnum(Condition::values);
			
			private final QuadFunction<SburbPlayerData, ImmutableGristSet, ImmutableGristSet, Boolean, ImmutableGristSet> process;
			
			Condition(QuadFunction<SburbPlayerData, ImmutableGristSet, ImmutableGristSet, Boolean, ImmutableGristSet> process)
			{
				this.process = process;
			}
			
			public ImmutableGristSet apply(SburbPlayerData playerData, ImmutableGristSet primarySet, ImmutableGristSet secondarySet, boolean usePrimary)
			{
				return process.apply(playerData, primarySet, secondarySet, usePrimary);
			}
			
			@Override
			public String getSerializedName()
			{
				return name().toLowerCase(Locale.ROOT);
			}
		}
		
		@FunctionalInterface
		public interface QuadFunction<A, B, C, D, X>
		{
			X apply(A var1, B var2, C var3, D var4);
		}
	}
}