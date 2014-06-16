package com.tgame.mods.libs.multiblocks.simpleimpl;

/**
 * @author tgame14
 * @since 23/05/14
 */
public enum MultiblockFacing
{
	UNKNOWN, INTERIOR, FRAMECORNER, FRAME, TOPFACE, BOTTOMFACE, NORTHFACE, SOUTHFACE, EASTFACE, WESTFACE;

	public boolean isFace(MultiblockFacing facing)
	{
		switch (facing)
		{
			case TOPFACE:
			case BOTTOMFACE:
			case NORTHFACE:
			case SOUTHFACE:
			case EASTFACE:
			case WESTFACE:
				return true;
			default:
				return false;
		}
	}
}
