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
		put(EnumClass.WITCH, new GTWitchModel(1));
		put(EnumClass.SEER, new GTSeerModel(1));
		put(EnumClass.PAGE, new GTPageModel(1));
		put(EnumClass.MAGE, new GTMageModel(1));
		put(EnumClass.BARD, new GTBardModel(1));
		put(EnumClass.THIEF, new GTBardModel(1));
		put(EnumClass.PRINCE, new GTPrinceModel(1));
		put(EnumClass.MAID, new GTMaidModel(1));
		put(EnumClass.ROGUE, new GTRogueModel(1));
		put(EnumClass.SYLPH, new GTSylphModel(1));
		
		put(EnumClass.LORD, new GTLordModel(1));
		put(EnumClass.MUSE, new GTMuseModel(1));
	}};
}