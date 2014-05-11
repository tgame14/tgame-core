package com.tgame.mods.libs.registry;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

/**
 * @since 11/05/14
 * @author tgame14
 */
public @interface TileData
{
    public Class<? extends TileEntitySpecialRenderer> tesrClass ();
}
