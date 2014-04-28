package com.tgame.mods.libs.registry;

import com.tgame.mods.core.Settings;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import org.apache.logging.log4j.Level;

/**
 * @since 25/04/14
 * @author tgame14
 */
public class ClientRegistryProxy extends CommonRegistryProxy
{
    @Override
    public void registerItem (IItemDefinition itemDefinition)
    {
        super.registerItem(itemDefinition);
        if (itemDefinition.getItem().getClass().getAnnotation(Registry.ItemData.class) != null)
        {
            try
            {
                MinecraftForgeClient.registerItemRenderer(itemDefinition.getItem(), itemDefinition.getIItemRendererClass().newInstance());
            }
            catch (InstantiationException e)
            {
                Settings.LOGGER.catching(Level.FATAL, e);
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                Settings.LOGGER.catching(Level.FATAL, e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerBlock (IItemDefinition itemDefinition)
    {
        super.registerBlock(itemDefinition);
    }

    @Override
    public void registerTile (IItemDefinition itemDefinition)
    {
        super.registerTile(itemDefinition);
        Registry.TileData data = itemDefinition.getTile().getAnnotation(Registry.TileData.class);
        if (data != null)
        {
            try
            {
                ClientRegistry.bindTileEntitySpecialRenderer(itemDefinition.getTile(), data.tesrClass().newInstance());
            }
            catch (InstantiationException e)
            {
                Settings.LOGGER.catching(Level.FATAL, e);
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                Settings.LOGGER.catching(Level.FATAL, e);
                e.printStackTrace();
            }
        }
    }


}
