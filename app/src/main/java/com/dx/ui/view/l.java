// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.view;

import android.animation.ArgbEvaluator;

import com.dx.ui.utils.ResUtil;

import java.util.HashMap;
import java.util.Map;

public class l
{
    private static ClassK a;
    private static ClassK b;
    private static ClassK c;
    private static Map<String, ClassK> d = new HashMap<String, ClassK>();
    private static ArgbEvaluator e = new ArgbEvaluator();

    static {
        a();
    }
    
    public static int a(int i, final boolean b, final String s) {
        if (i < 0 || i > 100) {
            //NLog.e(l.absDashScanHelper, "\u9519\u8bef\uff1a\u503c\u5c0f\u4e8e0\u6216\u5927\u4e8e100", new Object[0]);
        }
        ClassK a;
        if (l.d.get(s) != null) {
            a = l.d.get(s);
        }
        else {
            a = l.a;
        }
        if (a.b.length != a.arrayFloat.length) {
           // NLog.e(l.absDashScanHelper, "\u9519\u8bef\uff1a\u989c\u8272\u6570\u7ec4\u548c\u53d8\u5316\u4f4d\u7f6e\u6570\u7ec4\u957f\u5ea6\u4e0d\u4e00\u6837", new Object[0]);
        }
        final float n = i / 100.0f;
        if (n > a.arrayFloat[a.arrayFloat.length - 1]) {
            i = a.b[a.arrayFloat.length - 1];
        }
        else {
            if (n > a.arrayFloat[0]) {
                i = 1;
                while (true) {
                    while (i < a.arrayFloat.length) {
                        if (n > a.arrayFloat[i - 1] && n <= a.arrayFloat[i]) {
                            --i;
                            if (!b) {
                                i = a.b[i];
                                return i;
                            }
                            i = (int)l.e.evaluate((n - a.arrayFloat[i]) / (a.arrayFloat[i + 1] - a.arrayFloat[i]), (Object)a.b[i], (Object)a.b[i + 1]);
                            return i;
                        }
                        else {
                            ++i;
                        }
                    }
                    i = 0;
                    continue;
                }
            }
            i = a.b[0];
        }
        return i;
    }
    
    public static int a(final long n, final boolean b, final String s) {
        if (n < 0L) {
            //NLog.e(l.absDashScanHelper, "\u9519\u8bef\uff1a\u503c\u5c0f\u4e8e0", new Object[0]);
        }
        ClassK b2;
        if (l.d.get(s) != null) {
            b2 = l.d.get(s);
        }
        else {
            b2 = l.b;
        }
        if (b2.b.length != b2.arrayLong.length) {
            //NLog.e(l.absDashScanHelper, "\u9519\u8bef\uff1a\u989c\u8272\u6570\u7ec4\u548c\u53d8\u5316\u4f4d\u7f6e\u6570\u7ec4\u957f\u5ea6\u4e0d\u4e00\u6837", new Object[0]);
        }
        int intValue;
        if (n > b2.arrayLong[b2.arrayLong.length - 1]) {
            intValue = b2.b[b2.arrayLong.length - 1];
        }
        else {
            if (n > b2.arrayLong[0]) {
                int i = 1;
                while (true) {
                    while (i < b2.arrayLong.length) {
                        if (n > b2.arrayLong[i - 1] && n <= b2.arrayLong[i]) {
                            --i;
                            if (!b) {
                                intValue = b2.b[i];
                                return intValue;
                            }
                            intValue = (int)l.e.evaluate((n - b2.arrayLong[i]) / (b2.arrayLong[i + 1] - b2.arrayLong[i]), (Object)b2.b[i], (Object)b2.b[i + 1]);
                            return intValue;
                        }
                        else {
                            ++i;
                        }
                    }
                    i = 0;
                    continue;
                }
            }
            intValue = b2.b[0];
        }
        return intValue;
    }
    
    public static ClassK a(final String s) {
        final ClassK k = l.d.get(s);
        ClassK i;
        if (k != null) {
            i = k;
        }
        else if (s.equals("2") || s.equals("4")) {
            i = l.a;
        }
        else {
            i = l.b;
        }
        return i;
    }
    
