package com.mraof.minestuck.blockentity.machine;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.blockentity.MSBlockEntityTypes;
import com.mraof.minestuck.entity.item.GristEntity;
import com.mraof.minestuck.inventory.AutowidgetMenu;
import com.mraof.minestuck.util.ExtraForgeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

import javax.annotation.Nullable;

public class AutowidgetBlockEntity extends MachineProcessBlockEntity implements MenuProvider, UraniumPowered
{
	public static final String TITLE = "container.minestuck.autowidget";
	public static final short MAX_FUEL = 128;
	public static final short WIDGET_FUEL_COST = 2;
	public static final short WIDGET_RATE = 100; //how many ticks pass before widgeting occurs
	
	private short fuel = 0;
	private int timer = 0;
	
	private final DataSlot fuelHolder = new DataSlot()
	{
		@Override
		public int get()
		{
			return fuel;
		}
		
		@Override
		public void set(int value)
		{
			fuel = (short) value;
		}
	};
	
	
	public AutowidgetBlockEntity(BlockPos pos, BlockState state)
	{
		super(MSBlockEntityTypes.AUTOWIDGET.get(), pos, state);
	}
	
	@Override
	public Component getDisplayName()
	{
		return Component.translatable(TITLE);
	}
	
	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		
		fuel = compound.getShort("fuel");
		timer = compound.getInt("timer");
	}
	
	@Override
	public void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		
		compound.putShort("fuel", fuel);
		compound.putInt("timer", timer);
	}
	
	@Override
	protected ItemStackHandler createItemHandler()
	{
		return new CustomHandler(2, (index, stack) -> index == 0 || stack.is(ExtraForgeTags.Items.URANIUM_CHUNKS));
	}
	
	@Override
	protected void tick()
	{
		if(level == null || level.isClientSide)
			return;
		
		if(canBeRefueled() && itemHandler.getStackInSlot(1).is(ExtraForgeTags.Items.URANIUM_CHUNKS))
		{
			addFuel((short) FUEL_INCREASE);
			itemHandler.extractItem(1, 1, false);
		}
		
		ItemStack slotStack = itemHandler.getStackInSlot(0);
		
		boolean ready = canWidget(slotStack) && hasEnoughFuel(this);
		
		if(timer == 100 && ready)
		{
			process(slotStack);
			
			timer = 0;
			
			return;
		}
		
		if(ready)
			timer++;
		else
			timer = 0;
	}
	
	public void process(ItemStack slotStack)
	{
		GristSet gristSet = getCardGrist(slotStack);
		
		boolean collectorFound = false;
		for(Direction direction : Direction.Plane.HORIZONTAL)
		{
			if(level.getBlockEntity(worldPosition.relative(direction)) instanceof GristCollectorBlockEntity collector)
			{
				gristSet.asAmounts().forEach(collector::addGristAmount);
				collectorFound = true;
				break;
			}
		}
		
		if(!collectorFound)
			GristEntity.spawnGristEntities(gristSet, level, worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5, level.random, entity -> entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.5, 0.5, 0.5)));
		
		itemHandler.extractItem(0, 1, false);
		
		fuel -= WIDGET_FUEL_COST;
	}
	
	private boolean canWidget(ItemStack slotStack)
	{
		if(slotStack.isEmpty())
			return false;
		
		//GristSet fullSet = GristCostRecipe.findCostForItem(slotStack, null, false, level);
		GristSet gristSet = getCardGrist(slotStack);
		
		return gristSet != null && !gristSet.isEmpty();
	}
	
	private GristSet getCardGrist(ItemStack slotStack)
	{
		return GristWidgetBlockEntity.getGristWidgetResult(slotStack.copyWithCount(1), level);
	}
	
	private static boolean hasEnoughFuel(AutowidgetBlockEntity widget)
	{
		return widget.fuel >= WIDGET_FUEL_COST;
	}
	
	/**
	 * Checks that fuel can be added without any excess/wasted points being attributed
	 */
	public boolean canBeRefueled()
	{
		return fuel <= MAX_FUEL - FUEL_INCREASE;
	}
	
	@Override
	public void addFuel(short fuelAmount)
	{
		fuel += fuelAmount;
	}
	
	@Override
	public boolean atMaxFuel()
	{
		return fuel >= MAX_FUEL;
	}
	
	@Nullable
	public IItemHandler getItemHandler(@Nullable Direction side)
	{
		if(side == null)
			return this.itemHandler;
		
		if(side == Direction.UP)
			return new RangedWrapper(itemHandler, 0, 1);
		if(side == Direction.DOWN)
			return null;
		
		return new RangedWrapper(itemHandler, 1, 2);
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player)
	{
		return new AutowidgetMenu(windowId, playerInventory, itemHandler, fuelHolder, ContainerLevelAccess.create(level, worldPosition));
	}
}