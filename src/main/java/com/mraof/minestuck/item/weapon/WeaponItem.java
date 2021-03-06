package com.mraof.minestuck.item.weapon;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class WeaponItem extends SwordItem //To allow weapons to have the sweep effect
{
	private final float efficiency;
	//private static final HashMap<ToolType, Set<Material>> toolMaterials = new HashMap<>();
	
	@Nullable
	private final MSToolType toolType;
	
	public WeaponItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, float efficiency, @Nullable MSToolType toolType, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
		this.toolType = toolType;
		this.efficiency = efficiency;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		for(ToolType tool : getToolTypes(stack))
			if(state.isToolEffective(tool))
				return efficiency;
		if(toolType != null && toolType.canHarvest(state))
			return efficiency;
			
		return super.getDestroySpeed(stack, state);
	}
	
	//Thanks to Mraof for supplying the base for this method.
	@Override
	public boolean canHarvestBlock(BlockState blockIn)
	{
        ToolType blockTool = blockIn.getHarvestTool();
		Set<ToolType> itemTools = getToolTypes(new ItemStack(this));
		int blockHarvestLevel = blockIn.getHarvestLevel();
		int toolHarvestLevel = getHarvestLevel(new ItemStack(this), blockTool, null, blockIn);
		
        if(blockTool != null && itemTools.contains(blockTool))
        {
            return toolHarvestLevel >= blockHarvestLevel;
        } else		//We know that no specific harvestTool is specified, meaning any harvestTool efficiency is defined in the harvestTool itself.
        {			//This also means that there's no harvestTool *level* specified, so any harvestTool of that class is sufficient.
        	Material mat = blockIn.getMaterial();
        	if(mat.isToolNotRequired())
        		return true;
        	
        	if(toolType != null)
			{
				if(toolType.getHarvestMaterials().contains(mat) && toolHarvestLevel >= blockHarvestLevel)
					return true;
			}
			return super.canHarvestBlock(blockIn);
        }
        
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
	{
		if (state.getBlockHardness(worldIn, pos) != 0.0F)
		{
			int damage = 2;
			
			if(getToolTypes(stack).contains(state.getHarvestTool()))
				damage = 1;
			else if(toolType != null)
			{
				if(toolType.getHarvestMaterials().contains(state.getMaterial()))
					damage = 1;
			}
			
			stack.damageItem(damage, entityLiving, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
		}
		
		return true;
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState)
	{
		int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
		if(harvestLevel == -1 && getToolTypes(stack).contains(tool))
			return getTier().getHarvestLevel();
		return harvestLevel;
	}
	
	@Override
	public Set<ToolType> getToolTypes(ItemStack stack)
	{
		if(toolType == null)
			return super.getToolTypes(stack);
		else
		{
			Set<ToolType> types = new HashSet<>();
			types.addAll(toolType.getToolTypes());
			types.addAll(super.getToolTypes(stack));
			return types;
		}
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return isDamageable() || (toolType != null && !toolType.getEnchantments().isEmpty());
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
	{
		if(isDamageable() && enchantment.type == EnchantmentType.BREAKABLE)
			return true;
		if(toolType == null)
			return false;
		
		return toolType.getEnchantments().contains(enchantment);
	}
	
	@Nullable
	public MSToolType getToolType() {return toolType;}
	public float getEfficiency()		{return efficiency;}
}