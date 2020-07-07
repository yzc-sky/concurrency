package com.yzc.concurrency.composition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 客户端加锁，若没有则添加
 * @param <E>
 */
public class ListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x){
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }
            return absent;
        }
    }
}
