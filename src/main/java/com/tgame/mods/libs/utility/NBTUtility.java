package com.tgame.mods.libs.utility;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @since 28/04/14
 * @author tgame14
 */
@SuppressWarnings("unused")
public class NBTUtility
{
    /**
     * A method that is used to simplify the addition of a key if it didn't exist before
     *
     * @param nbt {@link net.minecraft.nbt.NBTTagCompound} to write this key to
     * @param key the {@link java.lang.String} key to use
     * @param value the int to put into save
     */
    public static void addNBTIntKey(NBTTagCompound nbt, String key, int value)
    {
        if (!nbt.hasKey(key))
        {
            nbt.setInteger(key, value);
        }
    }

    public static void addNBTDoubleKey(NBTTagCompound nbt, String key, double value)
    {
        if (!nbt.hasKey(key))
        {
            nbt.setDouble(key, value);
        }
    }

    public static void addNBTStringKey(NBTTagCompound nbt, String key, String value)
    {
        if (!nbt.hasKey(key))
        {
            nbt.setString(key, value);
        }
    }

    public static void addNBTTagKey(NBTTagCompound nbt, String key, NBTTagCompound value)
    {
        if (!nbt.hasKey(key))
        {
            nbt.setTag(key, value);
        }
    }
}
