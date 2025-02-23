package com.mraof.minestuck.entity.carapacian;

import com.mraof.minestuck.entity.AnimatedPathfinderMob;
import com.mraof.minestuck.util.MSTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public abstract class CarapacianEntity extends AnimatedPathfinderMob implements NeutralMob
{
	private final EnumEntityKingdom kingdom;
	
	protected final TagKey<EntityType<?>> allyTag;
	
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	@Nullable
	private UUID persistentAngerTarget;
	
	public CarapacianEntity(EntityType<? extends CarapacianEntity> type, EnumEntityKingdom kingdom, Level level)
	{
		super(type, level);
		this.kingdom = kingdom;
		allyTag = kingdom == EnumEntityKingdom.PROSPITIAN ? MSTags.EntityTypes.PROSPITIAN_CARAPACIANS : MSTags.EntityTypes.DERSITE_CARAPACIANS;
	}
	
	@Override
	protected void registerGoals()
	{
		super.registerGoals();
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, false, this::isAngryAt));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
	}
	
	public static AttributeSupplier.Builder carapacianAttributes()
	{
		return Mob.createMobAttributes().add(Attributes.FOLLOW_RANGE, 32);
	}
	
	@Override
	public boolean isAngryAt(LivingEntity target)
	{
		if(target instanceof CarapacianEntity carapacianTarget)
		{
			if(!carapacianTarget.isSoldier() || !this.isSoldier())
				return false; //civilians should not be involved in aggression with other carapacians
			
			return !isAlly(carapacianTarget); //soldiers will only attack the enemy kingdom
		}
		
		return NeutralMob.super.isAngryAt(target);
	}
	
	public boolean isSoldier()
	{
		return true; //may be overridden via Profession
	}
	
	public EnumEntityKingdom getKingdom()
	{
		return Objects.requireNonNull(kingdom);
	}
	
	public boolean isAlly(Entity entity)
	{
		return entity.getType().is(allyTag);
	}
	
	@Override
	public void aiStep()
	{
		super.aiStep();
		
		if(this.level() instanceof ServerLevel serverLevel)
		{
			this.updatePersistentAnger(serverLevel, true);
		}
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag compound)
	{
		super.addAdditionalSaveData(compound);
		this.addPersistentAngerSaveData(compound);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag compound)
	{
		super.readAdditionalSaveData(compound);
		this.readPersistentAngerSaveData(this.level(), compound);
	}
	
	@Override
	public void startPersistentAngerTimer()
	{
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}
	
	@Override
	public void setRemainingPersistentAngerTime(int time)
	{
		this.remainingPersistentAngerTime = time;
	}
	
	@Override
	public int getRemainingPersistentAngerTime()
	{
		return this.remainingPersistentAngerTime;
	}
	
	@Nullable
	@Override
	public UUID getPersistentAngerTarget()
	{
		return this.persistentAngerTarget;
	}
	
	@Override
	public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget)
	{
		this.persistentAngerTarget = persistentAngerTarget;
	}
}