    private static void a() {
        final int b = ResUtil.getResColor(com.dx.ui.R.color.sk_gradient_1);
        final int b2 = ResUtil.getResColor(com.dx.ui.R.color.sk_gradient_2);
        final int b3 = ResUtil.getResColor(com.dx.ui.R.color.sk_gradient_3);
        final int[] array = { ResUtil.getResColor(com.dx.ui.R.color.sk_gradient_1_start), ResUtil.getResColor(com.dx.ui.R.color.sk_gradient_1_end) };
        final int[] array2 = { ResUtil.getResColor(com.dx.ui.R.color.sk_gradient_2_start), ResUtil.getResColor(com.dx.ui.R.color.sk_gradient_2_end) };
        final int[] array3 = { ResUtil.getResColor(com.dx.ui.R.color.sk_gradient_3_start), ResUtil.getResColor(com.dx.ui.R.color.sk_gradient_3_end) };
        final float[] array5;
        final float[] array4 = array5 = new float[5];
        array5[0] = 0.0f;
        array5[1] = 0.1f;
        array5[2] = 0.4f;
        array5[3] = 0.7f;
        array5[4] = 1.0f;
        l.a = ClassK.setFloat(new int[] { b, b2, b3 }, new int[][] { array, array2, array3 }, array4);
        l.b = ClassK.setLong(new int[] { b, b2, b3 }, new int[][] { array, array2, array3 }, new long[] { 0L, 20971520L, 83886080L, 209715200L, 1048576000L });
        l.c = ClassK.setFloat(new int[] { b, b, b }, new int[][] { array, array, array }, array4);
    }
    
    public static void a(int i, final int[] array, final String s) {
        if (i < 0 || i > 100) {
            //NLog.e(l.absDashScanHelper, "\u9519\u8bef\uff1a\u503c\u5c0f\u4e8e0\u6216\u5927\u4e8e100", new Object[0]);
        }
        ClassK a;
        if (l.d.get(s) != null) {
            a = l.d.get(s);
        }
        else {
            a = l.a;
        }
        if (a.b.length != a.arrayFloat.length) {
            //NLog.e(l.absDashScanHelper, "\u9519\u8bef\uff1a\u989c\u8272\u6570\u7ec4\u548c\u53d8\u5316\u4f4d\u7f6e\u6570\u7ec4\u957f\u5ea6\u4e0d\u4e00\u6837", new Object[0]);
        }
        final float n = i / 100.0f;
        if (n > a.arrayFloat[a.arrayFloat.length - 1]) {
            a(array, a.c[a.arrayFloat.length - 1]);
        }
        else {
            if (n > a.arrayFloat[0]) {
                while (true) {
                    for (i = 1; i < a.arrayFloat.length; ++i) {
                        if (n > a.arrayFloat[i - 1] && n <= a.arrayFloat[i]) {
                            --i;
                            a(array, a.c[i]);
                            return;
                        }
                    }
                    i = 0;
                    continue;
                }
            }
            a(array, a.c[0]);
        }
    }
    
    public static void a(final long n, final int[] array, final String s) {
        if (n < 0L) {
           // NLog.e(l.absDashScanHelper, "\u9519\u8bef\uff1a\u503c\u5c0f\u4e8e0", new Object[0]);
        }
        ClassK b;
        if (l.d.get(s) != null) {
            b = l.d.get(s);
        }
        else {
            b = l.b;
        }
        if (b.b.length != b.arrayLong.length) {
           // NLog.e(l.absDashScanHelper, "\u9519\u8bef\uff1a\u989c\u8272\u6570\u7ec4\u548c\u53d8\u5316\u4f4d\u7f6e\u6570\u7ec4\u957f\u5ea6\u4e0d\u4e00\u6837", new Object[0]);
        }
        if (n > b.arrayLong[b.arrayLong.length - 1]) {
            a(array, b.c[b.arrayLong.length - 1]);
        }
        else {
            if (n > b.arrayLong[0]) {
                while (true) {
                    for (int i = 1; i < b.arrayLong.length; ++i) {
                        if (n > b.arrayLong[i - 1] && n <= b.arrayLong[i]) {
                            --i;
                            a(array, b.c[i]);
                            return;
                        }
                    }
                    int i = 0;
                    continue;
                }
            }
            a(array, b.c[0]);
        }
    }
    
    private static void a(final int[] array, final int[] array2) {
        System.arraycopy(array2, 0, array, 0, array.length);
    }
}
