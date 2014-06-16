package com.tgame.mods.libs.registry;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

/**
 * @author tgame14
 * @since 25/04/14
 */
public class RegistryHandler
{
	@SidedProxy(clientSide = "com.tgame.mods.libs.registry.ClientRegistryProxy", serverSide = "com.tgame.mods.libs.registry.CommonRegistryProxy")
	private static CommonRegistryProxy registryProxy;

	public IItemDefinition registerBlock(Block block, Class<? extends Block> clazz)
	{
		IItemDefinition itemdef = new ItemDefinition(block, clazz);
		registryProxy.registerBlock(itemdef);
		return itemdef;
	}

	public void registerTile(Class<? extends TileEntity> clazz)
	{
		registryProxy.registerTile(clazz);

	}

	public IItemDefinition registerItem(Item item, Class<? extends Item> clazz)
	{
		IItemDefinition itemdef = new ItemDefinition(item, clazz);
		registryProxy.registerItem(itemdef);
		return itemdef;

	}

	public void registerEntity(Class<? extends Entity> clazz)
	{
		EntityRegistry.registerGlobalEntityID(clazz, clazz.getSimpleName(), EntityRegistry.findGlobalUniqueEntityId());

	}


}
