package com.tgame.mods.libs.multiblocks;

/**
 * @since 19/05/14
 * @author tgame14
 */
public class MultiblockRegistry
{
    private static final MultiblockRegistry instance = new MultiblockRegistry();

    public static MultiblockRegistry getInstance ()
    {
        return instance;
    }

    private MultiblockRegistry ()
    {
    }


}
