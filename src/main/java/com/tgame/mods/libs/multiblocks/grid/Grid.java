package com.tgame.mods.libs.multiblocks.grid;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.tgame.mods.libs.multiblocks.WorldPos;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @since 10/03/14
 * @author tgame14
 */
@SuppressWarnings("unused")
public class Grid implements IGrid
{

    private BiMap<WorldPos, IGridNode> gridMap;
    private List<IGridTicker> gridTickers;
    private IGridNode saveDelegate;

    public Grid(IGridNode node) throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        this.gridMap = HashBiMap.create();
        this.gridTickers = new ArrayList<IGridTicker>(GridTickerRegistry.gridTickerRegistry.size());

        for (Class<? extends IGridTicker> clazz : GridTickerRegistry.gridTickerRegistry)
        {
            for (Constructor<?> con : clazz.getConstructors())
            {
                if (con.getParameterTypes().length == 1 && con.getParameterTypes()[0].isInstance(this))
                {
                    gridTickers.add((IGridTicker) con.newInstance(this));
                }
            }
        }
    }

    @SubscribeEvent
    public void tickEvent(TickEvent event)
    {
        if (event.phase == TickEvent.Phase.START && event.type == TickEvent.Type.WORLD)
        {
            for (IGridTicker gridTicker : this.gridTickers)
            {
                gridTicker.updateGrid();
            }
        }

    }

    @Override
    public IGridTicker getGridTicker (Class<? extends IGridTicker> clazz)
    {
        for (IGridTicker ticker : this.gridTickers)
        {
            if (ticker.getClass().equals(clazz))
                return ticker;
        }
        return null;
    }

    @Override
    public void postEventToGrid (Event event)
    {

    }

    public IGridNode getSaveDelegate()
    {
        if (saveDelegate == null)
        {
            for (IGridNode node : gridMap)
            {
                if (node.canBeDelegate())
                {
                    this.saveDelegate = node;
                    return this.saveDelegate;
                }
            }
        }

        return this.saveDelegate;
    }

    public void writeToDelegate()
    {
        NBTTagCompound nbttag = new NBTTagCompound();

        for (IGridTicker gridTicker : gridTickers)
        {
            nbttag.setByteArray(gridTicker.nbtKeyTag(), gridTicker.saveData().array());
        }

        getSaveDelegate().saveGridData(nbttag)

    }

    public void readFromDelegate(NBTTagCompound tag)
    {
        NBTTagCompound nbt = tag.getCompoundTag(IGrid.NBT_SAVE_KEY);

        for (IGridTicker gridTicker : gridTickers)
        {
            gridTicker.readData(Unpooled.wrappedBuffer(nbt.getByteArray(gridTicker.nbtKeyTag())));
        }


    }


}
