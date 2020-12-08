package com.bjtu.redis;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import java.io.File;

/*
 ** Function: 文件监听器
 ** Author:   王磊 18301137
 ** Date:     2020年12月8日
 */

public class FileListener extends FileAlterationListenerAdaptor {
    @Override
    public void onFileChange(File file) {//文件修改
        //重新读取Action.json和Counter.json
        RedisDemoApplication.setCounters();
        System.out.println();
        System.out.println("已检测到有Json文件更改...");
        System.out.println("已重新读取Action.json和Counter.json文件...");
    }
}