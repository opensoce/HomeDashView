// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.dx.ui.utils.ResUtil;

import java.util.Map;

public class GradientLinearProgress extends View
{
    private static final int a;
    private static final int b;
    private static final String l;
    private Paint c;
    private Paint d;
    private int e;
    private int f;
    private RectF g;
    private RectF h;
    private boolean i;
    private ValueAnimator j;
    private long k;
    private int m;
    private int n;
    
    static {
        a = ResUtil.getResColor(com.dx.ui.R.color.sk_progress_bar_bg);
        b = ResUtil.getResColor(com.dx.ui.R.color.sk_progress_bar_fg);
        l = GradientLinearProgress.class.getSimpleName();
    }
    
    public GradientLinearProgress(final Context context) {
        this(context, null);
    }
    
    public GradientLinearProgress(final Context context, final AttributeSet set) {
        super(context, set);
        this.c = new Paint(1);
        this.d = new Paint(1);
        this.e = 0;
        this.i = false;
        this.k = 800L;
        this.c.setColor(GradientLinearProgress.a);
        this.c.setStyle(Style.STROKE);
        this.d.setColor(GradientLinearProgress.b);
        this.d.setStyle(Style.STROKE);
    }

    public int getMax() {
        return this.m;
    }

    public int getPercent() {
        return this.e;
    }

    public int getProgress() {
        return this.n;
    }

    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(this.g, this.c);
        canvas.drawRect(this.h = new RectF(0.0f, 0.0f, (float)(int)(this.f * this.getMeasuredWidth() / 100.0f), (float)this.getMeasuredHeight()), this.d);
    }

    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        super.onLayout(b, n, n2, n3, n4);
        this.c.setStrokeWidth((float)this.getMeasuredHeight());
        this.d.setStrokeWidth((float)this.getMeasuredHeight());
        this.g = new RectF(0.0f, 0.0f, (float)this.getMeasuredWidth(), (float)this.getMeasuredHeight());
    }

    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
    }

    public void setDuration(final long k) {
        this.k = k;
    }

    public void setGradient(final boolean i) {
        this.i = i;
    }

    public void setMax(final int m) {
        this.m = m;
    }

    public void setPercent(final int e) {
        if (this.e < e && e >= 0 && e <= 100) {
            this.e = e;
            if (this.j != null && this.j.isRunning()) {
                this.j.cancel();
            }
            (this.j = ValueAnimator.ofInt(new int[] { this.f, e })).setDuration(this.k);
            this.j.addUpdateListener((AnimatorUpdateListener)new m(this));
            this.j.start();
        }
    }
    
    public void setProgress(final int n) {
        if (this.m <= 0) {
            this.m = 0;
        }
        if (this.m < n) {
            this.m = n;
        }
        this.n = n;
        this.setPercent((int)(n * 100.0f / this.m));
    }
    
    public void setProgressColor(final String s) {
        this.d.setColor(Color.parseColor(s));
    }
    
    public void updateTheme(final Map<String, Object> map) {
        this.c.setColor((int)map.get("progress_bg_color"));
        this.d.setColor((int)map.get("progress_fg_color"));
        this.invalidate();
    }
    class m implements AnimatorUpdateListener
    {
        final /* synthetic */ GradientLinearProgress a;

        m(final GradientLinearProgress a) {
            this.a = a;
        }

        public void onAnimationUpdate(final ValueAnimator valueAnimator) {
            this.a.f = (int)valueAnimator.getAnimatedValue();
            this.a.postInvalidate();
        }
    }
}
