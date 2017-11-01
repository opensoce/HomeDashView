// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DensityUtils
{
    public static Float a;
    public static Float b;
    
    public static float getWidthDPI(final Context context) {
        if (a == null) {
            a = getWidth(context) * 2.0f / (getDPI(context) * 720.0f);
        }
        return a;
    }
    
    private static float doConvert(final Context context, final int method, float px, final DisplayMetrics displayMetrics) {
        switch (method) {
            default: {
                px = 0.0f;
                break;
            }
            case TypedValue.COMPLEX_UNIT_DIP:
            case TypedValue.COMPLEX_UNIT_SP: {
                px = TypedValue.applyDimension(method, px, displayMetrics);
                break;
            }
            case 6: {//px2dp
                px /= displayMetrics.density;
                break;
            }
            case 7: {//px2sp
                px /= displayMetrics.scaledDensity;
                break;
            }
            case 8: {//dp2px
                px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getDPI(context) * px, displayMetrics);
                break;
            }
            case 9: {
                px *= getDPI(context);
                break;
            }
            case 10: {//dp2px
                px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getWidthDPI(context) * px, displayMetrics);
                break;
            }
        }
        return px;
    }
    
    public static int dp2px(final Context context, final float n) {
        return (int)doConvert(context, TypedValue.COMPLEX_UNIT_DIP, n, context.getResources().getDisplayMetrics());
    }
    
    public static float getDPI(final Context context) {
        if (b == null) {
            b = getHeight(context) * 2.0f / (getDensity(context) * 1280.0f);
        }
        return b;
    }
    
    public static int sp2px(final Context context, final float n) {
        return (int)doConvert(context, TypedValue.COMPLEX_UNIT_SP, n, context.getResources().getDisplayMetrics());
    }

    public static int px2sp(final Context context, final float n) {
        return (int)doConvert(context, 7, n, context.getResources().getDisplayMetrics());
    }
    
    public static int getWidth(final Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeight(final Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    
    public static float getDensity(final Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static double format(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        final long n3 = (long)Math.pow(10.0, n2);
        double a = Math.round(n3 * n) ;
        double c = a / n3;
        return c;
    }
}
