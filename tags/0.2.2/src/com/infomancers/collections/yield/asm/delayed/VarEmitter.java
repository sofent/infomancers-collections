package com.infomancers.collections.yield.asm.delayed;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Copyright (c) 2007, Aviad Ben Dov
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
public class VarEmitter extends DelayedInstructionEmitter {
    public VarEmitter(int insn, Object[] params) {
        super(insn, params);
    }

    @Override
    public void emit(MethodVisitor mv) {
        mv.visitVarInsn(insn, (Integer) params[0]);
    }

    @Override
    public int pushAmount() {
        if (insn >= Opcodes.ILOAD && insn <= Opcodes.ALOAD) {
            return 1;
        } else if (insn >= Opcodes.ISTORE && insn <= Opcodes.ASTORE) {
            return 0;
        } else {
            throw new IllegalStateException("Unknown instruction: " + insn);
        }
    }

    @Override
    public int popAmount() {
        if (insn >= Opcodes.ILOAD && insn <= Opcodes.ALOAD) {
            return 0;
        } else if (insn >= Opcodes.ISTORE && insn <= Opcodes.ASTORE) {
            return 1;
        } else {
            throw new IllegalStateException("Unknown instruction: " + insn);
        }
    }
}
