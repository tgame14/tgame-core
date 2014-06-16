package com.tgame.mods.interfaces;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author tgame14
 * @since 10/05/14
 */
public interface IDataObject
{
	public void readFromNBT(NBTTagCompound nbt);

	public void writeToNBT(NBTTagCompound nbt);
}
