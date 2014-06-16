package com.tgame.mods.libs.inventory;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Implement this interface on TileEntities which should connect to item transportation blocks.
 * <p/>
 * Note that {@link IInventoryHandler} is an extension of this.
 *
 * @author King Lemming
 */
public interface IInventoryConnection
{

	/**
	 * Returns TRUE if the TileEntity can connect on a given side.
	 */
	boolean canConnectInventory(ForgeDirection from);

}
