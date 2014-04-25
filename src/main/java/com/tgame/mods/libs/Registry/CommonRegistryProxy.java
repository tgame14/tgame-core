package com.tgame.mods.libs.registry;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @since 25/04/14
 * @author tgame14
 */
public class CommonRegistryProxy
{
    public void registerItem(IItemDefinition itemDefinition)
    {
        GameRegistry.registerItem(itemDefinition.getItem(), itemDefinition.getItem().getUnlocalizedName().replaceAll("item.", "").replaceAll(".name", ""));
    }

    public void registerBlock()
    {

    }

    public void registerTile()
    {

    }

}
