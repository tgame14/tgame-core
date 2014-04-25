package com.tgame.mods.libs.registry;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

/**
 * @since 25/04/14
 * @author tgame14
 */
public @interface RegisterData
{
    public static @interface BlockData
    {
        public Class<? extends TileEntity> tileClass ();

        public Class<? extends ItemBlock> itemBlock () default ItemBlock.class;
    }

    public static @interface TileData
    {
        public Class<? extends TileEntitySpecialRenderer> tesrClass ();
    }

    public static @interface ItemData
    {
        public Class<? extends IItemRenderer> itemRenderer();
    }


}
