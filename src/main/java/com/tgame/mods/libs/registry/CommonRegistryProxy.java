package com.tgame.mods.libs.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.LinkedList;
import java.util.List;

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
		registerTile(itemDefinition.getTile());
	}

	public void registerTile(Class<? extends TileEntity> clazz)
	{
		GameRegistry.registerTileEntity(clazz, clazz.getName());
	}

}
