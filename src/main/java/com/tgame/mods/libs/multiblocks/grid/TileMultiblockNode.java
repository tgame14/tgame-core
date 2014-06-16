package com.tgame.mods.libs.multiblocks.grid;

import com.tgame.mods.core.Settings;
import com.tgame.mods.libs.multiblocks.MultiblockRegistry;
import com.tgame.mods.libs.multiblocks.WorldPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tgame14
 * @since 23/05/14
 */
public abstract class TileMultiblockNode extends AbstractMultiblockNode
{

	private boolean visited;

	private boolean saveMultiblockData;
	private NBTTagCompound cache;
	private boolean paused;

	public TileMultiblockNode()
	{
		super();

		controller = null;
		visited = false;
		saveMultiblockData = false;
		paused = false;
		cache = null;
	}

	// this is where the magic happens

	@Override
	public Set<GridController> attachToNeighbors()
	{
		Set<GridController> controllers = null;
		GridController bestController = null;

		AbstractMultiblockNode[] partsToCheck = getNeighboringParts();
		for (AbstractMultiblockNode neighborPart : partsToCheck)
		{
			if (neighborPart.isConnected())
			{
				GridController candidate = neighborPart.getMultiblockController();
				if (!candidate.getClass().equals(this.getMultiblockControllerType()))
				{
					continue;
				}

				if (controllers == null)
				{
					controllers = new HashSet<GridController>();
					bestController = candidate;
				}
				else if (!controllers.contains(candidate) && candidate.shouldConsume(bestController))
				{
					bestController = candidate;
				}

				controllers.add(candidate);
			}
		}

		if (bestController != null)
		{
			this.controller = bestController;
			bestController.attachBlock(this);
		}
		return controllers;
	}

