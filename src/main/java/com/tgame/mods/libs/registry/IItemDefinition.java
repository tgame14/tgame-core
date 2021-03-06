package com.tgame.mods.libs.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

/**
 * @author tgame14, adapted originaly from AlgorithmX2
 * @since 21/04/14
 */
public interface IItemDefinition
{
	/**
	 * @return the {@link net.minecraft.block.Block} Implementation if applicable
	 */
	Block getBlock();

	/**
	 * @return the {@link net.minecraft.item.ItemBlock} implementation if exist, if doesn't exist return regular class
	 */
	Class<? extends ItemBlock> getItemBlockClass();

	/**
	 * @return the {@link net.minecraft.item.Item} Implementation if applicable
	 */
	Item getItem();

	/**
	 * @return the {@link net.minecraftforge.client.IItemRenderer} class if exists, null otherwise
	 */
	Class<? extends IItemRenderer> getIItemRendererClass();

	/**
	 * @return the {@link net.minecraft.tileentity.TileEntity} Class if applicable.
	 */
	Class<? extends TileEntity>[] getTile();

	ItemStack getItemStack(int amount, int meta);

}
