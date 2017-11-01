package com.dx.ui.utils;

/**
 * Created by dxd on 2016/12/3 0003.
 */
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.dx.ui.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class Format {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat shortFormatter = new SimpleDateFormat("yyyy-MM-dd");

    /** 打开应用的时间 **/
    public static long OPENAPPTIME = 0;

    public static final long MIN = 60 * 1000;
    public static long UPDATERULETIME = 30 * MIN;
    public static String SOUND;
    public static String POSTDATA;
    public static String vcode;
    public static String screeSize;
    public static String channel;
    public static String imie;
    public static String mobile;

    /**
     * 转为日期
     *
     * @param date
     * @return
     */
    public static Date format2Date(String date) {
        if(date.contains("-")){
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else if(date.contains("/"))
            formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }


    /**
     * 转为日期字符串
     *
     * @param date
     * @return
     */
    public static String format2ShortDate(String date) {
        try {
            if (date != null)
                return date.substring(0, date.lastIndexOf("-") + 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /** 将时间戳转换为日期格式**/
    public static String getStrTime(long time)
    {
        String mStrTime = null;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mStrTime = formatter.format(new Date(time));
        return mStrTime;
    }

    /** long time 转换成时间格式 00:00**/
    public static String intToTime(int time)
    {
        String minStr, secStr;
        StringBuffer timeStr = new StringBuffer();

        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;
        if(min / 10 == 0)
        {
            minStr = "0" + min;
        }
        else
        {
            minStr = min + "";
        }
        if(sec / 10 == 0)
        {
            secStr = "0" + sec;
        }
        else
        {
            secStr = sec + "";
        }
        timeStr.append(minStr);
        timeStr.append(":");
        timeStr.append(secStr);
        return timeStr.toString();
    }


    public static String apkSizeFormat(double dSize, String ext) {

        return String.valueOf(apkSizeFormat.format(dSize)) + ext;
    }

    private static NumberFormat apkSizeFormat = new DecimalFormat("0.##");

    /**
     * @param source
     * @return
     * @throws IOException
     */
    public static byte[] decompress(byte[] source) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(source);
        GZIPInputStream gis = new GZIPInputStream(bin);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int length = 0;
        while ((length = gis.read(buf)) > 0) {
            bos.write(buf, 0, length);
        }
        return bos.toByteArray();
    }

    public static byte[] compress(byte[] source) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(source);
        gzip.close();
        return out.toByteArray();
    }

    /**
     * 给Url加密
     *
     * @param content
     * @return
     */
    public static String encodeContentForUrl(String content) {
        String ReStr = "";
        if (content != null) {
            try {
                ReStr = URLEncoder.encode(content, "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return ReStr;

    }

    /**
     * 取得评论图形
     *
     * @param d
     * @return
     */
    public static float getFloatRateVaue(double d) {
        return (float) d;// 0x4000000000000000L
    }

    /**
     * 取得详情评分
     *
     * @param d
     * @return
     */
    public static float getDrawRateVaue(double d) {
        return ((int) Math.ceil(d * 2)) * 1.0f / 2;// 0x4000000000000000L
    }

    public static String getIMEI(Context context) {
        final TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }

    public static String getIMSI(Context context) {
        final TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        return imsi;
    }
    //sim卡是否可读
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            return TelephonyManager.SIM_STATE_READY == mgr
                    .getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 获取手机型号，如 "HTC Desire"等
     * **/
    public static String getModel() {
        return Build.MODEL;
    }

    public static String getNetAvialbleType(Context context) {
        String net_type = "null";
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取代表联网状态的NetWorkInfo对象
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                return "";
            }
            // 获取当前的网络连接是否可用
            boolean available = networkInfo.isAvailable();
            if (available) {
            } else {
                net_type = "";
            }

            android.net.NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            if (android.net.NetworkInfo.State.CONNECTED == state) {
                net_type = "gprs";
            }

            state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            if (android.net.NetworkInfo.State.CONNECTED == state) {
                net_type = "wifi";
            }
        } catch (Exception ee) {

        }
        return net_type;
    }

    public static String getScreenSize(Activity context) {
        String size = "";
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int w = mDisplayMetrics.widthPixels;
        int h = mDisplayMetrics.heightPixels;
        size = w + "x" + h;
        return size;
    }


    public static String getChannelName(Context context){
        ApplicationInfo appInfo;
        String msg="";
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            //msg=appInfo.metaData.getInt("UMENG_CHANNEL");
            msg=appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(msg.startsWith("um")){
            msg = msg.substring(2, msg.length());
        }
        return msg;
    }

    public static String getVcode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(packageInfo.versionCode);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static int getPcode(Context context) {
        SharedPreferences sp = context.getSharedPreferences("push", Context.MODE_PRIVATE);
        return sp.getInt("pid", 0);
    }

    public static boolean isInstalled(Context context, String pkg) {
        List<PackageInfo> pis = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : pis) {
            if (packageInfo.packageName.equals(pkg))
                return true;
        }
        return false;
    }

    public static void saveFile(OutputStream os, String content) {
        try {
            os.write(content.getBytes());
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(InputStream is) {
        byte[] buff = new byte[512];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int tag = -1;
        try {
            while ((tag = is.read(buff)) != -1) {
                os.write(buff, 0, tag);
                os.flush();
            }
            return new String(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }


    /**
     * 下载 量格式化
     *
     * @param dNum
     * @param ext
     * @return
     */
    public static String formatDnum(int dNum, String ext) {
        StringBuilder formatData = new StringBuilder();
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(1);
        if (dNum >= 0 && dNum < 100000) {
            formatData.append(dNum);
        } else if (dNum >= 100000 ) {
            formatData.append(100000+"+");
			/*formatData.append(df.format((int)(dNum * 1.0 / 10000)) + BaseApplication.getAppContext().getResources().getString(R.string.myriad)).append(ext);*/
        }/* else if (dNum >= 100000 && dNum < ) {
			formatData.append(df.format(dNum * 1.0 / 100000) + BaseApplication.getAppContext().getResources().getString(R.string.myriad)).append(ext);
		} else if (dNum >= 1000000 && dNum < 10000000) {
			formatData.append(df.format(dNum * 1.0 / 1000000) + BaseApplication.getAppContext().getResources().getString(R.string.myriad)).append(ext);
		} else if (dNum >= 10000000 && dNum < 100000000) {
			formatData.append(df.format(dNum * 1.0 / 10000000) + BaseApplication.getAppContext().getResources().getString(R.string.ten_million)).append(ext);
		} else if (dNum >= 100000000) {
			formatData.append(df.format(dNum * 1.0 / 100000000) + BaseApplication.getAppContext().getResources().getString(R.string.calculate)).append(ext);
		}*/
        return formatData.toString();
    }
    public static String formatVideoDnum(int dNum) {
        String down = null;
        if (dNum >= 0 && dNum < 100000) {
            down=""+dNum;
        } else if (dNum >= 100000 ) {
            down=(dNum+"+");
        }
        return down;
    }
    public static void killApps(Context context) {

        ActivityManager activityManger = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi1 = new MemoryInfo();
        activityManger.getMemoryInfo(mi1);// 201613312
        List<ActivityManager.RunningAppProcessInfo> list = activityManger.getRunningAppProcesses();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RunningAppProcessInfo apinfo = list.get(i);

                System.out.println("pid            " + apinfo.pid);
                System.out.println("processName              " + apinfo.processName);
                System.out.println("importance            " + apinfo.importance);
                String[] pkgList = apinfo.pkgList;

                if (apinfo.importance >= ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                    for (int j = 0; j < pkgList.length; j++) {
                        // 2.2以上是过时的,请用killBackgroundProcesses代替
                        // activityManger.restartPackage(pkgList[j]);
                        activityManger.killBackgroundProcesses(pkgList[j]);
                    }
                }
            }
        }
        MemoryInfo mi2 = new MemoryInfo();
        activityManger.getMemoryInfo(mi2);
        long availMem = mi2.availMem - mi1.availMem;
        if (availMem < 0) {
            availMem = 0;
        }
    }

    public   static  String bytes2kb( long  bytes)  {
        BigDecimal filesize  =   new  BigDecimal(bytes);
        BigDecimal megabyte  =   new  BigDecimal( 1024 * 1024 );
        float  returnValue  =  filesize.divide(megabyte,  2 , BigDecimal.ROUND_UP).floatValue();
        if  (returnValue  >   1 )
            return (returnValue  +   "  MB " );
        BigDecimal kilobyte  =   new  BigDecimal( 1024 );
        returnValue  =  filesize.divide(kilobyte,  2 , BigDecimal.ROUND_UP).floatValue();
        return (returnValue  +   "  KB " );
    }

    /**防暴力点击**/
    private static long lastClickTime = 0;
    public static boolean isFastDoubleClick()
    {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 1000)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    public static boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the 
        // device 

        ActivityManager activityManager = (ActivityManager) BaseApplication.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = BaseApplication.getAppContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with. 
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
    /**
     *
     */

    public static boolean isApplicationBroughtToBackground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks != null && !tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            //  Debug.i(TAG, "topActivity:" + topActivity.flattenToString()); 
            //  Debug.f(TAG, "topActivity:" + topActivity.flattenToString()); 
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("null")
    public static boolean isBackgroundRunning(Context context) {
        // mContext = context.getApplicationContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<RunningTaskInfo> list = activityManager.getRunningTasks(100);
        if (list == null && list.size() == 0) return false;
        for (RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) || info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}