	@Override
	public void assertDetached()
	{
		if (this.controller != null)
		{
			Settings.LOGGER.info("[assert] Part @ (%d, %d, %d) should be detached already, but detected that it was not. This is not fatal, it will be repaired, but a bit odd.", xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);

		if (data.hasKey("multiblockData"))
		{
			this.cache = (NBTTagCompound) data.getTag("multiblockData");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);

		if (isMultiblockSaveDelegate() && isConnected())
		{
			NBTTagCompound multiblockData = new NBTTagCompound();
			this.controller.writeToNBT(multiblockData);
			data.setTag("multiblockData", multiblockData);
		}
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		detachSelf(false);
	}

	/**
	 * Called from Minecraft's tile entity loop, after all tile entities have been ticked,
	 * as the chunk in which this tile entity is contained is unloading.
	 * Happens before the Forge TickEnd event.
	 *
	 * @see net.minecraft.tileentity.TileEntity#onChunkUnload()
	 */
	@Override
	public void onChunkUnload()
	{
		super.onChunkUnload();
		detachSelf(true);
	}

	/**
	 * This is called when a block is being marked as valid by the chunk, but has not yet fully
	 * been placed into the world's TileEntity cache. this.worldObj, xCoord, yCoord and zCoord have
	 * been initialized, but any attempts to read data about the world can cause infinite loops -
	 * if you call getTileEntity on this TileEntity's coordinate from within validate(), you will
	 * blow your call stack.
	 * <p/>
	 * TL;DR: Here there be dragons.
	 *
	 * @see net.minecraft.tileentity.TileEntity#validate()
	 */
	@Override
	public void validate()
	{
		super.validate();
		MultiblockRegistry.instance().onPartAdded(this.worldObj, this);
	}

	// Network Communication
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound packetData = new NBTTagCompound();
		encodeDescriptionPacket(packetData);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, packetData);
	}

	@Override
	public void onDataPacket(NetworkManager network, S35PacketUpdateTileEntity packet)
	{
		decodeDescriptionPacket(packet.func_148857_g());
	}

	///// Things to override in most implementations (AbstractMultiblockNode)
	protected void decodeDescriptionPacket(NBTTagCompound packetData)
	{
		if (packetData.hasKey("multiblockData"))
		{
			NBTTagCompound tag = (NBTTagCompound) packetData.getTag("multiblockData");
			if (isConnected())
			{
				getMultiblockController().decodeDescriptionPacket(tag);
			}
			else
			{
				// This part hasn't been added to a machine yet, so cache the data.
				this.cache = tag;
			}
		}
	}

	/**
	 * Override this to easily modify the description packet's data without having
	 * to worry about sending the packet itself.
	 * Decode this data in decodeDescriptionPacket.
	 *
	 * @param packetData An NBT compound tag into which you should write your custom description data.
	 */
	protected void encodeDescriptionPacket(NBTTagCompound packetData)
	{
		if (this.isMultiblockSaveDelegate() && isConnected())
		{
			NBTTagCompound tag = new NBTTagCompound();
			getMultiblockController().formatDescriptionPacket(tag);
			packetData.setTag("multiblockData", tag);
		}
	}

	@Override
	public boolean hasMultiblockSaveData()
	{
		return this.cache != null;
	}

	@Override
	public NBTTagCompound getMultiblockSaveData()
	{
		return this.cache;
	}

	@Override
	public void onMultiblockDataAssimilated()
	{
		this.cache = null;
	}

	///// Game logic callbacks (AbstractMultiblockNode)

	@Override
	public abstract void onMachineAssembled(GridController GridController);

	@Override
	public abstract void onMachineBroken();

	@Override
	public abstract void onMachineActivated();

	@Override
	public abstract void onMachineDeactivated();

	///// Miscellaneous multiblock-assembly callbacks and support methods (AbstractMultiblockNode)

	@Override
	public boolean isConnected()
	{
		return (controller != null);
	}

	@Override
	public GridController getMultiblockController()
	{
		return controller;
	}

	@Override
	public WorldPos getWorldLocation()
	{
		return new WorldPos(worldObj, this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public void becomeMultiblockSaveDelegate()
	{
		this.saveMultiblockData = true;
	}

	@Override
	public void forfeitMultiblockSaveDelegate()
	{
		this.saveMultiblockData = false;
	}

	@Override
	public boolean isMultiblockSaveDelegate()
	{
		return this.saveMultiblockData;
	}

	@Override
	public void setUnvisited()
	{
		this.visited = false;
	}

	@Override
	public void setVisited()
	{
		this.visited = true;
	}

	@Override
	public boolean isVisited()
	{
		return this.visited;
	}

	@Override
	public void onAssimilated(GridController newController)
	{
		assert (this.controller != newController);
		this.controller = newController;
	}

	@Override
	public void onAttached(GridController newController)
	{
		this.controller = newController;
	}

	@Override
	public void onDetached(GridController oldController)
	{
		this.controller = null;
	}

	@Override
	public abstract GridController createNewMultiblock();

	@Override
	public AbstractMultiblockNode[] getNeighboringParts()
	{
		WorldPos[] neighbors = new WorldPos[] {
				new WorldPos(worldObj, this.xCoord - 1, this.yCoord, this.zCoord),
				new WorldPos(worldObj, this.xCoord, this.yCoord - 1, this.zCoord),
				new WorldPos(worldObj, this.xCoord, this.yCoord, this.zCoord - 1),
				new WorldPos(worldObj, this.xCoord, this.yCoord, this.zCoord + 1),
				new WorldPos(worldObj, this.xCoord, this.yCoord + 1, this.zCoord),
				new WorldPos(worldObj, this.xCoord + 1, this.yCoord, this.zCoord)
		};

		TileEntity te;
		List<AbstractMultiblockNode> neighborParts = new ArrayList<AbstractMultiblockNode>();
		IChunkProvider chunkProvider = worldObj.getChunkProvider();
		for (WorldPos neighbor : neighbors)
		{
			if (!chunkProvider.chunkExists(neighbor.getChunkX(), neighbor.getChunkZ()))
			{
				// Chunk not loaded, skip it.
				continue;
			}

			te = this.worldObj.getTileEntity(neighbor.x(), neighbor.y(), neighbor.z());
			if (te instanceof AbstractMultiblockNode)
			{
				neighborParts.add((AbstractMultiblockNode) te);
			}
		}
		AbstractMultiblockNode[] tmp = new AbstractMultiblockNode[neighborParts.size()];
		return neighborParts.toArray(tmp);
	}

	@Override
	public void onOrphaned(GridController controller, int oldSize, int newSize)
	{
		worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
	}

	///// Private/Protected Logic Helpers
	/*
     * Detaches this block from its controller. Calls detachBlock() and clears the controller member.
	 */
	protected void detachSelf(boolean chunkUnloading)
	{
		if (this.controller != null)
		{
			// Clean part out of controller
			this.controller.detachBlock(this, chunkUnloading);

			// The above should call onDetached, but, just in case...
			this.controller = null;
		}

		// Clean part out of lists in the registry
		MultiblockRegistry.instance().onPartRemovedFromWorld(worldObj, this);
	}
}
