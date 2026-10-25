package com.example.downloader;

import com.example.downloader.remote.RemoteFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * 单线程下载器：顺序把远程文件整块读完，写到本地。
 * 它的作用是给后面多线程版本做"性能基线"和"正确性基线"。
 */
public class SingleThreadDownloader {

    private final RemoteFile remote;   // 远程文件
    private final String localPath;    // 本地保存路径
    private static final int BUFFER = 1024 * 1024;  // 每次读 1MB

    public SingleThreadDownloader(RemoteFile remote, String localPath) {
        this.remote = remote;
        this.localPath = localPath;
    }

    /**
     * 执行下载：从 0 读到末尾，边读边写本地。
     */
    public void download() throws IOException {
        long total = remote.getSize();

        long start = System.currentTimeMillis();

        try (RandomAccessFile raf = new RandomAccessFile(localPath, "rw")) {

            long offset = 0;   // 当前读到哪了

            while (offset < total) {
                int len = (int) Math.min(BUFFER, (total - offset));
                byte[] data = remote.read(offset, len);
                raf.seek(offset);
                raf.write(data);
                offset += len;
            }
        }

    }

    /**
     * 校验：把本地文件读出来，和远程数据的快照逐字节比较。
     *
     * @return true 表示完全一致
     */
    public boolean verify() throws IOException {

        byte[] local = Files.readAllBytes(Path.of(localPath));
        byte[] remoteDAta = remote.getDataSnapshot();

        return Arrays.equals(local, remoteDAta);
    }
}