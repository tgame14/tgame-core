package com.tgame.mods.libs.guis.components;

import com.tgame.mods.libs.guis.GuiBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * @author tgame14
 * @since 22/06/2014
 */
public abstract class ComponentBase
{
	protected GuiBase gui;
	protected ResourceLocation texture;

	protected int posX;
	protected int posY;

	protected int sizeX;
	protected int sizeY;

	protected int texW;
	protected int texH;

	protected String name;

	private boolean visible;
	private boolean enabled;

	public ComponentBase(GuiBase gui, int posX, int posY)
	{
		this.gui = gui;
		this.posX = posX;
		this.posY = posY;

		this.texW = 256;
		this.texH = 256;
	}

	public ComponentBase(GuiBase gui, int posX, int posY, int width, int height)
	{
		this(gui, posX, posY);

		this.sizeX = width;
		this.sizeY = height;
	}

	public ComponentBase setName(String name)
	{

		this.name = name;
		return this;
	}

	public ComponentBase setPosition(int posX, int posY)
	{

		this.posX = posX;
		this.posY = posY;
		return this;
	}

	public ComponentBase setSize(int sizeX, int sizeY)
	{

		this.sizeX = sizeX;
		this.sizeY = sizeY;
		return this;
	}

	public ComponentBase setTexture(String texture, int texW, int texH)
	{

		this.texture = new ResourceLocation(texture);
		this.texW = texW;
		this.texH = texH;
		return this;
	}

	public final ComponentBase setVisible(boolean visible)
	{

		this.visible = visible;
		return this;
	}

	public boolean isVisible()
	{

		return visible;
	}

	public final ComponentBase setEnabled(boolean enabled)
	{

		this.enabled = enabled;
		return this;
	}

	public boolean isEnabled()
	{

		return enabled;
	}

	public void update(int mouseX, int mouseY)
	{

		update();
	}

	public void update()
	{

	}

	public abstract void drawBackground(int mouseX, int mouseY, float gameTicks);

	public abstract void drawForeground(int mouseX, int mouseY);

	public void addTooltip(List<String> list)
	{

	}

	public void drawModelRect(int x, int y, int width, int height, int color)
	{
		gui.drawSizedModalRect(x, y, width, height, color);
	}

	public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
	{

		gui.drawSizedTexturedModalRect(x, y, u, v, width, height, texW, texH);
	}

	public void drawCenteredString(FontRenderer fontRenderer, String text, int x, int y, int color)
	{

		fontRenderer.drawStringWithShadow(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
	}

	public boolean onMousePressed(int mouseX, int mouseY, int mouseButton)
	{

		return false;
	}

	public void onMouseReleased(int mouseX, int mouseY)
	{

		return;
	}

	public boolean onMouseWheel(int mouseX, int mouseY, int movement)
	{

		return false;
	}

	public boolean onKeyTyped(char characterTyped, int keyPressed)
	{

		return false;
	}

	public boolean intersectsWith(int mouseX, int mouseY)
	{

		if (mouseX >= this.posX && mouseX <= this.posX + this.sizeX && mouseY >= this.posY && mouseY <= this.posY + this.sizeY)
		{
			return true;
		}
		return false;
	}

	public final String getName()
	{

		return name;
	}

	public final GuiBase getContainerScreen()
	{

		return gui;
	}

	public final FontRenderer getFontRenderer()
	{

		return gui.getFontRenderer();
	}

	/**
	 * This method is relative to the GUI's y coordinate
	 */
	public final int getPosY()
	{

		return posY;
	}

	/**
	 * This method is relative to the GUI's x coordinate
	 */
	public final int getPosX()
	{

		return posX;
	}

	public final int getHeight()
	{

		return sizeY;
	}

	public final int getWidth()
	{

		return sizeX;
	}

}
