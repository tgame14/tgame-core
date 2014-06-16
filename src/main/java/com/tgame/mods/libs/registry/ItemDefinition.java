package com.tgame.mods.libs.registry;

import com.tgame.mods.core.Settings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import java.lang.annotation.Annotation;

/**
 * @author tgame14
 * @since 25/04/14
 */
class ItemDefinition implements IItemDefinition
{
	protected Block block;
	protected Class<? extends TileEntity>[] tileClass;
	protected Class<? extends ItemBlock> itemBlockClass;

	protected Item item;
	protected Class<? extends IItemRenderer> itemRenderClass;

	public ItemDefinition(Block block, Class<? extends Block> clazz)
	{
		this.block = block;
		for (Annotation anote : clazz.getAnnotations())
		{
			if (anote instanceof BlockData)
			{
				BlockData data = (BlockData) anote;
				this.tileClass = data.tileClass();
				this.itemBlockClass = data.itemBlockClass();
			}
		}
		Settings.LOGGER.info("Registering Block " + getBlock().getUnlocalizedName());
	}

	public ItemDefinition(Item item, Class<? extends Item> clazz)
	{
		this.item = item;
		if (clazz.getAnnotation(ItemData.class) != null)
		{
			ItemData data = clazz.getAnnotation(ItemData.class);
			this.itemRenderClass = data.itemRendererClass();

			Settings.LOGGER.warn("IItemRender class: " + this.itemRenderClass);
		}
	}

	@Override
	public Block getBlock()
	{
		return block;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass()
	{
		return this.itemBlockClass;
	}

	@Override
	public Item getItem()
	{
		return this.item;
	}

	@Override
	public Class<? extends IItemRenderer> getIItemRendererClass()
	{
		return this.itemRenderClass;
	}

	@Override
	public Class<? extends TileEntity>[] getTile()
	{
		return tileClass;
	}

	@Override
	public ItemStack getItemStack(int amount, int meta)
	{
		if (this.item != null)
		{
			return new ItemStack(this.item, amount, meta);
		}
		else if (this.block != null)
		{
			return new ItemStack(this.block, amount, meta);
		}
		return null;
	}

}
