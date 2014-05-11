package com.tgame.mods.libs.registry;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

/**
 * @since 11/05/14
 * @author tgame14
 */
public @interface BlockData
{
    public Class<? extends TileEntity> tileClass ();

    public Class<? extends ItemBlock> itemBlockClass () default ItemBlock.class;
}
