package com.mraof.minestuck.client.gui.playerStats;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mraof.minestuck.player.KindAbstratusList;
import com.mraof.minestuck.player.KindAbstratusType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class StrifeSpecibusScreen extends PlayerStatsScreen
{
	public static final String TITLE = "minestuck.strife_specibus";
	public static final String KIND_ABSTRATUS = "minestuck.kind_abstratus";
	
	private static final ResourceLocation guiStrifeSelector = new ResourceLocation("minestuck", "textures/gui/strife_selector.png");
	
	private static final int columnWidth = 70, columns = 3;
	
	public StrifeSpecibusScreen()
	{
		super(new TranslationTextComponent(TITLE));
		guiWidth = 228;
		guiHeight = 150;
	}
	
	@Override
	public void render(int xcor, int ycor, float par3)
	{
		super.render(xcor, ycor, par3);
		this.renderBackground();
		
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		drawTabs();
		
		this.mc.getTextureManager().bindTexture(guiStrifeSelector);
		
		this.blit(xOffset, yOffset, 0, 0, guiWidth, guiHeight);
		
		String message = "This feature isn't implemented yet.";//new TranslationTextComponent(KIND_ABSTRATUS).getFormattedText();
		mc.fontRenderer.drawString(message, (this.width / 2F) - mc.fontRenderer.getStringWidth(message) / 2F, yOffset + 12, 0x404040);
		
		int i = 0;
		for(KindAbstratusType type : KindAbstratusList.getTypeList()) {
			String typeName = type.getDisplayName().getFormattedText();
			int xPos = xOffset+9+(columnWidth)*((i%columns)+1)-mc.fontRenderer.getStringWidth(typeName);
			int yPos = yOffset+35+(mc.fontRenderer.FONT_HEIGHT+1)*(int)(i/columns);
			
			if(!isPointInRegion(xOffset+9+(columnWidth)*(i%columns)+1, yPos-1, columnWidth-1, mc.fontRenderer.FONT_HEIGHT+1, xcor, ycor))
				mc.fontRenderer.drawString(typeName, xPos, yPos, 0xFFFFFF);
			else {
				fill(xOffset+9+(columnWidth)*(i%columns)+1, yPos-1, xOffset+9+(columnWidth)*((i%columns)+1), yPos+mc.fontRenderer.FONT_HEIGHT, 0xFFAFAFAF);
				mc.fontRenderer.drawString(typeName, xPos, yPos, 0x000000);
			}
			i++;
		}
		
		drawActiveTabAndOther(xcor, ycor);
		
	}
	
}
