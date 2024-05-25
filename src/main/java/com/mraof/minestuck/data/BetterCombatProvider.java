package com.mraof.minestuck.data;

import com.google.gson.*;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.MSItems;
import com.mraof.minestuck.item.weapon.MSToolType;
import com.mraof.minestuck.item.weapon.WeaponItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.mraof.minestuck.item.MSItemTypes.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BetterCombatProvider implements DataProvider
{
	private final PackOutput output;
	
	Map<String, String> weaponWithParent = new HashMap<>();
	
	public BetterCombatProvider(PackOutput output)
	{
		this.output = output;
	}
	
	/**
	 * First gets all the weapons from MSItems and adds it to a Map where the key is the registry name and the value is the data.
	 * Then it checks for custom defined weapons to use either in addition to the existing entries or to replace
	 */
	@Override
	public final CompletableFuture<?> run(CachedOutput cache)
	{
		List<CompletableFuture<?>> futures = new ArrayList<>();
		Path basePath = this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(Minestuck.MOD_ID).resolve("weapon_attributes");
		
		MSItems.REGISTER.getEntries().forEach(weaponHolder ->
		{
			if(weaponHolder.asOptional().isEmpty())
				return;
			
			if(weaponHolder.get() instanceof WeaponItem weaponItem && weaponItem.getToolType() != null)
			{
				String parent = getWeaponParent(weaponItem.getToolType());
				
				if(!parent.isEmpty())
					weaponWithParent.put(weaponHolder.getKey().location().getPath(), parent);
			}
		});
		
		customWeapons();
		
		for(Map.Entry<String, String> entry : weaponWithParent.entrySet())
		{
			JsonObject object = new JsonObject();
			
			object.addProperty("parent", entry.getValue());
			
			futures.add(DataProvider.saveStable(cache, object, basePath.resolve(entry.getKey() + ".json")));
		}
		
		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
	}
	
	public String getWeaponParent(MSToolType toolType)
	{
		if(toolType == SICKLE_TOOL)
			return "bettercombat:sickle";
		else if(toolType == SCYTHE_TOOL)
			return "bettercombat:scythe";
		else if(toolType == CLAWS_TOOL)
			return "bettercombat:claw";
		else if(toolType == PICKAXE_TOOL)
			return "bettercombat:pickaxe";
		else if(toolType == HAMMER_TOOL)
			return "bettercombat:hammer";
		else if(toolType == AXE_TOOL)
			return "bettercombat:heavy_axe";
		else if(toolType == CLUB_TOOL || toolType == SHOVEL_TOOL)
			return "bettercombat:mace";
		else if(toolType == SWORD_TOOL || toolType == KEY_TOOL || toolType == BATON_TOOL)
			return "bettercombat:sword";
		else if(toolType == KNIFE_TOOL || toolType == FAN_TOOL)
			return "bettercombat:dagger";
		else if(toolType == LANCE_TOOL)
			return "bettercombat:lance";
		else if(toolType == STAFF_TOOL)
			return "bettercombat:battlestaff";
		else if(toolType == WAND_TOOL)
			return "bettercombat:wand";
		else if(toolType == AXE_HAMMER_TOOL)
			return "bettercombat:double_axe";
		return "";
	}
	
	public void customWeapons()
	{
		addWeapon(MSItems.MAILBOX, "bettercombat:hammer");
		addWeapon(MSItems.ESTROGEN_EMPOWERED_EVERYTHING_ERADICATOR, "bettercombat:hammer");
		addWeapon(MSItems.BOOMBOX_BEATER, "bettercombat:hammer");
		
		addWeapon(MSItems.SWONGE, "bettercombat:sword");
		addWeapon(MSItems.WET_SWONGE, "bettercombat:sword");
		addWeapon(MSItems.PUMORD, "bettercombat:sword");
		addWeapon(MSItems.WET_PUMORD, "bettercombat:sword");
		addWeapon(MSItems.MUSIC_SWORD, "bettercombat:sword");
		
		addWeapon(MSItems.CHAINSAW_KATANA, "bettercombat:katana");
		addWeapon(MSItems.KATANA, "bettercombat:katana");
		addWeapon(MSItems.UNBREAKABLE_KATANA, "bettercombat:katana");
		
		addWeapon(MSItems.CACTACEAE_CUTLASS, "bettercombat:cutlass");
		addWeapon(MSItems.CUTLASS_OF_ZILLYWAIR, "bettercombat:cutlass");
		addWeapon(MSItems.SCARLET_RIBBITAR, "bettercombat:cutlass");
		addWeapon(MSItems.SCARLET_ZILLYHOO, "bettercombat:cutlass");
		addWeapon(MSItems.TOO_HOT_TO_HANDLE, "bettercombat:cutlass");
		
		addWeapon(MSItems.CLAYMORE, "bettercombat:claymore");
		addWeapon(MSItems.MACUAHUITL, "bettercombat:cutlass");
		addWeapon(MSItems.FROSTY_MACUAHUITL, "bettercombat:cutlass");
		
		addWeapon(MSItems.ANGEL_APOCALYPSE, "bettercombat:rapier");
		addWeapon(MSItems.FIRE_POKER, "bettercombat:rapier");
		
		addWeapon(MSItems.NIFE, "bettercombat:dagger");
		
		addWeapon(MSItems.LUCERNE_HAMMER, "bettercombat:halberd");
		addWeapon(MSItems.LUCERNE_HAMMER_OF_UNDYING, "bettercombat:halberd");
		
		addWeapon(MSItems.OBSIDIAN_AXE_KNIFE, "bettercombat:claw");
		
		addWeapon(MSItems.PROSPECTING_PICKSCYTHE, "bettercombat:scythe");
		
		addWeapon(MSItems.CUESTICK, "bettercombat:lance");
		addWeapon(MSItems.TV_ANTENNA, "bettercombat:sword");
		
		addWeapon(MSItems.SPEAR_CANE, "bettercombat:spear");
	}
	
	public void addWeapon(DeferredItem<Item> weapon, String parent)
	{
		weaponWithParent.put(weapon.getKey().location().getPath(), parent);
	}
	
	//TODO chainsaws
	//TODO for canes use mace as base but change sounds
	//TODO for forks use existing file
	
	@Override
	public String getName()
	{
		return "Minestuck Better Combat Weapon Files";
	}
}