package com.tgame.mods.libs.multiblocks.debugger;

import com.tgame.mods.libs.multiblocks.grid.GridController;

import javax.swing.*;

/**
 * @author tgame14
 * @since 16/06/14
 */
public class NodeJTable extends JTable
{
	private GridController grid;
	private static String[] columnNames = { "Type", "WorldPos", "NBT" };

	public NodeJTable(GridController newGrid)
	{
		super(newGrid.getNodeTableData(), columnNames);
		this.setToolTipText("Node Table");
		this.setName("Nodes");
		this.grid = newGrid;

	}


}
