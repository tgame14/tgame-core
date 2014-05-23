package com.tgame.mods.libs.multiblocks.simpleimpl;

import com.tgame.mods.libs.multiblocks.MultiblockValidationException;
import com.tgame.mods.libs.multiblocks.WorldPos;
import com.tgame.mods.libs.multiblocks.grid.AbstractMultiblockNode;
import com.tgame.mods.libs.multiblocks.grid.GridController;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @since 23/05/14
 * @author tgame14
 */
public abstract class TileSimpleNode extends AbstractMultiblockNode
{
    private MultiblockFacing facing;
    private ForgeDirection outwards;

    public TileSimpleNode()
    {
        super();

        this.facing = MultiblockFacing.UNKNOWN;
        this.outwards = ForgeDirection.UNKNOWN;
    }

    public ForgeDirection getOutwardsDir()
    {
        return this.outwards;
    }

    public MultiblockFacing getMultiblockFacing()
    {
        return this.facing;
    }

    // Handlers from MultiblockTileEntityBase
    @Override
    public void onAttached(GridController newController) {
        //super.onAttached(newController); TODO: RENABLE
        recalculateOutwardsDirection(newController.getMinimumCoord(), newController.getMaximumCoord());
    }


    @Override
    public void onMachineAssembled(GridController controller) {
        WorldPos maxCoord = controller.getMaximumCoord();
        WorldPos minCoord = controller.getMinimumCoord();

        // Discover where I am on the reactor
        recalculateOutwardsDirection(minCoord, maxCoord);
    }

    @Override
    public void onMachineBroken() {
       facing = MultiblockFacing.UNKNOWN;
        outwards = ForgeDirection.UNKNOWN;
    }

    // Positional helpers
    public void recalculateOutwardsDirection(WorldPos minCoord, WorldPos maxCoord) {
        outwards = ForgeDirection.UNKNOWN;
       facing = MultiblockFacing.UNKNOWN;

        int facesMatching = 0;
        if(maxCoord.x() == this.xCoord || minCoord.x() == this.xCoord) { facesMatching++; }
        if(maxCoord.y() == this.yCoord || minCoord.y() == this.yCoord) { facesMatching++; }
        if(maxCoord.z() == this.zCoord || minCoord.z() == this.zCoord) { facesMatching++; }

        if(facesMatching <= 0) {facing = MultiblockFacing.INTERIOR; }
        else if(facesMatching >= 3) {facing = MultiblockFacing.FRAMECORNER; }
        else if(facesMatching == 2) {facing = MultiblockFacing.FRAME; }
        else {
            // 1 face matches
            if(maxCoord.x() == this.xCoord) {
               facing = MultiblockFacing.EASTFACE;
                outwards = ForgeDirection.EAST;
            }
            else if(minCoord.x() == this.xCoord) {
               facing = MultiblockFacing.WESTFACE;
                outwards = ForgeDirection.WEST;
            }
            else if(maxCoord.z() == this.zCoord) {
               facing = MultiblockFacing.SOUTHFACE;
                outwards = ForgeDirection.SOUTH;
            }
            else if(minCoord.z() == this.zCoord) {
               facing = MultiblockFacing.NORTHFACE;
                outwards = ForgeDirection.NORTH;
            }
            else if(maxCoord.y() == this.yCoord) {
               facing = MultiblockFacing.TOPFACE;
                outwards = ForgeDirection.UP;
            }
            else {
               facing = MultiblockFacing.BOTTOMFACE;
                outwards = ForgeDirection.DOWN;
            }
        }
    }

    ///// Validation Helpers (IMultiblockPart)
    public abstract void isGoodForFrame() throws MultiblockValidationException;

    public abstract void isGoodForSides() throws MultiblockValidationException;

    public abstract void isGoodForTop() throws MultiblockValidationException;

    public abstract void isGoodForBottom() throws MultiblockValidationException;

    public abstract void isGoodForInterior() throws MultiblockValidationException;
}
