// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

public class HomeDash_e
{
    public static void a(final Animator animator) {
        if (animator != null) {
            animator.removeAllListeners();
            animator.cancel();
        }
    }

    public static void a(final ObjectAnimator objectAnimator) {
        if (objectAnimator != null) {
            objectAnimator.removeAllListeners();
            objectAnimator.removeAllUpdateListeners();
            objectAnimator.cancel();
        }
    }

    public static void removeValueAnimator(final ValueAnimator valueAnimator) {
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
        }
    }
}
