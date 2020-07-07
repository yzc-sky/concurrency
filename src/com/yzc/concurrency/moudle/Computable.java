package com.yzc.concurrency.moudle;

import java.util.concurrent.*;

public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException, ExecutionException;
}

class Memoizer<A, V> implements Computable<A, V> {
    //缓存
    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = () -> c.compute(arg);
            FutureTask<V> ft = new FutureTask<>(eval);
            // 复合操作若没有则添加，原子性
            f = cache.putIfAbsent(arg, ft);
            if(f == null){
                f = ft;
                ft.run();
            }
        }
        try {
            return f.get();
        } catch (CancellationException e){
            cache.remove(arg, f);
            return null;
        } catch (ExecutionException e) {
            throw e;
        }
    }
}