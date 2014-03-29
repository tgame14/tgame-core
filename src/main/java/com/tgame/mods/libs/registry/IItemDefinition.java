package com.tgame.mods.libs.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * @since 29/03/14
 * @author AlgorithmX2, Adapted by tgame14
 */
public interface IItemDefinition
{
    /**
     * @return the {@link net.minecraft.block.Block} Implementation if applicable
     */
    Block getBlock();

    /**
     * @return the {@link net.minecraft.item.Item} Implementation if applicable
     */
    Item getItem();

    /**
     * @return the {@link net.minecraft.tileentity.TileEntity} Class if applicable.
     */
    Class<? extends TileEntity> getTile();

    /**
     * Compare {@link ItemStack} with this {@link com.tgame.mods.libs.registry.IItemDefinition}
     *
     * @param comparableItem
     * @return true if the item stack is a matching item.
     */
    boolean sameAs(ItemStack comparableItem);
}
