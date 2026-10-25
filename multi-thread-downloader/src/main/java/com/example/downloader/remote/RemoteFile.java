package com.example.downloader.remote;

import java.io.IOException;
import java.util.Random;

/**
 * 模拟远程文件服务器。
 * 它不真正联网，而是用 sleep 模拟网络延迟，用 byte[] 模拟远程数据。
 * <p>
 * 你可以把它理解为"云盘上的一个文件"，只能通过 read(offset, length) 分段读取。
 */
public class RemoteFile {

    /**
     * 文件的真实数据（模拟"服务器上存的内容"）
     */
    private final byte[] data;

    /**
     * 模拟网速：每秒下载多少字节。比如 5MB/s
     */
    private final long bytesPerSecond;

    private final Random random = new Random();

    /**
     * @param totalSize      文件总大小（字节）
     * @param bytesPerSecond 模拟下载速度（字节/秒）
     */
    public RemoteFile(long totalSize, long bytesPerSecond) {
        if (totalSize <= 0) throw new IllegalArgumentException("文件大小必须 > 0");
        this.data = new byte[(int) totalSize];
        this.bytesPerSecond = bytesPerSecond;
        // 填充一些可识别的数据，便于校验（比如每个字节都存它的下标低 8 位）
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i & 0xFF);
        }
    }

    /**
     * 从远程读取一段数据。
     * 注意：这是"网络 IO"，会有耗时，必须处理 InterruptedException。
     *
     * @param offset 起始位置（从 0 开始）
     * @param length 读取长度
     * @return 读到的字节数组
     */
    public byte[] read(long offset, int length) throws IOException {
        // 参数校验
        if (offset < 0 || length <= 0 || offset + length > data.length) {
            throw new IllegalArgumentException(
                    "越界读取: offset=" + offset + ", length=" + length + ", total=" + data.length);
        }

        // 模拟网络延迟：字节数 / 速度 = 秒；再加 10% 随机抖动，让每次耗时略有不同
        long baseMillis = (long) (length * 1000.0 / bytesPerSecond);
        long jitter = (long) (baseMillis * 0.1 * random.nextDouble());
        try {
            Thread.sleep(baseMillis + jitter);
        } catch (InterruptedException e) {
            // 恢复中断标志，把异常抛出去让上层处理（这是线程编程的好习惯）
            Thread.currentThread().interrupt();
            throw new IOException("读取被中断", e);
        }

        // 真正"读"数据：从 data 里截取一段
        byte[] result = new byte[length];
        System.arraycopy(data, (int) offset, result, 0, length);
        return result;
    }

    /**
     * 文件总大小
     */
    public long getSize() {
        return data.length;
    }

    /**
     * 提供数据快照，方便校验本地文件是否下载完整（测试用）
     */
    public byte[] getDataSnapshot() {
        return data.clone();
    }
}