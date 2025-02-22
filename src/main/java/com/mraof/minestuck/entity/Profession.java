package com.mraof.minestuck.entity;

import com.mojang.serialization.Codec;
import com.mraof.minestuck.entity.dialogue.RandomlySelectableDialogue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public interface Profession
{
	void addProfessionGoals();
	
	default void writeProfession(CompoundTag compound, Type type)
	{
		compound.putString("profession", type.getSerializedName());
	}
	
	default boolean professionSaved(CompoundTag compound)
	{
		return compound.contains("profession");
	}
	
	default Type readProfession(CompoundTag compound)
	{
		return Type.getFromName(compound.getString("profession"));
	}
	
	RandomlySelectableDialogue.DialogueCategory dialogueCategory();
	
	enum Type implements StringRepresentable
	{
		NONE(),
		SHADY_MERCHANT(),
		FOOD_MERCHANT(),
		GENERAL_MERCHANT(),
		SOLDIER();
		
		public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
		
		public static Type getFromName(String str)
		{
			for(Type type : Type.values())
				if(type.name().toLowerCase().equals(str))
					return type;
			throw new IllegalArgumentException("Invalid profession type " + str);
		}
		
		@Override
		public String getSerializedName()
		{
			return name().toLowerCase(Locale.ROOT);
		}
	}
}
