package com.tgame.mods.libs.multiblocks;

import com.tgame.mods.core.Settings;
import com.tgame.mods.libs.multiblocks.grid.AbstractMultiblockNode;
import com.tgame.mods.libs.multiblocks.grid.GridController;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * @author tgame14
 * @since 19/05/14
 */
public class MultiblockRegistry
{
	private static final MultiblockRegistry instance = new MultiblockRegistry();
	protected HashMap<World, MultiblockWorldRegistry> registries = new HashMap<World, MultiblockWorldRegistry>();

	private MultiblockRegistry()
	{
	}

	public static MultiblockRegistry instance()
	{
		return instance;
	}

	public void tickStart(World world)
	{
		if (registries.containsKey(world))
		{
			MultiblockWorldRegistry registry = registries.get(world);
			registry.tickStart();
		}
	}

	public void tickEnd(World world)
	{
		if (registries.containsKey(world))
		{
			MultiblockWorldRegistry registry = registries.get(world);
			registry.tickEnd();
		}
	}

	public void onChunkLoaded(World world, int chunkX, int chunkZ)
	{
		if (registries.containsKey(world))
		{
			registries.get(world).onChunkLoaded(chunkX, chunkZ);
		}
	}

	public void onPartAdded(World world, AbstractMultiblockNode part)
	{
		MultiblockWorldRegistry registry = getOrCreateRegistry(world);
		registry.onPartAdded(part);
	}

	public void onPartRemovedFromWorld(World world, AbstractMultiblockNode part)
	{
		if (registries.containsKey(world))
		{
			registries.get(world).onPartRemovedFromWorld(part);
		}
	}

	/**
	 * Called whenever a world is unloaded. Unload the relevant registry, if we have one.
	 *
	 * @param world The world being unloaded.
	 */
	public void onWorldUnloaded(World world)
	{
		if (registries.containsKey(world))
		{
			registries.get(world).onWorldUnloaded();
			registries.remove(world);
		}
	}

	/**
	 * Call to mark a controller as dirty. Dirty means that parts have
	 * been added or removed this tick.
	 *
	 * @param world      The world containing the multiblock
	 * @param controller The dirty controller
	 */
	public void addDirtyController(World world,
			GridController controller)
	{
		if (registries.containsKey(world))
		{
			registries.get(world).addDirtyController(controller);
		}
		else
		{
			throw new IllegalArgumentException("Adding a dirty controller to a world that has no registered controllers!");
		}
	}

	/**
	 * Call to mark a controller as dead. It should only be marked as dead
	 * when it has no connected parts. It will be removed after the next world tick.
	 *
	 * @param world      The world formerly containing the multiblock
	 * @param controller The dead controller
	 */
	public void addDeadController(World world, GridController controller)
	{
		if (registries.containsKey(world))
		{
			registries.get(world).addDeadController(controller);
		}
		else
		{
			Settings.LOGGER.warn("Controller %d in world %s marked as dead, but that world is not tracked! Controller is being ignored.", controller.hashCode(), world);
		}
	}

	/// *** PRIVATE HELPERS *** ///

	private MultiblockWorldRegistry getOrCreateRegistry(World world)
	{
		if (registries.containsKey(world))
		{
			return registries.get(world);
		}
		else
		{
			MultiblockWorldRegistry newRegistry = new MultiblockWorldRegistry(world);
			registries.put(world, newRegistry);
			return newRegistry;
		}
	}


}
