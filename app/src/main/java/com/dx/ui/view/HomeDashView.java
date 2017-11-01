package com.dx.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import com.dx.ui.R;
import com.dx.ui.utils.DensityUtils;
import com.dx.ui.utils.ResUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomeDashView extends View
{
    private static final String TAG = HomeDashView.class.getSimpleName();
    private ValueAnimator valueanimatora;
    private int shaderB;
    private int shaderC;
    private int sahderD;
    private int bottomLineStrokeColor;
    private AtomicBoolean atomicboolf = new AtomicBoolean(false);
    private Runnable runnableg = new Runnable() {
            @Override
            public void run() {
                //涟漪动画，中心向外扩散
                final Iterator<ValueAnimatorExt> iterator = ValueAnimatorExt.iterator();
                while (iterator.hasNext()) {
                    iterator.next().start();
                }
                postDelayed(this, 5000L);
            }
        };
    private float H = 1.0F;
    private InterfaceU interfaceI;
    private boolean isNativeAnimFinished = false;
    private float dashScreenCenterX;
    private float dashScreenCenterY;
    private float dashScreenRadius;
    private float N;
    private float radiusLineLong;
    private RectF rectfp;
    private RectF rectfq;
    private int intR;
    private int S;
    private int mDashProgress = 0;
    private int mBottomProgress = 0;
    private Path pathTopLine;
    private Path pathw;
    float angleDashStart = 136.0F;
    float angleDashStroke = 267.0F;
    float c = angleDashStroke / 360.0F;
    float d = 1.0F;
    List<ValueAnimatorExt> ValueAnimatorExt = new ArrayList();
    AbsDashScanHelper absDashScanHelper;
    private Paint paintDashTick = new Paint(1);
    private Paint paintDashTickStroke = new Paint(1);
    private Paint paintDashLine = new Paint(1);
    private Paint paintDashLineStroke = new Paint(1);
    private Paint paintDashLineTick = new Paint(1);
    private Paint paintDashPaint = new Paint(1);

    private Paint paintBottomBody = new Paint(1);
    private Paint paintBottomLine = new Paint(1);
    private Paint paintBottomLineStroke = new Paint(1);
    private Paint paintBottomPoint = new Paint(1);

    private PathEffect patheffecti = new DashPathEffect(new float[] { 3.0F, 6.0F, 3.0F, 6.0F }, 0.0F);

    private int dashTickWidth;
    private int dashTickStrokeWidth;
    private int dashLineWidth;
    private int dashLineStrokeWidth;
    private int bottomBodyWidth;
    private int bottomLineWidth;
    private int bottomLineStrokeWidth;

    private int n;
    private int dashPointRadius;
    private int bottomPointRadius;
    private int lengthLineTick;
    private int distanceTickLine;

    private int dashTickColor;
    private int dashTickStrokeColor;
    private int dashLineColor;
    private int dashLineStrokeColor;
    private int dashPointColor;
    private int bottomLineColor;
    private int bottomBodyColor;
    private int bottomPointColor;

    private boolean mAnimEnable; // 是否播放动画
    private boolean isAnimFinished = true; // 动画是否播放完毕
    private long mDuration = 600; // 动画默认时长


    public HomeDashView(final Context context) {
        this(context, null, 0);
    }
    public HomeDashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0 );
    }
    public HomeDashView(Context context, AttributeSet set, int defStyleAttr) {
        super(context, set, defStyleAttr);
        initVars(context, set, defStyleAttr);
        paintDashTick.setStyle(Paint.Style.STROKE);
        paintDashTick.setStrokeWidth(dashTickWidth);
        paintDashTick.setPathEffect(patheffecti);
        paintDashTick.setColor(dashTickColor);

        paintDashTickStroke.setStyle(Paint.Style.STROKE);
        paintDashTickStroke.setStrokeWidth(dashTickStrokeWidth);
        paintDashTickStroke.setPathEffect(patheffecti);
        paintDashTickStroke.setColor(dashTickStrokeColor);

        paintDashLine.setStyle(Paint.Style.STROKE);
        paintDashLine.setStrokeWidth(dashLineWidth);
        paintDashLine.setStrokeCap(Paint.Cap.ROUND);
        paintDashLine.setStrokeJoin(Paint.Join.ROUND);
        paintDashLine.setColor(dashLineColor);

        paintDashLineStroke.setStyle(Paint.Style.STROKE);
        paintDashLineStroke.setStrokeWidth(dashLineStrokeWidth);
        paintDashLineStroke.setColor(dashLineStrokeColor);

        paintDashPaint.setStyle(Paint.Style.FILL);
        paintDashPaint.setStrokeWidth(dashPointRadius);
        paintDashPaint.setColor(dashPointColor);

        paintDashLineTick.setStyle(Paint.Style.STROKE);
        paintDashLineTick.setStrokeWidth( dashLineWidth);if (isInEditMode()) { return; }
        paintDashLineTick.setColor(dashLineColor);

        paintBottomBody.setStyle(Paint.Style.STROKE);
        paintBottomBody.setStrokeWidth(bottomBodyWidth);
        paintBottomBody.setColor(bottomBodyColor);

        paintBottomLine.setStyle(Paint.Style.STROKE);
        paintBottomLine.setStrokeWidth(bottomLineWidth);
        paintBottomLine.setStrokeCap(Paint.Cap.ROUND);
        paintBottomLine.setColor(bottomLineColor);

        paintBottomLineStroke.setStyle(Paint.Style.STROKE);
        paintBottomLineStroke.setStrokeWidth(bottomLineStrokeWidth);
        paintBottomLineStroke.setStrokeCap(Paint.Cap.ROUND);
        paintBottomLineStroke.setColor(bottomLineStrokeColor);

        paintBottomPoint.setStyle(Paint.Style.FILL);
        paintBottomPoint.setStrokeWidth(bottomPointRadius);
        paintBottomPoint.setColor(bottomPointColor);
    }

    private void initVars(final Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.HomeDashView, defStyleAttr, 0);
        try {
            lengthLineTick = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_lengthLineTick, DensityUtils.dp2px(context, 10.0f));
            distanceTickLine = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_distanceTickLine, DensityUtils.dp2px(context, 7.0f));

            dashTickWidth = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_dashTickWidth, DensityUtils.dp2px(context, 20.0f));
            dashTickColor = typeArray.getColor(R.styleable.HomeDashView_dashTickColor, ResUtil.getResColor(R.color.sk_blackholeview_color7));
            dashTickStrokeWidth = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_dashTickStrokeWidth, dashTickWidth);
            dashTickStrokeColor = typeArray.getColor(R.styleable.HomeDashView_dashTickStrokeColor, ResUtil.getResColor(R.color.sk_dash_circle_bg));

            dashLineWidth = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_dashLineWidth, DensityUtils.dp2px(context, 1.0f));
            dashLineColor = typeArray.getColor(R.styleable.HomeDashView_dashLineColor, ResUtil.getResColor(R.color.sk_blackholeview_color7));
            dashLineStrokeWidth = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_dashLineWidth, dashLineWidth);
            dashLineStrokeColor = typeArray.getColor(R.styleable.HomeDashView_dashLineStrokeColor, ResUtil.getResColor(R.color.sk_dash_circle_bg));
            dashPointRadius = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_dashPointRadius, DensityUtils.dp2px(context, 4.0f));
            dashPointColor = typeArray.getColor(R.styleable.HomeDashView_dashPointColor, ResUtil.getResColor(R.color.sk_dash_circle_bg));

            bottomBodyWidth = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_bottomBodyWidth, DensityUtils.dp2px(context, 20.0f));
            bottomBodyColor = typeArray.getColor(R.styleable.HomeDashView_bottomBodyColor, ResUtil.getResColor(R.color.sk_blackholeview_color7));
            bottomLineWidth = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_bottomLineWidth, DensityUtils.dp2px(context, 1.0f));
            bottomLineColor = typeArray.getColor(R.styleable.HomeDashView_bottomLineStrokeColor, ResUtil.getResColor(R.color.sk_perm_bottom_color));
            bottomLineStrokeWidth = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_bottomLineStrokeWidth, bottomLineWidth);
            bottomLineStrokeColor = typeArray.getColor(R.styleable.HomeDashView_bottomLineStrokeColor, ResUtil.getResColor(R.color.sk_dash_circle_bg));
            bottomPointRadius = typeArray.getDimensionPixelOffset(R.styleable.HomeDashView_bottomPointRadius, DensityUtils.dp2px(context, 4.0f));
            bottomPointColor = typeArray.getColor(R.styleable.HomeDashView_bottomPointColor, ResUtil.getResColor(R.color.sk_dash_circle_bg));

            n = DensityUtils.dp2px(context, 2.0f);
            shaderB = typeArray.getColor(R.styleable.HomeDashView_shaderBColor, ResUtil.getResColor(R.color.sk_gradient_1));
            shaderC = typeArray.getColor(R.styleable.HomeDashView_shaderCColor, ResUtil.getResColor(R.color.sk_gradient_1));
            sahderD = typeArray.getColor(R.styleable.HomeDashView_shaderDColor, ResUtil.getResColor(R.color.sk_gradient_1));

            (absDashScanHelper = new AbsDashScanHelper() {
                    @Override
                    protected void gotDatas(final boolean b, final int... array) {
                        if (mDashProgress != array[0] || mBottomProgress != array[1]) {
                            mDashProgress = array[0];
                            mBottomProgress = array[1];
                            postInvalidate();
                            if (interfaceI != null) {
                                interfaceI.onAnimUpdate(mDashProgress, mBottomProgress);
                            }
                        }
                    }
                    @Override
                    protected void onUpdate() {
                    }
                }).initKeys("sd", "mem");
            (valueanimatora = ValueAnimator.ofFloat(new float[] { 0.0f, 1.0f }).setDuration(500L)).setRepeatCount(-1);
            valueanimatora.setRepeatMode(ValueAnimator.REVERSE);
            valueanimatora.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                    H = valueAnimator.getAnimatedFraction();
                    postInvalidate();
                }
            });
        }
        finally {
            typeArray.recycle();
        }
    }
    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }*/

    private ValueAnimatorExt getAnimation(final float radiusMin, final float radiusMax, final float alphaTargetRadio, final int delay) {
        final ValueAnimatorExt animator = new ValueAnimatorExt(dashScreenCenterX, dashScreenCenterY, radiusMin, radiusMax, alphaTargetRadio, delay);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isNativeAnimFinished = true;
            }
        });
        return animator;
    }
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(rectfp, 130.0f, -80.0f, false, paintBottomBody);
        canvas.drawArc(rectfp, 125.0f, -70.0f, false, paintBottomLine);
        canvas.drawArc(rectfp, 125.0f, mBottomProgress / 100.0f * -70.0f, false, paintBottomLineStroke);//底部线进度
        final float angleBottomEnd = 125.0f + mBottomProgress / 100.0f * -70.0f;
        canvas.drawCircle((float)(N * Math.cos(angleBottomEnd / 180.0f * 3.141592653589793)) + dashScreenCenterX,
                (float)(N * Math.sin(angleBottomEnd / 180.0f * 3.141592653589793)) + dashScreenCenterY, bottomPointRadius, paintBottomPoint);//下圈进度点

        canvas.drawArc(rectfp, angleDashStart, angleDashStroke, false, paintDashTick);
        canvas.drawArc(rectfp, angleDashStart, angleDashStroke * (mDashProgress / 100.0f), false, paintDashTickStroke);//Dash刻度进度
        canvas.drawPath(pathTopLine, paintDashLine);//上圈内圈
        canvas.drawArc(rectfq, angleDashStart, angleDashStroke * (mDashProgress / 100.0f), false, paintDashLineStroke);//Dash线进度
        canvas.drawPath(pathw, paintDashLineTick);//Dash线刻度
        final float angleDashEnd = angleDashStart + mDashProgress / 100.0f * angleDashStroke;
        final float dashCenterX = (float)(radiusLineLong * Math.cos(angleDashEnd / 180.0f * 3.141592653589793)) + dashScreenCenterX;
        final float dashCenterY = (float)(radiusLineLong * Math.sin(angleDashEnd / 180.0f * 3.141592653589793)) + dashScreenCenterY;
        paintDashPaint.setAlpha(255);
        canvas.drawCircle(dashCenterX, dashCenterY, dashPointRadius *0.6f, paintDashPaint);//Dash进度点内圈
        paintDashPaint.setAlpha(76);
        canvas.drawCircle(dashCenterX, dashCenterY, dashPointRadius, paintDashPaint);//Dash进度点外圈
        canvas.save();
        final Iterator<ValueAnimatorExt> iterator = ValueAnimatorExt.iterator();
        while (iterator.hasNext()) {
            iterator.next().drawCircle(canvas);
        }
        canvas.restore();
    }

    private void drawArc() {
        dashScreenCenterX = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
        dashScreenCenterY = (getMeasuredHeight() - getPaddingBottom() - getPaddingTop()) / 2 + getPaddingTop();
        dashScreenRadius = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom() - getPaddingTop()) / 2.0f;
        N = dashScreenRadius - bottomBodyWidth / 2;
        rectfp = new RectF(dashScreenCenterX - N, dashScreenCenterY - N, dashScreenCenterX + N, dashScreenCenterY + N);
        //setShader();
        pathTopLine = new Path();
        pathw = new Path();
        final float radiusLineShort = dashScreenRadius - bottomBodyWidth - distanceTickLine - lengthLineTick;//Dash线内圈
        final float radiusLineShortStartX = (float)(radiusLineShort * Math.cos(angleDashStart / 180.0f * 3.141592653589793) + dashScreenCenterX);
        final float radiusLineShortStartY = (float)(radiusLineShort * Math.sin(angleDashStart / 180.0f * 3.141592653589793) + dashScreenCenterY);
        pathTopLine.moveTo(radiusLineShortStartX, radiusLineShortStartY);
        pathw.moveTo(radiusLineShortStartX, radiusLineShortStartY);
        radiusLineLong = dashScreenRadius - bottomBodyWidth - distanceTickLine;//Dash线外圈
        final float radiusLineLongStartX = (float)(radiusLineLong * Math.cos(angleDashStart / 180.0f * 3.141592653589793) + dashScreenCenterX);
        final float radiusLineLongStartY = (float)(radiusLineLong * Math.sin(angleDashStart / 180.0f * 3.141592653589793) + dashScreenCenterY);
        pathTopLine.lineTo(radiusLineLongStartX, radiusLineLongStartY);
        pathw.lineTo(radiusLineLongStartX, radiusLineLongStartY);
        rectfq = new RectF(dashScreenCenterX - radiusLineLong, dashScreenCenterY - radiusLineLong, dashScreenCenterX + radiusLineLong, dashScreenCenterY + radiusLineLong);
        pathTopLine.addArc(rectfq, angleDashStart, angleDashStroke);
        pathTopLine.lineTo((float)(radiusLineShort * Math.cos((angleDashStart + angleDashStroke) / 180.0f * 3.141592653589793) + dashScreenCenterX),
                (float)(radiusLineShort * Math.sin((angleDashStart + angleDashStroke) / 180.0f * 3.141592653589793) + dashScreenCenterY));
        ValueAnimatorExt.clear();
        ValueAnimatorExt.add(getAnimation(0.0f, Math.min(radiusLineShort, getMeasuredHeight() / 2), 0.2f, 0));
    }

    public void resume() {
        if (atomicboolf.compareAndSet(false, true)) {
            postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    if (atomicboolf.get()) {
                        runnableg.run();
                    }
                }
            }, 500L);
        }
    }

    public void setPercentData(final int sizeSD, final int sizeStorage, final long animDuration) {
        if (sizeSD < 0 || sizeSD > 100 || sizeStorage < 0 || sizeStorage > 100) {
            //NLog.e(HomeDashView.TAG, "percent must lese than 100 and bigger than 0", new Object[0]);
        }
        else {
            if (absDashScanHelper.isAniming()) {
                absDashScanHelper.setData("sd", sizeSD);
                absDashScanHelper.setData("mem", sizeStorage);
            }
            else {
                absDashScanHelper.setData("sd", sizeSD);
                absDashScanHelper.setData("mem", sizeStorage);
                absDashScanHelper.startAnim(animDuration);
            }
            S = sizeStorage;
            intR = sizeSD;
        }
    }

    public void clean() {
        if (atomicboolf.compareAndSet(true, false)) {
            final Iterator<ValueAnimatorExt> iterator = ValueAnimatorExt.iterator();
            while (iterator.hasNext()) {
                iterator.next().cancel();
            }
            removeCallbacks(runnableg);
        }
    }

    public void setShader() {
        int i = 0;
        synchronized (this) {
            final ClassK a = com.dx.ui.view.l.a("2");
            final int b = shaderB;
            final int b2 = shaderB;
            final int c = shaderC;
            final int d = sahderD;
            final int d2 = sahderD;
            final int e = bottomLineStrokeColor;
            final float[] array = new float[a.arrayFloat.length + 1];
            while (i < a.arrayFloat.length) {
                array[i] = a.arrayFloat[i] * c;
                ++i;
            }
            array[array.length - 1] = 1.0f;
            Shader shaderj = new SweepGradient(dashScreenCenterX, dashScreenCenterY, new int[] { b, b2, c, d, d2, e }, array);
            final Matrix localMatrix = new Matrix();
            localMatrix.setRotate(angleDashStart, dashScreenCenterX, dashScreenCenterY);
            shaderj.setLocalMatrix(localMatrix);
            paintDashTickStroke.setShader(shaderj);
            paintDashLineTick.setShader(shaderj);
            paintDashPaint.setShader(shaderj);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isNativeAnimFinished = true;
        HomeDash_e.removeValueAnimator(valueanimatora);
        final Iterator<ValueAnimatorExt> iterator = ValueAnimatorExt.iterator();
        while (iterator.hasNext()) {
            HomeDash_e.removeValueAnimator(iterator.next());
        }
        ValueAnimatorExt.clear();
        removeCallbacks(runnableg);
    }


//onLayout(boolean changed, int left, int top, int right, int bottom)
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        drawArc();
    }
    
    public void setRealTimeValue(float valueDash, float valueBottom) {
        if (!mAnimEnable) {
            invalidate();
        } else {
            if (isAnimFinished) {
                isAnimFinished = false;
                //playAnimation(valueDash, valueBottom);
            }
        }
    }

    public void setRealTimeValue(float valueDash, float valueBottom, boolean animEnable) {
        mAnimEnable = animEnable;
        setRealTimeValue(valueDash,  valueBottom);
    }

    public void setRealTimeValue(float valueDash, float valueBottom, boolean animEnable, long duration) {
        this.mDuration = duration;

        setRealTimeValue(valueDash,  valueBottom, animEnable);
    }

    /*private void playAnimation(float valueDash,float valueBottom) {
        ValueAnimator animator = ValueAnimator.ofFloat(mValueDash,  mValueBottom, valueDash, valueBottom);
        animator.setDuration(mDuration);
        animator.setInterpolator(new PointerBounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //mRealTimeValue = Math.ceil((float) animation.getAnimatedValue());
                *//*mRealTimeValue = (float)animation.getAnimatedValue();
                if (mRealTimeValue < mMinValue) {
                    mRealTimeValue = mMinValue;
                }
                if (mRealTimeValue > mMaxValue) {
                    mRealTimeValue = mMaxValue;
                }
                initAngle = getAngleFromResult(mRealTimeValue);*//*
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimFinished = true;
            }
        });
        animator.start();
    }*/

    public class PointerBounceInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input)
                    * Math.sin((input - 0.4f / 4)
                    * (2 * Math.PI) / 0.4f) + 1);
        }
    }

    public void setValueCallback(InterfaceU i) {
        interfaceI = i;
    }

    public class ValueAnimatorExt extends ValueAnimator
    {
        Paint paint;
        private float radiusMin;
        private float radiusMax;
        private float centerx;
        private float centery;
        private int alphaTarget;
        private int mStartDelay;

        public ValueAnimatorExt(final float centerX, final float centerY, final float radiusMin, final float radiusMax, final float alphaTargetRadio, final int delay) {
            this.paint = new Paint(1);
            this.centerx = centerX;
            this.centery = centerY;
            this.mStartDelay = delay;
            this.radiusMin = radiusMin;
            this.radiusMax = radiusMax;
            this.alphaTarget = (int)(255.0f * alphaTargetRadio);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setColor(ResUtil.getResColor(R.color.sk_blue_bg));
            this.setDuration(2000L);
            this.setFloatValues(new float[] { 0.0f, 1.0f });
            this.setStartDelay((long)delay);
        }

        public void drawCircle(final Canvas canvas) {
            if (this.isRunning()) {
                final float animatedFraction = getAnimatedFraction();//当前进度
                final float radiusCurrent = this.radiusMin + (this.radiusMax - this.radiusMin) * animatedFraction;
                final float radiusTo = (this.radiusMin + radiusCurrent) / 2.0f;
                final float radiusMin = this.radiusMin;
                this.paint.setAlpha((int)((1.0f - animatedFraction) * this.alphaTarget));
                this.paint.setStrokeWidth(radiusCurrent - radiusMin);
                canvas.drawCircle(this.centerx, this.centery, radiusTo, this.paint);
            }
        }
    }

    public class v
    {
        float a;
        float b;
        float c;
        int d;

        public v(final float a, final float b, final float c, final int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }
}
