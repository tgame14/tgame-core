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

    public void registerBlock(IItemDefinition itemDefinition)
    {

        GameRegistry.registerBlock(itemDefinition.getBlock(), itemDefinition.getItemBlockClass(), itemDefinition.getBlock().getUnlocalizedName().replaceAll("tile.", "").replaceAll(".name", ""));
        if (itemDefinition.getTile() != null)
        {
            this.registerTile(itemDefinition);
        }
    }

    public void registerTile(IItemDefinition itemDefinition)
    {
        GameRegistry.registerTileEntity(itemDefinition.getTile(), itemDefinition.getTile().getName());
    }

}
