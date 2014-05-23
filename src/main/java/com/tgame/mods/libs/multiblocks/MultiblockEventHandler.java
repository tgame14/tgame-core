package com.tgame.mods.libs.multiblocks;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;

/**
 * @since 19/05/14
 * @author tgame14
 */
public class MultiblockEventHandler
{
    @SubscribeEvent
    public void tickEvent(TickEvent event)
    {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChunkLoad(ChunkEvent.Load event)
    {
        Chunk chunk = event.getChunk();
        World world = event.world;
        //MultiblockRegistry.instance().
    }
}
