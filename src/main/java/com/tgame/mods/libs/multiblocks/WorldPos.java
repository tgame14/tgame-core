package com.tgame.mods.libs.multiblocks;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @since 10/03/14
 * @author tgame14
 */
public class WorldPos implements Comparable<WorldPos>, Cloneable
{
    protected int x;
    protected int y;
    protected int z;

    protected World world;

    public WorldPos (World world, int x, int y, int z)
    {
        this.world = world;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getChunkX ()
    {
        return x >> 4;
    }

    public int getChunkZ ()
    {
        return z >> 4;
    }

    public long getChunkXZHash ()
    {
        return ChunkCoordIntPair.chunkXZ2Int(x >> 4, z >> 4);
    }

    public WorldPos translate (ForgeDirection dir)
    {
        this.x += dir.offsetX;
        this.y += dir.offsetY;
        this.z += dir.offsetZ;
        return this;
    }

    public WorldPos[] getNeighbors ()
    {
        return new WorldPos[]
                {
                        new WorldPos(world(), x + 1, y, z),
                        new WorldPos(world(), x - 1, y, z),
                        new WorldPos(world(), x, y + 1, z),
                        new WorldPos(world(), x, y - 1, z),
                        new WorldPos(world(), x, y, z + 1),
                        new WorldPos(world(), x, y, z - 1)
                };
    }


    /**
     *
     * @param other - other value
     * @return a hacky compareTo, based on priority corner location
     */
    @Override
    public int compareTo (WorldPos other)
    {
        if (this.x < other.x)
        {
            return -1;
        }
        else if (this.x > other.x)
        {
            return 1;
        }
        else if (this.y < other.y)
        {
            return -1;
        }
        else if (this.y > other.y)
        {
            return 1;
        }
        else if (this.z < other.z)
        {
            return -1;
        }
        else if (this.z > other.z)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public int x ()
    {
        return x;
    }

    public void setX (int x)
    {
        this.x = x;
    }

    public int y ()
    {
        return y;
    }

    public void setY (int y)
    {
        this.y = y;
    }

    public int z ()
    {
        return z;
    }

    public void setZ (int z)
    {
        this.z = z;
    }

    public World world ()
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

    public boolean equals (int x, int y, int z)
    {
        return this.equals(new WorldPos(world, x, y, z));
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

    @Override
    public WorldPos clone ()
    {
        return new WorldPos(this.world, this.x, this.y, this.z);
    }
}
