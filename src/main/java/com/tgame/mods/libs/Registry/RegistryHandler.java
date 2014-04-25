package com.tgame.mods.libs.registry;

import cpw.mods.fml.common.SidedProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * @since 25/04/14
 * @author tgame14
 */
public class RegistryHandler
{
    @SidedProxy(clientSide = "com.tgame.mods.libs.registry.ClientRegistryProxy", serverSide = "com.tgame.mods.libs.registry.CommonRegistryProxy")
    private static CommonRegistryProxy registryProxy;

    public IItemDefinition registerBlock(Block block)
    {
        return new ItemDefinition(block);
    }

    public IItemDefinition registerItem(Item item)
    {
        IItemDefinition itemdef = new ItemDefinition(item);

        return itemdef;

    }


}
