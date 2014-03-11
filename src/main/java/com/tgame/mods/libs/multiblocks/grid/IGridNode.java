package com.tgame.mods.libs.multiblocks.grid;

import com.tgame.mods.libs.multiblocks.WorldPos;
import net.minecraft.nbt.NBTTagCompound;

/**
 * implement this on tiles
 *
 * @since 10/03/14
 * @author tgame14
 */
public interface IGridNode
{
    public WorldPos getWorldPos();

    public boolean PowerConsumePerTick();

    public IGrid getGrid();

    public void setGrid();

    public boolean canBeDelegate();

    public boolean saveGridData(NBTTagCompound tag, String gridKey);


}
