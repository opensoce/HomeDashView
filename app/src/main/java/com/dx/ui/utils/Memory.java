package com.dx.ui.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

import com.dx.ui.BaseApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by dxd on 2016/12/4 0004.
 */

public class Memory {
    public static Long getTotalMemory() {
// 系统内存信息文件
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            // 读取meminfo第一行，系统总内存大小
            str2 = localBufferedReader.readLine();

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                JLog.e(str2, num + "\t");
            }
            // 获得系统总内存，单位是KB，乘以1024转换为Byte
            initial_memory = Long.valueOf(arrayOfString[1]) * 1024;
            localBufferedReader.close();
        } catch (IOException e) {
        }

        // Byte转换为KB或者MB，内存大小规格化
        return initial_memory;
    }


    /** 获取android当前可用内存大小 */
    public static Long getAvailMemory() {
        ActivityManager am = (ActivityManager) BaseApplication.getAppContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        // 将获取的内存大小规格化
        return  mi.availMem;
    }

    public static void getRunningAppProcessInfo() {
        ActivityManager mActivityManager = (ActivityManager) BaseApplication.getAppContext().getSystemService(
                Context.ACTIVITY_SERVICE);

        // 获得系统里正在运行的所有进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager
                .getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            // 进程ID号
            int pid = runningAppProcessInfo.pid;
            // 用户ID
            int uid = runningAppProcessInfo.uid;
            // 进程名
            String processName = runningAppProcessInfo.processName;
            // 占用的内存
            int[] pids = new int[] { pid };
            Debug.MemoryInfo[] memoryInfo = mActivityManager
                    .getProcessMemoryInfo(pids);
            int memorySize = memoryInfo[0].dalvikPrivateDirty;

            JLog.e( "processName=" + processName + ",pid=" + pid + ",uid="
                    + uid + ",memorySize=" + memorySize + "kb");
        }
    }


    /**
     * 获取单个 app 内存限制大小
     * 返回以 M 为单位的数字，
     * 可能在不同的平台或者设备上值都不太一样
     */
    public static void test01() {
        ActivityManager activityManager2 = (ActivityManager) BaseApplication.getAppContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        activityManager2.getMemoryClass();
    }
}
