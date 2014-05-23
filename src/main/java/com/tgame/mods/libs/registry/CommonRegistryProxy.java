package com.tgame.mods.libs.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

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
        registerTile(itemDefinition.getTile());
    }

    public void registerTile(Class<? extends TileEntity> clazz)
    {
        GameRegistry.registerTileEntity(clazz, clazz.getName());
    }

}
