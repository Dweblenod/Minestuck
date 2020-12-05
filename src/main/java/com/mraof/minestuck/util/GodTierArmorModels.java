package com.mraof.minestuck.util;

import com.mraof.minestuck.models.armor.GodTierAbstractModel;
import com.mraof.minestuck.models.armor.ModelGTBard;
import com.mraof.minestuck.models.armor.ModelGTHeir;
import com.mraof.minestuck.player.EnumClass;

import java.util.HashMap;

@Side(Side.CLIENT)
public class GodTierArmorModels
{
	public static final HashMap<EnumClass, GodTierArmorModels> models = new HashMap<EnumClass, GodTierAbstractModel>()
	{{
		put(EnumClass.KNIGHT, new ModelGTKnight());
		put(EnumClass.HEIR, new ModelGTHeir());
		put(EnumClass.WITCH, new ModelGTWitch());
		put(EnumClass.SEER, new ModelGTSeer());
		put(EnumClass.PAGE, new ModelGTPage());
		put(EnumClass.MAGE, new ModelGTMage());
		put(EnumClass.BARD, new ModelGTBard());
		put(EnumClass.THIEF, new ModelGTThief());
		put(EnumClass.PRINCE, new ModelGTPrince());
		put(EnumClass.MAID, new ModelGTMaid());
		put(EnumClass.ROGUE, new ModelGTRogue());
		put(EnumClass.SYLPH, new ModelGTSylph());
		
		put(EnumClass.LORD, new ModelGTLord());
		put(EnumClass.MUSE, new ModelGTMuse());
	}};
}