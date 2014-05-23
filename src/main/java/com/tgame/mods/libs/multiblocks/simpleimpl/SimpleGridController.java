package com.tgame.mods.libs.multiblocks.simpleimpl;

import com.tgame.mods.libs.multiblocks.MultiblockValidationException;
import com.tgame.mods.libs.multiblocks.WorldPos;
import com.tgame.mods.libs.multiblocks.grid.AbstractMultiblockNode;
import com.tgame.mods.libs.multiblocks.grid.GridController;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @since 23/05/14
 * @author tgame14
 */
public abstract class SimpleGridController extends GridController
{
    protected SimpleGridController (World world)
    {
        super(world);
    }

    @Override
    public void onAttachedPartWithMultiblockData (AbstractMultiblockNode part, NBTTagCompound data)
    {
        //Any data that the separate multiblock may have should be assimilated into main data, Yet multiblock specific data should be done in onAssimilated
    }

    @Override
    protected void onBlockAdded (AbstractMultiblockNode newPart)
    {

    }

    @Override
    protected void onBlockRemoved (AbstractMultiblockNode oldPart)
    {

    }

    @Override
    protected void onMachineAssembled ()
    {

    }

    @Override
    protected void onMachineRestored ()
    {

    }

    @Override
    protected void onMachinePaused ()
    {

    }

    @Override
    protected void onMachineDisassembled ()
    {

    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine ()
    {
        return 0;
    }

    @Override
    protected int getMaximumXSize ()
    {
        return 32;
    }

    @Override
    protected int getMaximumZSize ()
    {
        return 32;
    }

    @Override
    protected int getMaximumYSize ()
    {
        return 32;
    }

    @Override
    protected void isMachineWhole () throws MultiblockValidationException
    {
        // throw an MultiblockValidationException Exception if you for some reason don't want the multiblock to form

        if (connectedParts.size() < getMinimumNumberOfBlocksForAssembledMachine())
        {
            throw new MultiblockValidationException("Machine is too small");
        }

        WorldPos maximumCoord = getMaximumCoord();
        WorldPos minimumCoord = getMinimumCoord();

        int deltaX = maximumCoord.x() - minimumCoord.x() + 1;
        int deltaY = maximumCoord.y() - minimumCoord.y() + 1;
        int deltaZ = maximumCoord.z() - minimumCoord.z() + 1;

        int maxX = getMaximumXSize();
        int maxY = getMaximumYSize();
        int maxZ = getMaximumZSize();
        int minX = getMinimumXSize();
        int minY = getMinimumYSize();
        int minZ = getMinimumZSize();

        if (maxX > 0 && deltaX > maxX)
        {
            throw new MultiblockValidationException(String.format("Machine is too large, it may be at most %d blocks in the X dimension", maxX));
        }
        if (maxY > 0 && deltaY > maxY)
        {
            throw new MultiblockValidationException(String.format("Machine is too large, it may be at most %d blocks in the Y dimension", maxY));
        }
        if (maxZ > 0 && deltaZ > maxZ)
        {
            throw new MultiblockValidationException(String.format("Machine is too large, it may be at most %d blocks in the Z dimension", maxZ));
        }
        if (deltaX < minX)
        {
            throw new MultiblockValidationException(String.format("Machine is too small, it must be at least %d blocks in the X dimension", minX));
        }
        if (deltaY < minY)
        {
            throw new MultiblockValidationException(String.format("Machine is too small, it must be at least %d blocks in the Y dimension", minY));
        }
        if (deltaZ < minZ)
        {
            throw new MultiblockValidationException(String.format("Machine is too small, it must be at least %d blocks in the Z dimension", minZ));
        }

        TileEntity te = null;
        TileSimpleNode part;

        for (int x = minimumCoord.x(); x <= maximumCoord.x(); x++)
        {
            for (int y = minimumCoord.y(); y <= maximumCoord.y(); y++)
            {
                for (int z = minimumCoord.z(); z <= maximumCoord.z(); z++)
                {
                    // Okay, figure out what sort of block this should be.

                    te = this.worldObj.getTileEntity(x, y, z);
                    if (te instanceof TileSimpleNode)
                    {
                        part = (TileSimpleNode) te;
                    }
                    else
                    {
                        // This is permitted so that we can incorporate certain non-multiblock parts inside interiors
                        part = null;
                    }

                    // Validate block type against both part-level and material-level validators.
                    int extremes = 0;
                    if (x == minimumCoord.x())
                    {
                        extremes++;
                    }
                    if (y == minimumCoord.y())
                    {
                        extremes++;
                    }
                    if (z == minimumCoord.z())
                    {
                        extremes++;
                    }

                    if (x == maximumCoord.x())
                    {
                        extremes++;
                    }
                    if (y == maximumCoord.y())
                    {
                        extremes++;
                    }
                    if (z == maximumCoord.z())
                    {
                        extremes++;
                    }

                    if (extremes >= 2)
                    {
                        if (part != null)
                        {
                            part.isGoodForFrame();
                        }
                        else
                        {
                            isBlockGoodForFrame(this.worldObj, x, y, z);
                        }
                    }
                    else if (extremes == 1)
                    {
                        if (y == maximumCoord.y())
                        {
                            if (part != null)
                            {
                                part.isGoodForTop();
                            }
                            else
                            {
                                isBlockGoodForTop(this.worldObj, x, y, z);
                            }
                        }
                        else if (y == minimumCoord.y())
                        {
                            if (part != null)
                            {
                                part.isGoodForBottom();
                            }
                            else
                            {
                                isBlockGoodForBottom(this.worldObj, x, y, z);
                            }
                        }
                        else
                        {
                            // Side
                            if (part != null)
                            {
                                part.isGoodForSides();
                            }
                            else
                            {
                                isBlockGoodForSides(this.worldObj, x, y, z);
                            }
                        }
                    }
                    else
                    {
                        if (part != null)
                        {
                            part.isGoodForInterior();
                        }
                        else
                        {
                            isBlockGoodForInterior(this.worldObj, x, y, z);
                        }
                    }

                }
            }
        }
    }

    @Override
    protected void onAssimilate (GridController assimilated)
    {

    }

    @Override
    protected void onAssimilated (GridController assimilator)
    {

    }

    /**
     *
     * @return if chunk data is dirty of multiblock is and if data should refresh
     */
    @Override
    protected boolean updateServer ()
    {
        return false;
    }

    @Override
    public void writeToNBT (NBTTagCompound data)
    {

    }

    @Override
    public void readFromNBT (NBTTagCompound data)
    {

    }

    /**
     * Use this method for sending data from {@code SERVER} to {@code CLIENT}
     *
     * @param data A fresh compound tag to write your multiblock data into
     */
    @Override
    public void formatDescriptionPacket (NBTTagCompound data)
    {

    }

    /**
     * Called when receiving an {@link com.tgame.mods.libs.multiblocks.grid.GridController#formatDescriptionPacket(net.minecraft.nbt.NBTTagCompound)} Packet
     * @param data A compound tag containing multiblock data to import
     */
    @Override
    public void decodeDescriptionPacket (NBTTagCompound data)
    {

    }
}
