package com.mraof.minestuck.util;

import com.mraof.minestuck.models.armor.godtier.*;
import com.mraof.minestuck.player.EnumClass;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class GodTierArmorModels
{
	public static final HashMap<EnumClass, GTAbstractModel> models = new HashMap<EnumClass, GTAbstractModel>()
	{{
		put(EnumClass.KNIGHT, new GTKnightModel(1));
		put(EnumClass.HEIR, new GTHeirModel(1));
		//put(EnumClass.WITCH, new ModelGTWitch());
		put(EnumClass.SEER, new GTSeerModel(1));
		//put(EnumClass.PAGE, new ModelGTPage());
		//put(EnumClass.MAGE, new ModelGTMage());
		put(EnumClass.BARD, new GTBardModel(1));
		//put(EnumClass.THIEF, new ModelGTThief());
		//put(EnumClass.PRINCE, new ModelGTPrince());
		//put(EnumClass.MAID, new ModelGTMaid());
		//put(EnumClass.ROGUE, new ModelGTRogue());
		//put(EnumClass.SYLPH, new ModelGTSylph());
		
		//put(EnumClass.LORD, new ModelGTLord());
		//put(EnumClass.MUSE, new ModelGTMuse());
	}};
}