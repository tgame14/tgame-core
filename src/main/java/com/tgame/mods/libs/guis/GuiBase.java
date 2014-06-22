package com.tgame.mods.libs.guis;

import com.tgame.mods.libs.guis.components.ComponentBase;
import com.tgame.mods.libs.guis.slots.SlotFalseCopy;
import com.tgame.mods.libs.utility.RenderUtility;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tgame14
 * @since 22/06/2014
 */
public abstract class GuiBase extends GuiContainer
{
	public static final SoundHandler guiSoundHandler = FMLClientHandler.instance().getClient().getSoundHandler();

	protected boolean drawInv;
	protected int mouseX;
	protected int mouseY;

	protected int prevIndex;

	protected String name;
	protected ResourceLocation texture;

	protected List<ComponentBase> components;

	protected List<String> tooltip;
	protected boolean tooltips = true;

	public GuiBase(Container container)
	{
		super(container);

		this.components = new ArrayList<ComponentBase>();
		this.tooltip = new LinkedList<String>();

		this.tooltips = true;
		this.mouseX = 0;
		this.mouseY = 0;
		this.prevIndex = -1;

	}

	public GuiBase(Container container, ResourceLocation texture)
	{
		this(container);
		this.texture = texture;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		components.clear();
	}

	@Override
	public void drawScreen(int x, int y, float partialTick)
	{

		updateComponentInformation();

		super.drawScreen(x, y, partialTick);

		if (tooltips && mc.thePlayer.inventory.getItemStack() == null)
		{
			addTooltips(tooltip);
			drawTooltip(tooltip);
		}
		mouseX = x - guiLeft;
		mouseY = y - guiTop;

		updateComponents();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{

		fontRendererObj.drawString(StatCollector.translateToLocal(name), getCenteredOffset(StatCollector.translateToLocal(name)), 6, 0x404040);
		if (drawInv)
		{
			fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 3, 0x404040);
		}

		drawComponents(0, true);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y)
	{

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		mouseX = x - guiLeft;
		mouseY = y - guiTop;

		GL11.glPushMatrix();
		GL11.glTranslatef(guiLeft, guiTop, 0.0F);
		drawComponents(partialTick, false);
		GL11.glPopMatrix();
	}

	@Override
	protected void keyTyped(char characterTyped, int keyPressed)
	{

		for (int i = components.size(); i-- > 0; )
		{
			ComponentBase c = components.get(i);
			if (!c.isVisible() || !c.isEnabled())
			{
				continue;
			}
			if (c.onKeyTyped(characterTyped, keyPressed))
			{
				return;
			}
		}
		super.keyTyped(characterTyped, keyPressed);
	}

