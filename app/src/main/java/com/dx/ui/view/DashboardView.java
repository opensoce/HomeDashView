package com.dx.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;

import com.dx.ui.R;

import java.util.List;

/**
 * 自定义仪表盘View，仿汽车速度仪、刻度盘等，可自定义多种模式
 * GitHub: https://github.com/woxingxiao/DashboardView
 */
public class DashboardView extends View {

    private int mRadius; // 圆弧半径
    private int mStartAngle; // 起始角度
    private int mSweepAngle; // 绘制角度
    private int mBigSliceCount; // 大份数
    private int mSliceCountInOneBigSlice; // 划分一大份长的小份数
    private int mArcColor; // 弧度颜色
    private int mMeasureTextSize; // 刻度字体大小
    private int mTextColor; // 字体颜色
    private int mTextColorOfValue; // 读数字体颜色
    private int mTextSizeOfValue; // 读数字体大小
    private int mPointerColor; // 指针颜色
    private String mHeaderTitle = ""; // 表头
    private int mHeaderTextSize; // 表头字体大小
    private int mHeaderRadius; // 表头半径
    private int mPointerRadius; // 指针半径
    private int mCenterCircleRadius; // 中心圆盘半径
    private int mCenterCircleColor; // 中心圆盘背景色
    private int mCircleRadius; // 中心圆半径
    private int mMinValue; // 最小值
    private int mMaxValue; // 最大值
    private float mRealTimeValue; // 实时值
    private int mShieldRadius; // 遮蔽半径
    private int mStripeWidth; // 色条宽度
    private StripeMode mStripeMode = StripeMode.NORMAL;
    private TextStyle mTextStyle = TextStyle.NORMAL;
    private int mBigSliceRadius; // 较长刻度半径
    private int mSmallSliceRadius; // 较短刻度半径
    private int mWidthOfSlice; // 进度刻度盘宽度
    private int mNumMeaRadius; // 数字刻度半径
    private int mModeType;
    private int mModeStyle;
    private List<HighlightCR> mStripeHighlight; // 高亮范围颜色对象的集合
    private int mBgColor; // 背景色

    private int mViewWidth; // 控件宽度
    private int mViewHeight; // 控件高度
    private float mCenterX;
    private float mCenterY;

    private Paint mPaintArc;
    private Paint mPaintArcSubline;
    private Paint mPaintArcProgress;
    private Paint mPaintText;
    private Paint mPaintPointer;
    private Paint mPaintCenterCircle;
    private Paint mPaintValue;
    private Paint mPaintStripe;

    private RectF mRectArc;
    private RectF mRectStripe;
    private Rect mRectMeasures;
    private Rect mRectHeader;
    //private Rect mRectRealText;
    private Path mPath;

    private int mSmallSliceCount; // 短刻度个数
    private float mBigSliceAngle; // 大刻度等分角度
    private float mSmallSliceAngle; // 小刻度等分角度

    private String[] mGraduations; // 等分的刻度值
    private float initAngle;
    private boolean textColorFlag = true; // 若不单独设置文字颜色，则文字和圆弧同色
    private boolean mAnimEnable; // 是否播放动画
    private boolean isAnimFinished = true; // 动画是否播放完毕
    private long mDuration = 600; // 动画默认时长

    public DashboardView(Context context) {
        this(context, null);
    }

