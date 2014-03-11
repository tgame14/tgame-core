package com.tgame.mods.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * @since 10/03/14
 * @author tgame14
 */
@IFMLLoadingPlugin.TransformerExclusions(value = { "com.tgame.mods.coremod" } )
public class TgamePluginLoader implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass ()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass ()
    {
        return null;
    }

    @Override
    public String getSetupClass ()
    {
        return null;
    }

    @Override
    public void injectData (Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass ()
    {
        return null;
    }
}
