package com.tgame.mods.libs.network.npackets;

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


    public PacketTile (int x, int y, int z, ByteBuf data)
    {

    }

    @Override
    public void encodeInto (ChannelHandlerContext ctx, ByteBuf buffer)
    {
        throw new UnsupportedOperationException("This packet only supports Tile read and write");
    }

    @Override
    public void decodeInto (ChannelHandlerContext ctx, ByteBuf buffer)
    {
        throw new UnsupportedOperationException("This packet only supports Tile read and write");
    }

    @Override
    public void handleClientSide (EntityPlayer player)
    {
        throw new UnsupportedOperationException("This packet only supports Tile read and write");
    }

    @Override
    public void handleServerSide (EntityPlayer player)
    {
        throw new UnsupportedOperationException("This packet only supports Tile read and write");
    }
}
