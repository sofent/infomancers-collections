package com.infomancers.collections.yield.asmbase;

import com.infomancers.collections.yield.asm.NewMember;

import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: aviadbendov
 * Date: Apr 3, 2009
 * Time: 8:41:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface YielderInformationContainer {
    int getCounter();

    Iterable<? extends NewMember> getSlots();

    Queue<Integer> getLoads();

    NewMember getSlot(int var);
}
