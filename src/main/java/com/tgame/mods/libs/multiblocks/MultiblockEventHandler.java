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
    public void worldTickEvent (TickEvent.WorldTickEvent event)
    {
        switch (event.phase)
        {
        case START:
            tickStart(event.world);
            return;
        case END:
            tickEnd(event.world);
            return;
        default:
            break;
        }
    }

    private void tickStart (World world)
    {
        MultiblockRegistry.instance().tickStart(world);
    }

    private void tickEnd (World world)
    {
        MultiblockRegistry.instance().tickEnd(world);
    }

    @SubscribeEvent
    public void onChunkLoad (ChunkEvent.Load event)
    {
        Chunk chunk = event.getChunk();
        World world = event.world;
        MultiblockRegistry.instance().onChunkLoaded(world, chunk.xPosition, chunk.zPosition);
    }
}
