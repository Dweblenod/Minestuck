package com.mraof.minestuck.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mraof.minestuck.inventory.SendificatorContainer;
import com.mraof.minestuck.network.MSPacketHandler;
import com.mraof.minestuck.network.SendificatorPacket;
import com.mraof.minestuck.tileentity.machine.MachineProcessTileEntity;
import com.mraof.minestuck.tileentity.machine.SendificatorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

import javax.annotation.Nullable;

public class SendificatorScreen extends MachineScreen<SendificatorContainer>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation("minestuck:textures/gui/sendificator.png");
	private static final ResourceLocation PROGRESS = new ResourceLocation("minestuck:textures/gui/progress/uranium_level.png");
	
	private final int progressX;
	private final int progressY;
	private final int progressWidth;
	private final int progressHeight;
	private final int goX;
	private final int goY;
	
	private final SendificatorTileEntity te;
	private TextFieldWidget destinationTextFieldX;
	private TextFieldWidget destinationTextFieldY;
	private TextFieldWidget destinationTextFieldZ;
	private ExtendedButton updateButton;
	private ExtendedButton goButton;
	@Nullable
	private BlockPos startingDestPos;
	@Nullable
	private BlockPos parsedPos;
	
	
	SendificatorScreen(SendificatorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(MachineProcessTileEntity.RunType.BUTTON, screenContainer, inv, titleIn);
		
		//sets progress bar information
		progressX = 67 - 15;
		progressY = 24;
		progressWidth = 35;
		progressHeight = 39;
		goX = 115;
		goY = 60;
		
		//TODO find out if there is a more elegant method of getting the te
		SendificatorTileEntity tempTE = null;
		World world = inv.player.level;
		if(world != null && screenContainer.machinePos != null)
		{
			TileEntity tileEntity = world.getBlockEntity(screenContainer.machinePos);
			if(tileEntity instanceof SendificatorTileEntity)
				tempTE = ((SendificatorTileEntity) tileEntity); //will cause crashes if a check for a null te is not done
		}
		te = tempTE;
		startingDestPos = te != null ? te.getDestinationBlockPos() : null;
	}
	
	@Override
	public void init()
	{
		super.init();
		
		int yOffset = (height - imageHeight) / 2;
		
		updateButton = addButton(new ExtendedButton((width - imageWidth) / 2 + 105, yOffset + 40, 50, 12, new StringTextComponent("Update"), button -> updateDestinationPos()));
		
		this.destinationTextFieldX = new TextFieldWidget(this.font, this.width / 2 - 10, yOffset + 10, 35, 15, new StringTextComponent("X value of destination block pos")); //TODO make these translatable
		addButton(destinationTextFieldX);
		destinationTextFieldX.setResponder(s -> onTextFieldChange());
		
		this.destinationTextFieldY = new TextFieldWidget(this.font, this.width / 2 + 25, yOffset + 10, 20, 15, new StringTextComponent("Y value of destination block pos"));
		addButton(destinationTextFieldY);
		destinationTextFieldY.setResponder(s -> onTextFieldChange());
		
		this.destinationTextFieldZ = new TextFieldWidget(this.font, this.width / 2 + 45, yOffset + 10, 35, 15, new StringTextComponent("Z value of destination block pos"));
		addButton(destinationTextFieldZ);
		destinationTextFieldZ.setResponder(s -> onTextFieldChange());
		
		//activates processContents() in SendificatorTileEntity
		goButton = new GoButton((width - imageWidth) / 2 + goX, yOffset + goY, 30, 12, new StringTextComponent(menu.overrideStop() ? "STOP" : "GO"));
		addButton(goButton);
		
		if(startingDestPos != null)
		{
			this.destinationTextFieldX.setValue(String.valueOf(startingDestPos.getX()));
			this.destinationTextFieldY.setValue(String.valueOf(startingDestPos.getY()));
			this.destinationTextFieldZ.setValue(String.valueOf(startingDestPos.getZ()));
		}
		
		updateButton.active = false;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		goButton.active = te != null && te.getDestinationBlockPos() != null;
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY)
	{
		//draws the name of the TE
		font.draw(matrixStack, this.title, 8, 6, 0x404040);
		
		//draws "Inventory" or your regional equivalent
		font.draw(matrixStack, this.inventory.getDisplayName(), 8, imageHeight - 96 + 2, 0x404040);
	}
	
	@Override
	protected void renderBg(MatrixStack matrixStack, float par1, int par2, int par3)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		//draw background
		this.minecraft.getTextureManager().bind(BACKGROUND);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		this.blit(matrixStack, x, y, 0, 0, imageWidth, imageHeight);
		
		//draw progress bar
		this.minecraft.getTextureManager().bind(PROGRESS);
		int width = progressWidth;
		int height = getScaledValue(menu.getFuel(), SendificatorTileEntity.MAX_FUEL, progressHeight);
		blit(matrixStack, x + progressX, y + progressY + progressHeight - height, 0, progressHeight - height, width, height, progressWidth, progressHeight);
	}
	
	@Override
	public void resize(Minecraft minecraft, int width, int height)
	{
		String destX = this.destinationTextFieldX.getValue();
		String destY = this.destinationTextFieldY.getValue();
		String destZ = this.destinationTextFieldZ.getValue();
		this.init(minecraft, width, height);
		this.destinationTextFieldX.setValue(destX);
		this.destinationTextFieldY.setValue(destY);
		this.destinationTextFieldZ.setValue(destZ);
	}
	
	private void updateDestinationPos()
	{
		if(parsedPos != null)
		{
			MSPacketHandler.sendToServer(new SendificatorPacket(parsedPos));
			startingDestPos = parsedPos;
			updateButton.active = false;
		}
	}
	
	/**
	 * Sets the Update button to active if a valid change has occurred
	 */
	private void onTextFieldChange()
	{
		try
		{
			parsedPos = parseBlockPos();
			// Do not make the update button clickable if the position is not different
			updateButton.active = !parsedPos.equals(startingDestPos);
		} catch(NumberFormatException ignored)
		{
			parsedPos = null;
			updateButton.active = false;
		}
	}
	
	private static int parseInt(TextFieldWidget widget)
			throws NumberFormatException
	{
		return Integer.parseInt(widget.getValue());
	}
	
	private BlockPos parseBlockPos()
			throws NumberFormatException
	{
		int x = parseInt(destinationTextFieldX);
		int y = parseInt(destinationTextFieldY);
		int z = parseInt(destinationTextFieldZ);
		
		return new BlockPos(x, y, z);
	}
}