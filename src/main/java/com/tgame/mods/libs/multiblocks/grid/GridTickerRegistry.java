package com.tgame.mods.libs.multiblocks.grid;

import java.util.LinkedList;
import java.util.List;

/**
 * @since 11/03/14
 * @author tgame14
 */
public final class GridTickerRegistry
{
    protected static List<Class<? extends IGridTicker>> gridTickerRegistry = new LinkedList<Class<? extends IGridTicker>>();

    public static boolean register(Class<? extends IGridTicker> clazz)
    {
        if (gridTickerRegistry.contains(clazz))
            return false;

        gridTickerRegistry.add(clazz);
        return true;
    }
}
