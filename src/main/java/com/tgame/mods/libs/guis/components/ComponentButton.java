package com.tgame.mods.libs.guis.components;

import com.tgame.mods.libs.guis.GuiBase;
import com.tgame.mods.libs.utility.RenderUtility;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * @author tgame14
 * @since 22/06/2014
 */
public class ComponentButton extends ComponentBase
{
	private int sheetX;
	private int sheetY;
	private int hoverX;
	private int hoverY;
	private int disabledX;
	private int disabledY;
	private boolean tooltipLocalized;
	private String tooltip;

	public ComponentButton(GuiBase gui, int posX, int posY, String name, int sheetX, int sheetY, int hoverX, int hoverY, int sizeX, int sizeY, String texture)
	{

		super(gui, posX, posY);
		setName(name);
		setSize(sizeX, sizeY);
		setTexture(texture, texW, texH);
		this.sheetX = sheetX;
		this.sheetY = sheetY;
		this.hoverX = hoverX;
		this.hoverY = hoverY;

		this.disabledX = 0;
		this.disabledY = 0;
		this.tooltipLocalized = false;
	}


	public ComponentButton(GuiBase gui, int posX, int posY, String name, int sheetX, int sheetY, int hoverX, int hoverY, int disabledX, int disabledY, int sizeX,
			int sizeY, String texture)
	{

		this(gui, posX, posY, name, sheetX, sheetY, hoverX, hoverY, sizeX, sizeY, texture);

		this.disabledX = disabledX;
		this.disabledY = disabledY;
	}

	public ComponentButton clearToolTip()
	{

		this.tooltip = null;
		return this;
	}

	public ComponentButton setToolTip(String tooltip)
	{

		this.tooltip = tooltip;
		return this;
	}

	public ComponentButton setToolTipLocalized(boolean localized)
	{

		this.tooltipLocalized = localized;
		return this;
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks)
	{

		RenderUtility.bindTexture(texture);
		if (isEnabled())
		{
			if (intersectsWith(mouseX, mouseY))
			{

				drawTexturedModalRect(posX, posY, hoverX, hoverY, sizeX, sizeY);
			}
			else
			{
				drawTexturedModalRect(posX, posY, sheetX, sheetY, sizeX, sizeY);
			}
		}
		else
		{
			drawTexturedModalRect(posX, posY, disabledX, disabledY, sizeX, sizeY);
		}
	}

	@Override
	public void drawForeground(int mouseX, int mouseY)
	{

	}

	@Override
	public void addTooltip(List<String> list)
	{

		if (tooltip != null)
		{
			if (tooltipLocalized)
			{
				list.add(tooltip);
			}
			else
			{
				list.add(StatCollector.translateToLocal(tooltip));
			}
		}
	}

	@Override
	public boolean onMousePressed(int x, int y, int mouseButton)
	{

		if (isEnabled())
		{
			gui.handleComponentButtonClick(getName(), mouseButton);
			return true;
		}
		return false;
	}

	public void setSheetX(int pos)
	{

		sheetX = pos;
	}

	public void setSheetY(int pos)
	{

		sheetY = pos;
	}

	public void setHoverX(int pos)
	{

		hoverX = pos;
	}

	public void setHoverY(int pos)
	{

		hoverY = pos;
	}

	public void setActive()
	{

		setEnabled(true);
	}

	public void setDisabled()
	{

		setEnabled(false);
	}

}
