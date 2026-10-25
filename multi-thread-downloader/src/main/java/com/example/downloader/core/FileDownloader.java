package com.example.downloader.core;

import com.example.downloader.task.DownloadTask;
import com.example.downloader.remote.RemoteFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程下载调度器。
 * 负责：分块、开池、提交任务、等完成、关池。
 */
public class FileDownloader {
    private final RemoteFile remote;
    private final String localPath;
    private final int chunkSize;  //每块大小
    private final int threadCount;  //线程池大小

    private int downloadBytes = 0;

    public FileDownloader(RemoteFile remote, String localPath, int chunkSize, int threadCount) {
        if (remote == null) {
            throw new IllegalArgumentException("remote 不能为null");
        }
        if (localPath == null || localPath.isBlank()) {
            throw new IllegalArgumentException("localPath 不能为null");
        }
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize 必须 >0");
        }
        if (threadCount <= 0) {
            throw new IllegalArgumentException("threadCount 必须>0");
        }

        this.remote = remote;
        this.localPath = localPath;
        this.chunkSize = chunkSize;
        this.threadCount = threadCount;
    }

    private final AtomicInteger downloadedBytes = new AtomicInteger(0);

    public void addProgress(int len) {
        downloadedBytes.addAndGet(len);
    }

    public int getDownloadedBytes() {
        return downloadedBytes.get();
    }

    /**
     * 执行多线程下载。
     */
    public void download() throws IOException, InterruptedException {
        long total = remote.getSize();

        //计算总块数，向上取整
        int blockCount = (int) ((total + chunkSize - 1) / chunkSize);
        //System.out.println("[多线程]文件大小" + total + "字节，块大小" +
        //        chunkSize + ",共" + blockCount + "块，线程池" + threadCount);

        //创建线程池
//        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                threadCount,
                threadCount,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(),
                r -> {
                    Thread t = new Thread(r);
                    t.setName("dl-" + t.getId());
                    t.setDaemon(false);
                    return t;
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        //创建CountDownLatch,计数=blockCount
        CountDownLatch latch = new CountDownLatch(blockCount);

        //打开本地文件(共享所有任务)
        try (RandomAccessFile raf = new RandomAccessFile(localPath, "rw")) {
            //循环 blockCount 次，为每一块计算 offset 和 length，提交任务
            for (int i = 0; i < blockCount; i++) {
                long offset = (long) i * chunkSize;
                int length = (int) Math.min(chunkSize, total - offset);
                pool.execute(new DownloadTask(remote, raf, offset, length, latch, this));

            }

            latch.await();
        }

        //关闭线程池
        pool.shutdown();// 不再接新任务
        if (!pool.awaitTermination(5, TimeUnit.MINUTES)) {// 等最多 5 分钟
            pool.shutdownNow();// 超时强杀
        }
    }

}
