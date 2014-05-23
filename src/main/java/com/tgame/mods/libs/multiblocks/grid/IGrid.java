package com.tgame.mods.libs.multiblocks.grid;

import cpw.mods.fml.common.eventhandler.Event;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

import java.util.Set;

/**
 * @since 19/05/14
 * @author tgame14
 */
public interface IGrid
{
    public static final String NBT_SAVE_KEY = "gridDataStorage";

    void onTick();

    void updateMultiblockEntity();

    void postEventToGrid(Event event);

    ByteBuf writeToDelegate();

    void readFromDlegate(ByteBuf data);

    World world();

    Set<IGridNode> getNodes();

    boolean isEmpty();

    void attachNode(IGridNode node);

    void detachNode(IGridNode node);

    boolean shouldConsume(IGrid other);
}
