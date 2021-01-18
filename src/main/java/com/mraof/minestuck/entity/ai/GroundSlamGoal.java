package com.mraof.minestuck.entity.ai;

import com.mraof.minestuck.util.MSSoundEvents;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;

/**
 * Used instead of {@link MeleeAttackGoal} for when you want a custom attack rate or distance multiplier
 */
public class GroundSlamGoal extends MeleeAttackGoal
{
	private final int attackRate;
	private final float distanceMultiplier;
	private int ticksSeeingTarget;
	
	public GroundSlamGoal(CreatureEntity entity, float speed, boolean useMemory, int attackRate)
	{
		this(entity, speed, useMemory, attackRate, 10.0F);
	}
	
	public GroundSlamGoal(CreatureEntity entity, float speed, boolean useMemory, int attackRate, float distanceMultiplier)
	{
		super(entity, speed, useMemory);
		this.attackRate = attackRate;
		this.distanceMultiplier = distanceMultiplier;
	}
	
	@Override
	protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr)
	{
		boolean tickCondition = attackTick <= 0;
		super.checkAndPerformAttack(enemy, distToEnemySqr);
		if(attackTick == 9)
		if(enemy.onGround && !enemy.isOnLadder() && !enemy.isInWater() && !enemy.isPassenger() && !enemy.isCrouching())
		{
			enemy.swingArm(Hand.MAIN_HAND);
			enemy.setMotion(enemy.getMotion().x + enemy.getRNG().nextInt(1), enemy.getMotion().y + enemy.getRNG().nextInt(1), enemy.getMotion().z + enemy.getRNG().nextInt(1));
			attacker.world.playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_IRON_GOLEM_ATTACK, SoundCategory.HOSTILE, 1.5F, 0.3F);
		}
		if(tickCondition && attackTick > 0)
			attackTick = attackRate;
	}
	
	@Override
	protected double getAttackReachSqr(LivingEntity attackTarget)
	{
		return attacker.getWidth() * distanceMultiplier * attacker.getWidth() * distanceMultiplier + attackTarget.getWidth();
	}
	
	/*@Override
	public void tick()
	{
		double d0 = this.attacker.getDistanceSq(this.attacker.getPosX(), this.attacker.getBoundingBox().minY, this.attacker.getPosZ());
		boolean flag = this.attacker.getEntitySenses().canSee(this.en);
		
		if (flag)
		{
			++this.ticksSeeingTarget;
		}
		else
		{
			this.ticksSeeingTarget = 0;
		}
		
		if (d0 <= (double)this.field_82642_h && this.ticksSeeingTarget >= 20)
		{
			this.entityHost.getNavigator().clearPath();
		}
		else
		{
			this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
		}
		
		this.entityHost.getLookController().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
		float f;
		double meleeRange = (double)(this.attacker.getWidth() * 2.0F * this.attacker.getWidth() * 2.0F);
		if(d0 > meleeRange)
		{
			if (--this.rangedAttackTime == 0)
			{
				if (d0 > (double)this.field_82642_h || !flag)
				{
					return;
				}
				
				f = MathHelper.sqrt(d0) / this.field_96562_i;
				float f1 = f;
				
				if (f < 0.1F)
				{
					f1 = 0.1F;
				}
				
				if (f1 > 1.0F)
				{
					f1 = 1.0F;
				}
				
				((IRangedAttackMob) this.attacker).attackEntityWithRangedAttack(this.attackTarget, f1);
				this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.field_96561_g) + (float)this.field_96561_g);
			}
			else if (this.rangedAttackTime < 0)
			{
				f = MathHelper.sqrt(d0) / this.field_96562_i;
				this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.field_96561_g) + (float)this.field_96561_g);
			}
		}
		else
		{
			this.rangedAttackTime = Math.max(this.rangedAttackTime - 1, 0);
			if(this.attacker.getDistanceSq(this.attackTarget.getPosX(), this.attackTarget.getBoundingBox().minY, this.attackTarget.getPosZ()) <= d0)
			{
				if(this.rangedAttackTime <= 0)
				{
					this.rangedAttackTime = 20;
					
					if(!this.attacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty())
					{
						this.attacker.swingArm(Hand.MAIN_HAND);
					}
					
					this.attacker.attackEntityAsMob(this.attackTarget);
				}
			}
		}
	}*/
}
