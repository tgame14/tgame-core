package com.tgame.mods.libs.multiblocks.ngrid;

import com.tgame.mods.libs.multiblocks.WorldPos;

/**
 * Implement this on tiles
 *
 * @since 19/05/14
 * @author tgame14
 */
public interface IGridHost
{
    IGridNode getNode();

    WorldPos getWorldPos();
}
