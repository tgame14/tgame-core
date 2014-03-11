package com.tgame.mods.libs.multiblocks.grid;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.tgame.mods.coremod.TgameCore;
import com.tgame.mods.libs.multiblocks.WorldPos;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public Grid (IGridNode node)
    {
        this.gridMap = HashBiMap.create();
        this.gridTickers = new ArrayList<IGridTicker>(GridTickerRegistry.gridTickerRegistry.size());

        for (Class<? extends IGridTicker> clazz : GridTickerRegistry.gridTickerRegistry)
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
                    TgameCore.LOGGER.fatal("A IGridTicker doesnt have a Constructor with IGrid in it! This is an error", e);
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
            for (Map.Entry<WorldPos, IGridNode> node : gridMap.entrySet())
            {
                if (node.getValue().canBeDelegate())
                {
                    TgameCore.LOGGER.info("Node at " + node.getKey() + " is a save Manager");
                    this.saveDelegate = node.getValue();
                    return this.saveDelegate;
                }
            }
        }

        return this.saveDelegate;
    }

    @Override
    public void writeToDelegate()
    {
        NBTTagCompound nbttag = new NBTTagCompound();

        for (IGridTicker gridTicker : gridTickers)
        {
            nbttag.setByteArray(gridTicker.nbtKeyTag(), gridTicker.saveData().array());
        }

        getSaveDelegate().saveGridData(nbttag, IGrid.NBT_SAVE_KEY);

    }

    @Override
    public void readFromDelegate(NBTTagCompound tag)
    {
        NBTTagCompound nbt = tag.getCompoundTag(IGrid.NBT_SAVE_KEY);

        for (IGridTicker gridTicker : gridTickers)
        {
            gridTicker.readData(Unpooled.wrappedBuffer(nbt.getByteArray(gridTicker.nbtKeyTag())));
        }

    }


}
