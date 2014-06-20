package com.tgame.mods.libs.network;

import java.util.List;

/** Applied to all classes that can send packets. For ease of use.
 * 
 * @author tgame14 */
public interface IPacketSender
{
    public List getPacketData(int type);
}
