package com.mraof.minestuck.entity.underling;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.player.Echeladder;
import com.mraof.minestuck.player.PlayerIdentifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public record UnderlingData(EntityType<?> entityType, int expBase, float expGristMultiplier, int gelMin, int gelMax,
							int minDistance, int weight, int passiveRung)
{
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static final Codec<UnderlingData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(UnderlingData::entityType),
			Codec.INT.fieldOf("exp_base").forGetter(UnderlingData::expBase),
			Codec.FLOAT.fieldOf("exp_grist_multiplier").forGetter(UnderlingData::expGristMultiplier),
			Codec.INT.fieldOf("gel_min").forGetter(UnderlingData::gelMin),
			Codec.INT.fieldOf("gel_max").forGetter(UnderlingData::gelMax),
			Codec.INT.fieldOf("min_distance").forGetter(UnderlingData::minDistance),
			Codec.INT.fieldOf("weight").forGetter(UnderlingData::weight),
			Codec.INT.fieldOf("passive_rung").forGetter(UnderlingData::passiveRung)
	).apply(instance, UnderlingData::new));
	
	public int getVitalityGel(RandomSource random)
	{
		return random.nextInt(gelMax - gelMin) + gelMin;
	}
	
	/*public void computePlayerProgress(Entity entity, int progress)
	{
		if(!(entity instanceof UnderlingEntity underlingEntity))
			return;
		
		Map<PlayerIdentifier, Double> damageMap = underlingEntity.damageMap;
		
		double totalDamage = 0;
		for(Double i : damageMap.values())
			totalDamage += i;
		if(totalDamage < underlingEntity.getMaxHealth())
			totalDamage = underlingEntity.getMaxHealth();
		
		int maxProgress = (int) (progress * UnderlingEntity.MAX_SHARED_PROGRESS);
		damageMap.remove(null);
		PlayerIdentifier[] playerList = damageMap.keySet().toArray(new PlayerIdentifier[0]);
		double[] modifiers = new double[playerList.length];
		double totalModifier = 0;
		
		for(int i = 0; i < playerList.length; i++)
		{
			double f = damageMap.get(playerList[i]) / totalDamage;
			modifiers[i] = 2 * f - f * f;
			totalModifier += modifiers[i];
		}
		
		if(playerList.length > 0)
			LOGGER.debug("{} players are splitting on {} progress from {}", playerList.length, progress, underlingEntity.getType());
		
		if(totalModifier > UnderlingEntity.MAX_SHARED_PROGRESS)
		{
			for(int i = 0; i < playerList.length; i++)
				Echeladder.get(playerList[i], underlingEntity.level())
						.increaseProgress((int) (maxProgress * modifiers[i] / totalModifier));
		} else
		{
			for(int i = 0; i < playerList.length; i++)
				Echeladder.get(playerList[i], underlingEntity.level())
						.increaseProgress((int) (progress * modifiers[i]));
		}
	}*/
}