	@Override
	public void handleMouseInput()
	{

		int x = Mouse.getEventX() * width / mc.displayWidth;
		int y = height - Mouse.getEventY() * height / mc.displayHeight - 1;

		mouseX = x - guiLeft;
		mouseY = y - guiTop;

		int wheelMovement = Mouse.getEventDWheel();

		if (wheelMovement != 0)
		{
			for (int i = components.size(); i-- > 0; )
			{
				ComponentBase c = components.get(i);
				if (!c.isVisible() || !c.isEnabled() || !c.intersectsWith(mouseX, mouseY))
				{
					continue;
				}
				if (c.onMouseWheel(mouseX, mouseY, wheelMovement))
				{
					return;
				}
			}
		}
		super.handleMouseInput();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{

		mouseX -= guiLeft;
		mouseY -= guiTop;

		for (int i = components.size(); i-- > 0; )
		{
			ComponentBase c = components.get(i);
			if (!c.isVisible() || !c.isEnabled() || !c.intersectsWith(mouseX, mouseY))
			{
				continue;
			}
			if (c.onMousePressed(mouseX, mouseY, mouseButton))
			{
				return;
			}
		}

		mouseX += guiLeft;
		mouseY += guiTop;

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton)
	{

		mouseX -= guiLeft;
		mouseY -= guiTop;

		if (mouseButton >= 0 && mouseButton <= 2)
		{ // 0:left, 1:right, 2: middle
			for (int i = components.size(); i-- > 0; )
			{
				ComponentBase c = components.get(i);
				if (!c.isVisible() || !c.isEnabled())
				{ // no bounds checking on mouseUp events
					continue;
				}
				c.onMouseReleased(mouseX, mouseY);
			}
		}
		mouseX += guiLeft;
		mouseY += guiTop;

		super.mouseMovedOrUp(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseClickMove(int mX, int mY, int lastClick, long timeSinceClick)
	{

		Slot slot = getSlotAtPosition(mX, mY);
		ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();

		if (this.field_147007_t && slot != null && itemstack != null && slot instanceof SlotFalseCopy)
		{
			if (prevIndex != slot.slotNumber)
			{
				prevIndex = slot.slotNumber;
				this.handleMouseClick(slot, slot.slotNumber, 0, 0);
			}
		}
		else
		{
			prevIndex = -1;
			super.mouseClickMove(mX, mY, lastClick, timeSinceClick);
		}
	}

	public Slot getSlotAtPosition(int xCoord, int yCoord)
	{

		for (int k = 0; k < this.inventorySlots.inventorySlots.size(); ++k)
		{
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(k);

			if (this.isMouseOverSlot(slot, xCoord, yCoord))
			{
				return slot;
			}
		}
		return null;
	}

	public boolean isMouseOverSlot(Slot theSlot, int xCoord, int yCoord)
	{

		return this.func_146978_c(theSlot.xDisplayPosition, theSlot.yDisplayPosition, 16, 16, xCoord, yCoord);
	}

	/**
	 * Draws the Components for this GUI.
	 */
	protected void drawComponents(float partialTick, boolean foreground)
	{

		if (foreground)
		{
			for (int i = 0; i < components.size(); i++)
			{
				ComponentBase component = components.get(i);
				if (component.isVisible())
				{
					component.drawForeground(mouseX, mouseY);
				}
			}
		}
		else
		{
			for (int i = 0; i < components.size(); i++)
			{
				ComponentBase component = components.get(i);
				if (component.isVisible())
				{
					component.drawBackground(mouseX, mouseY, partialTick);
				}
			}
		}
	}

	/**
	 * Called by NEI if installed
	 */
	// @Override
	public List<String> handleTooltip(int mousex, int mousey, List<String> tooltip)
	{

		if (mc.thePlayer.inventory.getItemStack() == null)
		{
			addTooltips(tooltip);
		}
		return tooltip;
	}

	public void addTooltips(List<String> tooltip)
	{
		ComponentBase Component = getComponentAtPosition(mouseX, mouseY);

		if (Component != null)
		{
			Component.addTooltip(tooltip);
		}
	}

	/* ELEMENTS */
	public ComponentBase addComponent(ComponentBase Component)
	{

		components.add(Component);
		return Component;
	}

	protected ComponentBase getComponentAtPosition(int mX, int mY)
	{

		for (int i = components.size(); i-- > 0; )
		{
			ComponentBase component = components.get(i);
			if (component.intersectsWith(mX, mY))
			{
				return component;
			}
		}
		return null;
	}

	protected final void updateComponents()
	{

		for (int i = components.size(); i-- > 0; )
		{
			ComponentBase c = components.get(i);
			if (c.isVisible() && c.isEnabled())
			{
				c.update(mouseX, mouseY);
			}
		}
	}

	protected void updateComponentInformation()
	{

	}

	public void handleComponentButtonClick(String buttonName, int mouseButton)
	{

	}

	/// * * * HELPERS * * * ///

	public void bindTexture(ResourceLocation texture)
	{

		mc.renderEngine.bindTexture(texture);
	}

	/**
	 * Abstract method to retrieve icons by name from a registry You must override this if you use any of the String methods below
	 */
	public IIcon getIcon(String name)
	{

		return null;
	}

	/**
	 * Essentially a placeholder method for tabs to use should they need to draw a button.
	 */
	public void drawButton(IIcon icon, int x, int y, int spriteSheet, int mode)
	{

		drawIcon(icon, x, y, spriteSheet);
	}

	public void drawButton(String iconName, int x, int y, int spriteSheet, int mode)
	{

		drawButton(getIcon(iconName), x, y, spriteSheet, mode);
	}

	/**
	 * Simple method used to draw a fluid of arbitrary size.
	 */
	public void drawFluid(int x, int y, FluidStack fluid, int width, int height)
	{

		if (fluid == null || fluid.getFluid() == null)
		{
			return;
		}
		RenderUtility.setBlockTextureSheet();
		RenderUtility.setColor3ub(fluid.getFluid().getColor(fluid));

		drawTiledTexture(x, y, fluid.getFluid().getIcon(fluid), width, height);
	}

	public void drawTiledTexture(int x, int y, IIcon icon, int width, int height)
	{

		int i = 0;
		int j = 0;

		int drawHeight = 0;
		int drawWidth = 0;

		for (i = 0; i < width; i += 16)
		{
			for (j = 0; j < height; j += 16)
			{
				drawWidth = Math.min(width - i, 16);
				drawHeight = Math.min(height - j, 16);
				drawScaledTexturedModelRectFromIcon(x + i, y + j, icon, drawWidth, drawHeight);
			}
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
	}

	public void drawIcon(IIcon icon, int x, int y, int spriteSheet)
	{

		if (spriteSheet == 0)
		{
			RenderUtility.setBlockTextureSheet();
		}
		else
		{
			RenderUtility.setItemTextureSheet();
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		drawTexturedModelRectFromIcon(x, y, icon, 16, 16);
	}

	public void drawIcon(String iconName, int x, int y, int spriteSheet)
	{

		drawIcon(getIcon(iconName), x, y, spriteSheet);
	}

	public void drawSizedModalRect(int x1, int y1, int x2, int y2, int color)
	{

		int temp;

		if (x1 < x2)
		{
			temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (y1 < y2)
		{
			temp = y1;
			y1 = y2;
			y2 = temp;
		}

		float a = (color >> 24 & 255) / 255.0F;
		float r = (color >> 16 & 255) / 255.0F;
		float g = (color >> 8 & 255) / 255.0F;
		float b = (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(r, g, b, a);
		tessellator.startDrawingQuads();
		tessellator.addVertex(x1, y2, this.zLevel);
		tessellator.addVertex(x2, y2, this.zLevel);
		tessellator.addVertex(x2, y1, this.zLevel);
		tessellator.addVertex(x1, y1, this.zLevel);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void drawSizedTexturedModalRect(int x, int y, int u, int v, int width, int height, float texW, float texH)
	{

		float texU = 1 / texW;
		float texV = 1 / texH;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, this.zLevel, (u + 0) * texU, (v + height) * texV);
		tessellator.addVertexWithUV(x + width, y + height, this.zLevel, (u + width) * texU, (v + height) * texV);
		tessellator.addVertexWithUV(x + width, y + 0, this.zLevel, (u + width) * texU, (v + 0) * texV);
		tessellator.addVertexWithUV(x + 0, y + 0, this.zLevel, (u + 0) * texU, (v + 0) * texV);
		tessellator.draw();
	}

	public void drawScaledTexturedModelRectFromIcon(int x, int y, IIcon icon, int width, int height)
	{

		if (icon == null)
		{
			return;
		}
		double minU = icon.getMinU();
		double maxU = icon.getMaxU();
		double minV = icon.getMinV();
		double maxV = icon.getMaxV();

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, this.zLevel, minU, minV + (maxV - minV) * height / 16F);
		tessellator.addVertexWithUV(x + width, y + height, this.zLevel, minU + (maxU - minU) * width / 16F, minV + (maxV - minV) * height / 16F);
		tessellator.addVertexWithUV(x + width, y + 0, this.zLevel, minU + (maxU - minU) * width / 16F, minV);
		tessellator.addVertexWithUV(x + 0, y + 0, this.zLevel, minU, minV);
		tessellator.draw();
	}

	public void drawTooltip(List<String> list)
	{

		drawTooltipHoveringText(list, mouseX + guiLeft, mouseY + guiTop, fontRendererObj);
		tooltip.clear();
	}

	@SuppressWarnings("rawtypes")
	protected void drawTooltipHoveringText(List list, int x, int y, FontRenderer font)
	{

		if (list == null || list.isEmpty())
		{
			return;
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		int k = 0;
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			String s = (String) iterator.next();
			int l = font.getStringWidth(s);

			if (l > k)
			{
				k = l;
			}
		}
		int i1 = x + 12;
		int j1 = y - 12;
		int k1 = 8;

		if (list.size() > 1)
		{
			k1 += 2 + (list.size() - 1) * 10;
		}
		if (i1 + k > this.width)
		{
			i1 -= 28 + k;
		}
		if (j1 + k1 + 6 > this.height)
		{
			j1 = this.height - k1 - 6;
		}
		this.zLevel = 300.0F;
		itemRender.zLevel = 300.0F;
		int l1 = -267386864;
		this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
		this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
		this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
		this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
		this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
		int i2 = 1347420415;
		int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
		this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
		this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
		this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

		for (int k2 = 0; k2 < list.size(); ++k2)
		{
			String s1 = (String) list.get(k2);
			font.drawStringWithShadow(s1, i1, j1, -1);

			if (k2 == 0)
			{
				j1 += 2;
			}
			j1 += 10;
		}
		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	}

	/**
	 * Passthrough method for tab use.
	 */
	public void mouseClicked(int mouseButton)
	{

		super.mouseClicked(guiLeft + mouseX, guiTop + mouseY, mouseButton);
	}

	public FontRenderer getFontRenderer()
	{

		return fontRendererObj;
	}

	protected int getCenteredOffset(String string)
	{

		return this.getCenteredOffset(string, xSize);
	}

	protected int getCenteredOffset(String string, int xWidth)
	{

		return (xWidth - fontRendererObj.getStringWidth(string)) / 2;
	}

	public int getGuiLeft()
	{

		return guiLeft;
	}

	public int getGuiTop()
	{

		return guiTop;
	}

	public int getMouseX()
	{

		return mouseX;
	}

	public int getMouseY()
	{

		return mouseY;
	}

	public void overlayRecipe()
	{

	}
}
