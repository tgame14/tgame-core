package com.tgame.mods.libs.multiblocks.grid;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * @since 11/03/14
 * @author tgame14
 */
public interface IGrid
{
    public static final String NBT_SAVE_KEY = "gridDataStorage";

    public void tickEvent(TickEvent event);

    public IGridTicker getGridTicker(Class<? extends IGridTicker> clazz);

    public void postEventToGrid(Event event);


}
