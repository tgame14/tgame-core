package com.tgame.mods.libs.network.npackets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * implement on Tiles to receive packets
 *
 * @since 10/05/14
 * @author tgame14
 */
public interface IPacketReceiverWithID
{
    void onReceivePacketId (ChannelHandlerContext ctx, ByteBuf buffer);
}
