package com.tgame.mods.libs.multiblocks.gridevents;

import com.tgame.mods.libs.multiblocks.grid.GridController;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.world.World;

/**
 * @author tgame14
 * @since 23/05/14
 */
public class GridStateChangedEvent extends Event
{
	public final World world;
	public final GridController.AssemblyState state;

	public GridStateChangedEvent(World world, GridController.AssemblyState state)
	{
		this.world = world;
		this.state = state;
	}
}
