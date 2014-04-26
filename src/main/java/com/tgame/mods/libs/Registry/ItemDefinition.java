package com.tgame.mods.libs.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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

    public ItemDefinition (Block block)
    {
        this.block = block;
        if (this.block.getClass().getAnnotation(Registry.BlockData.class) != null)
        {
            Registry.BlockData data = this.block.getClass().getAnnotation(Registry.BlockData.class);
            this.tileClass = data.tileClass();
            this.itemBlockClass = data.itemBlock();

        }
    }

    public ItemDefinition (Item item)
    {
        this.item = item;
        if (this.item.getClass().getAnnotation(Registry.ItemData.class) != null)
        {
            Registry.ItemData data = this.item.getClass().getAnnotation(Registry.ItemData.class);
            this.itemRenderClass = data.itemRenderer();
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

    @Override
    public boolean sameAs (ItemStack comparableItem)
    {
        return false;
    }
}
