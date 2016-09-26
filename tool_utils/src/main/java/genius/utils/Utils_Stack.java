package genius.utils;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Hongsec on 2016-09-26.
 */

public class Utils_Stack {

    /**
     * 判断进程是否运行
     *
     * @param context
     * @param proessName getPackageName
     * @return
     */
    public static boolean isProessRunning(Context context, String proessName) {

        try {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);

            List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
            if (lists == null || lists.isEmpty()) {
                return false;
            }

            for (ActivityManager.RunningAppProcessInfo info : lists) {
                if (info.processName.equals(proessName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * http://blog.csdn.net/chaozhung_no_l/article/details/50416901
     * 要检测 是否开启  , 需要 特殊开启
     *
     * @return
     */
    public static String getRunningForegroundApp(Context context) {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            long ts = System.currentTimeMillis();
            try {
                UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatses = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, ts);

                if (usageStatses == null || usageStatses.isEmpty()) {
                    return null;
                } else {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                    for (UsageStats usageStat : usageStatses) {
                        mySortedMap.put(usageStat.getLastTimeUsed(), usageStat);
                    }
                    if (mySortedMap != null && !mySortedMap.isEmpty()) {
                        return mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                String componentInfo = getRunningForeGround(context);
                if (componentInfo != null) return componentInfo;
            }


        } else {
            String componentInfo = getRunningForeGround(context);
            if (componentInfo != null) return componentInfo;

        }

        return null;
    }


    @Nullable
    private static String getRunningForeGround(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);

            ComponentName componentInfo = taskInfo.get(0).topActivity;

            return componentInfo.getPackageName();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 安装应用
     *
     * @param path
     */
    public static void installapk(Context context, String path) {

        try {

            Intent intent = new Intent();
//        String filepath="/SDCard/XXX.apk";
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断应用是否安装
     *
     * @param pkg
     * @return
     */
    public static boolean checkApkinstalled(Context context, String pkg) {
        try {
            final PackageManager packageManager = context.getPackageManager();
            // 获取所有已安装程序的包信息
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);

            for (PackageInfo packageInfo : pinfo) {
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {// 非系统应用
                    if (packageInfo.packageName.equalsIgnoreCase(pkg))
                        return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }


   /* *//**
     * 包括系统应用
     * @param context
     * @param packagename
     * @return
     *//*
    public static boolean isInstalled(Context context, String packagename) {
        try {
            final PackageManager packageManager = context.getPackageManager();
            // 获取所有已安装程序的包信息
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
            for (int i = 0; i < pinfo.size(); i++) {
                if (pinfo.get(i).packageName.equalsIgnoreCase(packagename))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }*/


}
