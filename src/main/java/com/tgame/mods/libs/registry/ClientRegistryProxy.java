package com.tgame.mods.libs.registry;

import com.tgame.mods.core.Settings;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import org.apache.logging.log4j.Level;

/**
 * @author tgame14
 * @since 25/04/14
 */
public class ClientRegistryProxy extends CommonRegistryProxy
{
	@Override
	public void registerItem(IItemDefinition itemDefinition)
	{
		super.registerItem(itemDefinition);
		try
		{
			Settings.LOGGER.info("An IItemRenderClass " + itemDefinition.getIItemRendererClass());
			if (itemDefinition.getIItemRendererClass() != null)
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

	@Override
	public void registerBlock(IItemDefinition idef)
	{
		super.registerBlock(idef);
	}

	@Override
	public void registerTile(IItemDefinition itemDefinition)
	{
		super.registerTile(itemDefinition);
		for (int i = 0; i < itemDefinition.getTile().length; i++)
		{
			TileData data = itemDefinition.getTile()[i].getAnnotation(TileData.class);
			if (data != null)
			{
				try
				{
					ClientRegistry.bindTileEntitySpecialRenderer(itemDefinition.getTile()[i], data.tesrClass().newInstance());
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


}
