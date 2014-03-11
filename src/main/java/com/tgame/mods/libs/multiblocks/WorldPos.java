package com.tgame.mods.libs.multiblocks;

import net.minecraft.world.World;

/**
 * @since 10/03/14
 * @author tgame14
 */
public class WorldPos implements Comparable<WorldPos>, Cloneable
{
    private int x;
    private int y;
    private int z;

    private World world;

    public WorldPos(World world, int x, int y, int z)
    {
        this.world = world;

        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public int compareTo (WorldPos o)
    {
        if (y > o.y)
        {
            return (y - o.y) + (x - o.x) + (z - o.z);
        }

        return (o.y - y) + (o.x - x) + (o.z - z);
    }

    public int getX ()
    {
        return x;
    }

    public void setX (int x)
    {
        this.x = x;
    }

    public int getY ()
    {
        return y;
    }

    public void setY (int y)
    {
        this.y = y;
    }

    public int getZ ()
    {
        return z;
    }

    public void setZ (int z)
    {
        this.z = z;
    }

    public World getWorld ()
    {
        return world;
    }

    public void setWorld (World world)
    {
        this.world = world;
    }

    @Override
    public boolean equals (Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        WorldPos worldPos = (WorldPos) o;

        if (x != worldPos.x)
        {
            return false;
        }
        if (y != worldPos.y)
        {
            return false;
        }
        if (z != worldPos.z)
        {
            return false;
        }
        if (!world.equals(worldPos.world))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode ()
    {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + world.hashCode();
        return result;
    }

}
