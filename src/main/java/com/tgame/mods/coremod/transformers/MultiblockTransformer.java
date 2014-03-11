package com.tgame.mods.coremod.transformers;

import net.minecraft.launchwrapper.IClassTransformer;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 10/03/14
 * @author tgame14
 */
public class MultiblockTransformer implements IClassTransformer
{
    public static Set<Class> gridTickers = new HashSet<Class>();

    @Override
    public byte[] transform (String name, String transformedName, byte[] bytes)
    {
        return bytes;
    }
}
