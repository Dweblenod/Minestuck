package com.mraof.minestuck.client.gui;

import com.mojang.datafixers.util.Pair;
import com.mraof.minestuck.blockentity.redstone.ConwayGeneratorBlockEntity;
import com.mraof.minestuck.network.block.ConwayGeneratorSettingsPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class ConwayGeneratorScreen extends Screen
{
	public static final String TITLE = "minestuck.conway_generator";
	public static final String DONE_MESSAGE = "minestuck.conway_generator.done";
	private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation("minestuck", "textures/gui/generic_medium.png");
	
	private static final int GUI_WIDTH = 150;
	private static final int GUI_HEIGHT = 98;
	
	private final ConwayGeneratorBlockEntity be;
	private static final Map<Pair<Integer, Integer>, CellWidget> cellMap = new HashMap<>();
	
	
	ConwayGeneratorScreen(ConwayGeneratorBlockEntity be)
	{
		super(Component.translatable(TITLE));
		
		this.be = be;
	}
	
	@Override
	public void init()
	{
		int yOffset = (this.height / 2) - (GUI_HEIGHT / 2);
		int xOffset = (this.width / 2) - (GUI_WIDTH / 2);
		
		Map<Pair<Integer, Integer>, Boolean> startConfiguration = be.getStartConfiguration();
		
		for(Map.Entry<Pair<Integer, Integer>, Boolean> entry : startConfiguration.entrySet())
		{
			int x = entry.getKey().getFirst();
			int y = entry.getKey().getSecond();
			
			CellWidget cell = new CellWidget(xOffset + 10 + (x * CellWidget.DIMENSION - 1), yOffset + 10 + (y * CellWidget.DIMENSION - 1), entry.getKey(), entry.getValue());
			cellMap.put(Pair.of(x, y), cell);
			addRenderableWidget(cell);
		}
		
		addRenderableWidget(new ExtendedButton(xOffset + 60, yOffset + 70, 40, 20, Component.translatable(DONE_MESSAGE), button -> finish()));
	}
	
	@Override
	public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
	{
		super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		
		int yOffset = (this.height / 2) - (GUI_HEIGHT / 2);
		
		guiGraphics.blit(GUI_BACKGROUND, (this.width / 2) - (GUI_WIDTH / 2), yOffset, 0, 0, GUI_WIDTH, GUI_HEIGHT);
	}
	
	private void finish()
	{
		Map<Pair<Integer, Integer>, Boolean> startConfiguration = new HashMap<>();
		
		for(Map.Entry<Pair<Integer, Integer>, CellWidget> entry : cellMap.entrySet())
		{
			startConfiguration.put(entry.getKey(), entry.getValue().live);
		}
		
		PacketDistributor.SERVER.noArg().send(new ConwayGeneratorSettingsPacket(startConfiguration, be.getBlockPos()));
		onClose();
	}
	
	protected static class CellWidget extends AbstractWidget
	{
		public static int DIMENSION = 5;
		
		private Pair<Integer, Integer> coords;
		private boolean live;
		
		public CellWidget(int x, int y, Pair<Integer, Integer> coords, boolean live)
		{
			super(x, y, DIMENSION, DIMENSION, Component.empty());
			this.coords = coords;
			this.live = live;
			
			updateTooltip();
		}
		
		private void updateTooltip()
		{
			MutableComponent component = Component.literal("X: " + coords.getFirst() + ", Y: " + coords.getSecond() + "\n").append(live ? "(live)" : "(dead)");
			setTooltip(Tooltip.create(component));
		}
		
		@Override
		protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
		{
			int x = getX();
			int y = getY();
			
			//black or white square with a gray border
			graphics.fill(x, y, x + width, y + height, 0xFF666666);
			graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, live ? 0xFFFFFFFF : 0xFF000000);
		}
		
		@Override
		public void onClick(double mouseX, double mouseY, int button)
		{
			super.onClick(mouseX, mouseY, button);
			
			live = !live;
			updateTooltip();
		}
		
		@Override
		protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput)
		{
		}
	}
}
