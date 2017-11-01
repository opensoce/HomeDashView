// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.utils;

import android.text.TextUtils;

import java.io.File;

public final class NLog
{
    private static final String LOGGING_PROPERTIES = "logging.properties";
    private static final String LOG_FILENAME = "tcl_logcat.log";
    private static boolean debug;
    private static Logger logger;
    
    static {
        NLog.debug = false;
        NLog.logger = null;
    }
    
    private static String buildWholeMessage(final String s, final Object... array) {
        String format = s;
        if (array != null) {
            if (array.length == 0) {
                format = s;
            }
            else {
                format = String.format(s, array);
            }
        }
        return format;
    }
    
    public static void d(final String s, final String s2, final Object... array) {
        if (NLog.debug) {
            NLog.logger.d(s, buildWholeMessage(s2, array));
        }
    }
    
    public static void e(final String s, final String s2, final Object... array) {
        if (NLog.debug) {
            NLog.logger.e(s, buildWholeMessage(s2, array));
        }
    }
    
    public static void e(final String s, final Throwable t) {
        if (NLog.debug) {
            NLog.logger.e(s, t);
        }
    }
    
    public static Logger getLogger() {
        return NLog.logger;
    }
    
    public static void i(final String s, final String s2, final Object... array) {
        if (NLog.debug) {
            NLog.logger.i(s, buildWholeMessage(s2, array));
        }
    }
    
    public static boolean init(final String s) {
        // monitorenter(NLog.class)
        // monitorexit(NLog.class)
        return false;
    }
    
    public static boolean isDebug() {
        return NLog.debug;
    }
    
    public static void printStackTrace(final Throwable t) {
        if (NLog.debug) {
            NLog.logger.e("TCLException", t);
        }
    }
    
    public static void setDebug(final boolean debug, final int level) {
        synchronized (NLog.class) {
            final boolean debug2 = NLog.debug;
            if (debug2 != debug) {
                if (debug2) {
                    trace(1, null);
                }
                NLog.debug = debug;
                if (debug) {
                    if (NLog.logger == null) {
                        NLog.logger = Logger.getLogger(null);
                    }
                    NLog.logger.setLevel(level);
                }
            }
        }
    }
    
    public static boolean trace(final int n, final String s) {
        synchronized (NLog.class) {
            if (!NLog.debug) {
                throw new IllegalStateException("you should enable log before modifing trace mode");
            }
        }
        if (NLog.logger == null) {
            NLog.logger = Logger.getLogger(null);
        }

            String string = null;

                final String s2="";
                if (n != 3) {
                    string = s2;
                    if (n == 2) {

                        if (TextUtils.isEmpty((CharSequence) s2)) {
                            throw new IllegalArgumentException("path should not be null for offline and all mode");
                        }
                        final File file = new File(s2);
                        if ((file.exists() && file.isDirectory()) || file.mkdirs()) {
                            final StringBuffer sb = new StringBuffer(s2);
                            sb.append(File.separator);
                            sb.append("tcl_logcat.log");
                            string = sb.toString();
                        }
                        return false;
                    }
                }
return true;
    }
    
    public static void v(final String s, final String s2, final Object... array) {
        if (NLog.debug) {
            NLog.logger.v(s, buildWholeMessage(s2, array));
        }
    }
    
    public static void w(final String s, final String s2, final Object... array) {
        if (NLog.debug) {
            NLog.logger.w(s, buildWholeMessage(s2, array));
        }
    }
}
