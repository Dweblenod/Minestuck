package com.mraof.minestuck.item.weapon;

import com.mraof.minestuck.client.util.GunEffect;
import com.mraof.minestuck.entity.underling.UnderlingEntity;
import com.mraof.minestuck.item.BulletItem;
import com.mraof.minestuck.network.GunEffectPacket;
import com.mraof.minestuck.network.MSPacketHandler;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.MSTags;
import com.mraof.minestuck.world.storage.PlayerSavedData;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GunRightClickEffect implements ItemRightClickEffect
{
	private final double accuracy;
	private final int damage;
	private final int distance;
	final int reloadTime;
	private final Supplier<EffectInstance> effect;
	private final Supplier<SoundEvent> sound;
	private final float pitch;
	public int projectileAmount;
	public int penetratingPower;
	public List<LivingEntity> struckEntities = new ArrayList<>();
	
	@Nullable
	private final GunEffect.Type type;
	
	EntityPredicate visiblePredicate = (new EntityPredicate()).setLineOfSiteRequired();
	
	public static final GunRightClickEffect SHOTGUN = new ShotgunGunEffect(2, 5, 20, 70, null, null, 1.0F, GunEffect.Type.SMOKE, 5);
	public static final GunRightClickEffect SHORT_DISTANCE = new GunRightClickEffect(2, 3, 20, 15, null, null, 1.0F, GunEffect.Type.SMOKE);
	
	public static final GunRightClickEffect STANDARD_DISTANCE_ABYSMAL_SPEED = new GunRightClickEffect(10, 4, 35, 130, null, null, 1.0F, GunEffect.Type.SMOKE);
	public static final GunRightClickEffect STANDARD_DISTANCE_LOW_SPEED = new GunRightClickEffect(10, 4, 35, 70, null, null, 1.0F, GunEffect.Type.SMOKE);
	public static final GunRightClickEffect STANDARD_DISTANCE = new GunRightClickEffect(10, 4, 35, 15, null, null, 1.0F, GunEffect.Type.SMOKE);
	public static final GunRightClickEffect STANDARD_DISTANCE_HIGH_SPEED = new GunRightClickEffect(10, 4, 35, 4, null, null, 1.0F, GunEffect.Type.SMOKE);
	public static final GunRightClickEffect STANDARD_DISTANCE_EXTREME_SPEED = new GunRightClickEffect(10, 4, 35, 0, null, null, 1.0F, GunEffect.Type.SMOKE);
	
	public static final GunRightClickEffect GREEN_SUN_STREETSWEEPER = new GunRightClickEffect(4, 5, 45, 0, () -> new EffectInstance(Effects.WITHER, 120, 2), null, 1.0F, GunEffect.Type.GREEN);
	
	public static final GunRightClickEffect IMPROVED_DISTANCE = new GunRightClickEffect(20, 5, 50, 15, null, null, 1.0F, GunEffect.Type.SMOKE);
	
	public static final GunRightClickEffect LONG_DISTANCE_LOW_SPEED = new GunRightClickEffect(35, 8, 75, 70, null, null, 1.0F, GunEffect.Type.SMOKE);
	public static final GunRightClickEffect LONG_DISTANCE = new GunRightClickEffect(35, 8, 75, 15, null, null, 1.0F, GunEffect.Type.SMOKE);
	public static final GunRightClickEffect LONG_DISTANCE_HIGH_SPEED = new GunRightClickEffect(35, 8, 75, 4, null, null, 1.0F, GunEffect.Type.SMOKE);
	public static final GunRightClickEffect LONG_DISTANCE_EXTREME_SPEED = new GunRightClickEffect(35, 8, 75, 1, null, null, 1.0F, GunEffect.Type.SMOKE);
	
	public static final GunRightClickEffect EXTREME_DISTANCE = new GunRightClickEffect(100, 9, 90, 15, null, null, 1.0F, GunEffect.Type.SMOKE);
	
	protected GunRightClickEffect(double accuracy, int damage, int distance, int reloadTime, Supplier<EffectInstance> effect, Supplier<SoundEvent> sound, float pitch, @Nullable GunEffect.Type type)
	{
		this.accuracy = accuracy;
		this.damage = damage;
		this.distance = distance;
		this.reloadTime = reloadTime;
		this.effect = effect;
		this.sound = sound;
		this.pitch = pitch;
		this.type = type;
	}
	
	@Override
	public ActionResult<ItemStack> onRightClick(World world, PlayerEntity player, Hand hand)
	{
		ItemStack itemStackIn = player.getHeldItem(hand);
		ItemStack bulletStack = null;
		boolean foundItem = false;
		List<Item> bulletType = new ArrayList<>(MSTags.Items.BULLETS.getAllElements());
		List<Item> gunType = new ArrayList<>(MSTags.Items.GUNS.getAllElements());
		
		for(ItemStack invItem : player.inventory.mainInventory)
		{
			if(!foundItem)
			{
				for(int i = 0; i < MSTags.Items.BULLETS.getAllElements().size(); i++)
				{
					if(ItemStack.areItemsEqual(invItem, bulletType.get(i).getDefaultInstance()))
					{
						foundItem = true;
						bulletStack = invItem;
						if(!player.isCreative())
							invItem.shrink(1);
						break;
					}
				}
			}
		}
		
		if(!foundItem)
		{
			world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.8F, 1.6F);
		}
		
		if(player instanceof ServerPlayerEntity && foundItem)
			gunShoot(world, (ServerPlayerEntity) player, bulletStack);
		
		if(foundItem)
		{
			for(int i = 0; i < MSTags.Items.GUNS.getAllElements().size(); i++)
			{
				player.getCooldownTracker().setCooldown(gunType.get(i), reloadTime);
			}
			
			itemStackIn.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(Hand.MAIN_HAND));
			player.addStat(Stats.ITEM_USED.get(itemStackIn.getItem()));
		}
		
		return ActionResult.resultPass(itemStackIn);
	}
	
	void gunShoot(World world, ServerPlayerEntity player, ItemStack bulletStack)
	{
		if(bulletStack.getItem() instanceof BulletItem)
		{
			BulletItem bulletItem = (BulletItem) bulletStack.getItem();
			
			if(sound != null && player.getRNG().nextFloat() < .1F) //optional sound effect adding
				world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), sound.get(), SoundCategory.PLAYERS, 0.7F, pitch);
			world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 0.9F, 2F);
			
			targetEffect(player);
			
			double accuracyFactor = bulletItem.getAccuracy() + this.accuracy;
			
			Vec3d eyePos = player.getEyePosition(1.0F);
			Vec3d lookVec = player.getLookVec().add((player.getRNG().nextDouble() - 0.5D) / accuracyFactor, (player.getRNG().nextDouble() - 0.5D) / accuracyFactor, (player.getRNG().nextDouble() - 0.5D) / accuracyFactor);
			int travelDistance = distance + bulletItem.getDistance() + player.getRNG().nextInt(((int) distance / 10));
			penetratingPower = bulletItem.getPenetratingPower();
			
			struckEntities.clear();
			
			for(int step = 0; step < (travelDistance) * 2; step++) //uses the float i value to increase the distance away from where the player is looking and creating a sort of raytrace
			{
				Vec3d vecPos = eyePos.add(lookVec.scale(step / 2D));
				
				boolean hitObstacle = checkCollisionInPath(world, player, vecPos, bulletItem);
				
				if(hitObstacle)
				{
					sendEffectPacket(world, eyePos, lookVec, step, true);
					return;
				}
			}
			sendEffectPacket(world, eyePos, lookVec, travelDistance * 2, false);
		}
	}
	
	// If you're an addon that want to use this class with your own effect, override this to use your own network packet
	protected void sendEffectPacket(World world, Vec3d pos, Vec3d lookVec, int length, boolean collides)
	{
		if(type != null)
			MSPacketHandler.sendToNear(new GunEffectPacket(type, pos, lookVec, length, collides),
					new PacketDistributor.TargetPoint(pos.x, pos.y, pos.z, 64, world.getDimension().getType()));
	}
	
	protected void targetEffect(ServerPlayerEntity player)
	{
	}
	
	private boolean checkCollisionInPath(World world, ServerPlayerEntity player, Vec3d vecPos, BulletItem bulletItem)
	{
		BlockPos blockPos = new BlockPos(vecPos);
		
		if(!world.getBlockState(blockPos).allowsMovement(world, blockPos, PathType.LAND))
		{
			return true;
		}
		
		AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos);
		// gets entities in a bounding box around each vector position in the for loop
		LivingEntity closestTarget = player.world.getClosestEntityWithinAABB(LivingEntity.class, visiblePredicate, player, vecPos.x, vecPos.y, vecPos.z, axisAlignedBB);
		if(closestTarget != null)
		{
			int playerRung = PlayerSavedData.getData(player).getEcheladder().getRung();
			
			float strikePowerPercentage = (float) (penetratingPower / bulletItem.getPenetratingPower());
			int combinedDamages = damage + bulletItem.getDamage();
			
			if(closestTarget instanceof UnderlingEntity)
				closestTarget.attackEntityFrom(DamageSource.causePlayerDamage(player).setProjectile(), (combinedDamages + playerRung / 2F) * strikePowerPercentage); //damage increase from rung is higher against underlings
			else if(closestTarget instanceof PlayerEntity)
				closestTarget.attackEntityFrom(DamageSource.causePlayerDamage(player).setProjectile(), ((combinedDamages + playerRung / 5F) / 1.8F) * strikePowerPercentage);
			else
				closestTarget.attackEntityFrom(DamageSource.causePlayerDamage(player).setProjectile(), (combinedDamages + playerRung / 5F * strikePowerPercentage));
			if(effect != null && player.getRNG().nextFloat() < .1F)
				closestTarget.addPotionEffect(effect.get());
			if(bulletItem.getEffect() != null && player.getRNG().nextFloat() < .75F)
				closestTarget.addPotionEffect(bulletItem.getEffect().get());
			
			Debug.debugf("struckEntities has closestTarget = %s, struckEntities = %s, closestTarget = %s", struckEntities.contains(closestTarget), struckEntities, closestTarget);
			
			if(penetratingPower > 0)
			{
				if(!struckEntities.contains(closestTarget))
				{
					penetratingPower--;
					struckEntities.add(closestTarget);
				}
				return false;
			} else
				return true;
		} else return false;
	}
	
	private static class ShotgunGunEffect extends GunRightClickEffect
	{
		ShotgunGunEffect(double accuracy, int damage, int distance, int reloadSpeed, Supplier<EffectInstance> effect, Supplier<SoundEvent> sound, float pitch, @Nullable GunEffect.Type type, int projectileAmount)
		{
			super(accuracy, damage, distance, reloadSpeed, effect, sound, pitch, type);
			this.projectileAmount = projectileAmount;
		}
		
		@Override
		public ActionResult<ItemStack> onRightClick(World world, PlayerEntity player, Hand hand)
		{
			ItemStack itemStackIn = player.getHeldItem(hand);
			ItemStack bulletStack = null;
			boolean foundItem = false;
			List<Item> bulletType = new ArrayList<>(MSTags.Items.BULLETS.getAllElements());
			List<Item> gunType = new ArrayList<>(MSTags.Items.GUNS.getAllElements());
			
			for(ItemStack invItem : player.inventory.mainInventory)
			{
				if(!foundItem)
				{
					for(int i = 0; i < MSTags.Items.BULLETS.getAllElements().size(); i++)
					{
						if(ItemStack.areItemsEqual(invItem, bulletType.get(i).getDefaultInstance()) && invItem.getCount() >= projectileAmount)
						{
							foundItem = true;
							bulletStack = invItem;
							if(!player.isCreative())
								invItem.shrink(projectileAmount);
							break;
						}
					}
				}
			}
			
			if(!foundItem)
			{
				world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.8F, 1.6F);
			}
			
			if(player instanceof ServerPlayerEntity && foundItem)
			{
				for(int i = 0; i < projectileAmount; i++)
				{
					gunShoot(world, (ServerPlayerEntity) player, bulletStack);
				}
			}
			
			if(foundItem)
			{
				for(int i = 0; i < MSTags.Items.GUNS.getAllElements().size(); i++)
				{
					player.getCooldownTracker().setCooldown(gunType.get(i), reloadTime);
				}
				
				itemStackIn.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(Hand.MAIN_HAND));
				player.addStat(Stats.ITEM_USED.get(itemStackIn.getItem()));
			}
			
			return ActionResult.resultPass(itemStackIn);
		}
	}
}