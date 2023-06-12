package com.mraof.minestuck.util;

import com.mraof.minestuck.alchemy.*;
import com.mraof.minestuck.item.MSItemTypes;
import com.mraof.minestuck.item.MSItems;
import com.mraof.minestuck.item.weapon.MSToolType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

import java.util.Map;

public class UniversalToolCostUtil
{
	
	//The following constant maps are taken from the value tables in the cost generator spreadsheet.
	
	public static final Map<Tier, Double> TIER_CONSTANTS = Map.ofEntries(
			Map.entry(Tiers.WOOD, 0.25),
			Map.entry(Tiers.STONE, 0.4),
			Map.entry(Tiers.IRON, 0.55),
			Map.entry(Tiers.DIAMOND, 0.7),
			Map.entry(Tiers.GOLD, 0.45),
			Map.entry(Tiers.NETHERITE, 0.85),
			Map.entry(MSItemTypes.SBAHJ_TIER, 0.1),
			Map.entry(MSItemTypes.PAPER_TIER, 0.2),
			Map.entry(MSItemTypes.ORGANIC_TIER, 0.3),
			Map.entry(MSItemTypes.MEAT_TIER, 0.3),
			Map.entry(MSItemTypes.CANDY_TIER, 0.25),
			Map.entry(MSItemTypes.BOOK_TIER, 0.35),
			Map.entry(MSItemTypes.CACTUS_TIER, 0.35),
			Map.entry(MSItemTypes.ICE_TIER, 0.55),
			Map.entry(MSItemTypes.POGO_TIER, 0.45),
			Map.entry(MSItemTypes.EMERALD_TIER, 0.7),
			Map.entry(MSItemTypes.PRISMARINE_TIER, 0.6),
			Map.entry(MSItemTypes.CORUNDUM_TIER, 0.65),
			Map.entry(MSItemTypes.REGI_TIER, 0.8),
			Map.entry(MSItemTypes.HORRORTERROR_TIER, 0.7),
			Map.entry(MSItemTypes.URANIUM_TIER, 0.75),
			Map.entry(MSItemTypes.DENIZEN_TIER, 0.9),
			Map.entry(MSItemTypes.ZILLY_TIER, 0.95),
			Map.entry(MSItemTypes.WELSH_TIER, 1.0)
	);
	
	public static final Map<Item, Double> RANGED_CATEGORY_CONSTANTS = Map.ofEntries(
			Map.entry(MSItems.ARTIFUCKER.get(), 0.5),
			Map.entry(MSItems.POINTER_WAND.get(), 0.9),
			Map.entry(MSItems.NEEDLE_WAND.get(), 1.0),
			Map.entry(MSItems.POOL_CUE_WAND.get(), 1.2),
			Map.entry(MSItems.THORN_OF_OGLOGOTH.get(), 2.0),
			Map.entry(MSItems.THISTLE_OF_ZILLYWICH.get(), 2.5),
			Map.entry(MSItems.QUILL_OF_ECHIDNA.get(), 3.0)
	);
	
	public static final Map<MSToolType, Double> TOOL_CATEGORY_CONSTANTS = Map.ofEntries(
			Map.entry(MSItemTypes.MISC_TOOL, 0.0),
			Map.entry(MSItemTypes.SICKLE_TOOL, 0.25),
			Map.entry(MSItemTypes.CLAWS_TOOL, 0.25),
			Map.entry(MSItemTypes.PICKAXE_TOOL, 1.0),
			Map.entry(MSItemTypes.HAMMER_TOOL, 1.0),
			Map.entry(MSItemTypes.AXE_TOOL, 1.0),
			Map.entry(MSItemTypes.SHOVEL_TOOL, 1.0),
			Map.entry(MSItemTypes.SWORD_TOOL, 0.0),
			Map.entry(MSItemTypes.AXE_PICK_TOOL, 2.0),
			Map.entry(MSItemTypes.AXE_HAMMER_TOOL, 2.0),
			Map.entry(MSItemTypes.SHOVEL_PICK_TOOL, 2.0),
			Map.entry(MSItemTypes.SHOVEL_AXE_TOOL, 2.0),
			Map.entry(MSItemTypes.MULTI_TOOL, 3.0),
			Map.entry(MSItemTypes.GAUNTLET_TOOL, 2.0)
	);
	
	public static final Map<Rarity, Double> RARITY_CONSTANTS = Map.ofEntries(
			Map.entry(Rarity.COMMON, 0.0),
			Map.entry(Rarity.UNCOMMON, 1.0),
			Map.entry(Rarity.RARE, 2.0),
			Map.entry(Rarity.EPIC, 3.0)
	);
	
	public static final Map<Item, Double> ASPECT_OR_DENIZEN_CONSTANTS = Map.ofEntries(
			Map.entry(MSItems.UMBRELLA.get(), 1.0),
			Map.entry(MSItems.ZEPHYR_CANE.get(), 2.0),
			Map.entry(MSItems.CLOWN_CLUB.get(), 1.75),
			Map.entry(MSItems.DESOLATOR_MACE.get(), 2.8),
			Map.entry(MSItems.LUCERNE_HAMMER_OF_UNDYING.get(), 2.0),
			Map.entry(MSItems.ANGEL_APOCALYPSE.get(), 2.0),
			Map.entry(MSItems.FEAR_NO_ANVIL.get(), 2.05)
	);
	
