package com.tgame.mods.libs.network.packets;

import com.tgame.mods.libs.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @since 10/05/14
 * @author tgame14
 */
public class PacketTile extends AbstractPacket
{
    @Override
    public void encodeInto (ChannelHandlerContext ctx, ByteBuf buffer)
    {

    }

    @Override
    public void decodeInto (ChannelHandlerContext ctx, ByteBuf buffer)
    {

    }

    @Override
    public void handleClientSide (EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide (EntityPlayer player)
    {

    }
}