    public DashboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DashboardView, defStyleAttr, 0);

        mRadius = a.getDimensionPixelSize(R.styleable.DashboardView_radius, dpToPx(80));
        mBigSliceRadius = a.getDimensionPixelSize(R.styleable.DashboardView_bigSliceRadius, mRadius-15);
        mSmallSliceRadius = a.getDimensionPixelSize(R.styleable.DashboardView_smallSliceRadius, mRadius-8);
        mRadius = a.getDimensionPixelSize(R.styleable.DashboardView_radius, dpToPx(80));
        mStartAngle = a.getInteger(R.styleable.DashboardView_startAngle, 180);
        mSweepAngle = a.getInteger(R.styleable.DashboardView_sweepAngle, 180);
        mBigSliceCount = a.getInteger(R.styleable.DashboardView_bigSliceCount, 10);
        mSliceCountInOneBigSlice = a.getInteger(R.styleable.DashboardView_sliceCountInOneBigSlice, 5);
        mArcColor = a.getColor(R.styleable.DashboardView_arcColor, Color.WHITE);
        mMeasureTextSize = a.getDimensionPixelSize(R.styleable.DashboardView_measureTextSize, spToPx(12));
        mTextSizeOfValue = a.getDimensionPixelSize(R.styleable.DashboardView_textSizeOfValue, spToPx(26));
        mTextColor = a.getColor(R.styleable.DashboardView_textColor, mArcColor);
        mTextColorOfValue = a.getColor(R.styleable.DashboardView_textColorOfValue, 0xff135ece);
        mPointerColor = a.getColor(R.styleable.DashboardView_pointerColor, mArcColor);
        mHeaderTitle = a.getString(R.styleable.DashboardView_headerTitle);
        if (mHeaderTitle == null) {
            mHeaderTitle = "";
        }
        mHeaderTextSize = a.getDimensionPixelSize(R.styleable.DashboardView_headerTextSize, spToPx(14));
        mHeaderRadius = a.getDimensionPixelSize(R.styleable.DashboardView_headerRadius, mRadius / 3);
        mPointerRadius = a.getDimensionPixelSize(R.styleable.DashboardView_pointerRadius, mRadius / 3 * 2);

        mCircleRadius = a.getDimensionPixelSize(R.styleable.DashboardView_circleRadius, mRadius / 17);
        mShieldRadius = a.getDimensionPixelSize(R.styleable.DashboardView_shieldRadius, 0);
        mMinValue = a.getInteger(R.styleable.DashboardView_minValue, 0);
        mMaxValue = a.getInteger(R.styleable.DashboardView_maxValue, 100);
        mRealTimeValue = a.getFloat(R.styleable.DashboardView_realTimeValue, 0.0f);
        mStripeWidth = a.getDimensionPixelSize(R.styleable.DashboardView_stripeWidth, 0);
        mModeType = a.getInt(R.styleable.DashboardView_stripeMode, 0);
        mModeStyle = a.getInt(R.styleable.DashboardView_textStyle, 0);
        mBgColor = a.getColor(R.styleable.DashboardView_bgColor, 0);

        mCenterCircleColor = a.getColor(R.styleable.DashboardView_centerCircleColor, mArcColor);
        mCenterCircleRadius = a.getDimensionPixelSize(R.styleable.DashboardView_centerCircleRadius, mRadius /3);

        a.recycle();

        initObjects();
        initSizes();
    }

    private String[] getMeasureNumbers() {
        String[] strings = new String[mBigSliceCount + 1];
        for (int i = 0; i <= mBigSliceCount; i++) {
            if (i == 0) {
                strings[i] = String.valueOf(mMinValue);
            } else if (i == mBigSliceCount) {
                strings[i] = String.valueOf(mMaxValue);
            } else {
                strings[i] = String.valueOf(((mMaxValue - mMinValue) / mBigSliceCount) * i);
            }
        }

        return strings;
    }

    private void initObjects() {
        mPaintArc = new Paint();
        mPaintArc.setAntiAlias(true);
        mPaintArc.setColor(mArcColor);
        mPaintArc.setStyle(Paint.Style.STROKE);
        //mPaintArc.setStrokeCap(Paint.Cap.ROUND);

        mPaintArcSubline = new Paint();
        mPaintArcSubline.setAntiAlias(true);
        mPaintArcSubline.setColor(0xffdddddd);
        mPaintArcSubline.setStyle(Paint.Style.STROKE);
        mPaintArcSubline.setStrokeCap(Paint.Cap.ROUND);
        mPaintArcSubline.setStrokeWidth(1.0f);//dpToPx(1)

        mPaintArcProgress = new Paint();
        mPaintArcProgress.setAntiAlias(true);
        mPaintArcProgress.setColor(mArcColor);
        mPaintArcProgress.setStyle(Paint.Style.STROKE);


        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(mTextColor);
        mPaintText.setStyle(Paint.Style.STROKE);

        mPaintPointer = new Paint();
        mPaintPointer.setAntiAlias(true);

        mPaintStripe = new Paint();
        mPaintStripe.setAntiAlias(true);
        mPaintStripe.setStyle(Paint.Style.STROKE);
        mPaintStripe.setStrokeWidth(mStripeWidth);

        mRectMeasures = new Rect();
        mRectHeader = new Rect();
        //mRectRealText = new Rect();
        mPath = new Path();

        mPaintValue = new Paint();
        mPaintValue.setAntiAlias(true);
        mPaintValue.setColor(mTextColorOfValue);
        mPaintValue.setStyle(Paint.Style.STROKE);
        mPaintValue.setTextAlign(Paint.Align.CENTER);
        mPaintValue.setTextSize(mTextSizeOfValue);

        mPaintCenterCircle = new Paint();
        mPaintCenterCircle.setAntiAlias(true);
        mPaintCenterCircle.setColor(mCenterCircleColor);
        mPaintCenterCircle.setStyle(Paint.Style.STROKE);
        mPaintCenterCircle.setTextAlign(Paint.Align.CENTER);
        mPaintCenterCircle.setTextSize(mTextSizeOfValue);
    }

    private void initSizes() {
        if (mSweepAngle > 360) {
            throw new IllegalArgumentException("sweepAngle must less than 360 degree");
        }

        mWidthOfSlice = mSmallSliceRadius - mBigSliceRadius;
        mPaintArcProgress.setStrokeWidth(mWidthOfSlice);

        mNumMeaRadius = mBigSliceRadius - dpToPx(3);

        mSmallSliceCount = mBigSliceCount * mSliceCountInOneBigSlice;
        mBigSliceAngle = mSweepAngle / (float) mBigSliceCount;
        mSmallSliceAngle = mBigSliceAngle / (float) mSliceCountInOneBigSlice;
        mGraduations = getMeasureNumbers();

        switch (mModeType) {
            case 0:
                mStripeMode = StripeMode.NORMAL;
                break;
            case 1:
                mStripeMode = StripeMode.INNER;
                break;
            case 2:
                mStripeMode = StripeMode.OUTER;
                break;
        }

        switch (mModeStyle) {
            case 0:
                mPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                mTextStyle = TextStyle.NORMAL;
                break;
            case 1:
                mPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                mTextStyle = TextStyle.BOLD;
                break;
            case 2:
                mPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                mTextStyle = TextStyle.ITALIC;
                break;
            case 3:
                mPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));
                mTextStyle = TextStyle.BOLD_ITALIC;
                break;
        }


        int totalRadius;
        if (mStripeMode == StripeMode.OUTER) {
            totalRadius = mRadius + mStripeWidth;
        } else {
            totalRadius = mRadius;
        }

        mCenterX = mCenterY = 0.0f;
        if (mStartAngle <= 180 && mStartAngle + mSweepAngle >= 180) {
            mViewWidth = totalRadius * 2 + getPaddingLeft() + getPaddingRight() + dpToPx(2) * 2;
        } else {
            float[] point1 = getCoordinatePoint(totalRadius, mStartAngle);
            float[] point2 = getCoordinatePoint(totalRadius, mStartAngle + mSweepAngle);
            float max = Math.max(Math.abs(point1[0]), Math.abs(point2[0]));
            mViewWidth = (int) (max * 2 + getPaddingLeft() + getPaddingRight() + dpToPx(2) * 2);
        }
        if ((mStartAngle <= 90 && mStartAngle + mSweepAngle >= 90) ||
                (mStartAngle <= 270 && mStartAngle + mSweepAngle >= 270)) {
            mViewHeight = totalRadius * 2 + getPaddingTop() + getPaddingBottom() + dpToPx(2) * 2;
        } else {
            float[] point1 = getCoordinatePoint(totalRadius, mStartAngle);
            float[] point2 = getCoordinatePoint(totalRadius, mStartAngle + mSweepAngle);
            float max = Math.max(Math.abs(point1[1]), Math.abs(point2[1]));
            mViewHeight = (int) (max * 2 + getPaddingTop() + getPaddingBottom() + dpToPx(2) * 2);
        }

        mCenterX = mViewWidth / 2.0f;
        mCenterY = mViewHeight / 2.0f;

        mRectArc = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        int r = 0;
        if (mStripeWidth > 0) {
            if (mStripeMode == StripeMode.OUTER) {
                r = mRadius + dpToPx(1) + mStripeWidth / 2;
            } else if (mStripeMode == StripeMode.INNER) {
                r = mRadius + dpToPx(1) - mStripeWidth / 2;
            }
            mRectStripe = new RectF(mCenterX - r, mCenterY - r, mCenterX + r, mCenterY + r);
        }

        initAngle = getAngleFromResult(mRealTimeValue);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mViewWidth = widthSize;
        } else {
            if (widthMode == MeasureSpec.AT_MOST) {
                mViewWidth = Math.min(mViewWidth, widthSize);
            }
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mViewHeight = heightSize;
        } else {
            int totalRadius;
            if (mStripeMode == StripeMode.OUTER) {
                totalRadius = mRadius + mStripeWidth;
            } else {
                totalRadius = mRadius;
            }
            if (mStartAngle >= 180 && mStartAngle + mSweepAngle <= 360) {
                mViewHeight = totalRadius + mCircleRadius + dpToPx(2) + dpToPx(25) +
                        getPaddingTop() + getPaddingBottom();// + mRectRealText.height();
            } else {
                float[] point1 = getCoordinatePoint(totalRadius, mStartAngle);
                float[] point2 = getCoordinatePoint(totalRadius, mStartAngle + mSweepAngle);
                float maxY = Math.max(Math.abs(point1[1]) - mCenterY, Math.abs(point2[1]) - mCenterY);
                float f = mCircleRadius + dpToPx(2) + dpToPx(25);// + mRectRealText.height();
                float max = Math.max(maxY, f);
                mViewHeight = (int) (max + totalRadius + getPaddingTop() + getPaddingBottom() + dpToPx(2) * 2);
            }
            if (widthMode == MeasureSpec.AT_MOST) {
                mViewHeight = Math.min(mViewHeight, widthSize);
            }
        }

        setMeasuredDimension(mViewWidth, mViewHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mBgColor != 0) {
            canvas.drawColor(mBgColor);
        }

        drawStripe(canvas);
        drawMeasures(canvas);
        drawArc(canvas);
        drawCircleAndReadingText(canvas);
        drawPointer(canvas);
    }

    /**
     * 绘制色带
     */
    private void drawStripe(Canvas canvas) {
        if (mStripeMode != StripeMode.NORMAL && mStripeHighlight != null) {
            for (int i = 0; i < mStripeHighlight.size(); i++) {
                HighlightCR highlightCR = mStripeHighlight.get(i);
                if (highlightCR.getColor() == 0 || highlightCR.getSweepAngle() == 0) {
                    continue;
                }

                mPaintStripe.setColor(highlightCR.getColor());
                if (highlightCR.getStartAngle() + highlightCR.getSweepAngle() <= mStartAngle + mSweepAngle) {
                    canvas.drawArc(mRectStripe, highlightCR.getStartAngle(),
                            highlightCR.getSweepAngle(), false, mPaintStripe);
                } else {
                    canvas.drawArc(mRectStripe, highlightCR.getStartAngle(),
                            mStartAngle + mSweepAngle - highlightCR.getStartAngle(), false, mPaintStripe);
                    break;
                }
            }
        }
    }

    /**
     * 绘制刻度盘
     */
    private void drawMeasures(Canvas canvas) {
        mPaintArc.setStrokeWidth(dpToPx(2));
        for (int i = 0; i <= mBigSliceCount; i++) {
            //绘制大刻度
            float angle = i * mBigSliceAngle + mStartAngle;
            float[] point1 = getCoordinatePoint(mSmallSliceRadius, angle);
            float[] point2 = getCoordinatePoint(mBigSliceRadius, angle);


            if (mStripeMode == StripeMode.NORMAL && mStripeHighlight != null) {
                for (int j = 0; j < mStripeHighlight.size(); j++) {
                    HighlightCR highlightCR = mStripeHighlight.get(j);
                    if (highlightCR.getColor() == 0 || highlightCR.getSweepAngle() == 0) {
                        continue;
                    }

                    if (angle <= highlightCR.getStartAngle() + highlightCR.getSweepAngle()) {
                        mPaintArc.setColor(highlightCR.getColor());
                        break;
                    } else {
                        mPaintArc.setColor(mArcColor);
                    }
                }
            } else {
                mPaintArc.setColor(mArcColor);
            }
            //canvas.drawLine(point1[0], point1[1], point2[0], point2[1], mPaintArcSubline);

            //绘制圆盘上的数字
            mPaintText.setTextSize(mMeasureTextSize);
            String number = mGraduations[i];
            mPaintText.getTextBounds(number, 0, number.length(), mRectMeasures);
            if (angle % 360 > 135 && angle % 360 < 225) {
                mPaintText.setTextAlign(Paint.Align.LEFT);
            } else if ((angle % 360 >= 0 && angle % 360 < 45) || (angle % 360 > 315 && angle % 360 <= 360)) {
                mPaintText.setTextAlign(Paint.Align.RIGHT);
            } else {
                mPaintText.setTextAlign(Paint.Align.CENTER);
            }
            float[] numberPoint = getCoordinatePoint(mNumMeaRadius, angle);
            if (i == 0 || i == mBigSliceCount) {
                float x = numberPoint[0], y = numberPoint[1];
                float z = (mWidthOfSlice)/1.6f;
                if (i==0){
                    y = y+z;
                }else{
                    x = x-z/4.0f;
                    y = y+z;
                }
                canvas.drawText(number, x, y + (mRectMeasures.height() / 2), mPaintText);
            } else {
                canvas.drawText(number, numberPoint[0], numberPoint[1] + mRectMeasures.height(), mPaintText);
            }
        }

        //绘制小的子刻度
        mPaintArc.setStrokeWidth(dpToPx(1));
        for (int i = 0; i < mSmallSliceCount; i++) {
            if (i % mSliceCountInOneBigSlice != 0) {
                float angle = i * mSmallSliceAngle + mStartAngle;
                float[] point1 = getCoordinatePoint(mRadius, angle);
                float[] point2 = getCoordinatePoint(mSmallSliceRadius, angle);

                if (mStripeMode == StripeMode.NORMAL && mStripeHighlight != null) {
                    for (int j = 0; j < mStripeHighlight.size(); j++) {
                        HighlightCR highlightCR = mStripeHighlight.get(j);
                        if (highlightCR.getColor() == 0 || highlightCR.getSweepAngle() == 0) {
                            continue;
                        }

                        if (angle <= highlightCR.getStartAngle() + highlightCR.getSweepAngle()) {
                            mPaintArc.setColor(highlightCR.getColor());
                            break;
                        } else {
                            mPaintArc.setColor(mArcColor);
                        }
                    }
                } else {
                    mPaintArc.setColor(mArcColor);
                }
                mPaintArc.setStrokeWidth(dpToPx(1));
                canvas.drawLine(point1[0], point1[1], point2[0], point2[1], mPaintArc);
            }
        }

    }

    /**
     * 绘制刻度盘的弧形
     */
    private void drawArc(Canvas canvas) {
        mPaintArc.setStrokeWidth(dpToPx(2));
        if (mStripeMode == StripeMode.NORMAL) {
            if (mStripeHighlight != null) {
                for (int i = 0; i < mStripeHighlight.size(); i++) {
                    HighlightCR highlightCR = mStripeHighlight.get(i);
                    if (highlightCR.getColor() == 0 || highlightCR.getSweepAngle() == 0) {
                        continue;
                    }

                    mPaintArc.setColor(highlightCR.getColor());
                    if (highlightCR.getStartAngle() + highlightCR.getSweepAngle() <= mStartAngle + mSweepAngle) {
                        canvas.drawArc(mRectArc, highlightCR.getStartAngle(), highlightCR.getSweepAngle()+1, false, mPaintArc);
                        RectF mRectArc1 = new RectF(mCenterX - mSmallSliceRadius, mCenterY - mSmallSliceRadius, mCenterX + mSmallSliceRadius, mCenterY + mSmallSliceRadius);
                        RectF mRectArc2 = new RectF(mCenterX - mBigSliceRadius, mCenterY - mBigSliceRadius, mCenterX + mBigSliceRadius, mCenterY + mBigSliceRadius);
                        //canvas.drawArc(mRectArc1, highlightCR.getStartAngle(), highlightCR.getSweepAngle()+1, false, mPaintArcSubline);
                        //canvas.drawArc(mRectArc2, highlightCR.getStartAngle(), highlightCR.getSweepAngle()+1, false, mPaintArcSubline);
                    } else {
                        canvas.drawArc(mRectArc, highlightCR.getStartAngle(), mStartAngle + mSweepAngle - highlightCR.getStartAngle(), false, mPaintArc);
                        break;
                    }
                }
            } else {
                mPaintArc.setColor(mArcColor);
                canvas.drawArc(mRectArc, mStartAngle, mSweepAngle, false, mPaintArc);
            }
        } else if (mStripeMode == StripeMode.OUTER) {
            mPaintArc.setColor(mArcColor);
            canvas.drawArc(mRectArc, mStartAngle, mSweepAngle, false, mPaintArc);
        }
    }

    /**
     * 绘制圆和文字读数
     */
    private void drawCircleAndReadingText(Canvas canvas) {
        //表头
        mPaintText.setTextSize(mHeaderTextSize);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.getTextBounds(mHeaderTitle, 0, mHeaderTitle.length(), mRectHeader);
        canvas.drawText(mHeaderTitle, mCenterX, mCenterY - mHeaderRadius + mRectHeader.height(), mPaintText);

        //绘制中心圆盘外圈
        RectF mRectArc2 = new RectF(mCenterX - mCenterCircleRadius - mWidthOfSlice, mCenterY - mCenterCircleRadius - mWidthOfSlice,
                mCenterX + mCenterCircleRadius + mWidthOfSlice, mCenterY + mCenterCircleRadius + mWidthOfSlice);
        canvas.drawArc(mRectArc2, 0, 360, false, mPaintArc);

        //绘制中心点的圆
        mPaintCenterCircle.setStyle(Paint.Style.FILL);
        mPaintCenterCircle.setColor(mCenterCircleColor);
        canvas.drawCircle(mCenterX, mCenterY, mCenterCircleRadius, mPaintCenterCircle);

        /*//绘制圆心
        mPaintCenterCircle.setStyle(Paint.Style.STROKE);
        mPaintCenterCircle.setStrokeWidth(dpToPx(4));
        mPaintCenterCircle.setColor(mArcColor);
        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius + dpToPx(2), mPaintCenterCircle);*/

        // 绘制读数
        canvas.drawText(formatValue(mRealTimeValue), mCenterX,  mCenterY , mPaintValue);
    }

    /**
     * 绘制指针
     */
    private void drawPointer(Canvas canvas) {
        mPaintPointer.setStyle(Paint.Style.FILL);
        mPaintPointer.setColor(mPointerColor);
        mPath.reset();
        if (mShieldRadius==0) {
            float[] point1 = getCoordinatePoint(mCircleRadius / 2, initAngle + 90);
            mPath.moveTo(point1[0], point1[1]);
            float[] point2 = getCoordinatePoint(mCircleRadius / 2, initAngle - 90);
            mPath.lineTo(point2[0], point2[1]);
            float[] point3 = getCoordinatePoint(mPointerRadius, initAngle);
            mPath.lineTo(point3[0], point3[1]);
            mPath.close();
            canvas.drawPath(mPath, mPaintPointer);
            // 绘制三角形指针底部的圆弧效果
            canvas.drawCircle((point1[0] + point2[0]) / 2, (point1[1] + point2[1]) / 2, mCircleRadius / 2, mPaintPointer);
        }else{
            float[] point1 = getCoordinatePoint(mShieldRadius, initAngle + 2);
            mPath.moveTo(point1[0], point1[1]);
            float[] point2 = getCoordinatePoint(mShieldRadius, initAngle - 2);
            mPath.lineTo(point2[0], point2[1]);
            float[] point3 = getCoordinatePoint(mPointerRadius, initAngle);
            mPath.lineTo(point3[0], point3[1]);
            mPath.close();
            canvas.drawPath(mPath, mPaintPointer);
        }
         //左上右下
        RectF mRectArc1 = new RectF(mCenterX - mSmallSliceRadius+mWidthOfSlice/2.0f, mCenterY - mSmallSliceRadius+mWidthOfSlice/2.0f,
                mCenterX + mSmallSliceRadius-mWidthOfSlice/2.0f, mCenterY + mSmallSliceRadius-mWidthOfSlice/2.0f);
        canvas.drawArc(mRectArc1, mStartAngle, initAngle-mStartAngle, false, mPaintArcProgress);
    }

    /**
     * 依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
     */
    public float[] getCoordinatePoint(int radius, float cirAngle) {
        float[] point = new float[2];

        double arcAngle = Math.toRadians(cirAngle); //将角度转换为弧度
        if (cirAngle < 90) {
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (cirAngle == 90) {
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        } else if (cirAngle > 90 && cirAngle < 180) {
            arcAngle = Math.PI * (180 - cirAngle) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (cirAngle == 180) {
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        } else if (cirAngle > 180 && cirAngle < 270) {
            arcAngle = Math.PI * (cirAngle - 180) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        } else if (cirAngle == 270) {
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        } else {
            arcAngle = Math.PI * (360 - cirAngle) / 180.0;
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }

        return point;
    }

    /**
     * 通过数值得到角度位置
     */
    private float getAngleFromResult(float result) {
        if (result > mMaxValue) {
            return mMaxValue;
        }
        return mSweepAngle * (result - mMinValue) / (mMaxValue - mMinValue) + mStartAngle;
    }

    /**
     * float类型如果小数点后为零则显示整数否则保留
     */
    public static String trimFloat(float value) {
        if (Math.round(value) - value == 0) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
    public static String formatValue(float value) {

        return String.format("%3.1f", value);
    }



    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        mRadius = dpToPx(radius);
        initSizes();
        invalidate();
    }

    public int getPointerColor() {
        return mPointerColor;
    }

    public void setPointerColor(int pointerColor) {
        this.mPointerColor =pointerColor;
        invalidate();
    }

    public int getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
        initSizes();
        invalidate();
    }

    public int getSweepAngle() {
        return mSweepAngle;
    }

    public void setSweepAngle(int sweepAngle) {
        mSweepAngle = sweepAngle;
        initSizes();
        invalidate();
    }

    public int getBigSliceCount() {
        return mBigSliceCount;
    }

    public void setBigSliceCount(int bigSliceCount) {
        mBigSliceCount = bigSliceCount;
        initSizes();
        invalidate();
    }

    public int getSliceCountInOneBigSlice() {
        return mSliceCountInOneBigSlice;
    }

    public void setSliceCountInOneBigSlice(int sliceCountInOneBigSlice) {
        mSliceCountInOneBigSlice = sliceCountInOneBigSlice;
        initSizes();
        invalidate();
    }

    public int getmTextColorOfValue() {
        return mTextColorOfValue;
    }

    public void setmTextColorOfValue(int mTextColorOfValue) {
        this.mTextColorOfValue = mTextColorOfValue;
        mPaintValue.setColor(this.mTextColorOfValue);
        invalidate();
    }

    public int getArcColor() {
        return mArcColor;
    }

    public void setArcColor(int arcColor) {
        mArcColor = arcColor;
        mPaintArc.setColor(arcColor);
        if (textColorFlag) {
            mTextColor = mArcColor;
            mPaintText.setColor(arcColor);
        }
        invalidate();
    }

    public int getCenterCircleRadius() {
        return mCenterCircleRadius;
    }

    public void setCenterCircleRadius(int CenterCircleRadius) {
        mCenterCircleRadius = dpToPx(CenterCircleRadius);
        invalidate();
    }

    public int getCenterCircleColor() {
        return mCenterCircleColor;
    }

    public void setCenterCircleColor(int CenterCircleColor) {
        mCenterCircleColor = CenterCircleColor;
        mPaintCenterCircle.setColor(mCenterCircleColor);
        invalidate();
    }

    public int getTextSizeOfValue() {
        return mTextSizeOfValue;
    }

    public void setTextSizeOfValue(int mTextSizeOfValue) {
        this.mTextSizeOfValue = mTextSizeOfValue;
        invalidate();
    }

    public int getMeasureTextSize() {
        return mMeasureTextSize;
    }

    public void setMeasureTextSize(int measureTextSize) {
        mMeasureTextSize = spToPx(measureTextSize);
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        textColorFlag = false;
        mPaintText.setColor(textColor);
        invalidate();
    }

    public String getHeaderTitle() {
        return mHeaderTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        mHeaderTitle = headerTitle;
        invalidate();
    }

    public int getHeaderTextSize() {
        return mHeaderTextSize;
    }

    public void setHeaderTextSize(int headerTextSize) {
        mHeaderTextSize = spToPx(headerTextSize);
        invalidate();
    }

    public int getHeaderRadius() {
        return mHeaderRadius;
    }

    public void setHeaderRadius(int headerRadius) {
        mHeaderRadius = dpToPx(headerRadius);
        invalidate();
    }

    public int getPointerRadius() {
        return mPointerRadius;
    }

    public void setPointerRadius(int pointerRadius) {
        mPointerRadius = dpToPx(pointerRadius);
        invalidate();
    }

    public int getCircleRadius() {
        return mCircleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        mCircleRadius = dpToPx(circleRadius);
        invalidate();
    }

    public int getMinValue() {
        return mMinValue;
    }

    public void setMinValue(int minValue) {
        mMinValue = minValue;
        invalidate();
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
        invalidate();
    }

    public int getShieldRadius() {
        return mShieldRadius;
    }

    public void setShieldRadius(int mShieldRadius) {
        mPointerRadius = dpToPx(mShieldRadius);
        invalidate();
    }

    public float getRealTimeValue() {
        return mRealTimeValue;
    }

    public void setRealTimeValue(float realTimeValue) {
        if (!mAnimEnable) {
            invalidate();
        } else {
            if (isAnimFinished) {
                isAnimFinished = false;
                playAnimation(realTimeValue);
            }
        }
    }

    public void setRealTimeValue(float realTimeValue, boolean animEnable) {
        mAnimEnable = animEnable;

        setRealTimeValue(realTimeValue);
    }

    public void setRealTimeValue(float realTimeValue, boolean animEnable, long duration) {
        this.mDuration = duration;

        setRealTimeValue(realTimeValue, animEnable);
    }

    private void playAnimation(float targetValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(mRealTimeValue, targetValue);
        animator.setDuration(mDuration);
        animator.setInterpolator(new PointerBounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //mRealTimeValue = (float) Math.ceil((float) animation.getAnimatedValue());
                mRealTimeValue = (float)animation.getAnimatedValue();
                if (mRealTimeValue < mMinValue) {
                    mRealTimeValue = mMinValue;
                }
                if (mRealTimeValue > mMaxValue) {
                    mRealTimeValue = mMaxValue;
                }
                initAngle = getAngleFromResult(mRealTimeValue);
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
    }

    public int getStripeWidth() {
        return mStripeWidth;
    }

    public void setStripeWidth(int stripeWidth) {
        mStripeWidth = dpToPx(stripeWidth);
        initSizes();
        invalidate();
    }

    public TextStyle getTextStyle() {
        return mTextStyle;
    }

    public void setTextStyle(TextStyle textStyle) {
        this.mTextStyle = textStyle;
        switch(mTextStyle.ordinal()){
            case 0:
                mModeStyle = 0;
                mPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                break;
            case 1:
                mModeStyle = 1;
                mPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                break;
            case 2:
                mModeStyle = 2;
                mPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                break;
            case 3:
                mModeStyle = 3;
                mPaintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));
                break;
        }
        initSizes();
        invalidate();
    }

    public StripeMode getStripeMode() {
        return mStripeMode;
    }

    public void setStripeMode(StripeMode mStripeMode) {
        this.mStripeMode = mStripeMode;
        switch (mStripeMode) {
            case NORMAL:
                mModeType = 0;
                break;
            case INNER:
                mModeType = 1;
                break;
            case OUTER:
                mModeType = 2;
                break;
        }
        initSizes();
        invalidate();
    }

    public int getBigSliceRadius() {
        return mBigSliceRadius;
    }

    public void setBigSliceRadius(int bigSliceRadius) {
        mBigSliceRadius = dpToPx(bigSliceRadius);
        initSizes();
        invalidate();
    }

    public int getSmallSliceRadius() {
        return mSmallSliceRadius;
    }

    public void setSmallSliceRadius(int smallSliceRadius) {
        mSmallSliceRadius = dpToPx(smallSliceRadius);
        initSizes();
        invalidate();
    }

    public int getNumMeaRadius() {
        return mNumMeaRadius;
    }

    public void setNumMeaRadius(int numMeaRadius) {
        mNumMeaRadius = dpToPx(numMeaRadius);
        initSizes();
        invalidate();
    }

    public void setStripeHighlightColorAndRange(List<HighlightCR> stripeHighlight) {
        mStripeHighlight = stripeHighlight;
        mPaintStripe.setStrokeWidth(mStripeWidth);
        invalidate();
    }
    public enum TextStyle {
        NORMAL,
        BOLD,
        ITALIC,
        BOLD_ITALIC
    }
    public enum StripeMode {
        NORMAL,
        INNER,
        OUTER
    }

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
        invalidate();
    }

    public boolean isAnimEnable() {
        return mAnimEnable;
    }

    public void setAnimEnable(boolean animEnable) {
        mAnimEnable = animEnable;
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    public class PointerBounceInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input)
                    * Math.sin((input - 0.4f / 4)
                    * (2 * Math.PI) / 0.4f) + 1);
        }
    }

}