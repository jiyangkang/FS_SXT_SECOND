package views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hqyj.dev.ji.fs_sxt.R;

import nodes.NodeInfo;
import nodes.Omnidirectional;

/**
 * Created by jiyangkang on 2016/4/22 0022.
 */
public class DrawCrrButton extends View {

    private Context mContext;

    private Paint mPanit;
    private Bitmap bitmap;
    private int withMetrics;

    private Bitmap btn, btnDown;
    private Bitmap bstop, bopen, bclose;

    private Rect orBG, orDown, dstBG, orBtn, dstBtn;
    private Rect dstOpen, dstClose, dstStop;
    private boolean isStop, isClose, isOpen;


    public DrawCrrButton(Context context) {
        this(context, null);
    }

    public DrawCrrButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCrrButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        withMetrics = context.getResources().getDisplayMetrics().widthPixels;
        mPanit = new Paint();
        mPanit.setTextSize(18 * withMetrics / 1024);
        mPanit.setColor(Color.BLACK);
        mPanit.setAntiAlias(true);
        mPanit.setFlags(Paint.ANTI_ALIAS_FLAG);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.currtain);
        int ox = bitmap.getWidth();
        int oy = bitmap.getHeight();
        orBG = new Rect(0, 0, ox, oy);
        int rx, ry;
        rx = withMetrics * 220 / 1024;
        ry = rx * oy / ox;
        dstBG = new Rect(0, 0, rx, ry);

        btn = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigup);
        int orbx = btn.getWidth();
        int orby = btn.getHeight();
        orBtn = new Rect(0, 0, orbx, orby);

        int dstbx = withMetrics * 180 / 1024;
        int dstby = dstbx * orby / orbx * 4 / 5;

        dstBtn = new Rect(dstBG.left + dstBG.width() / 2 - dstbx / 2, dstBG.height() / 2, dstBG.right + dstbx / 2 - dstBG.width() / 2, dstBG.height() / 2 + dstby);

        btnDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigdown);
        bstop = bclose = bopen = null;

        int orcx = btnDown.getWidth();
        int orcy = btnDown.getHeight();
        orDown = new Rect(0, 0, orcx, orcy);

        dstOpen = new Rect(dstBtn.left, dstBtn.top, dstBtn.width() / 3 + dstBtn.left, dstBtn.bottom);
        dstStop = new Rect(dstBtn.width() / 3 + dstBtn.left, dstBtn.top, dstBtn.width() * 2 / 3 + dstBtn.left, dstBtn.bottom);
        dstClose = new Rect(dstBtn.width() * 2 / 3 + dstBtn.left, dstBtn.top, dstBtn.right, dstBtn.bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, orBG, dstBG, mPanit);
        canvas.drawBitmap(btn, orBtn, dstBtn, mPanit);
        canvas.drawText("开", dstOpen.left + dstOpen.width() / 2 - mPanit.getTextSize() / 2, dstOpen.height() * 3 / 5 + dstOpen.top, mPanit);
        canvas.drawText("关", dstClose.left + dstClose.width() / 2 - mPanit.getTextSize() / 2, dstClose.height() * 3 / 5 + dstClose.top, mPanit);
        canvas.drawText("停", dstStop.left + dstStop.width() / 2 - mPanit.getTextSize() / 2, dstStop.height() * 3 / 5 + dstClose.top, mPanit);
        if (bstop != null) {
            canvas.drawBitmap(bstop, orDown, dstStop, mPanit);
        }

        if (bopen != null) {
            canvas.drawBitmap(bopen, orDown, dstOpen, mPanit);
        }

        if (bclose != null) {
            canvas.drawBitmap(bclose, orDown, dstClose, mPanit);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
            case MotionEvent.ACTION_DOWN:
                if (x > dstOpen.left && x < dstOpen.right && y > dstOpen.top && y < dstOpen.bottom) {
                    bopen = btnDown;
                    isOpen = true;
                } else if (x > dstStop.left && x < dstStop.right && y > dstStop.top && y < dstStop.bottom) {
                    bstop = btnDown;
                    isStop = true;
                } else if (x > dstClose.left && x < dstClose.right && y > dstClose.top && y < dstClose.bottom) {
                    bclose = btnDown;
                    isClose = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (x > dstOpen.left && x < dstOpen.right && y > dstOpen.top && y < dstOpen.bottom) {
                    if (isOpen) {
                        bopen = null;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.CURTAIN_ON);
                        isOpen = false;
                    }
                } else if (x > dstStop.left && x < dstStop.right && y > dstStop.top && y < dstStop.bottom) {
                    if (isStop) {
                        bstop = null;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.CURTAIN_STOP);
                        isStop = false;
                    }
                } else if (x > dstClose.left && x < dstClose.right && y > dstClose.top && y < dstClose.bottom) {
                    if (isClose) {
                        bclose = null;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.CURTAIN_STOP);
                        isClose = false;
                    }
                } else {
                    isClose = isOpen = isStop = false;
                    bclose = bstop = bopen = null;
                }
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }
}
