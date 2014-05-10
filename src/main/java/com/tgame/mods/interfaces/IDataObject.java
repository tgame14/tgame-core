package com.tgame.mods.interfaces;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @since 10/05/14
 * @author tgame14
 */
public interface IDataObject
{
    public void readFromNBT(NBTTagCompound nbt);

    public void writeToNBT(NBTTagCompound nbt);
}
