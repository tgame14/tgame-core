package com.tgame.mods.libs.multiblocks.ngrid;

import cpw.mods.fml.common.eventhandler.Event;
import io.netty.buffer.ByteBuf;

/**
 * @since 19/05/14
 * @author tgame14
 */
public interface IGrid
{
    public static final String NBT_SAVE_KEY = "gridDataStorage";

    public void onTick();

    public void postEventToGrid(Event event);

    public ByteBuf writeToDelegate();

    public void readFromDlegate(ByteBuf data);
}
