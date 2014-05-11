package com.tgame.mods.libs.network.npackets;

import com.tgame.mods.core.Settings;
import com.tgame.mods.libs.network.AbstractPacket;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * @since 10/05/14
 * @author tgame14
 */
public class PacketTile extends PacketType
{
    private int x;
    private int y;
    private int z;
    private int id;
    private ByteBuf data;

    /**
     * to only be used for the packet pipeline itself
     */
    protected PacketTile()
    {

    }

    public PacketTile (int x, int y, int z, int id, ByteBuf data)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
        this.data = data;

    }

    public PacketTile(TileEntity tile, int id, ByteBuf data)
    {
        this(tile.xCoord, tile.yCoord, tile.zCoord, id, data);
    }

    @Override
    public void encodeInto (ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeInt(id);
        buffer.writeBytes(data);

    }

    @Override
    public void decodeInto (ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.id = buffer.readInt();
        this.data = buffer.slice();
    }

    @Override
    public void handleClientSide (EntityPlayer player)
    {
        TileEntity tile = player.worldObj.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IPacketReceiverWithID)
        {
            ((IPacketReceiverWithID) tile).onReceivePacketId(this.id, player, data, Side.SERVER);
        }
        else
        {
            throw new UnsupportedOperationException("Must send packet to Existing tile implementing IPacketReceiverWithID");
        }
    }

    @Override
    public void handleServerSide (EntityPlayer player)
    {
        TileEntity tile = player.worldObj.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IPacketReceiverWithID)
        {
            ((IPacketReceiverWithID) tile).onReceivePacketId(this.id, player, data, Side.SERVER);
        }
        else
        {
            throw new UnsupportedOperationException("Must send packet to Existing tile implementing IPacketReceiverWithID");
        }
    }
}
