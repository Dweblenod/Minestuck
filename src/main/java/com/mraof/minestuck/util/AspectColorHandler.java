package com.mraof.minestuck.util;

import com.mraof.minestuck.player.EnumAspect;

import java.util.ArrayList;
import java.util.HashMap;

public class AspectColorHandler
{
	public static final AspectColor[] defaultSet = createColorSet(0xB9B9B9, 0x484848, 0x979797, 0xAEFE00, 0xC50128, 0xAEFE00, 0x5CCB00);
	
	public static final HashMap<EnumAspect, AspectColor[]> aspectColors = new HashMap<EnumAspect, AspectColor[]>()
	{{
		put(EnumAspect.BLOOD, createColorSet(0x3D190A, 0x290704, 0x5C2913, 0xB90F15, 0x583980));   //blood
		put(EnumAspect.BREATH, createColorSet(0x0187EB, 0x0053F1, 0x006EE8, 0x0FE1FE, 0xFFED08));     //breath
		put(EnumAspect.DOOM, createColorSet(0x204121, 0x242E26, 0x1C3823, 0x0000000, 0xD6BB78));         //doom
		put(EnumAspect.HEART, createColorSet(0x6E0E2E, 4395044, 5575722, 0xBD1764, 1394208));    //heart
		put(EnumAspect.HOPE, createColorSet(0xFEDA82, 16693286, 16172362, 0xFDFDFD, 5796184, 0xFEC433, 0xFFE094)); //hope
		put(EnumAspect.LIFE, createColorSet(13419444, 6313026, 8022868, 7783246, 996473, 0x3E990C, 0x76C24E));     //life
		put(EnumAspect.LIGHT, createColorSet(0xF98100, 16402176, 15164928, 16251726, 1106175)); //light
		put(EnumAspect.MIND, createColorSet(4039514, 3695654, 6403645, 458697, 8133949));      //mind
		put(EnumAspect.RAGE, createColorSet(0x442769, 2496058, 5060968, 0x9C4DAD, 3347210, 0x7C43B1, 0x9B4EAA));    //rage
		put(EnumAspect.SPACE, createColorSet(0x0F0E0E, 0x2F2F2F, 0x1D1D1D, 0xFFFFFF, 12256514, 0x848484, 0x4D4D4D));   //space
		put(EnumAspect.TIME, createColorSet(11996430, 5309958, 9311510, 16720134, 1973794, 16720134, 0xAB4032));   //time
		put(EnumAspect.VOID, createColorSet(9062, 2050176, 1204608, 571, 71038341, 0x004CB0, 0x043476));           //void
	}};
	
	private static AspectColor[] createColorSet(int shirt, int primary, int secondary, int symbol, int shoes)
	{
		return createColorSet(shirt, primary, secondary, symbol, shoes, symbol, shirt);
	}
	
	private static AspectColor[] createColorSet(int shirt, int primary, int secondary, int symbol, int shoes, int detailA, int detailB)
	{
		ArrayList<AspectColor> aspectColors = new ArrayList<>();
		
		for(int c : new int[] {shirt, primary, secondary, symbol, shoes, detailA, detailB, 0xFFFFFF})
			aspectColors.add(new AspectColor(c));
		
		return aspectColors.toArray(new AspectColor[aspectColors.size()]);
	}
	
	public static int getAspectColor(int aspectId, EnumColor color)
	{
		if(aspectId >= 0 && aspectId < EnumColor.values().length)
			return getAspectColor(EnumAspect.values()[aspectId], color);
		return defaultSet[color.ordinal()].hex;
	}
	
	public static int getAspectColor(EnumAspect aspect, EnumColor color)
	{
		if(aspect == null)
			return defaultSet[color.ordinal()].hex;
		return aspectColors.get(aspect)[color.ordinal()].hex;
	}
	
	public static AspectColor[] getAspectColorSet(int aspectId)
	{
		if(aspectId >= 0 && aspectId < EnumColor.values().length)
			return getAspectColorSet(EnumAspect.values()[aspectId]);
		return defaultSet;
	}
	
	public static AspectColor[] getAspectColorSet(EnumAspect aspect)
	{
		if(aspect == null)
			return defaultSet;
		return aspectColors.get(aspect);
	}
	public static float[] getAspectColorRBG(int aspectId, EnumColor color)
	{
		if(aspectId >= 0 && aspectId < EnumColor.values().length)
			return getAspectColorRGB(EnumAspect.values()[aspectId], color);
		AspectColor aspectColor = defaultSet[color.ordinal()];
		return new float[] {aspectColor.r, aspectColor.g, aspectColor.b};
	}
	
	public static float[] getAspectColorRGB(EnumAspect aspect, EnumColor color)
	{
		AspectColor aspectColor = defaultSet[color.ordinal()];
		if(aspect != null)
			aspectColor = aspectColors.get(aspect)[color.ordinal()];
		return new float[] {aspectColor.r, aspectColor.g, aspectColor.b};
	}
	
	public enum EnumColor
	{
		SHIRT,
		PRIMARY,
		SECONDARY,
		SYMBOL,
		SHOES,
		DETAIL_PRIMARY,
		DETAIL_SECONDARY
	}
	
	public static class AspectColor
	{
		public final int hex;
		public final float r;
		public final float g;
		public final float b;
		
		public AspectColor(int hex)
		{
			this.hex = hex;
			
			r = (float)((hex & 16711680) >> 16) / 255.0F;
			g = (float)((hex & '\uff00') >> 8) / 255.0F;
			b = (float)((hex & 255) >> 0) / 255.0F;
		}
	}
}