// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.utils;

import android.util.Log;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger
{
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int TRACE_ALL = 3;
    public static final int TRACE_OFFLINE = 2;
    public static final int TRACE_REALTIME = 1;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    int failCount;
    private int level;
    private String logFileName;
    OutputStreamWriter logWriter;
    private final Object mLogLock;
    protected int trace;
    
    private Logger(final String logFileName, final int level) {
        this.trace = 1;
        this.level = 4;
        this.mLogLock = new Object();
        this.logFileName = null;
        this.logWriter = null;
        this.failCount = 0;
        this.logFileName = logFileName;
        this.level = level;
    }
    
    private void closeLogStream() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: aload_0        
        //     1: getfield        com/tcl/framework/log/Logger.trace:I
        //     4: iconst_2       
        //     5: iand           
        //     6: ifeq            26
        //     9: aload_0        
        //    10: getfield        com/tcl/framework/log/Logger.logFileName:Ljava/lang/String;
        //    13: ifnull          26
        //    16: aload_0        
        //    17: getfield        com/tcl/framework/log/Logger.logFileName:Ljava/lang/String;
        //    20: invokevirtual   java/lang/String.length:()I
        //    23: ifne            27
        //    26: return         
        //    27: aload_0        
        //    28: getfield        com/tcl/framework/log/Logger.mLogLock:Ljava/lang/Object;
        //    31: astore_1       
        //    32: aload_1        
        //    33: monitorenter   
        //    34: aload_0        
        //    35: getfield        com/tcl/framework/log/Logger.logWriter:Ljava/io/OutputStreamWriter;
        //    38: astore_2       
        //    39: aload_2        
        //    40: ifnull          55
        //    43: aload_0        
        //    44: getfield        com/tcl/framework/log/Logger.logWriter:Ljava/io/OutputStreamWriter;
        //    47: invokevirtual   java/io/OutputStreamWriter.close:()V
        //    50: aload_0        
        //    51: aconst_null    
        //    52: putfield        com/tcl/framework/log/Logger.logWriter:Ljava/io/OutputStreamWriter;
        //    55: aload_1        
        //    56: monitorexit    
        //    57: goto            26
        //    60: astore_2       
        //    61: aload_1        
        //    62: monitorexit    
        //    63: aload_2        
        //    64: athrow         
        //    65: astore_2       
        //    66: goto            50
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  34     39     60     65     Any
        //  43     50     65     69     Ljava/io/IOException;
        //  43     50     60     65     Any
        //  50     55     60     65     Any
        //  55     57     60     65     Any
        //  61     63     60     65     Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0050:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static Logger getLogger(final String s) {
        return new Logger(s, 4);
    }
    
    private boolean openLogStream() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: iconst_1       
        //     1: istore_2       
        //     2: iload_2        
        //     3: istore_1       
        //     4: aload_0        
        //     5: getfield        com/tcl/framework/log/Logger.trace:I
        //     8: iconst_2       
        //     9: iand           
        //    10: ifeq            34
        //    13: iload_2        
        //    14: istore_1       
        //    15: aload_0        
        //    16: getfield        com/tcl/framework/log/Logger.logFileName:Ljava/lang/String;
        //    19: ifnull          34
        //    22: aload_0        
        //    23: getfield        com/tcl/framework/log/Logger.logFileName:Ljava/lang/String;
        //    26: invokevirtual   java/lang/String.length:()I
        //    29: ifne            36
        //    32: iload_2        
        //    33: istore_1       
        //    34: iload_1        
        //    35: ireturn        
        //    36: aload_0        
        //    37: getfield        com/tcl/framework/log/Logger.mLogLock:Ljava/lang/Object;
        //    40: astore_3       
        //    41: aload_3        
        //    42: monitorenter   
        //    43: new             Ljava/io/File;
        //    46: astore          5
        //    48: aload           5
        //    50: aload_0        
        //    51: getfield        com/tcl/framework/log/Logger.logFileName:Ljava/lang/String;
        //    54: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    57: aload           5
        //    59: invokevirtual   java/io/File.exists:()Z
        //    62: ifne            71
        //    65: aload           5
        //    67: invokevirtual   java/io/File.createNewFile:()Z
        //    70: pop            
        //    71: new             Ljava/io/FileWriter;
        //    74: astore          4
        //    76: aload           4
        //    78: aload           5
        //    80: iconst_1       
        //    81: invokespecial   java/io/FileWriter.<init>:(Ljava/io/File;Z)V
        //    84: aload_0        
        //    85: aload           4
        //    87: putfield        com/tcl/framework/log/Logger.logWriter:Ljava/io/OutputStreamWriter;
        //    90: aload_0        
        //    91: iconst_0       
        //    92: putfield        com/tcl/framework/log/Logger.failCount:I
        //    95: aload_3        
        //    96: monitorexit    
        //    97: iload_2        
        //    98: istore_1       
        //    99: goto            34
        //   102: astore          4
        //   104: aload_3        
        //   105: monitorexit    
        //   106: aload           4
        //   108: athrow         
        //   109: astore          4
        //   111: aload_3        
        //   112: monitorexit    
        //   113: iconst_0       
        //   114: istore_1       
        //   115: goto            34
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  43     57     102    109    Any
        //  57     71     109    118    Ljava/io/IOException;
        //  57     71     102    109    Any
        //  71     95     109    118    Ljava/io/IOException;
        //  71     95     102    109    Any
        //  95     97     102    109    Any
        //  104    106    102    109    Any
        //  111    113    102    109    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0071:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private int println(final int p0, final String p1, final String p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: iconst_m1      
        //     1: istore          4
        //     3: iload_1        
        //     4: aload_0        
        //     5: getfield        com/tcl/framework/log/Logger.level:I
        //     8: if_icmplt       26
        //    11: aload_0        
        //    12: getfield        com/tcl/framework/log/Logger.failCount:I
        //    15: iconst_5       
        //    16: if_icmpge       26
        //    19: aload_0        
        //    20: getfield        com/tcl/framework/log/Logger.logWriter:Ljava/io/OutputStreamWriter;
        //    23: ifnonnull       30
        //    26: iconst_0       
        //    27: istore_1       
        //    28: iload_1        
        //    29: ireturn        
        //    30: new             Ljava/text/SimpleDateFormat;
        //    33: dup            
        //    34: ldc             "[MM-dd HH:mm:ss.SSS]"
        //    36: invokespecial   java/text/SimpleDateFormat.<init>:(Ljava/lang/String;)V
        //    39: new             Ljava/util/Date;
        //    42: dup            
        //    43: invokespecial   java/util/Date.<init>:()V
        //    46: invokevirtual   java/text/SimpleDateFormat.format:(Ljava/util/Date;)Ljava/lang/String;
        //    49: astore          6
        //    51: new             Ljava/lang/StringBuilder;
        //    54: dup            
        //    55: invokespecial   java/lang/StringBuilder.<init>:()V
        //    58: astore          5
        //    60: aload           5
        //    62: aload           6
        //    64: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    67: pop            
        //    68: aload           5
        //    70: ldc             "\t"
        //    72: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    75: pop            
        //    76: aload           5
        //    78: bipush          8
        //    80: anewarray       Ljava/lang/String;
        //    83: dup            
        //    84: iconst_0       
        //    85: ldc             ""
        //    87: aastore        
        //    88: dup            
        //    89: iconst_1       
        //    90: ldc             ""
        //    92: aastore        
        //    93: dup            
        //    94: iconst_2       
        //    95: ldc             "V"
        //    97: aastore        
        //    98: dup            
        //    99: iconst_3       
        //   100: ldc             "D"
        //   102: aastore        
        //   103: dup            
        //   104: iconst_4       
        //   105: ldc             "I"
        //   107: aastore        
        //   108: dup            
        //   109: iconst_5       
        //   110: ldc             "W"
        //   112: aastore        
        //   113: dup            
        //   114: bipush          6
        //   116: ldc             "E"
        //   118: aastore        
        //   119: dup            
        //   120: bipush          7
        //   122: ldc             "A"
        //   124: aastore        
        //   125: iload_1        
        //   126: aaload         
        //   127: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   130: pop            
        //   131: aload           5
        //   133: ldc             "/"
        //   135: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   138: pop            
        //   139: aload           5
        //   141: aload_2        
        //   142: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   145: pop            
        //   146: invokestatic    android/os/Process.myPid:()I
        //   149: istore_1       
        //   150: aload           5
        //   152: ldc             "("
        //   154: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   157: pop            
        //   158: aload           5
        //   160: iload_1        
        //   161: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   164: pop            
        //   165: aload           5
        //   167: ldc             "):"
        //   169: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   172: pop            
        //   173: aload           5
        //   175: aload_3        
        //   176: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   179: pop            
        //   180: aload           5
        //   182: ldc             "\n"
        //   184: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   187: pop            
        //   188: aload_0        
        //   189: getfield        com/tcl/framework/log/Logger.mLogLock:Ljava/lang/Object;
        //   192: astore_2       
        //   193: aload_2        
        //   194: monitorenter   
        //   195: aload_0        
        //   196: getfield        com/tcl/framework/log/Logger.logWriter:Ljava/io/OutputStreamWriter;
        //   199: astore_3       
        //   200: aload_3        
        //   201: ifnull          217
        //   204: aload_3        
        //   205: aload           5
        //   207: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   210: invokevirtual   java/io/OutputStreamWriter.write:(Ljava/lang/String;)V
        //   213: aload_3        
        //   214: invokevirtual   java/io/OutputStreamWriter.flush:()V
        //   217: aload_0        
        //   218: getfield        com/tcl/framework/log/Logger.failCount:I
        //   221: iconst_5       
        //   222: if_icmplt       229
        //   225: aload_0        
        //   226: invokespecial   com/tcl/framework/log/Logger.closeLogStream:()V
        //   229: aload_2        
        //   230: monitorexit    
        //   231: iconst_0       
        //   232: istore_1       
        //   233: goto            28
        //   236: astore_3       
        //   237: aload_0        
        //   238: aload_0        
        //   239: getfield        com/tcl/framework/log/Logger.failCount:I
        //   242: iconst_1       
        //   243: iadd           
        //   244: putfield        com/tcl/framework/log/Logger.failCount:I
        //   247: aload_0        
        //   248: getfield        com/tcl/framework/log/Logger.failCount:I
        //   251: iconst_5       
        //   252: if_icmplt       259
        //   255: aload_0        
        //   256: invokespecial   com/tcl/framework/log/Logger.closeLogStream:()V
        //   259: aload_2        
        //   260: monitorexit    
        //   261: iload           4
        //   263: istore_1       
        //   264: goto            28
        //   267: astore_3       
        //   268: aload_2        
        //   269: monitorexit    
        //   270: aload_3        
        //   271: athrow         
        //   272: astore_3       
        //   273: aload_0        
        //   274: aload_0        
        //   275: getfield        com/tcl/framework/log/Logger.failCount:I
        //   278: iconst_1       
        //   279: iadd           
        //   280: putfield        com/tcl/framework/log/Logger.failCount:I
        //   283: aload_0        
        //   284: getfield        com/tcl/framework/log/Logger.failCount:I
        //   287: iconst_5       
        //   288: if_icmplt       295
        //   291: aload_0        
        //   292: invokespecial   com/tcl/framework/log/Logger.closeLogStream:()V
        //   295: aload_2        
        //   296: monitorexit    
        //   297: iload           4
        //   299: istore_1       
        //   300: goto            28
        //   303: astore_3       
        //   304: aload_0        
        //   305: getfield        com/tcl/framework/log/Logger.failCount:I
        //   308: iconst_5       
        //   309: if_icmplt       316
        //   312: aload_0        
        //   313: invokespecial   com/tcl/framework/log/Logger.closeLogStream:()V
        //   316: aload_3        
        //   317: athrow         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                           
        //  -----  -----  -----  -----  -------------------------------
        //  195    200    267    272    Any
        //  204    217    236    267    Ljava/io/FileNotFoundException;
        //  204    217    272    303    Ljava/io/IOException;
        //  204    217    303    318    Any
        //  217    229    267    272    Any
        //  229    231    267    272    Any
        //  237    247    303    318    Any
        //  247    259    267    272    Any
        //  259    261    267    272    Any
        //  268    270    267    272    Any
        //  273    283    303    318    Any
        //  283    295    267    272    Any
        //  295    297    267    272    Any
        //  304    316    267    272    Any
        //  316    318    267    272    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0217:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int d(final String s, final String s2) {
        final boolean b = false;
        int n = 0;
        if (3 >= this.level) {
            n = (b ? 1 : 0);
            if ((this.trace & 0x1) > 0) {
                n = Log.d(s, s2);
            }
            if ((this.trace & 0x2) > 0) {
                n = this.println(3, s, s2);
            }
        }
        return n;
    }
    
    public int d(final String s, final Throwable t) {
        int d;
        if (3 < this.level) {
            d = 0;
        }
        else {
            d = this.d(s, this.getStackTraceString(t));
        }
        return d;
    }
    
    public int e(final String s, final String s2) {
        final boolean b = false;
        int n = 0;
        if (6 >= this.level) {
            n = (b ? 1 : 0);
            if ((this.trace & 0x1) > 0) {
                n = Log.e(s, s2);
            }
            if ((this.trace & 0x2) > 0) {
                n = this.println(6, s, s2);
            }
        }
        return n;
    }
    
    public int e(final String s, final Throwable t) {
        int e;
        if (6 < this.level) {
            e = 0;
        }
        else {
            e = this.e(s, this.getStackTraceString(t));
        }
        return e;
    }
    
    public String getStackTraceString(final Throwable t) {
        String string;
        if (t == null) {
            string = "";
        }
        else {
            final StringWriter stringWriter = new StringWriter();
            t.printStackTrace(new PrintWriter(stringWriter));
            string = stringWriter.toString();
        }
        return string;
    }
    
    public int getTraceLevel() {
        return this.trace;
    }
    
    public int i(final String s, final String s2) {
        final boolean b = false;
        int n = 0;
        if (4 >= this.level) {
            n = (b ? 1 : 0);
            if ((this.trace & 0x1) > 0) {
                n = Log.i(s, s2);
            }
            if ((this.trace & 0x2) > 0) {
                n = this.println(4, s, s2);
            }
        }
        return n;
    }
    
    public int i(final String s, final Throwable t) {
        int i;
        if (4 < this.level) {
            i = 0;
        }
        else {
            i = this.i(s, this.getStackTraceString(t));
        }
        return i;
    }
    
    public void setLevel(final int level) {
        this.level = level;
    }
    
    protected boolean trace(final int trace, final String logFileName) {
        if (trace <= 0 || trace > 3) {
            throw new IllegalArgumentException("param traceLevel invalid");
        }
        if ((trace & 0x2) != 0x0 && (logFileName == null || logFileName.length() == 0)) {
            throw new IllegalArgumentException("offline trace level should with valid logPath");
        }
        this.closeLogStream();
        if ((trace & 0x2) != 0x0) {
            this.logFileName = logFileName;
        }
        this.trace = trace;
        return this.openLogStream();
    }
    
    public int v(final String s, final String s2) {
        final boolean b = false;
        int n = 0;
        if (2 >= this.level) {
            n = (b ? 1 : 0);
            if ((this.trace & 0x1) > 0) {
                n = Log.v(s, s2);
            }
            if ((this.trace & 0x2) > 0) {
                n = this.println(2, s, s2);
            }
        }
        return n;
    }
    
    public int v(final String s, final Throwable t) {
        int v;
        if (2 < this.level) {
            v = 0;
        }
        else {
            v = this.v(s, this.getStackTraceString(t));
        }
        return v;
    }
    
    public int w(final String s, final String s2) {
        final boolean b = false;
        int n = 0;
        if (5 >= this.level) {
            n = (b ? 1 : 0);
            if ((this.trace & 0x1) > 0) {
                n = Log.w(s, s2);
            }
            if ((this.trace & 0x2) > 0) {
                n = this.println(5, s, s2);
            }
        }
        return n;
    }
    
    public int w(final String s, final Throwable t) {
        int w;
        if (5 < this.level) {
            w = 0;
        }
        else {
            w = this.w(s, this.getStackTraceString(t));
        }
        return w;
    }
}
