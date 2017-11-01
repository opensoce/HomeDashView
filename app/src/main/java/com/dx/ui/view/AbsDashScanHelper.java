// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;

public abstract class AbsDashScanHelper
{
    private boolean isAniming;
    private DataClass[] dataClasses;
    private boolean isAnimAuto;
    private Handler handler;
    enum ANIM_TYPE
    {
        anim,
        auto;
    }
    public AbsDashScanHelper() {
        this.isAniming = false;
        this.isAnimAuto = false;
        this.handler = new Handler() {

            public void handleMessage(final Message message) {
                switch (message.what) {
                    case 18: {
                        final int[] array = new int[dataClasses.length];
                        initDatas(array, AbsDashScanHelper.ANIM_TYPE.auto, 0.0f);
                        gotDatas(isPlaying(), array);
                        outputMsg();
                        break;
                    }
                }
            }
        };
    }

    private DataClass getDataFromString(final String s) {
        for (final DataClass d : this.dataClasses) {
            if (d.key.equals(s)) {
                return d;
            }
        }
        return null;
    }

    private void initDatas(final int[] array, final AbsDashScanHelper.ANIM_TYPE  absDashScanHelper_ANIM_TYPE, final float n) {
        this.isAnimAuto = true;
        int i = 0;
        int n2 = 0;
        while (i < this.dataClasses.length) {
            final DataClass d = this.dataClasses[i];
            int n5=0;
            int n6=0;
            if (absDashScanHelper_ANIM_TYPE == AbsDashScanHelper.ANIM_TYPE.auto) {
                array[n2] = (d.b = Math.min(d.b + 2, d.c));
                int n3;
                if (d.b == d.c) {
                    n3 = 1;
                }
                else {
                    n3 = 0;
                }
                final int n4 = n2 + 1;
                n5 = n3;
                n6 = n4;
            }
            else {
                array[n2] = (int)(d.b + (d.c - d.b) * n);
                n6 = n2 + 1;
                n5 = 0;
            }
            isAnimAuto = (isAnimAuto && n5 != 0);
            ++i;
            n2 = n6;
        }
        if (isAnimAuto) {
            this.onUpdate();
        }
    }

    private void outputMsg() {
        if (!isPlaying()) {
            handler.sendEmptyMessageDelayed(18, 20L);
        }
    }

    private boolean isPlaying() {
        return !isAniming && this.isAnimAuto;
    }

    public void startAnim(final long duration) {
        if (!this.isAniming) {
            if (duration <= 0L) {
                final int[] array = new int[this.dataClasses.length];
                initDatas(array, AbsDashScanHelper.ANIM_TYPE.anim, 1.0f);
                gotDatas(true, array);
            }
            else {
                final ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[] { 0.0f, 1.0f });
                ofFloat.setDuration(duration);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                        final float floatValue = (float)valueAnimator.getAnimatedValue();
                        final int[] array = new int[dataClasses.length];
                        initDatas(array, AbsDashScanHelper.ANIM_TYPE.anim, floatValue);
                        gotDatas(false, array);
                    }
                });
                ofFloat.addListener(new Animator.AnimatorListener() {

                    public void onAnimationCancel(final Animator animator) {
                    }

                    public void onAnimationEnd(final Animator animator) {
                        for (int i = 0; i < dataClasses.length; ++i) {
                            dataClasses[i].b = dataClasses[i].c;
                        }
                        onUpdate();
                    }

                    public void onAnimationRepeat(final Animator animator) {
                    }

                    public void onAnimationStart(final Animator animator) {
                    }
                });
                ofFloat.start();
            }
        }
    }

    public void setData(final String s, final int c) {
        this.getDataFromString(s).c = c;
    }

    protected abstract void gotDatas(final boolean p0, final int... p1);

    public void initKeys(final String... array) {
        this.dataClasses = new DataClass[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.dataClasses[i] = new DataClass();
            this.dataClasses[i].key = array[i];
        }
    }

    public boolean isAniming() {
        return this.isAniming;
    }

    protected void onUpdate() {
    }

    class DataClass
    {
        public String key;
        public int b;
        public int c;
    }

}
