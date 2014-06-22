package com.tgame.mods.prefabs.tiles;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author tgame14
 * @since 22/06/2014
 */
public interface IRotatable
{

	ForgeDirection getDirection();

	void setDirection(ForgeDirection dir);
}
