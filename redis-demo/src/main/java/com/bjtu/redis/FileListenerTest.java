package com.bjtu.redis;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import java.util.concurrent.TimeUnit;

/*
 ** Function: 文件监听器测试
 ** Author:   王磊 18301137
 ** Date:     2020年12月8日
 */

public class FileListenerTest {
    public static void monitoring(){
        //监听的文件夹
        String monitorDir = "src/main/resources";
        // 轮询间隔时间（1000）毫秒
        long interval = TimeUnit.SECONDS.toMillis(1);
        FileAlterationObserver observer = new FileAlterationObserver(monitorDir);
        observer.addListener(new FileListener());
        // 创建文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        try {
            //开始监听
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
