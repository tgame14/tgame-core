package com.tgame.mods.coremod.prefabs.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @since 22/04/14
 * @author tgame14
 */
public abstract class ItemBase extends Item
{
    public ItemBase(CreativeTabs tab)
    {
        super();
        this.setCreativeTab(tab);
        this.setUnlocalizedName(this.getClass().getSimpleName());
        this.setTextureName(this.getUnlocalizedName());

    }
}
