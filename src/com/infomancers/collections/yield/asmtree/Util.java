package com.infomancers.collections.yield.asmtree;

import com.infomancers.collections.yield.asmbase.YielderInformationContainer;
import com.infomancers.collections.yield.asmtree.enhancers.EnhancersFactory;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (c) 2009, Aviad Ben Dov
 * <p/>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * <p/>
 * 1. Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 3. Neither the name of Infomancers, Ltd. nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

public final class Util {
    public static void enhanceLines(YielderInformationContainer info, ClassNode node, InsnList instructions,
                                    List<TryCatchBlockNode> tryCatchBlocks, EnhancersFactory factory) {
        // enhance lines as required
        for (AbstractInsnNode instruction = instructions.getLast();
             instruction != null;
             instruction = instruction.getPrevious()) {

            if (instruction.getType() == AbstractInsnNode.FRAME) {
                instruction = instruction.getPrevious();
                instructions.remove(instruction.getNext());
                continue;
            }

            InsnEnhancer enhancer = factory.createEnhancer(instruction);
            instruction = enhancer.enhance(node, instructions, getLimits(tryCatchBlocks), info, instruction);
        }
    }

    private static List<AbstractInsnNode> getLimits(List<TryCatchBlockNode> tryCatchBlocks) {
        if (tryCatchBlocks == null || tryCatchBlocks.size() == 0) return Collections.emptyList();

        List<AbstractInsnNode> result = new LinkedList<AbstractInsnNode>();

        for (TryCatchBlockNode tryCatchBlock : tryCatchBlocks) {
            result.add(tryCatchBlock.handler);
        }

        return result;
    }

    public static boolean isYieldNextCoreMethod(String name, String desc) {
        return "yieldNextCore".equals(name) && "()V".equals(desc);
    }

    public static boolean isInvokeYieldReturn(int opcode, String name, String desc) {
        return opcode == Opcodes.INVOKEVIRTUAL && "yieldReturn".equals(name) && "(Ljava/lang/Object;)V".equals(desc);
    }

    public static boolean isInvokeYieldBreak(int opcode, String name, String desc) {
        return opcode == Opcodes.INVOKEVIRTUAL && "yieldBreak".equals(name) && "()V".equals(desc);
    }


    public static void insertOrAdd(InsnList instructions, AbstractInsnNode backNode, AbstractInsnNode node) {
        if (backNode == null) {
            backNode = instructions.getFirst();
            while (backNode.getOpcode() < 0) {
                backNode = backNode.getNext();
            }

            instructions.insertBefore(backNode, node);
        } else {
            instructions.insert(backNode, node);
        }
    }

    public static void insertOrAdd(InsnList instructions, AbstractInsnNode backNode, InsnList list) {
        if (backNode == null) {
            backNode = instructions.getFirst();
            while (backNode.getOpcode() < 0) {
                backNode = backNode.getNext();
            }
            instructions.insertBefore(backNode, list);
        } else {
            instructions.insert(backNode, list);
        }
    }


    public static InsnList createList(AbstractInsnNode... nodes) {
        InsnList list = new InsnList();
        AbstractInsnNode last = null;
        for (AbstractInsnNode node : nodes) {
            if (last == null) {
                list.insert(node);
            } else {
                list.insert(last, node);
            }
            last = node;
        }

        return list;
    }
}