	public static final Map<Item, Double> RIGHT_CLICK_BLOCK_CONSTANTS = Map.ofEntries(
			Map.entry(MSItems.MELONBALLER.get(), 0.5),
			Map.entry(MSItems.SWONGE.get(), 0.75),
			Map.entry(MSItems.PUMORD.get(), 0.75)
	);
	
	public static final Map<Item, Double> FINISH_USE_CONSTANTS = Map.ofEntries(
			Map.entry(MSItems.IRRADIATED_STEAK_SWORD.get(), -4.0),
			Map.entry(MSItems.DOCTOR_DETERRENT.get(), 1.0)
	);
	
	public static final Map<Item, Double> ON_HIT_POTION_CONSTANTS = Map.ofEntries(
			Map.entry(MSItems.QUANTUM_SABRE.get(), 2.0),
			Map.entry(MSItems.ATOMIKITTY_KATAR_DRAWN.get(), 2.0), //TODO everything that uses on hit potion effects needs their own entry
			Map.entry(MSItems.RED_EYES.get(), 1.0),
			Map.entry(MSItems.BEAR_POKING_STICK.get(), -1.0),
			Map.entry(MSItems.SHADOWRAZOR.get(), 0.75)
	);
	
	public static final Map<Float, Double> BACKSTAB_DAMAGE_CONSTANTS = Map.ofEntries(
			Map.entry(3.0f, 2.0),
			Map.entry(4.0f, 2.0),
			Map.entry(9.0f, 3.0)
	);
	
	public static final Map<Item, Double> DESTROY_BLOCK_EFFECT_CONSTANTS = Map.ofEntries(
			Map.entry(MSItems.MELONSBANE.get(), 0.25),
			Map.entry(MSItems.CROP_CHOP.get(), 0.5),
			Map.entry(MSItems.THE_LAST_STRAW.get(), 0.5)
	);
	
	public static final Map<Item, Double> TARGET_EXTRA_DAMAGE_CONSTANTS = Map.ofEntries(
			Map.entry(MSItems.SKELETON_DISPLACER_DRAWN.get(), 2.25),
			Map.entry(MSItems.TEARS_OF_THE_ENDERLICH_DRAWN.get(), 2.5),
			Map.entry(MSItems.PIGLINS_PRIDE.get(), 2.25),
			Map.entry(MSItems.BASILISK_BREATH_DRAGONSLAYER.get(), 2.5)
	);
	
	public static final Map<Item, Double> ITEM_RIGHT_CLICK_CONSTANTS = Map.ofEntries(
			Map.entry(MSItems.BLACK_KINGS_SCEPTER.get(), 2.0),
			Map.entry(MSItems.WHITE_KINGS_SCEPTER.get(), 2.0),
			Map.entry(MSItems.KRAKENS_EYE.get(), 0.75)
	);
	
	/**
	 * Gets the grist-cost set for a set of weights, given a universal cost for a given item.
	 *
	 * @param weights       the grist-set of weights to use after the universal cost is calculated.
	 * @param universalCost the total amount of grist that should be distributed across the weighted grist-types.
	 * @return Grist-set with all weights applied.
	 */
	public static ImmutableGristSet finalCostByValueWeight(ImmutableGristSet weights, long universalCost)
	{
		double sumOfWeights = 0;
		for(GristAmount amount : weights.asAmounts())
		{
			sumOfWeights += Math.abs(amount.amount());
		}
		
		double multiplierValue = (universalCost / sumOfWeights);
		
		MutableGristSet finalCost = new MutableGristSet();
		for(GristAmount amount : weights.asAmounts())
		{
			float gristRarity = amount.type().getRarity();
			
			if(gristRarity > 0)
				finalCost.add(amount.type(), (long) (multiplierValue * amount.amount() * gristRarity));
			else
				throw new IllegalStateException("Grists with a rarity of 0 or lower should not be included in a weapon's weight GristSet");
		}
		
		return finalCost.asImmutable();
	}
	
	public static ImmutableGristSet finalCostByCountWeight(ImmutableGristSet weights, long universalCost)
	{
		double sumOfWeights = 0;
		for(GristAmount amount : weights.asAmounts())
		{
			float gristRarity = amount.type().getRarity();
			
			if(gristRarity > 0)
				sumOfWeights += Math.abs(amount.amount() * (1 / gristRarity));
		}
		
		double multiplierValue = (universalCost / sumOfWeights);
		
		MutableGristSet finalCost = new MutableGristSet();
		for(GristAmount amount : weights.asAmounts())
		{
			float gristRarity = amount.type().getRarity();
			
			if(gristRarity > 0)
				finalCost.add(amount.type(), (long) (multiplierValue * amount.amount() * gristRarity));
			else
				throw new IllegalStateException("Grists with a rarity of 0 or lower should not be included in a weapon's weight GristSet");
		}
		
		return finalCost.asImmutable();
	}
	
	/**
	 * Create
	 * @param outliers grist-set
	 * @return the combined value of all grists in the outliers
	 */
	public static long universalOutlierCost(ImmutableGristSet outliers)
	{
		long outlierCost = 0;
		
		for(GristAmount amount : outliers.asAmounts())
		{
			float gristRarity = amount.type().getRarity();
			
			if(gristRarity > 0)
				outlierCost += amount.amount() * (1 / gristRarity);
			else
				throw new IllegalStateException("Grists with a rarity of 0 or lower should not be included in a weapon's outlier GristSet");
		}
		
		return outlierCost;
	}
}