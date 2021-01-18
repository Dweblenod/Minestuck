package com.mraof.minestuck.entity.underling;

import com.mraof.minestuck.entity.IEntityMultiPart;
import com.mraof.minestuck.entity.ai.AttackByDistanceGoal;
import com.mraof.minestuck.entity.ai.CustomMeleeAttackGoal;
import com.mraof.minestuck.entity.carapacian.BishopEntity;
import com.mraof.minestuck.item.crafting.alchemy.GristHelper;
import com.mraof.minestuck.item.crafting.alchemy.GristSet;
import com.mraof.minestuck.item.crafting.alchemy.GristType;
import com.mraof.minestuck.player.Echeladder;
import com.mraof.minestuck.util.MSSoundEvents;
import com.mraof.minestuck.world.storage.PlayerSavedData;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class BasiliskEntity extends UnderlingEntity implements IEntityMultiPart, IRangedAttackMob
{
	private UnderlingPartEntity tail;
	
	public BasiliskEntity(EntityType<? extends BasiliskEntity> type, World world)
	{
		super(type, world, 5);
		tail = new UnderlingPartEntity(this, 0, 3F, 2F);
		//world.addEntity(tail); TODO Not safe to add entities to world on creation. A different solution is needed
	}
	
	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();
		getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(85.0D);
		getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.6D);
		getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
	}
	
	@Override
	protected void registerGoals()
	{
		super.registerGoals();
		this.goalSelector.addGoal(3, new CustomMeleeAttackGoal(this, 1.0F, false, 40, 1.2F));
		this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.0D, 100, 32.0F));
	}
	
	protected SoundEvent getAmbientSound()
	{
		return MSSoundEvents.ENTITY_BASILISK_AMBIENT;
	}
	
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return MSSoundEvents.ENTITY_BASILISK_HURT;
	}
	
	protected SoundEvent getDeathSound()
	{
		return MSSoundEvents.ENTITY_BASILISK_DEATH;
	}
	
	@Override
	public GristSet getGristSpoils()
	{
		return GristHelper.generateUnderlingGristDrops(this, damageMap, 6);
	}
	
	@Override
	protected int getVitalityGel()
	{
		return rand.nextInt(3) + 4;
	}
	
	@Override
	protected void onGristTypeUpdated(GristType type)
	{
		super.onGristTypeUpdated(type);
		applyGristModifier(SharedMonsterAttributes.MAX_HEALTH, 20 * type.getPower(), AttributeModifier.Operation.ADDITION);
		applyGristModifier(SharedMonsterAttributes.ATTACK_DAMAGE, 2.7 * type.getPower(), AttributeModifier.Operation.ADDITION);
		this.experienceValue = (int) (6 * type.getPower() + 4);
	}
	
	@Override
	public World getWorld()
	{
		return this.world;
	}
	
	@Override
	public void baseTick()
	{
		super.baseTick();
		this.updatePartPositions();
	}
	
	@Override
	public boolean attackEntityFromPart(Entity entityPart, DamageSource source, float damage)
	{
		return this.attackEntityFrom(source, damage);
	}
	
	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(par1Entity != this.tail)
			super.collideWithEntity(par1Entity);
	}
	
	@Override
	public void setPositionAndRotation(double par1, double par3, double par5, float par7, float par8)
	{
		super.setPositionAndRotation(par1, par3, par5, par7, par8);
		this.updatePartPositions();
	}
	
	@Override
	public void updatePartPositions()
	{
		if(tail == null)
			return;
		float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw);
		double tailPosX = (this.getPosX() + Math.sin(f1 / 180.0 * Math.PI) * tail.getWidth());
		double tailPosZ = (this.getPosZ() + -Math.cos(f1 / 180.0 * Math.PI) * tail.getWidth());
		
		tail.setPositionAndRotation(tailPosX, this.getPosY(), tailPosZ, this.rotationYaw, this.rotationPitch);
	}
	
	@Override
	public void addPart(Entity entityPart, int id)
	{
		this.tail = (UnderlingPartEntity) entityPart;
	}
	
	@Override
	public void onPartDeath(Entity entityPart, int id)
	{
	
	}
	
	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		Entity entity = cause.getTrueSource();
		if(this.dead && !this.world.isRemote)
		{
			computePlayerProgress((int) (100 * getGristType().getPower() + 160));
			if(entity instanceof ServerPlayerEntity)
			{
				Echeladder ladder = PlayerSavedData.getData((ServerPlayerEntity) entity).getEcheladder();
				ladder.checkBonus((byte) (Echeladder.UNDERLING_BONUS_OFFSET + 2));
			}
		}
	}
	
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		Vec3d vec3d = this.getLook(1.0F);
		double d0 = target.getPosX() - (this.getPosX() + vec3d.x * 2.0D);
		double d1 = target.getPosYHeight(0.5D) - (0.5D + this.getPosYHeight(0.5D));
		double d2 = target.getPosZ() - (this.getPosZ() + vec3d.z * 2.0D);
		
		SmallFireballEntity fireballentity = new SmallFireballEntity(world, this, d0, d1, d2);
		fireballentity.setPosition(this.getPosX() + vec3d.x * 2.0D, this.getPosYHeight(0.5D) + 0.5D, fireballentity.getPosZ() + vec3d.z * 2.0D);
		world.addEntity(fireballentity);
		world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
		this.setFire(-1);
	}
}