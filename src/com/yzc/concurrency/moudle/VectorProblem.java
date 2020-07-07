package com.yzc.concurrency.moudle;

import java.util.Vector;

public class VectorProblem {
    // 如果有其他线程同步修改list，则可能有数组越界异常
    public static Object getLast(Vector list){
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }

    // 通过客户端加锁，保证复合操作的原子性
    public static Object getSafeLast(Vector list){
        synchronized (list) {
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
    }
}
