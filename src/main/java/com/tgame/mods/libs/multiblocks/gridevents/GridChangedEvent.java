package com.tgame.mods.libs.multiblocks.gridevents;

import com.tgame.mods.libs.multiblocks.grid.GridController;
import com.tgame.mods.libs.multiblocks.grid.TileMultiblockNode;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * @since 19/05/14
 * @author tgame14
 */
public class GridChangedEvent extends Event
{
    public final GridController grid;

    public GridChangedEvent(GridController grid)
    {
        this.grid = grid;
    }

    public class NodeAddedEvent extends GridChangedEvent
    {
        public final TileMultiblockNode node;

        public NodeAddedEvent (GridController grid, TileMultiblockNode node)
        {
            super(grid);
            this.node = node;
        }
    }

    public class NodeRemovedEvent extends GridChangedEvent
    {
        public final TileMultiblockNode node;

        public NodeRemovedEvent (GridController grid, TileMultiblockNode node)
        {
            super(grid);
            this.node = node;
        }
    }
}
