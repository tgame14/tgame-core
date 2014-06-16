package com.tgame.mods.libs.multiblocks.debugger;

import com.tgame.mods.libs.multiblocks.grid.GridController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;


/**
 * @author tgame14
 * @since 16/06/14
 */
public class GridControllerGUI extends JComponent
{

	private static final Font font = new Font("Monospaced", 0, 12);
	private static final Logger frameLogger = LogManager.getLogger("GridControllerGUI");

	protected GridController controller;

	public GridControllerGUI(GridController controller)
	{
		super();

		this.controller = controller;
		this.setPreferredSize(new Dimension(854, 480));
		this.setLayout(new GridBagLayout());

		this.add(new NodeJComponent(this.controller), "Center");


	}


	public static void invoke(GridController controller)
	{
		GridControllerGUI gui = new GridControllerGUI(controller);
		JFrame frame = new JFrame("GridController Debug : tgame-core");

		frame.add(gui);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);



	}
}
