package com.mraof.minestuck.item.weapon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mraof.minestuck.item.MSItemTypes;
import com.mraof.minestuck.util.UniversalToolCostUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class WeaponItem extends TieredItem
{
	private final float efficiency;
	private final boolean disableShield;
	private final boolean hasKnockback;
	private final boolean playSound;
	private final float attackDamage;
	private final float attackSpeed;
	//private static final HashMap<ToolType, Set<Material>> toolMaterials = new HashMap<>();
	
	@Nullable
	private final MSToolType toolType;
	private final Set<ToolAction> toolActions;
	private final int fireDuration;
	private final float backstabDamage;
	private final double pogoMotion;
	private final List<OnHitEffect> onHitEffects;
	@Nullable
	private final DestroyBlockEffect destroyBlockEffect;
	@Nullable
	private final RightClickBlockEffect rightClickBlockEffect;
	@Nullable
	private final ItemRightClickEffect itemRightClickEffect;
	private final boolean innocuousDouble;
	private final int useDuration;
	private final UseAnim useAction;
	private final List<FinishUseItemEffect> itemUsageEffects;
	private final List<InventoryTickEffect> tickEffects;
	//Item attributes that are applied when the weapon is in the main hand.
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	
	@Deprecated
	public WeaponItem(Tier tier, int attackDamage, float attackSpeed, float efficiency, @Nullable MSToolType toolType, Properties properties)
	{
		this(new Builder(tier, attackDamage, attackSpeed).efficiency(efficiency).set(toolType), properties);
	}
	
	public WeaponItem(Builder builder, Properties properties)
	{
		super(builder.tier, properties);
		attackDamage = builder.attackDamage;
		attackSpeed = builder.attackSpeed;
		toolType = builder.toolType;
		toolActions = builder.toolActions;
		efficiency = builder.efficiency;
		disableShield = builder.disableShield;
		hasKnockback = builder.hasKnockback;
		playSound = builder.playSound;
		fireDuration = builder.fireDuration;
		backstabDamage = builder.backstabDamage;
		pogoMotion = builder.pogoMotion;
		onHitEffects = ImmutableList.copyOf(builder.onHitEffects);
		destroyBlockEffect = builder.destroyBlockEffect;
		rightClickBlockEffect = builder.rightClickBlockEffect;
		innocuousDouble = builder.innocuousDouble;
		itemRightClickEffect = builder.itemRightClickEffect;
		useDuration = builder.useDuration;
		useAction = builder.useAction;
		itemUsageEffects = ImmutableList.copyOf(builder.itemUsageEffects);
		tickEffects = ImmutableList.copyOf(builder.tickEffects);
		
		ImmutableMultimap.Builder<Attribute, AttributeModifier> modifiers = ImmutableMultimap.builder();
		modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getDamage(), AttributeModifier.Operation.ADDITION));
		modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getSpeed(), AttributeModifier.Operation.ADDITION));
		attributeModifiers = modifiers.build();
	}
	
	public double getDamage()
	{
		return (double) attackDamage + getTier().getAttackDamageBonus();
	}
	
	public double getSpeed()
	{
		return attackSpeed;
	}
	
	/**
	 * Generates an item's universal cost from the constants defined in UniversalToolCostUtil
	 *
	 * @return the universal cost value, as a long.
	 */
	public long generateUniversalCost()
	{
		double inGameDamage = getDamage() + 1.0; //In-game base damage adds one to the attribute.
		double inGameSpeed = getSpeed() + 4.0; //In-game base speed is positive, so to convert code base-speed we add its hard-cap, positive four.
		
		//some dps calculations from the spreadsheet
		double dpsNormal = inGameDamage * inGameSpeed;
		double dpsPvpAdjusted = (inGameDamage * 1.05) + (inGameSpeed * 0.95);
		double damageRecalc = ((dpsPvpAdjusted * 1.5) + (dpsNormal * 0.5)) / 2.0;
		
		//Item-material
		double tierModifier = UniversalToolCostUtil.TIER_CONSTANTS.getOrDefault(getTier(), 1.0);
		
		//detect vanilla on-hit effects.
		double disableShieldModifier = canDisableShield(null, null, null, null) ? 1.0 : 0;
		double sweepModifier = onHitEffects.contains(OnHitEffect.SWEEP) ? 1.0 : 0;
		double knockbackModifier = hasKnockback ? 1.0 : 0;
		
		//category mods
		double rangedCategoryModifier = UniversalToolCostUtil.RANGED_CATEGORY_CONSTANTS.getOrDefault(this, 0.0);
		double toolCategoryModifier = UniversalToolCostUtil.TOOL_CATEGORY_CONSTANTS.getOrDefault(toolType, 0.0);
		
		double totalSpecialPropertyModifier = sumOfSpecialProperties();
		
		double rarityModifier = UniversalToolCostUtil.RARITY_CONSTANTS.getOrDefault(getRarity(getDefaultInstance()), 0.0);
		
		//value of 3 if unbreakable, value of 1 if item uses the tiers default durability, log scaled value scaled to tiers default durability if neither of those are true
		int itemDurability = getDefaultInstance().getMaxDamage();
		double durabilityModifier = !canBeDepleted() ? 3.0 : itemDurability == getTier().getUses() ? 1.0 : 1.0 + Math.log(itemDurability / getTier().getUses());
		
		double fireImmuneModifier = isFireResistant() ? 1.0 : 0.0;
		
		return (long) Math.pow(2.5, ((damageRecalc / 2.0) + (tierModifier / 4.0) + (disableShieldModifier / 4.0) + (sweepModifier / 6.0) + (knockbackModifier / 4.0) + (rangedCategoryModifier) + (toolCategoryModifier / 6.0) + (totalSpecialPropertyModifier) + (rarityModifier) + (durabilityModifier / 2.0) + (fireImmuneModifier)));
	}
	
	private double sumOfSpecialProperties()
	{
		//TODO consider finding a method of producing a warning if an item is assumed to have a special property but hasnt been properly added to UniversalToolCostUtil
		
		//special property modifiers are listed in the same order as in the spreadsheet.
		double pogoModifier = pogoMotion;
		double aspectOrDenizenModifier = UniversalToolCostUtil.ASPECT_OR_DENIZEN_CONSTANTS.getOrDefault(this, getTier() == MSItemTypes.DENIZEN_TIER ? 2.0 : 0.0);
		double onHitFireDurationModifier = Mth.clamp(fireDuration / 15.0, 0.0, 4.0);
		double farmineModifier = destroyBlockEffect instanceof FarmineEffect ? 0.5 : 0.0;
		double randomDamageModifier = onHitEffects.contains(OnHitEffect.RANDOM_DAMAGE) ? 3.0 : 0.0;
		double sordModifier = onHitEffects.contains(OnHitEffect.SORD_DROP) ? -2.0 : 0.0;
		double rightClickBlockModifier = UniversalToolCostUtil.RIGHT_CLICK_BLOCK_CONSTANTS.getOrDefault(this, 0.0);
		double playSoundModifier = playSound ? 0.25 : 0.0;
		double edibleModifier = isEdible() ? 1.0 : 0.0;
		double finishUseModifier = UniversalToolCostUtil.FINISH_USE_CONSTANTS.getOrDefault(this, 0.0);
		double iceShardModifier = onHitEffects.contains(OnHitEffect.ICE_SHARD) ? 0.25 : 0.0;
		double onHitPotionModifier = UniversalToolCostUtil.ON_HIT_POTION_CONSTANTS.getOrDefault(this, 0.0);
		double musicPlayerModifier = this instanceof MusicPlayerWeapon ? 3.0 : 0.0;
		double backstabModifier = UniversalToolCostUtil.BACKSTAB_DAMAGE_CONSTANTS.getOrDefault(backstabDamage, 0.0);
		double destroyBlockModifier = UniversalToolCostUtil.DESTROY_BLOCK_EFFECT_CONSTANTS.getOrDefault(this, 0.0);
		double dropCandyModifier = onHitEffects.contains(OnHitEffect.SET_CANDY_DROP_FLAG) ? 0.25 : 0.0;
		double kundlerSurpriseModifier = onHitEffects.contains(OnHitEffect.KUNDLER_SURPRISE) ? 0.25 : 0.0;
		double switchItemModifier = innocuousDouble ? 0.25 : 0.0;
		double dropEnemysItemModifier = onHitEffects.contains(OnHitEffect.DROP_FOE_ITEM) ? 1.5 : 0.0;
		double dropInWaterModifier = tickEffects.contains(InventoryTickEffect.DROP_WHEN_IN_WATER) ? -0.5 : 0.0;
		double targetExtraDamageModifier = UniversalToolCostUtil.TARGET_EXTRA_DAMAGE_CONSTANTS.getOrDefault(this, 0.0);
		double itemRightClickModifier = UniversalToolCostUtil.ITEM_RIGHT_CLICK_CONSTANTS.getOrDefault(this, 0.0);
		double onHitHorrorterrorModifier = 0.0; //effects balance out at the moment, but this could change
		
		return pogoModifier + aspectOrDenizenModifier + onHitFireDurationModifier + farmineModifier + randomDamageModifier + sordModifier + rightClickBlockModifier + playSoundModifier + edibleModifier + finishUseModifier + iceShardModifier + onHitPotionModifier + musicPlayerModifier + backstabModifier + destroyBlockModifier + dropCandyModifier + kundlerSurpriseModifier + switchItemModifier + dropEnemysItemModifier + dropInWaterModifier + targetExtraDamageModifier + itemRightClickModifier + onHitHorrorterrorModifier;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		if(toolType != null && toolType.canHarvest(state))
			return efficiency;
		
		return super.getDestroySpeed(stack, state);
	}
	
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player)
	{
		if(this.toolType != null && this.toolType.canHarvest(state))
		{
			return true;
		}
		return !player.isCreative();
	}
	
	//Thanks to Mraof for supplying the base for this method.
	@Override
	public boolean isCorrectToolForDrops(ItemStack item, BlockState blockIn)
	{
		return this.toolType != null && this.toolType.canHarvest(blockIn) && TierSortingRegistry.isCorrectTierForDrops(this.getTier(), blockIn);
	}
	
	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving)
	{
		if(destroyBlockEffect != null)
			destroyBlockEffect.onDestroyBlock(stack, level, state, pos, entityLiving);
		
		if(state.getDestroySpeed(level, pos) != 0.0F)
		{
			int damage;
			
			if(toolType != null && toolType.canHarvest(state))
				damage = 1;
			else
				damage = 2;
			
			stack.hurtAndBreak(damage, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		
		return true;
	}
	
	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction)
	{
		return this.toolActions.contains(toolAction) || this.toolType != null && this.toolType.hasAction(toolAction);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context)
	{
		if(rightClickBlockEffect != null)
			return rightClickBlockEffect.onClick(context);
		else return super.useOn(context);
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker)
	{
		return disableShield;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn)
	{
		if(itemRightClickEffect != null)
			return itemRightClickEffect.onRightClick(level, playerIn, handIn);
		else return super.use(level, playerIn, handIn);
	}
	
	@Override
	public int getUseDuration(ItemStack stack)
	{
		return useDuration;
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack)
	{
		return useAction;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving)
	{
		for(FinishUseItemEffect effect : itemUsageEffects)
			stack = effect.onItemUseFinish(stack, level, entityLiving);
		return super.finishUsingItem(stack, level, entityLiving);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected)
	{
		for(InventoryTickEffect effect : tickEffects)
			effect.inventoryTick(stack, level, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		stack.hurtAndBreak(1, attacker, (PlayerEntity) -> PlayerEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		onHitEffects.forEach(effect -> effect.onHit(stack, target, attacker));
		return true;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return canBeDepleted() || (toolType != null && !toolType.getEnchantments().isEmpty());
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
	{
		if(canBeDepleted() && enchantment.category == EnchantmentCategory.BREAKABLE)
			return true;
		if(toolType == null)
			return false;
		
		return toolType.getEnchantments().contains(enchantment);
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
	{
		return slot == EquipmentSlot.MAINHAND ? attributeModifiers : super.getAttributeModifiers(slot, stack);
	}
	
	@Nullable
	public MSToolType getToolType()
	{
		return toolType;
	}
	
	public float getEfficiency()
	{
		return efficiency;
	}
	
	public static class Builder
	{
		private final Tier tier;
		private final int attackDamage;
		private final float attackSpeed;
		@Nullable
		private MSToolType toolType;
		private final Set<ToolAction> toolActions = new HashSet<>();
		private float efficiency;
		private boolean disableShield;
		private boolean hasKnockback;
		private boolean playSound;
		private int fireDuration;
		private float backstabDamage;
		private double pogoMotion;
		private final List<OnHitEffect> onHitEffects = new ArrayList<>();
		@Nullable
		private DestroyBlockEffect destroyBlockEffect = null;
		@Nullable
		private RightClickBlockEffect rightClickBlockEffect = null;
		@Nullable
		private ItemRightClickEffect itemRightClickEffect;
		private boolean innocuousDouble;
		private int useDuration = 0;
		private UseAnim useAction = UseAnim.NONE;
		private final List<FinishUseItemEffect> itemUsageEffects = new ArrayList<>();
		private final List<InventoryTickEffect> tickEffects = new ArrayList<>();
		
		public Builder(Tier tier, int attackDamage, float attackSpeed)
		{
			this.tier = tier;
			this.attackDamage = attackDamage;
			this.attackSpeed = attackSpeed;
			efficiency = tier.getSpeed();
		}
		
		public Builder set(@Nullable MSToolType toolType)
		{
			this.toolType = toolType;
			return this;
		}
		
		public Builder set(DestroyBlockEffect effect)
		{
			if(rightClickBlockEffect != null)
				throw new IllegalStateException("Destroy block effect has already been set");
			destroyBlockEffect = effect;
			return this;
		}
		
		public Builder set(RightClickBlockEffect effect)
		{
			if(rightClickBlockEffect != null)
				throw new IllegalStateException("Right click block effect has already been set");
			rightClickBlockEffect = effect;
			return this;
		}
		
		public Builder set(ItemRightClickEffect effect)
		{
			if(itemRightClickEffect != null)
				throw new IllegalStateException("Item right click effect has already been set");
			itemRightClickEffect = effect;
			return this;
		}
		
		public Builder pogo(double amplifier)
		{
			pogoMotion = amplifier;
			set(new PogoEffect(amplifier));
			add(new PogoEffect(amplifier));
			return this;
		}
		
		public Builder efficiency(float efficiency)
		{
			this.efficiency = efficiency;
			return this;
		}
		
		public Builder disableShield()
		{
			disableShield = true;
			return this;
		}
		
		public Builder knockback(float amplifier)
		{
			hasKnockback = true;
			add(OnHitEffect.enemyKnockback(amplifier));
			return this;
		}
		
		public Builder fireAspect(int seconds)
		{
			fireDuration = seconds;
			add(OnHitEffect.setOnFire(seconds));
			return this;
		}
		
		public Builder playSound(Supplier<SoundEvent> sound, float volume, float pitch)
		{
			playSound = true;
			add(OnHitEffect.playSound(sound, volume, pitch));
			return this;
		}
		
		public Builder backstab(float damage)
		{
			backstabDamage = damage;
			add(OnHitEffect.backstab(damage));
			return this;
		}
		
		public Builder switchTo(Supplier<Item> item)
		{
			innocuousDouble = true;
			set(ItemRightClickEffect.switchTo(item));
			return this;
		}
		
		public Builder add(OnHitEffect... effects)
		{
			onHitEffects.addAll(Arrays.asList(effects));
			return this;
		}
		
		public Builder add(InventoryTickEffect... effects)
		{
			tickEffects.addAll(Arrays.asList(effects));
			return this;
		}
		
		public Builder add(ToolAction... actions)
		{
			toolActions.addAll(List.of(actions));
			return this;
		}
		
		public Builder setEating(FinishUseItemEffect... effects)
		{
			return addItemUses(32, UseAnim.EAT, effects);
		}
		
		public Builder setEating(int duration, FinishUseItemEffect... effects)
		{
			return addItemUses(duration, UseAnim.EAT, effects);
		}
		
		public Builder addItemUses(int duration, UseAnim action, FinishUseItemEffect... effects)
		{
			useDuration = duration;
			useAction = action;
			itemUsageEffects.addAll(Arrays.asList(effects));
			set(ItemRightClickEffect.ACTIVE_HAND);
			return this;
		}
	}
}