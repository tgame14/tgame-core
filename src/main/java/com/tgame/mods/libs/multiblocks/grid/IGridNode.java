package com.tgame.mods.libs.multiblocks.grid;

import java.util.Set;

/**
 * @since 19/05/14
 * @author tgame14
 */
public interface IGridNode
{
    Class<? extends IGrid> getGridClassType ();

    IGrid createNewGrid ();

    IGridHost getHost ();

    Set<IGrid> attachToNeighbors ();

}
