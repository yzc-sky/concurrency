package com.yzc.concurrency.excutor;

import java.io.File;
import java.util.concurrent.BlockingQueue;

// 通过毒丸对象来停止服务
public class IndexingService {
    private static final File POISON = new File("");
    private final BlockingQueue<File> queue;
    private final File root;
    private final CrawlerThread producer = new CrawlerThread();
    private final IndexerThread consumer = new IndexerThread();

    public IndexingService(BlockingQueue<File> queue, File root) {
        this.queue = queue;
        this.root = root;
    }

    // 开始服务
    public void start(){
        producer.start();
        consumer.start();
    }

    // 停止服务
    public void stop(){
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException{
        consumer.join();
    }

    // 生产者线程
    class CrawlerThread extends Thread {
        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                e.printStackTrace();
                while (true){
                    try {
                        // 添加毒丸
                        queue.put(POISON);
                        break;
                    } catch (InterruptedException ex) {
                        // 重新尝试
                    }
                }
            }
        }

        private void crawl(File root) throws InterruptedException{

        }
    }

    // 消费者线程
    class IndexerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    File file = queue.take();
                    // 如果是毒丸，停止工作，结束线程
                    if(file == POISON) {
                        break;
                    } else {
                        // 正常工作
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
