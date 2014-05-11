package com.tgame.mods.libs.network.npackets;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * implement on Tiles to receive packets
 *
 * @since 10/05/14
 * @author tgame14
 */
public interface IPacketReceiverWithID
{
    void onReceivePacketId (int id, EntityPlayer player, ByteBuf buffer, Side side);
}
