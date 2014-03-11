package com.tgame.mods.libs.multiblocks.grid;

import java.util.LinkedList;
import java.util.List;

/**
 * @since 11/03/14
 * @author tgame14
 */
public final class GridTickerRegistry
{
    protected static List<Class<? extends IGridTicker>> TickerClasses = new LinkedList<Class<? extends IGridTicker>>();

    public static boolean register(Class<? extends IGridTicker> clazz)
    {
        if (TickerClasses.contains(clazz))
            return false;

        TickerClasses.add(clazz);
        return true;
    }
}
