// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.view;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.widget.TextView;

public class GradientTextView extends TextView
{
    private int a;
    private int b;
    private Shader c;
    
    public GradientTextView(final Context context) {
        super(context);
    }
    
    public GradientTextView(final Context context, final AttributeSet set) {
        super(context, set);
        this.c = this.getPaint().getShader();
    }
    
    public static void a(final GradientTextView gradientTextView, final long n, final String s, final int[] array) {
        if (gradientTextView == null) {
            return;
        }
        try {
            gradientTextView.setTextColor(l.a(n, false, "1"));
            l.a(n, array, "1");
            gradientTextView.a(s, array[0], array[1]);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void a() {
        this.getPaint().setShader(this.c);
    }
    
    public void a(final String text, final int a, final int b) {
        this.setText((CharSequence)text);
        if (this.a != a || this.b != b) {
            this.a = a;
            this.b = b;
            this.getPaint().setShader((Shader)new LinearGradient((float)(this.getLeft() + this.getWidth() / 2), (float)this.getTop(),
                    (float)(this.getLeft() + this.getWidth() / 2), (float)this.getBottom(), this.a, this.b, TileMode.CLAMP));
        }
    }
}
