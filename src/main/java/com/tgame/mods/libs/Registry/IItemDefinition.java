package com.tgame.mods.libs.Registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * @since 21/04/14
 * @author tgame14, adapted originaly from AlgorithmX2
 */
public interface IItemDefinition
{
    /**
     * @return the {@link net.minecraft.block.Block} Implementation if applicable
     */
    Block getBlock ();

    /**
     * @return the {@link net.minecraft.item.Item} Implementation if applicable
     */
    Item getItem ();

    /**
     * @return the {@link net.minecraft.tileentity.TileEntity} Class if applicable.
     */
    Class<? extends TileEntity> getTile ();

    /**
     * Compare {@link net.minecraft.item.ItemStack} with this {@link IItemDefinition}
     *
     * @param comparableItem
     * @return true if the item stack is a matching item.
     */
    boolean sameAs (ItemStack comparableItem);


}
