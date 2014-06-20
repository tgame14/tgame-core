package com.tgame.mods.libs.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

/** Implement this if an object can receive a packet.
 * 
 * @author tgame14 */
public interface IPacketReceiver
{
    /** @param data - data encoded into the packet
     * @param player - player that sent or is receiving the packet */
    public void onReceivePacket(ByteBuf data, EntityPlayer player, Object... extra);
}
