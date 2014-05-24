package com.tgame.mods.libs.utility;

import com.tgame.mods.libs.multiblocks.simpleimpl.SimpleGridController;

/**
 * @since 24/05/14
 * @author tgame14
 */
public class MultiblockUtility
{
    public static final String CATEGORY_MULTIBLOCK = "Multiblocks";

    /**
     * This method is applicable only for rectangular
     *
     * @param controller the {@link com.tgame.mods.libs.multiblocks.simpleimpl.SimpleGridController} to calculate
     * @return the internal volume of the tank
     */
    public static int getInternalBlocks (SimpleGridController controller)
    {
        int sizeX = controller.getMaximumCoord().x() - controller.getMinimumCoord().x() - 2;
        int sizeY = controller.getMaximumCoord().y() - controller.getMinimumCoord().y() - 2;
        int sizeZ = controller.getMaximumCoord().z() - controller.getMinimumCoord().z() - 2;

        return sizeX * sizeY * sizeZ;
    }
}
