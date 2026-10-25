package com.example.downloader;

import com.example.downloader.core.FileDownloader;
import com.example.downloader.remote.RemoteFile;

//实验类：跑性能对比
public class Experiment {
    public static void main(String[] args) throws Exception {
        System.out.println("CPU核数：" + Runtime.getRuntime().availableProcessors());
        System.out.println("文件大小 50MB，模拟速度 5MB/s");

        long[] multiCosts = new long[3];  //多线程的耗时
        long[] singleCosts = new long[3];  //单线程的耗时

        System.out.println("========== T1: 多线程(8线程) 跑 3 次 ==========");
        for (int i = 0; i < 3; i++) {
            multiCosts[i] = runMultiOnce(1024 * 1024, 8);     // 1MB 块，8 线程
            System.out.println("多线程第 " + (i + 1) + " 轮: " + multiCosts[i] + " ms");
        }
        System.out.println("========== T1 多线程部分完成 ==========");

        System.out.println("========== T2: 单线程 跑 3 次 ==========");
        for (int i = 0; i < 3; i++) {
            singleCosts[i] = runSingleOnce();
            System.out.println("单线程第 " + (i + 1) + " 轮: " + singleCosts[i] + " ms");
        }
        System.out.println("========== T2 单线程部分完成 ==========");

        // 算平均值
        long multiAvg = (multiCosts[0] + multiCosts[1] + multiCosts[2]) / 3;
        long singleAvg = (singleCosts[0] + singleCosts[1] + singleCosts[2]) / 3;

        // 打印结果表
        System.out.println("\n========== 最终结果 ==========");
        System.out.println("单线程平均耗时: " + singleAvg + " ms");
        System.out.println("多线程平均耗时: " + multiAvg + " ms");

        // 算加速比 (单线程 / 多线程)
        // 注意：这里一定要乘 1.0，否则整数除法会把小数抹掉（比如 3.5 变成 3）
        double speedup = singleAvg * 1.0 / multiAvg;
        System.out.printf("加速比: %.2fx\n", speedup);

        //定义要测试的线程数数组
        int[] threadCounts = {1, 2, 4, 8, 16, 32};

        System.out.println("========== T3: 线程数扫描 ==========");
        System.out.printf("%-8s %-10s %-10s %-10s %-10s\n", "线程数", "第1次", "第2次", "第3次", "平均");

        for (int tc : threadCounts) {
            long[] costs = new long[3];  //每个线程数存3次耗时

            //每个线程数跑三次
            for (int i = 0; i < 3; i++) {
                costs[i] = runMultiOnce(1024 * 1024, tc);
            }

            //平均值
            long avg = (costs[0] + costs[1] + costs[2]) / 3;

            //按表格格式打印
            System.out.printf("%-8d %-10d %-10d %-10d %-10d\n", tc, costs[0], costs[1], costs[2], avg);
        }
        System.out.println("========== T3 完成 ==========");
    }


    //跑一轮多线程下载，返回耗时（毫秒）
    static long runMultiOnce(int chunkSize, int threadCount) throws Exception {
        long total = 50L * 1024 * 1024;// 50MB
        long speed = 5L * 1024 * 1024;// 5MB/s
        String path = "downloads/t1-multi.dat";

        RemoteFile remote = new RemoteFile(total, speed);
        // 每次跑测试前清空旧文件
        java.nio.file.Files.deleteIfExists(java.nio.file.Path.of(path));
        FileDownloader d = new FileDownloader(remote, path, chunkSize, threadCount);

        //计时下载
        long start = System.currentTimeMillis();
        d.download();
        long cost = System.currentTimeMillis() - start;

        //校验
        byte[] local = java.nio.file.Files.readAllBytes(java.nio.file.Path.of(path));
//        if (java.util.Arrays.equals(local, remote.getDataSnapshot())) {
//            throw new RuntimeException("多线程文件内容校验失败");
//        }
        if (!java.util.Arrays.equals(local, remote.getDataSnapshot())) {
            System.out.println("出错了！本地文件大小: " + local.length + ", 远程文件大小: " + remote.getDataSnapshot().length);
            throw new RuntimeException("多线程文件内容校验失败");
        }

        if (d.getDownloadedBytes() != total) {
            throw new RuntimeException("多线程进度校验失败：" + d.getDownloadedBytes());
        }

        return cost;
    }

    //跑一轮单线程下载，返回耗时（毫秒）
    static long runSingleOnce() throws Exception {
        long total = 50 * 1024 * 1024;
        long speed = 5L * 1024 * 1024;
        String path = "downloads/t1-single.dat";

        java.nio.file.Files.deleteIfExists(java.nio.file.Path.of(path));

        RemoteFile remote = new RemoteFile(total, speed);
        SingleThreadDownloader d = new SingleThreadDownloader(remote, path);

        //计时下载
        long start = System.currentTimeMillis();
        d.download();
        long cost = System.currentTimeMillis() - start;

        //校验
        if (!d.verify()) {
            throw new RuntimeException("单线程校验失败");
        }

        return cost;
    }
}
