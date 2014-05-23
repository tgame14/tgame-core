package com.tgame.mods.libs.multiblocks.grid;

import com.tgame.mods.libs.multiblocks.WorldPos;
import net.minecraft.tileentity.TileEntity;

/**
 * Implement this on tiles
 *
 * @since 19/05/14
 * @author tgame14
 */
public interface IGridHost
{
    IGridNode getNode ();

    WorldPos getWorldPos ();

    TileEntity getTile ();

}
