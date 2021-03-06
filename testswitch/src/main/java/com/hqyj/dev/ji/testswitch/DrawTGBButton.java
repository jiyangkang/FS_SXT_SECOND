package com.hqyj.dev.ji.testswitch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jiyangkang on 2016/4/19 0019.
 */
public class DrawTGBButton extends View {

    private Context mContext;
    protected Paint mPanit;
    private int withMetrics;

    private Bitmap bitmap;
    private Bitmap bigUP, bigDown, smallUP, smallDown;
    private Rect orBG, dstBG;
    private Rect orBig, orSmall;
    private Rect open, close, luxUp, luxDown, r, g, b, w, flash, strobe, fade, smooth;
    private Bitmap bOpen, bClose, bluxUp, bluxDown, br, bg, bb, bw, bflash, bstrobe, bfade, bsmooth;
    private boolean isopen, isclose, isluxUp, isluxDown, isr;
    private boolean isg, isb, isw, isflash, isstrobe, isfade, issmooth;


    public DrawTGBButton(Context context) {
        this(context, null);
    }

    public DrawTGBButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawTGBButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        withMetrics = context.getResources().getDisplayMetrics().widthPixels;
        mPanit = new Paint();
        mPanit.setTextSize(18 * withMetrics / 1024);
        mPanit.setColor(Color.BLACK);
        mPanit.setAntiAlias(true);
        mPanit.setFlags(Paint.ANTI_ALIAS_FLAG);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rgb);
        int ox = bitmap.getWidth();
        int oy = bitmap.getHeight();
        orBG = new Rect(0, 0, ox, oy);
        int rx, ry;
        rx = withMetrics * 220 / 1024;
        ry = rx * oy / ox;
        dstBG = new Rect(0, 0, rx, ry);

        bigUP = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigup);
        bigDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigdown);

        smallUP = BitmapFactory.decodeResource(context.getResources(), R.drawable.smallup);
        smallDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.smalldown);

        bOpen = bClose = bigUP;
        int orbx = bigUP.getWidth();
        int orby = bigUP.getHeight();

        orBig = new Rect(0, 0, orbx, orby);
        int dstbx = withMetrics * 90 / 1024;
        int dstby = dstbx * orby / orbx;

        open = new Rect(12, dstBG.height() / 4, dstbx + 12, dstby + dstBG.height() / 4);
        close = new Rect(dstBG.width() - 12 - dstbx, dstBG.height() / 4,
                dstBG.width() - 12, dstby + dstBG.height() / 4);


        bluxDown = bluxUp = br = bg = bb = bw = bflash = bstrobe = bfade = bsmooth = smallUP;
        int orsx = smallUP.getWidth();
        int orsy = smallUP.getHeight();
        orSmall = new Rect(0, 0, orsx, orsy);
        int dstsx = withMetrics * 61 / 1024;
        int dstsy = dstsx * orsy / orsx;

        luxUp = new Rect(12, open.bottom + dstsy, 12 + dstsx, open.bottom + 2 * dstsy);
        luxDown = new Rect(dstBG.width() / 2 - dstsx / 2, open.bottom + dstsy,
                dstBG.width() / 2 + dstsx / 2, open.bottom + 2 * dstsy);
        w = new Rect(dstBG.width() - 12 - dstsx, open.bottom + dstsy,
                dstBG.width() - 12, open.bottom + 2 * dstsy);

        r = new Rect(12, luxUp.bottom + dstsy, 12 + dstsx, luxUp.bottom + 2 * dstsy);
        g = new Rect(dstBG.width() / 2 - dstsx / 2, luxUp.bottom + dstsy,
                dstBG.width() / 2 + dstsx / 2, luxUp.bottom + 2 * dstsy);
        b = new Rect(dstBG.width() - 12 - dstsx, luxUp.bottom + dstsy,
                dstBG.width() - 12, luxUp.bottom + 2 * dstsy);

        flash = new Rect(12, r.bottom + dstsy, 12 + dstsx, r.bottom + 2 * dstsy);
        strobe = new Rect(dstBG.width() / 2 - dstsx / 2, r.bottom + dstsy,
                dstBG.width() / 2 + dstsx / 2, r.bottom + 2 * dstsy);
        fade = new Rect(dstBG.width() - 12 - dstsx, r.bottom + dstsy,
                dstBG.width() - 12, r.bottom + 2 * dstsy);

        smooth = new Rect(dstBG.width() / 2 - dstsx / 2, flash.bottom + dstsy,
                dstBG.width() / 2 + dstsx / 2, flash.bottom + 2 * dstsy);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        mPanit.setTextSize(18 * withMetrics / 1024);
        canvas.drawBitmap(bitmap, orBG, dstBG, mPanit);
        canvas.drawBitmap(bOpen, orBig, open, mPanit);
        canvas.drawText("开", open.width() / 2 + open.left - mPanit.getTextSize() / 2,
                open.top + open.height() * 3 / 4, mPanit);
        canvas.drawBitmap(bClose, orBig, close, mPanit);
        canvas.drawText("关", close.width() / 2 + close.left - mPanit.getTextSize() / 2,
                close.top + open.height() * 3 / 4, mPanit);
        canvas.drawBitmap(bluxUp, orSmall, luxUp, mPanit);
        mPanit.setTextSize(14 * withMetrics / 1024);
        canvas.drawText("亮度+", luxUp.width() / 2 + luxUp.left - mPanit.getTextSize() * 3 / 2,
                luxUp.top + luxUp.height() * 3 / 4, mPanit);
        canvas.drawBitmap(bluxDown, orSmall, luxDown, mPanit);
        canvas.drawText("亮度-", luxDown.width() / 2 + luxDown.left - mPanit.getTextSize() * 3 / 2,
                luxDown.top + luxDown.height() * 3 / 4, mPanit);
        canvas.drawBitmap(bw, orSmall, w, mPanit);
        canvas.drawText("W", w.width() / 2 + w.left - mPanit.getTextSize() / 2,
                w.top + w.height() * 3 / 4, mPanit);
        canvas.drawBitmap(br, orSmall, r, mPanit);
        canvas.drawText("R", r.width() / 2 + r.left - mPanit.getTextSize() / 2,
                r.top + r.height() * 3 / 4, mPanit);
        canvas.drawBitmap(bg, orSmall, g, mPanit);
        canvas.drawText("G", g.width() / 2 + g.left - mPanit.getTextSize() / 2,
                g.top + g.height() * 3 / 4, mPanit);
        canvas.drawBitmap(bb, orSmall, b, mPanit);
        canvas.drawText("B", b.width() / 2 + b.left - mPanit.getTextSize() / 2,
                b.top + b.height() * 3 / 4, mPanit);
        canvas.drawBitmap(bflash, orSmall, flash, mPanit);
        canvas.drawText("闪光", flash.width() / 2 + flash.left - mPanit.getTextSize(),
                flash.top + flash.height() * 3 / 4, mPanit);
        canvas.drawBitmap(bstrobe, orSmall, strobe, mPanit);
        canvas.drawText("频闪", strobe.width() / 2 + strobe.left - mPanit.getTextSize(),
                strobe.top + strobe.height() * 3 / 4, mPanit);

        canvas.drawBitmap(bfade, orSmall, fade, mPanit);
        canvas.drawText("渐变", fade.width() / 3 + fade.left - mPanit.getTextSize(),
                fade.top + fade.height() * 3 / 4, mPanit);
        canvas.drawBitmap(bsmooth, orSmall, smooth, mPanit);
        canvas.drawText("平滑", smooth.width() / 3 + smooth.left - mPanit.getTextSize(),
                smooth.top + smooth.height() * 3 / 4, mPanit);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getPaddingLeft() + getPaddingRight() + dstBG.width();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + dstBG.height() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (open.right > x && x > open.left && y > open.top && y < open.bottom) {
                    bOpen = bigUP;
                    if (isopen) {

                        isopen = false;
                    }
                } else if (close.right > x && x > close.left && y > close.top && y < close.bottom) {
                    bClose = bigUP;
                    if (isclose) {
                        isclose = false;
                    }
                } else if (luxUp.right > x && x > luxUp.left && y > luxUp.top && y < luxUp.bottom) {
                    bluxUp = smallUP;
                    if (isluxUp) {
                        isluxUp = false;
                    }
                } else if (luxDown.right > x && x > luxDown.left &&
                        y > luxDown.top && y < luxDown.bottom) {
                    bluxDown = smallUP;
                    if (isluxDown) {
                        isluxDown = false;
                    }
                } else if (r.right > x && x > r.left && y > r.top && y < r.bottom) {
                    br = smallUP;
                    if (isr) {
                        isr = false;
                    }
                } else if (g.right > x && x > g.left && y > g.top && y < g.bottom) {
                    bg = smallUP;
                    if (isg) {
                        isg = false;
                    }
                } else if (b.right > x && x > b.left && y > b.top && y < b.bottom) {
                    bb = smallUP;
                    if (isb) {
                        isb = false;
                    }
                } else if (w.right > x && x > w.left && y > w.top && y < w.bottom) {
                    bw = smallUP;
                    if (isw) {
                        isw = false;
                    }
                } else if (flash.right > x && x > flash.left && y > flash.top && y < flash.bottom) {
                    bflash = smallUP;
                    if (isflash) {
                        isflash = false;
                    }
                } else if (strobe.right > x && x > strobe.left &&
                        y > strobe.top && y < strobe.bottom) {
                    bstrobe = smallUP;
                    if (isstrobe) {
                        isstrobe = false;
                    }
                } else if (fade.right > x && x > fade.left && y > fade.top && y < fade.bottom) {
                    bfade = smallUP;
                    if (isstrobe) {
                        isstrobe = false;
                    }
                } else if (smooth.right > x && x > smooth.left &&
                        y > smooth.top && y < smooth.bottom) {
                    bsmooth = smallUP;
                    if (issmooth) {
                        issmooth = false;
                    }
                } else {
                    bluxDown = bluxUp = br = bg = bb = bw = smallUP;
                    bflash = bstrobe = bfade = bsmooth = smallUP;
                    isluxDown = isluxUp = isr = isg = isb = isw = isflash = false;
                    isstrobe = isfade = issmooth = false;
                }

                break;
            case MotionEvent.ACTION_DOWN:
                if (open.right > x && x > open.left && y > open.top && y < open.bottom) {
                    bOpen = bigDown;
                    isopen = true;
                } else if (close.right > x && x > close.left && y > close.top && y < close.bottom) {
                    bClose = bigDown;
                    isclose = true;
                } else if (luxUp.right > x && x > luxUp.left && y > luxUp.top && y < luxUp.bottom) {
                    bluxUp = smallDown;
                    isluxUp = true;
                } else if (luxDown.right > x && x > luxDown.left &&
                        y > luxDown.top && y < luxDown.bottom) {
                    bluxDown = smallDown;
                    isluxDown = true;
                } else if (r.right > x && x > r.left && y > r.top && y < r.bottom) {
                    br = smallDown;
                    isr = true;
                } else if (g.right > x && x > g.left && y > g.top && y < g.bottom) {
                    bg = smallDown;
                    isg = true;
                } else if (b.right > x && x > b.left && y > b.top && y < b.bottom) {
                    bb = smallDown;
                    isb = true;
                } else if (w.right > x && x > w.left && y > w.top && y < w.bottom) {
                    bw = smallDown;
                    isw = true;
                } else if (flash.right > x && x > flash.left && y > flash.top && y < flash.bottom) {
                    bflash = smallDown;
                    isflash = true;
                } else if (strobe.right > x && x > strobe.left &&
                        y > strobe.top && y < strobe.bottom) {
                    bstrobe = smallDown;
                    isstrobe = true;
                } else if (fade.right > x && x > fade.left && y > fade.top && y < fade.bottom) {
                    bfade = smallDown;
                    isfade = true;
                } else if (smooth.right > x && x > smooth.left &&
                        y > smooth.top && y < smooth.bottom) {
                    bsmooth = smallDown;
                    issmooth = true;
                }
                break;
        }
        invalidate();
        return true;
    }
}

