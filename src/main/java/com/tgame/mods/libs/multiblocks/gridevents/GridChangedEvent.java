package com.tgame.mods.libs.multiblocks.gridevents;

import com.tgame.mods.libs.multiblocks.ngrid.IGrid;
import com.tgame.mods.libs.multiblocks.ngrid.IGridNode;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * @since 19/05/14
 * @author tgame14
 */
public class GridChangedEvent extends Event
{
    public final IGrid grid;

    public GridChangedEvent(IGrid grid)
    {
        this.grid = grid;
    }

    public class NodeAddedEvent extends GridChangedEvent
    {
        public final IGridNode node;

        public NodeAddedEvent (IGrid grid, IGridNode node)
        {
            super(grid);
            this.node = node;
        }
    }

    public class NodeRemovedEvent extends GridChangedEvent
    {
        public final IGridNode node;

        public NodeRemovedEvent (IGrid grid, IGridNode node)
        {
            super(grid);
            this.node = node;
        }
    }
}
