package com.tgame.mods.coremod.asm.transformers;

import com.tgame.mods.coremod.asm.ASMHelper;
import com.tgame.mods.libs.registry.IRegisterable;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 29/03/14
 * @author tgame14
 */
public class RegistryTransformer implements IClassTransformer
{
    private static Set<String> registerSet = new HashSet<String>();

    @Override
    public byte[] transform (String name, String transformedName, byte[] bytes)
    {
        ClassNode cnode = ASMHelper.createClassNode(bytes);

        if (cnode.interfaces.contains(IRegisterable.class.getName()))
        {
            registerSet.add(transformedName);
        }

        return bytes;
    }
}
