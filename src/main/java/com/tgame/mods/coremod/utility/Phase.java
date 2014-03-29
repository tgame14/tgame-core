package com.tgame.mods.coremod.utility;

/**
 * @since 12/03/14
 * @author tgame14
 */
public enum Phase
{
    PRELAUNCH, PREINIT, INIT, POSTINIT, DONE;

    private static Phase phase = PRELAUNCH;

    public static Phase getPhase()
    {
        return phase;
    }

    public static void setPhase(Phase state)
    {
        phase = state;
    }
}
