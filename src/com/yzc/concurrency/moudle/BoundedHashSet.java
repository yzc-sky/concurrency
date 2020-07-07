package com.yzc.concurrency.moudle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        this.sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        // 获取许可，阻塞
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if(!wasAdded){
                // 未添加成功，释放许可
                sem.release();
            }
        }
    }

    // 删除元素成功，释放一个许可
    public boolean remove(T o){
        boolean remove = set.remove(o);
        if(remove){
            sem.release();
        }
        return remove;
    }
}
