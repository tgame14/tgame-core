package com.tgame.mods.config;

import com.tgame.mods.core.Settings;
import cpw.mods.fml.common.discovery.ASMDataTable;
import gnu.trove.set.hash.TCustomHashSet;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 11/05/14
 * @author tgame14
 */
public class ConfigScanner
{

    private Set<ASMDataTable.ASMData> configs;
    protected Set<Class> classes;

    private static ConfigScanner instance = new ConfigScanner();

    private ConfigScanner()
    {
        this.configs = new HashSet<ASMDataTable.ASMData>();
        this.classes = new HashSet<Class>();
    }

    public static ConfigScanner instance ()
    {
        return instance;
    }

    public void generateSets(ASMDataTable table)
    {
        configs = table.getAll("com.tgame.mods.config.Config");

        for (ASMDataTable.ASMData data : configs)
        {
            try
            {
                classes.add(Class.forName(data.getClassName()));
            }
            catch (ClassNotFoundException e)
            {
                Settings.LOGGER.catching(e);
                e.printStackTrace();
            }
        }
    }
}
