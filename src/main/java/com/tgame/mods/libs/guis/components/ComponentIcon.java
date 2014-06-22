package com.tgame.mods.libs.guis.components;

import com.tgame.mods.libs.guis.GuiBase;
import com.tgame.mods.libs.utility.RenderUtility;

/**
 * @author tgame14
 * @since 22/06/2014
 */
public class ComponentIcon extends ComponentBase
{
	private int texU = 0;
	private int texV = 0;

	public ComponentIcon(GuiBase gui, int posX, int posY) {

		super(gui, posX, posY);
	}

	public ComponentIcon setTextureOffsets(int u, int v) {

		texU = u;
		texV = v;
		return this;
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {

		RenderUtility.bindTexture(texture);
		drawTexturedModalRect(posX, posY, texU, texV, sizeX, sizeY);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {

		return;
	}
}
