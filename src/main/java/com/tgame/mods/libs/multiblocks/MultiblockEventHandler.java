package com.tgame.mods.libs.multiblocks;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
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
            break;
        case END:
            tickEnd(event.world);
            break;
        default:
            break;
        }
    }

	public void ClientTickEvent(TickEvent.ClientTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.theWorld == null)
		{
			return;
		}


		switch (event.phase)
		{
			case START:
				tickStart(Minecraft.getMinecraft().theWorld);
				break;
			case END:
				tickEnd(Minecraft.getMinecraft().theWorld);
				break;
			default:
				break;
		}
	}

	@SubscribeEvent
	public void onChunkLoad (ChunkEvent.Load event)
	{
		Chunk chunk = event.getChunk();
		World world = event.world;
		MultiblockRegistry.instance().onChunkLoaded(world, chunk.xPosition, chunk.zPosition);
	}

    private void tickStart (World world)
    {
        MultiblockRegistry.instance().tickStart(world);
    }

    private void tickEnd (World world)
    {
        MultiblockRegistry.instance().tickEnd(world);
    }
}
