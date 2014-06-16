package com.tgame.mods.libs.multiblocks.gridevents;

import com.tgame.mods.libs.multiblocks.grid.AbstractMultiblockNode;
import com.tgame.mods.libs.multiblocks.grid.GridController;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * @author tgame14
 * @since 19/05/14
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
		public final AbstractMultiblockNode node;

		public NodeAddedEvent(GridController grid, AbstractMultiblockNode node)
		{
			super(grid);
			this.node = node;
		}
	}

	public class NodeRemovedEvent extends GridChangedEvent
	{
		public final AbstractMultiblockNode node;

		public NodeRemovedEvent(GridController grid, AbstractMultiblockNode node)
		{
			super(grid);
			this.node = node;
		}
	}
}
