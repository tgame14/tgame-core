package com.tgame.mods.libs.multiblocks.grid;

import com.tgame.mods.coremod.TgameCore;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @since 10/03/14
 * @author tgame14
 */
public class Grid implements IGrid
{

    private Set<IGridNode> nodeSet;
    private List<IGridTicker> gridTickers;
    private IGridNode saveDelegate;

    public Grid (IGridNode node)
    {
        this.nodeSet = new HashSet<IGridNode>();
        this.nodeSet.add(node);

        this.gridTickers = new ArrayList<IGridTicker>(GridTickerRegistry.tickerClasses.size());

        for (Class<? extends IGridTicker> clazz : GridTickerRegistry.tickerClasses)
        {
            for (Constructor<?> con : clazz.getConstructors())
            {
                try
                {
                    if (con.getParameterTypes().length == 1 && con.getParameterTypes()[0].isInstance(this))
                    {
                        gridTickers.add((IGridTicker) con.newInstance(this));
                    }
                }
                catch (Exception e)
                {
                    TgameCore.LOGGER.fatal("An IGridTicker doesn't have a Constructor with IGrid in it! This is an error", e);
                }
            }
        }
    }

    @Override
    @SubscribeEvent
    public void tickEvent (TickEvent event)
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

    public IGridNode getSaveDelegate ()
    {
        if (saveDelegate == null)
        {
            IGridNode candidate = null;
            for (IGridNode node : nodeSet)
            {
                if (node.canBeDelegate())
                {
                    if (candidate == null)
                    {
                        candidate = node;
                    }

                    else if (node.getWorldPos().compareTo(candidate.getWorldPos()) == 1)
                    {
                        candidate = node;
                    }
                }
            }
            saveDelegate = candidate;
        }

        return this.saveDelegate;
    }

    @Override
    public void writeToDelegate ()
    {
        NBTTagCompound nbttag = new NBTTagCompound();

        for (IGridTicker gridTicker : gridTickers)
        {
            nbttag.setByteArray(gridTicker.getClass().getName(), gridTicker.saveData().array());
        }

        getSaveDelegate().saveGridData(nbttag, IGrid.NBT_SAVE_KEY);

    }

    @Override
    public void readFromDelegate (NBTTagCompound tag)
    {
        NBTTagCompound nbt = tag.getCompoundTag(IGrid.NBT_SAVE_KEY);

        for (IGridTicker gridTicker : gridTickers)
        {
            gridTicker.loadData(Unpooled.wrappedBuffer(nbt.getByteArray(gridTicker.getClass().getName())));
        }

    }


}
