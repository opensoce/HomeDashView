// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.dx.ui.R;

import java.util.Map;

public class CleanCompletionView extends View
{
    private static final int DEFAULT_CIRCLE_STROKE_WIDTH = 3;
    private static final int DEFAULT_TICK_STROKE_WIDTH = 8;
    private static final int DEFAULT_WAVE_STROKE_WIDTH = 1;
    private static final String TAG;
    private int centerX;
    private int centerY;
    private ValueAnimator circleAnimator;
    private boolean hasWave;
    int innerRadius;
    private int mBgColor;
    private Paint mBgPaint;
    private Paint mCirclePaint;
    private RectF mCircleRect;
    private int mCircleStrokeWidth;
    private int mFrameColor;
    Animator.AnimatorListener mListener;
    private int mSweepAngel;
    private ValueAnimator mTickAnimator;
    private int mTickColor;
    private Paint mTickPaint;
    private float mTickPercent;
    private int mTickStrokeWidth;
    private ValueAnimator mWaveAnimator;
    private int mWaveCircleGap;
    private int mWaveCircleStrokeWidth;
    private Paint mWavePaint;
    private float mWavePercent;
    AnimatorSet set;
    PathMeasure tickPathMeasure;
    private int CleanCompletionView[] = new int[] { R.attr.c, R.attr.d, R.attr.e, R.attr.f, R.attr.g, R.attr.h };
    
    static {
        TAG = CleanCompletionView.class.getSimpleName();
    }
    
    public CleanCompletionView(final Context context) {
        this(context, null);
    }
    
