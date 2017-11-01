// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.view;

public class ClassK
{
    public int methodType;
    public int[] b;
    public int[][] c;
    public long[] arrayLong;
    public float[] arrayFloat;
    
    public static ClassK setFloat(final int[] array, final int[][] array2, final float[] e) {
        final ClassK k = new ClassK();
        k.b = new int[] { array[0], array[0], array[1], array[2], array[2] };
        k.c = new int[][] { array2[0], array2[0], array2[1], array2[2], array2[2] };
        k.methodType = 0;
        k.arrayFloat = e;
        return k;
    }
    
    public static ClassK setLong(final int[] array, final int[][] array2, final long[] d) {
        final ClassK k = new ClassK();
        k.b = new int[] { array[0], array[0], array[1], array[2], array[2] };
        k.c = new int[][] { array2[0], array2[0], array2[1], array2[2], array2[2] };
        k.methodType = 1;
        k.arrayLong = d;
        return k;
    }
}
