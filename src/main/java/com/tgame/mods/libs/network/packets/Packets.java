package com.tgame.mods.libs.network.packets;

import com.tgame.mods.libs.network.PacketPipelineHandler;
import com.tgame.mods.libs.network.npackets.PacketTile;

/**
 * @since 12/03/14
 * @author tgame14
 */
public class Packets
{
    public static void init(PacketPipelineHandler handler)
    {
        handler.registerPacket(PacketTile.class);
    }

}
