package com.tgame.mods.libs.multiblocks.ngrid;

/**
 * @since 19/05/14
 * @author tgame14
 */
public interface IGridNode
{
    Class<? extends IGrid> getGridClassType();

    IGrid createNewGrid();

}
