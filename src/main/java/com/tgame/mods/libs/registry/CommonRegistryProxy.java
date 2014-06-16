package com.tgame.mods.libs.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

/**
 * @author tgame14
 * @since 25/04/14
 */
public class CommonRegistryProxy
{
	public void registerItem(IItemDefinition itemDefinition)
	{
		GameRegistry.registerItem(itemDefinition.getItem(), itemDefinition.getItem().getUnlocalizedName().replaceAll("item.", "").replaceAll(".name", ""));
	}

	public void registerBlock(IItemDefinition idef)
	{

		GameRegistry.registerBlock(idef.getBlock(), idef.getItemBlockClass(), idef.getBlock().getUnlocalizedName().replaceAll("tile.", "").replaceAll(".name", ""));
		if (idef.getTile() != null)
		{
			this.registerTile(idef);
		}
	}

	public void registerTile(IItemDefinition itemDefinition)
	{
		for (int i = 0; i < itemDefinition.getTile().length; i++)
		{
			registerTile(itemDefinition.getTile()[i]);
		}
	}

	public void registerTile(Class<? extends TileEntity> clazz)
	{
		try
		{
			GameRegistry.registerTileEntity(clazz, clazz.getName());
		}
		catch (IllegalArgumentException ex)
		{
			// this happens when registering the same block origin twice, it is a unavoidable event considering using annotations
			//Settings.LOGGER.catching(Level.WARN, ex);
		}
	}

}
