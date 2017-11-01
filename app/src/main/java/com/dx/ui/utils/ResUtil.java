// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.dx.ui.BaseApplication;

public class ResUtil
{
    public static int a(final float n) {
        final Context h = BaseApplication.getAppContext();
        int n2 = (int)(n + 0.5f);
        try {
            n2 = (int)(h.getResources().getDisplayMetrics().density * n + 0.5f);
            return n2;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return n2;
        }
    }
    
    public static final String a(final int n) {
        final Context h = BaseApplication.getAppContext();
        String string;
        if (h == null || h.getResources() == null) {
            string = "";
        }
        else {
            string = "";
            try {
                string = h.getResources().getString(n);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return string;
    }
    
    public static final String a(final int n, final Object... array) {
        final Context h =  BaseApplication.getAppContext();
        String string;
        if (h == null || h.getResources() == null) {
            string = "";
        }
        else {
            final String s = "";
            try {
                string = h.getResources().getString(n, array);
            }
            catch (Exception ex) {
                NLog.printStackTrace(ex);
                string = s;
            }
        }
        return string;
    }
    
    public static final int getResColor(int color) {
        final Context h = BaseApplication.getAppContext();
        final int n = 0;
        try {
            color = h.getResources().getColor(color);
            return color;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            color = n;
            return color;
        }
    }
    
    public static final ColorStateList c(final int n) {
        final Context h = BaseApplication.getAppContext();
        ColorStateList colorStateList = null;
        try {
            colorStateList = h.getResources().getColorStateList(n);
            return colorStateList;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return colorStateList;
        }
    }
    
    public static final Drawable d(final int n) {
        final Context h = BaseApplication.getAppContext();
        Drawable drawable = null;
        try {
            drawable = h.getResources().getDrawable(n);
            return drawable;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return drawable;
        }
    }
    
    public static int e(int dimensionPixelOffset) {
        final Context h = BaseApplication.getAppContext();
        final int n = 0;
        try {
            dimensionPixelOffset = h.getResources().getDimensionPixelOffset(dimensionPixelOffset);
            return dimensionPixelOffset;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            dimensionPixelOffset = n;
            return dimensionPixelOffset;
        }
    }
}
