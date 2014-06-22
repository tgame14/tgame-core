package com.tgame.mods.prefabs.tiles;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author tgame14
 * @since 22/06/2014
 */
public interface IRedstoneProvider
{

	boolean isPoweringTo(ForgeDirection dir);

	boolean isIndirectlyPoweringTo(ForgeDirection dir);
}
