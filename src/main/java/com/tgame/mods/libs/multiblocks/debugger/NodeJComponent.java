package com.tgame.mods.libs.multiblocks.debugger;

import com.tgame.mods.libs.multiblocks.grid.GridController;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * @author tgame14
 * @since 16/06/14
 */
public class NodeJComponent extends JTable
{
	private GridController grid;
	private static String[] columnNames = {"Type", "WorldPos", "NBT"};

	public NodeJComponent(GridController newGrid)
	{
		super(newGrid.getNodeTableData(), columnNames);
		this.setName("Nodes");
		this.grid = newGrid;

	}


}
