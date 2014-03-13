package com.tgame.mods.libs.multiblocks.grid;

import com.google.common.collect.ImmutableList;
import com.tgame.mods.coremod.TgameCore;

import java.util.LinkedList;
import java.util.List;

/**
 * @since 11/03/14
 * @author tgame14
 */
public final class GridTickerRegistry
{
    protected static ImmutableList.Builder<Class<? extends IGridTicker>> TickerClasses = new ImmutableList.Builder<Class<? extends IGridTicker>>();

    public static boolean register(Class<? extends IGridTicker> clazz)
    {

    }
}
