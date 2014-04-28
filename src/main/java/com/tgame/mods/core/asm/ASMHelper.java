package com.tgame.mods.core.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ChickenBones
 *
 */
public class ASMHelper
{
    public static class CodeBlock
    {
        public Label start = new Label();
        public Label end = new Label();
    }

    public static class ForBlock extends CodeBlock
    {
        public Label cmp = new Label();
        public Label inc = new Label();
        public Label body = new Label();
    }

    public static ClassNode createClassNode(byte[] bytes)
    {
        return createClassNode(bytes, 0);
    }

    public static ClassNode createClassNode(byte[] bytes, int flags)
    {
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(cnode, flags);
        return cnode;
    }

    public static Map<LabelNode, LabelNode> cloneLabels(InsnList insns)
    {
        HashMap<LabelNode, LabelNode> labelMap = new HashMap<LabelNode, LabelNode>();
        for (AbstractInsnNode insn = insns.getFirst(); insn != null; insn = insn.getNext())
            if (insn.getType() == 8)
                labelMap.put((LabelNode) insn, new LabelNode());
        return labelMap;
    }

    public static InsnList cloneInsnList(InsnList insns)
    {
        return cloneInsnList(cloneLabels(insns), insns);
    }

    public static InsnList cloneInsnList(Map<LabelNode, LabelNode> labelMap, InsnList insns)
    {
        InsnList clone = new InsnList();
        for (AbstractInsnNode insn = insns.getFirst(); insn != null; insn = insn.getNext())
            clone.add(insn.clone(labelMap));

        return clone;
    }

    public static List<TryCatchBlockNode> cloneTryCatchBlocks(Map<LabelNode, LabelNode> labelMap, List<TryCatchBlockNode> tcblocks)
    {
        ArrayList<TryCatchBlockNode> clone = new ArrayList<TryCatchBlockNode>();
        for (TryCatchBlockNode node : tcblocks)
            clone.add(new TryCatchBlockNode(labelMap.get(node.start), labelMap.get(node.end), labelMap.get(node.handler), node.type));

        return clone;
    }

    public static List<LocalVariableNode> cloneLocals(Map<LabelNode, LabelNode> labelMap, List<LocalVariableNode> locals)
    {
        ArrayList<LocalVariableNode> clone = new ArrayList<LocalVariableNode>();
        for (LocalVariableNode node : locals)
            clone.add(new LocalVariableNode(node.name, node.desc, node.signature, labelMap.get(node.start), labelMap.get(node.end), node.index));

        return clone;
    }

    public static void copy(MethodNode src, MethodNode dst)
    {
        Map<LabelNode, LabelNode> labelMap = cloneLabels(src.instructions);
        dst.instructions = cloneInsnList(labelMap, src.instructions);
        dst.tryCatchBlocks = cloneTryCatchBlocks(labelMap, src.tryCatchBlocks);
        if (src.localVariables != null)
            dst.localVariables = cloneLocals(labelMap, src.localVariables);
        dst.visibleAnnotations = src.visibleAnnotations;
        dst.invisibleAnnotations = src.invisibleAnnotations;
        dst.visitMaxs(src.maxStack, src.maxLocals);
    }

    public static int getLocal(List<LocalVariableNode> list, String name)
    {
        int found = -1;
        for (LocalVariableNode node : list)
        {
            if (node.name.equals(name))
            {
                if (found >= 0)
                    throw new RuntimeException("Duplicate local variable: " + name + " not coded to handle this scenario.");

                found = node.index;
            }
        }
        return found;
    }

    public static void replaceMethodCode(MethodNode original, MethodNode replacement)
    {
        original.instructions.clear();
        if (original.localVariables != null)
            original.localVariables.clear();
        if (original.tryCatchBlocks != null)
            original.tryCatchBlocks.clear();
        replacement.accept(original);
    }
}

