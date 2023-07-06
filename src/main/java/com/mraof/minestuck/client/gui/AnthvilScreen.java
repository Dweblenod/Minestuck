package com.mraof.minestuck.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.*;
import com.mraof.minestuck.alchemy.recipe.GristCostRecipe;
import com.mraof.minestuck.blockentity.machine.AnthvilBlockEntity;
import com.mraof.minestuck.client.util.GuiUtil;
import com.mraof.minestuck.inventory.AnthvilMenu;
import com.mraof.minestuck.network.AnthvilPacket;
import com.mraof.minestuck.network.MSPacketHandler;
import com.mraof.minestuck.network.MachinePacket;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.widget.ExtendedButton;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AnthvilScreen extends MachineScreen<AnthvilMenu>
{
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Minestuck.MOD_ID, "textures/gui/anthvil.png");
	private static final ResourceLocation FUEL_STATUS = new ResourceLocation(Minestuck.MOD_ID, "textures/gui/progress/uranium_level.png");
	
	private static final int FUEL_STATUS_X = 133;
	private static final int FUEL_STATUS_Y = 7;
	private static final int STATUS_WIDTH = 35;
	private static final int STATUS_HEIGHT = 39;
	private static final int BUTTON_X = 12;
	private static final int BUTTON_Y = 38;
	private final AnthvilBlockEntity anthvilBE;
	private final AnthvilMenu screenContainer;
	
	AnthvilScreen(AnthvilMenu screenContainer, Inventory inv, Component titleIn)
	{
		super(screenContainer, inv, titleIn);
		
		this.screenContainer = screenContainer;
		this.anthvilBE = (AnthvilBlockEntity) inv.player.level.getBlockEntity(screenContainer.machinePos);
	}
	
	@Override
	public void init()
	{
		super.init();
		
		//activates processContents() in AnthvilBlockEntity, no GoButton due to necessity of player data
		addRenderableWidget(new ExtendedButton(this.leftPos + BUTTON_X, this.topPos + BUTTON_Y, 30, 12, Component.literal("MEND"), button -> mend()));
		addRenderableWidget(new ExtendedButton(this.leftPos + BUTTON_X, this.topPos + BUTTON_Y + 16, 30, 12, Component.literal("DONE"), button -> finish()));
	}
	
	private void mend()
	{
		MSPacketHandler.sendToServer(new MachinePacket.SetRunning(!this.menu.isRunning())); //handles refueling uranium using MachineScreen infrastructure
		MSPacketHandler.sendToServer(new AnthvilPacket()); //sends a request to mend
	}
	
	private void finish()
	{
		onClose();
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(poseStack, mouseX, mouseY);
		
		if(anthvilBE != null)
		{
			Level level = anthvilBE.getLevel();
			
			if(level != null)
			{
				ItemStack stack = screenContainer.getSlot(0).getItem();
				GristSet fullSet = GristCostRecipe.findCostForItem(stack, null, false, level);
				if(fullSet != null && !fullSet.isEmpty())
				{
					GristAmount pickedGrist = AnthvilBlockEntity.getUsedGrist(fullSet);
					
					GuiUtil.drawGristBoard(poseStack, pickedGrist, GuiUtil.GristboardMode.ALCHEMITER, (width - this.leftPos) / 2 - 4, (height - this.topPos) / 2 - 48, font, 2);
					//draw the grist
					Component tooltip = GuiUtil.getGristboardTooltip(pickedGrist, GuiUtil.GristboardMode.ALCHEMITER, mouseX, mouseY, (width - this.leftPos) / 2 - 4, (height - this.topPos) / 2 - 48, font, 2);
					if(tooltip != null)
						this.renderTooltip(poseStack, tooltip, mouseX, mouseY);
				}
			}
		}
	}
	
	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY)
	{
		//draws the name of the BE
		font.draw(poseStack, this.title, 8, 6, 0x404040);
		
		//draws "Inventory" or your regional equivalent
		font.draw(poseStack, this.playerInventoryTitle, 8, imageHeight - 96 + 2, 0x404040);
	}
	
	@Override
	protected void renderBg(PoseStack poseStack, float par1, int par2, int par3)
	{
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		
		//draw background
		RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
		this.blit(poseStack, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);
		
		//draw fuel bar
		RenderSystem.setShaderTexture(0, FUEL_STATUS);
		int fuelHeight = getScaledValue(menu.getFuel(), AnthvilBlockEntity.MAX_FUEL, STATUS_HEIGHT);
		blit(poseStack, this.leftPos + FUEL_STATUS_X, this.topPos + FUEL_STATUS_Y + STATUS_HEIGHT - fuelHeight,
				0, STATUS_HEIGHT - fuelHeight, STATUS_WIDTH, fuelHeight, STATUS_WIDTH, STATUS_HEIGHT);
	}
}