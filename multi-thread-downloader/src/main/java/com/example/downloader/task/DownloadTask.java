package com.example.downloader.task;

import com.example.downloader.remote.RemoteFile;
import com.example.downloader.core.FileDownloader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.CountDownLatch;

/**
 * 下载一个数据块的任务。
 * 由线程池执行，每个任务负责 [offset, offset+length) 这一段。
 */
public class DownloadTask implements Runnable{

    private final RemoteFile remote;
    private final RandomAccessFile raf;  //共享对象
    private final long offset;  //本块起始长度
    private final int length;  //本块长度
    private final CountDownLatch latch;  //完成后countDown
    private final FileDownloader owner;  //回调owner，把进度报给它统计

    private static final int MAX_RETER=3;

    public DownloadTask(RemoteFile remote, RandomAccessFile raf,long offset,int length,
                        CountDownLatch latch,FileDownloader owner) {
        this.remote=remote;
        this.raf=raf;
        this.offset=offset;
        this.length=length;
        this.latch=latch;
        this.owner=owner;
    }

    @Override
    public void run() {
        try {
            int attemp = 0;
            while (true) {
                try {
                    byte[] data = remote.read(offset, length);

                    synchronized (raf) {
                        raf.seek(offset);
                        raf.write(data);
                    }

                    owner.addProgress(length);
                    return; // 成功退出循环
                } catch (IOException e) {
                    attemp++;
                    System.err.println("块offset=" + offset + "第" + attemp + "次失败：" + e.getMessage());
                    if (attemp >= MAX_RETER) {
                        System.err.println("块offset" + offset + "重试" + MAX_RETER + "次仍失败，放弃");
                        return; // 重试耗尽，放弃
                    }
                    try {
                        Thread.sleep(100L * attemp);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        } finally {
            latch.countDown();
        }
    }
}
