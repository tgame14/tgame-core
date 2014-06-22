package com.tgame.mods.libs.multiblocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * @author tgame14
 * @since 19/05/14
 */
@SuppressWarnings("unused")
public class MultiblockEventHandler
{
	@SubscribeEvent
	public void worldTickEvent(TickEvent.WorldTickEvent event)
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

	@SubscribeEvent
	public void ClientTickEvent(TickEvent.ClientTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		// FML Does not have a WorldTickEvent on the client, even though the world does tick. this is a workaround
		if (mc.theWorld == null)
		{
			return;
		}

		switch (event.phase)
		{
			case START:
				tickStart(mc.theWorld);
				break;
			case END:
				tickEnd(mc.theWorld);
				break;
			default:
				break;
		}
	}

	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load event)
	{
		Chunk chunk = event.getChunk();
		World world = event.world;
		MultiblockRegistry.instance().onChunkLoaded(world, chunk.xPosition, chunk.zPosition);
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		World world = event.world;
		MultiblockRegistry.instance().onWorldUnloaded(world);
	}

	private void tickStart(World world)
	{
		MultiblockRegistry.instance().tickStart(world);
	}

	private void tickEnd(World world)
	{
		MultiblockRegistry.instance().tickEnd(world);
	}
}
