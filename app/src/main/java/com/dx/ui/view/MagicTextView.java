// 
// Decompiled by Procyon v0.5.30
// 

package com.dx.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.TextView;

import com.dx.ui.R;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class MagicTextView extends TextView
{
    private ArrayList<v> a;
    private ArrayList<v> b;
    private WeakHashMap<String, Pair<Canvas, Bitmap>> c;
    private Canvas d;
    private Bitmap e;
    private Drawable f;
    private float g;
    private Integer h;
    private Join i;
    private float j;
    private int[] k;
    private boolean l;

    public MagicTextView(final Context context) {
        super(context);
        this.l = false;
        this.a(null);
    }

    public MagicTextView(final Context context, final AttributeSet set) {
        super(context, set);
        this.l = false;
        this.a(set);
    }

    public MagicTextView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.l = false;
        this.a(set);
    }

    private void c() {
        final String format = String.format("%dx%d", this.getWidth(), this.getHeight());
        final Pair<Canvas, Bitmap> pair = this.c.get(format);
        if (pair != null) {
            this.d = (Canvas)pair.first;
            this.e = (Bitmap)pair.second;
        }
        else {
            this.d = new Canvas();
            this.e = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Config.ARGB_8888);
            this.d.setBitmap(this.e);
            this.c.put(format, (Pair<Canvas, Bitmap>)new Pair((Object)this.d, (Object)this.e));
        }
    }

    public void a() {
        this.k = new int[] { this.getCompoundPaddingLeft(), this.getCompoundPaddingRight(), this.getCompoundPaddingTop(), this.getCompoundPaddingBottom() };
        this.l = true;
    }

    public void a(final float n, final float n2, final float n3, final int n4) {
        float n5 = n;
        if (n == 0.0f) {
            n5 = 1.0E-4f;
        }
        this.a.add(new v(n5, n2, n3, n4));
    }

    public void a(final float g, final int n, final Join i, final float j) {
        this.g = g;
        this.h = n;
        this.i = i;
        this.j = j;
    }

    public void a(final AttributeSet set) {
        this.a = new ArrayList<v>();
        this.b = new ArrayList<v>();
        if (this.c == null) {
            this.c = new WeakHashMap<String, Pair<Canvas, Bitmap>>();
        }
        if (set != null) {
            int MagicTextView[] = new int[] {
                    R.attr.ab, R.attr.ac, R.attr.ad, R.attr.ae, R.attr.af,
                    R.attr.ag, R.attr.ah, R.attr.ai, R.attr.aj, R.attr.ak,
                    R.attr.al, R.attr.am, R.attr.an, R.attr.ao, R.attr.ap};
            final TypedArray attrs = this.getContext().obtainStyledAttributes(set, MagicTextView);
            final String string = attrs.getString(8);
            if (string != null) {
                this.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), String.format("fonts/%s.ttf", string)));
            }
            if (attrs.hasValue(9)) {
                final Drawable drawable = attrs.getDrawable(9);
                if (drawable != null) {
                    this.setForegroundDrawable(drawable);
                }
                else {
                    this.setTextColor(attrs.getColor(9, 0xff000000));
                }
            }
            if (attrs.hasValue(10)) {
                final Drawable drawable2 = attrs.getDrawable(10);
                if (drawable2 != null) {
                    this.setBackgroundDrawable(drawable2);
                }
                else {
                    this.setBackgroundColor(attrs.getColor(10, 0xff000000));
                }
            }
            if (attrs.hasValue(0)) {
                this.b(attrs.getDimensionPixelSize(1, 0), attrs.getDimensionPixelOffset(2, 0), attrs.getDimensionPixelOffset(3, 0), attrs.getColor(0, 0xff000000));
            }
            if (attrs.hasValue(4)) {
                this.a(attrs.getDimensionPixelSize(5, 0), attrs.getDimensionPixelOffset(6, 0), attrs.getDimensionPixelOffset(7, 0), attrs.getColor(4, 0xff000000));
            }
            if (attrs.hasValue(13)) {
                final float n = attrs.getDimensionPixelSize(11, 1);
                final int color = attrs.getColor(13, 0xff000000);
                final float n2 = attrs.getDimensionPixelSize(12, 10);
                Join  paint_Join = null;
                switch (attrs.getInt(14, 0)) {
                    case 0: {
                         paint_Join = Join.MITER;
                        break;
                    }
                    case 1: {
                         paint_Join = Join.BEVEL;
                        break;
                    }
                    case 2: {
                         paint_Join = Join.ROUND;
                        break;
                    }
                }
                this.a(n, color,  paint_Join, n2);
            }
            attrs.recycle();
        }
        if (Build.VERSION.SDK_INT >= 11 && (this.b.size() > 0 || this.f != null)) {
            this.setLayerType(1, null);
        }
    }

    public void b() {
        this.l = false;
    }

    public void b(final float n, final float n2, final float n3, final int n4) {
        float n5 = n;
        if (n == 0.0f) {
            n5 = 1.0E-4f;
        }
        this.b.add(new v(n5, n2, n3, n4));
    }

    public int getCompoundPaddingBottom() {
        int compoundPaddingBottom;
        if (!this.l) {
            compoundPaddingBottom = super.getCompoundPaddingBottom();
        }
        else {
            compoundPaddingBottom = this.k[3];
        }
        return compoundPaddingBottom;
    }

    public int getCompoundPaddingLeft() {
        int compoundPaddingLeft;
        if (!this.l) {
            compoundPaddingLeft = super.getCompoundPaddingLeft();
        }
        else {
            compoundPaddingLeft = this.k[0];
        }
        return compoundPaddingLeft;
    }

    public int getCompoundPaddingRight() {
        int compoundPaddingRight;
        if (!this.l) {
            compoundPaddingRight = super.getCompoundPaddingRight();
        }
        else {
            compoundPaddingRight = this.k[1];
        }
        return compoundPaddingRight;
    }

    public int getCompoundPaddingTop() {
        int compoundPaddingTop;
        if (!this.l) {
            compoundPaddingTop = super.getCompoundPaddingTop();
        }
        else {
            compoundPaddingTop = this.k[2];
        }
        return compoundPaddingTop;
    }

    public Drawable getForeground() {
        Object f;
        if (this.f == null) {
            f = this.f;
        }
        else {
            f = new ColorDrawable(this.getCurrentTextColor());
        }
        return (Drawable)f;
    }

    public void invalidate() {
        if (!this.l) {
            super.invalidate();
        }
    }

    public void invalidate(final int n, final int n2, final int n3, final int n4) {
        if (!this.l) {
            super.invalidate(n, n2, n3, n4);
        }
    }

    public void invalidate(final Rect rect) {
        if (!this.l) {
            super.invalidate(rect);
        }
    }

    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        this.a();
        final Drawable background = this.getBackground();
        final Drawable[] compoundDrawables = this.getCompoundDrawables();
        final int currentTextColor = this.getCurrentTextColor();
        this.setCompoundDrawables(null, null, null, null);
        for (final v v : this.a) {
            this.setShadowLayer(v.a, v.b, v.c, v.d);
            super.onDraw(canvas);
        }
        this.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        this.setTextColor(currentTextColor);
        if (this.f != null && this.f instanceof BitmapDrawable) {
            this.c();
            super.onDraw(this.d);
            ((BitmapDrawable)this.f).getPaint().setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            this.f.setBounds(canvas.getClipBounds());
            this.f.draw(this.d);
            canvas.drawBitmap(this.e, 0.0f, 0.0f, null);
            this.d.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        if (this.h != null) {
            final TextPaint paint = this.getPaint();
            paint.setStyle(Style.STROKE);
            paint.setStrokeJoin(this.i);
            paint.setStrokeMiter(this.j);
            this.setTextColor((int)this.h);
            paint.setStrokeWidth(this.g);
            super.onDraw(canvas);
            paint.setStyle(Style.FILL);
            this.setTextColor(currentTextColor);
        }
        if (this.b.size() > 0) {
            this.c();
            final TextPaint paint2 = this.getPaint();
            for (final v v2 : this.b) {
                this.setTextColor(v2.d);
                super.onDraw(this.d);
                this.setTextColor(0xff000000);
                paint2.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                paint2.setMaskFilter((MaskFilter)new BlurMaskFilter(v2.a, Blur.NORMAL));
                this.d.save();
                this.d.translate(v2.b, v2.c);
                super.onDraw(this.d);
                this.d.restore();
                canvas.drawBitmap(this.e, 0.0f, 0.0f, null);
                this.d.drawColor(0, PorterDuff.Mode.CLEAR);
                paint2.setXfermode(null);
                paint2.setMaskFilter(null);
                this.setTextColor(currentTextColor);
                this.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            }
        }
        if (compoundDrawables != null) {
            this.setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
        }
        this.setBackgroundDrawable(background);
        this.setTextColor(currentTextColor);
        this.b();
    }
    
    public void postInvalidate() {
        if (!this.l) {
            super.postInvalidate();
        }
    }
    
    public void postInvalidate(final int n, final int n2, final int n3, final int n4) {
        if (!this.l) {
            super.postInvalidate(n, n2, n3, n4);
        }
    }
    
    public void requestLayout() {
        if (!this.l) {
            super.requestLayout();
        }
    }
    
    public void setForegroundDrawable(final Drawable f) {
        this.f = f;
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
