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
public class DrawSocketButton extends View {

    private Context mContext;
    protected Paint mPanit;
    private int withMetrics;

    private Bitmap bitmap;
    private Bitmap bigUP, bigDown;
    private Rect orBG, dstBG;
    private Rect orBig;

    private Rect open, close, eng, watt, up, down, quit;
    private boolean isopen, isclose, iseng, iswatt, isup, isdown, isquit;
    private Bitmap bopen, bclose, beng, bwatt, bup, bdown, bquit;


    public DrawSocketButton(Context context) {
        this(context, null);
    }

    public DrawSocketButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawSocketButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        withMetrics = context.getResources().getDisplayMetrics().widthPixels;
        mPanit = new Paint();
        mPanit.setTextSize(18 * withMetrics / 1024);
        mPanit.setColor(Color.BLACK);
        mPanit.setAntiAlias(true);
        mPanit.setFlags(Paint.ANTI_ALIAS_FLAG);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.smart_socket);
        int ox = bitmap.getWidth();
        int oy = bitmap.getHeight();
        orBG = new Rect(0, 0, ox, oy);
        int rx, ry;
        rx = withMetrics * 220 / 1024;
        ry = rx * oy / ox;
        dstBG = new Rect(0, 0, rx, ry);

        bigUP = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigup);
        bigDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigdown);


        bopen = bclose = beng = bwatt = bup = bdown = bquit = bigUP;

        int orbx = bigUP.getWidth();
        int orby = bigUP.getHeight();

        orBig = new Rect(0, 0, orbx, orby);
        int dstbx = withMetrics * 90 / 1024;
        int dstby = dstbx * orby / orbx;
        open = new Rect(rx * 12 / 220, dstBG.height() / 4, dstbx + rx * 12 / 220, dstby + dstBG.height() / 4);
        close = new Rect(dstBG.width() - rx * 12 / 220 - dstbx, dstBG.height() / 4,
                dstBG.width() - rx * 12 / 220, dstby + dstBG.height() / 4);

        eng = new Rect(open.left, open.bottom + dstby, open.right, open.bottom + 2 * dstby);
        watt = new Rect(close.left, eng.top, close.right, eng.bottom);

        up = new Rect(open.left, eng.bottom+dstby, open.right, eng.bottom+2*dstby);
        down = new Rect(close.left, up.top, close.right, up.bottom);

        quit = new Rect(dstBG.width()/2 - dstbx/2, up.bottom+dstby, dstBG.width()/2+dstbx/2, up.bottom+2*dstby);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPanit.setTextSize(18 * withMetrics / 1024);
        canvas.drawBitmap(bitmap, orBG, dstBG, mPanit);

        canvas.drawBitmap(bopen, orBig, open, mPanit);
        canvas.drawText("合闸", open.width()/2 + open.left - mPanit.getTextSize(), open.top + open.height()*3/4, mPanit);

        canvas.drawBitmap(bclose, orBig, close, mPanit);
        canvas.drawText("开闸", close.width()/2 + close.left - mPanit.getTextSize(), close.top + close.height()*3/4, mPanit);

        canvas.drawBitmap(beng, orBig, eng, mPanit);
        canvas.drawText("电能显示", eng.width()/2 + eng.left - mPanit.getTextSize()*2, eng.top + eng.height()*3/4, mPanit);

        canvas.drawBitmap(bwatt, orBig, watt, mPanit);
        canvas.drawText("功率", watt.width()/2 + watt.left - mPanit.getTextSize(), watt.top + watt.height()*3/4, mPanit);

        canvas.drawBitmap(bup, orBig, up, mPanit);
        canvas.drawText("上翻", up.width()/2 + up.left - mPanit.getTextSize(), up.top + up.height()*3/4, mPanit);

        canvas.drawBitmap(bdown, orBig, down, mPanit);
        canvas.drawText("下翻", down.width()/2 + down.left - mPanit.getTextSize(), down.top + down.height()*3/4, mPanit);

        canvas.drawBitmap(bquit, orBig, quit, mPanit);
        canvas.drawText("退出", quit.width()/2 + quit.left - mPanit.getTextSize(), quit.top + quit.height()*3/4, mPanit);
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
            case MotionEvent.ACTION_DOWN:
                if (x > open.left && x < open.right && y > open.top && y < open.bottom){
                    bopen = bigDown;
                    isopen = true;
                }else if (x > close.left && x < close.right && y > close.top && y < close.bottom){
                    bclose = bigDown;
                    isopen = true;
                }else if (x > eng.left && x < eng.right && y > eng.top && y < eng.bottom){
                    beng = bigDown;
                    iseng = true;
                }else if (x > watt.left && x < watt.right && y > watt.top && y < watt.bottom){
                    bwatt = bigDown;
                    iswatt = true;
                }else if (x > up.left && x < up.right && y > up.top && y < up.bottom){
                    bup = bigDown;
                    isup = true;
                }else if (x > down.left && x < down.right && y > down.top && y < down.bottom){
                    bdown = bigDown;
                    isdown = true;
                }else if (x > quit.left && x < quit.right && y > quit.top && y < quit.bottom){
                    bquit = bigDown;
                    isquit = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (x > open.left && x < open.right && y > open.top && y < open.bottom){
                    bopen = bigUP;
                    if (isopen){
                        isopen = false;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.SOCKET_ON);
                    }
                }else if (x > close.left && x < close.right && y > close.top && y < close.bottom){
                    bclose = bigUP;
                    if (isclose){
                        isclose = false;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.SOCKET_OFF);
                    }
                }else if (x > eng.left && x < eng.right && y > eng.top && y < eng.bottom){
                    beng = bigUP;
                    if (iseng){
                        iseng = false;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.SOCKET_ENERGY);
                    }
                }else if (x > watt.left && x < watt.right && y > watt.top && y < watt.bottom){
                    bwatt = bigUP;
                    if (iswatt){
                        iswatt = false;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.SOCKET_WATE);
                    }
                }else if (x > up.left && x < up.right && y > up.top && y < up.bottom){
                    bup = bigUP;
                    if (isup){
                        isup = false;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.SOCKET_UP);
                    }
                }else if (x > down.left && x < down.right && y > down.top && y < down.bottom){
                    bdown = bigUP;
                    if (isdown){
                        isdown = false;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.SOCKET_DOWN);
                    }
                }else if (x > quit.left && x < quit.right && y > quit.top && y < quit.bottom){
                    bquit = bigUP;
                    if (isquit){
                        isquit = false;
                        Omnidirectional.getOmnidirectional().getmSendCMD().sendCMD(NodeInfo.SOCKET_QUIT);
                    }
                }else {
                    bopen = bclose = beng = bwatt = bup = bdown = bquit = bigUP;
                    isopen = isclose = iseng = iswatt = isup = isdown = isquit = false;
                }

                break;
            default:
                break;
        }
        invalidate();
        return true;
    }
}
