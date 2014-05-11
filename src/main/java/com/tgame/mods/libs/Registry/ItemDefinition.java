package com.tgame.mods.libs.registry;

import com.tgame.mods.core.Settings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

/**
 * @since 25/04/14
 * @author tgame14
 */
class ItemDefinition implements IItemDefinition
{
    protected Block block;
    protected Class<? extends TileEntity> tileClass;
    protected Class<? extends ItemBlock> itemBlockClass;

    protected Item item;
    protected Class<? extends IItemRenderer> itemRenderClass;

    public ItemDefinition (Block block, Class<? extends Block> clazz)
    {
        this.block = block;
        if (clazz.getAnnotation(BlockData.class) != null)
        {
            BlockData data = clazz.getAnnotation(BlockData.class);
            this.tileClass = data.tileClass();
            this.itemBlockClass = data.itemBlockClass();
        }
    }

    public ItemDefinition (Item item, Class<? extends Item> clazz)
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
    public Block getBlock ()
    {
        return block;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass ()
    {
        return this.itemBlockClass;
    }

    @Override
    public Item getItem ()
    {
        return this.item;
    }

    @Override
    public Class<? extends IItemRenderer> getIItemRendererClass ()
    {
        return this.itemRenderClass;
    }

    @Override
    public Class<? extends TileEntity> getTile ()
    {
        return tileClass;
    }

}
