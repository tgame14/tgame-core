package com.tgame.mods.libs.multiblocks.grid;

import io.netty.buffer.ByteBuf;

/**
 * give this a constructor with a IGrid in it
 *
 * @since 10/03/14
 * @author tgame14
 */
public interface IGridTicker
{
    /** the update Grid is Called before all Tiles tick */
    public void updateGrid();

    public void onNodeAdded(IGridNode node);

    public void onNodeRemoved(IGridNode node);

    public ByteBuf saveData();

    public String nbtKeyTag();

    public void readData(ByteBuf byteBuf);
}