    public CleanCompletionView(final Context context, AttributeSet attr) {
            super(context, attr);
            this.set = new AnimatorSet();
            this.hasWave = false;
            //obtainStyledAttributes = obtainStyledAttributes(obtainStyledAttributes, CleanCompletionView);

                try {
                    this.mCircleStrokeWidth =3;// ((TypedArray)obtainStyledAttributes).getDimensionPixelOffset(3, j.a(context, 3.0f));
                    this.mWaveCircleStrokeWidth = 1;//((TypedArray)obtainStyledAttributes).getDimensionPixelOffset(2, j.a(context, 1.0f));
                    this.mTickStrokeWidth =8;// ((TypedArray)obtainStyledAttributes).getDimensionPixelOffset(4, j.a(context, 8.0f));
                    this.mWaveCircleGap =30;// this.getResources().getDimensionPixelOffset(2131361844);
                    this.mFrameColor = 0xff23477c;//((TypedArray)obtainStyledAttributes).getColor(0, aw.a(2131558633));
                    this.mTickColor = 0xff26a5f6;//((TypedArray)obtainStyledAttributes).getColor(1, aw.a(2131558634));
                    //((TypedArray)obtainStyledAttributes).recycle();
                    (this.mCirclePaint = new Paint(1)).setColor(this.mFrameColor);
                    this.mCirclePaint.setStyle(Paint.Style.STROKE);
                    this.mCirclePaint.setStrokeWidth((float)this.mCircleStrokeWidth);
                    (this.mBgPaint = new Paint(1)).setStyle(Paint.Style.FILL);
                    (this.mTickPaint = new Paint(1)).setColor(this.mTickColor);
                    this.mTickPaint.setStyle(Paint.Style.STROKE);
                    this.mTickPaint.setStrokeWidth((float)this.mTickStrokeWidth);
                    this.mTickPaint.setStrokeCap(Paint.Cap.ROUND);
                    this.mTickPaint.setStrokeJoin(Paint.Join.ROUND);
                    (this.mWavePaint = new Paint(1)).setColor(this.mFrameColor);
                    this.mWavePaint.setStyle(Paint.Style.STROKE);
                    this.mWavePaint.setStrokeWidth((float)this.mWaveCircleStrokeWidth);
                    (this.circleAnimator = ValueAnimator.ofInt(new int[] { 0, 360 })).setDuration(500L);
                    this.circleAnimator.setInterpolator((TimeInterpolator)new AccelerateDecelerateInterpolator());
                    this.circleAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new CleanCompletionView$1(this));
                    (this.mTickAnimator = ValueAnimator.ofFloat(new float[] { 0.0f, 1.0f })).setDuration(500L);
                    this.mTickAnimator.setInterpolator((TimeInterpolator)new AccelerateDecelerateInterpolator());
                    this.mTickAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new CleanCompletionView$2(this));
                    (this.mWaveAnimator = ValueAnimator.ofFloat(new float[] { 0.0f, 1.0f })).setDuration(1000L);
                    this.mWaveAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
                    this.mWaveAnimator.setRepeatCount(1);
                    this.mWaveAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new CleanCompletionView$3(this));
                    if (this.hasWave) {
                        this.set.playSequentially(new Animator[] { this.circleAnimator, this.mTickAnimator, this.mWaveAnimator });
                        this.set.addListener((Animator.AnimatorListener)new CleanCompletionView$4(this));
                        return;
                    }
                }
                finally {
                    //((TypedArray)obtainStyledAttributes).recycle();
                }
                this.set.playSequentially(new Animator[] { this.circleAnimator, this.mTickAnimator });

    }
    
    private void compute() {
        final int n = Math.min(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom()) / 2;
        this.centerX = this.getPaddingLeft() + n;
        this.centerY = this.getPaddingTop() + n;
        if (this.hasWave) {
            this.innerRadius = n - this.mWaveCircleGap - this.mCircleStrokeWidth / 2;
        }
        else {
            this.innerRadius = n - this.mCircleStrokeWidth / 2;
        }
        this.mCircleRect = new RectF((float)(this.centerX - this.innerRadius), (float)(this.centerY - this.innerRadius), (float)(this.centerX + this.innerRadius), (float)(this.centerY + this.innerRadius));
        final Path path = new Path();
        path.moveTo((float)(this.centerX - this.innerRadius / 2), (float)this.centerY);
        path.lineTo((float)(this.centerX - this.innerRadius / 8), (float)(this.centerY + this.innerRadius * 3 / 8));
        path.lineTo((float)(this.centerX + this.innerRadius / 2), (float)(this.centerY - this.innerRadius / 4));
        this.tickPathMeasure = new PathMeasure(path, false);
    }
    
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.set != null) {
            this.set.cancel();
        }
    }
    
    protected void onDraw(final Canvas canvas) {
        if (this.mCircleRect == null || this.tickPathMeasure == null) {
            //JLog.e(CleanCompletionView.TAG, "compute", new Object[0]);
            this.compute();
        }
        if (this.mBgColor != 0) {
            canvas.drawCircle((float)this.centerX, (float)this.centerY, (float)this.innerRadius, this.mBgPaint);
        }
        canvas.drawArc(this.mCircleRect, -90.0f, (float)this.mSweepAngel, false, this.mCirclePaint);
        final Path path = new Path();
        this.tickPathMeasure.getSegment(0.0f, this.mTickPercent * this.tickPathMeasure.getLength(), path, true);
        path.rLineTo(0.0f, 0.0f);
        canvas.drawPath(path, this.mTickPaint);
        if (this.hasWave && this.mWavePercent > 0.0f) {
            canvas.drawCircle((float)this.centerX, (float)this.centerY, this.innerRadius + this.mWavePercent * this.mWaveCircleGap - this.mWaveCircleStrokeWidth / 2, this.mWavePaint);
        }
    }
    
    protected void onMeasure(int n, int mode) {
        super.onMeasure(n, mode);
        final int size = MeasureSpec.getSize(n);
        final int size2 = MeasureSpec.getSize(mode);
        final int n2 = size - this.getPaddingLeft() - this.getPaddingRight();
        final int n3 = size2 - this.getPaddingTop() - this.getPaddingBottom();
        mode = MeasureSpec.getMode(mode);
        n = MeasureSpec.getMode(n);
        if (mode != 0 && n != 0) {
            if (n2 > n3) {
                n = n3;
            }
            else {
                n = n2;
            }
        }
        else {
            n = Math.max(n3, n2);
        }
        this.setMeasuredDimension(this.getPaddingLeft() + n + this.getPaddingRight(), n + this.getPaddingTop() + this.getPaddingBottom());
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.compute();
    }
    
    public void setBgColor(final int mBgColor) {
        this.mBgColor = mBgColor;
        this.mBgPaint.setColor(this.mBgColor);
        this.invalidate();
    }
    
    public void setComplete() {
        this.mTickPercent = 1.0f;
        this.mSweepAngel = 360;
        this.invalidate();
    }
    
    public void setHasWave(final boolean hasWave) {
        this.hasWave = hasWave;
        this.requestLayout();
    }
    
    public void startAnim(final Animator.AnimatorListener  animator_AnimatorListener) {
        this.startAnim(animator_AnimatorListener, 0);
    }
    
    public void startAnim(final Animator.AnimatorListener mListener, final int n) {
        this.mListener = mListener;
        if (n > 0) {
            this.postDelayed((Runnable)new CleanCompletionView$5(this), (long)n);
        }
        else {
            this.set.start();
        }
    }
    
    public void updateTheme(final Map<String, Object> map) {
        this.mFrameColor = (int)map.get("complete_view_frame");
        this.mTickColor = (int)map.get("complete_view_tick");
        this.mCirclePaint.setColor(this.mFrameColor);
        this.mTickPaint.setColor(this.mTickColor);
        this.mWavePaint.setColor(this.mFrameColor);
        this.invalidate();
    }
    class CleanCompletionView$5 implements Runnable
    {
        final /* synthetic */ CleanCompletionView this$0;

        CleanCompletionView$5(final CleanCompletionView this$0) {
            this.this$0 = this$0;
        }

        @Override
        public void run() {
            this.this$0.set.start();
        }
    }
    class CleanCompletionView$4 implements Animator.AnimatorListener
    {
        final /* synthetic */ CleanCompletionView this$0;

        CleanCompletionView$4(final CleanCompletionView this$0) {
            this.this$0 = this$0;
        }

        public void onAnimationCancel(final Animator animator) {
        }

        public void onAnimationEnd(final Animator animator) {
            this.this$0.mWavePercent = 0.0f;
            this.this$0.invalidate();
            if (this.this$0.mListener != null) {
                this.this$0.mListener.onAnimationEnd(animator);
            }
        }

        public void onAnimationRepeat(final Animator animator) {
        }

        public void onAnimationStart(final Animator animator) {
            this.this$0.mTickPercent = 0.0f;
        }
    }
    class CleanCompletionView$3 implements ValueAnimator.AnimatorUpdateListener
    {
        final /* synthetic */ CleanCompletionView this$0;

        CleanCompletionView$3(final CleanCompletionView this$0) {
            this.this$0 = this$0;
        }

        public void onAnimationUpdate(final ValueAnimator valueAnimator) {
            this.this$0.mWavePercent = (float)valueAnimator.getAnimatedValue();
            this.this$0.invalidate();
        }
    }
    class CleanCompletionView$2 implements ValueAnimator.AnimatorUpdateListener
    {
        final /* synthetic */ CleanCompletionView this$0;

        CleanCompletionView$2(final CleanCompletionView this$0) {
            this.this$0 = this$0;
        }

        public void onAnimationUpdate(final ValueAnimator valueAnimator) {
            this.this$0.mTickPercent = (float)valueAnimator.getAnimatedValue();
            this.this$0.invalidate();
        }
    }
    class CleanCompletionView$1 implements ValueAnimator.AnimatorUpdateListener
    {
        final /* synthetic */ CleanCompletionView this$0;

        CleanCompletionView$1(final CleanCompletionView this$0) {
            this.this$0 = this$0;
        }

        public void onAnimationUpdate(final ValueAnimator valueAnimator) {
            this.this$0.mSweepAngel = (int)valueAnimator.getAnimatedValue();
            this.this$0.invalidate();
        }
    }
}
