package com.tgame.mods.libs.registry;

import net.minecraftforge.client.IItemRenderer;

/**
 * @since 11/05/14
 * @author tgame14
 */
public @interface ItemData
{
    public Class<? extends IItemRenderer> itemRendererClass ();
}
