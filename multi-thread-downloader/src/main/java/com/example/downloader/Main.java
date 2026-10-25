package com.example.downloader;

import com.example.downloader.core.FileDownloader;
import com.example.downloader.remote.RemoteFile;

import static jdk.internal.classfile.impl.verifier.VerifierImpl.verify;

public class Main {
    public static void main(String[] args) throws Exception {
        long totalSize = 50L * 1024 * 1024;  //50MB
        long speed = 5L * 1024 * 1024;  //5MB/s
        String localPath = "downloads/local.dat";

        // ========== 单线程基线 ==========
        RemoteFile remote1 = new RemoteFile(totalSize, speed);
        SingleThreadDownloader single = new SingleThreadDownloader(remote1, localPath);
        long t1 = System.currentTimeMillis();
        single.download();
        long singleCost = System.currentTimeMillis() - t1;
        System.out.println("[单线程] 耗时 " + singleCost + " ms，校验 "
                + (single.verify() ? "✅" : "❌"));

        // ========== 多线程版本 ==========
        RemoteFile remote2 = new RemoteFile(totalSize, speed);
        int chunksize = 1024 * 1024;//每块1MB
        int threadCount = 8;//8个线程

        FileDownloader multi = new FileDownloader(remote2, "downloads/local-multi.dat", chunksize, threadCount);
        long t2 = System.currentTimeMillis();
        multi.download();
        long multiCost = System.currentTimeMillis() - t2;

        System.out.println("[多线程] 耗时 " + multiCost + " ms");
        System.out.println("[多线程] 已下载 " + multi.getDownloadedBytes() + " 字节，应为 " + totalSize);
        System.out.println("[多线程] 校验 " + (verify(multi, remote2) ? "✅" : "❌"));
        System.out.println("[对比] 单线程 " + singleCost + " ms vs 多线程 " + multiCost + " ms");
    }

    //校验多线程下载的文件和远程是否一致
    static boolean verify(FileDownloader d, RemoteFile remote) throws Exception {
        byte[] local = java.nio.file.Files.readAllBytes(java.nio.file.Path.of("downloads/local-multi.dat"));
        byte[] remoteData = remote.getDataSnapshot();
        return java.util.Arrays.equals(local, remoteData);
    }
}
