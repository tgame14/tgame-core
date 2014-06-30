package com.tgame.mods.prefabs.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author tgame14
 * @since 22/06/2014
 */
public class TGTileBase extends TileEntity
{

	protected long ticks;

	public TGTileBase()
	{
		this.ticks = 0;
	}

	public void init()
	{

	}

	@Override
	public void updateEntity()
	{
		if (this.ticks == 0)
		{
			this.init();
		}

		if (this.ticks >= Long.MAX_VALUE)
		{
			this.ticks = 1;
		}

		this.ticks++;
	}

	public ForgeDirection getDirection()
	{
		return ForgeDirection.getOrientation(this.getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
	}

	public void setDirection(ForgeDirection dir)
	{
		this.getWorldObj().setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, dir.ordinal(), 3);
	}

	public void blockBroken()
	{
	}

	public int getComparatorInput(int side)
	{
		return 0;
	}
}
