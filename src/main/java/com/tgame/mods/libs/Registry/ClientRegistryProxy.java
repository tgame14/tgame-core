package com.tgame.mods.libs.registry;

import com.tgame.mods.coremod.Settings;
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
        if (itemDefinition.getItem().getClass().getAnnotation(RegisterData.ItemData.class) != null)
        {
            try
            {
                MinecraftForgeClient.registerItemRenderer(itemDefinition.getItem(), itemDefinition.getItem().getClass().getAnnotation(RegisterData.ItemData.class).itemRenderer().newInstance());
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
    public void registerBlock ()
    {
        super.registerBlock();
    }

    @Override
    public void registerTile ()
    {
        super.registerTile();
    }


}
