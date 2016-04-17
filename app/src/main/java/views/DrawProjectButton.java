package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hqyj.dev.ji.fs_sxt.R;

import nodes.NodeInfo;

/**
 *
 * Created by jiyangkang on 2016/4/15 0015.
 */
public class DrawProjectButton extends View {
    private Bitmap buttonBitmapEnable, buttonBitmapDisable;
    private Bitmap bitmap;
    private String mName;

    private boolean clickAble = false;

    private Context mContext;
    private Paint mPaint;
    private Rect orRect, dstRect;


    public DrawProjectButton(Context context) {
        this(context, null);
    }

    public DrawProjectButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }



    public DrawProjectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.DrawProjectButton, 0, 0);

        int withMetrics = context.getResources().getDisplayMetrics().widthPixels;
        mPaint = new Paint();
        mPaint.setTextSize(18);
        mPaint.setStrokeWidth(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);



        mName = typedArray.getString(R.styleable.DrawProjectButton_name);
        typedArray.recycle();
        Log.d("DRAWPB", mName);
        buttonBitmapEnable = BitmapFactory.decodeResource(getResources(),
                NodeInfo.projectMaplist.get(mName)[0]);
        buttonBitmapDisable = BitmapFactory.decodeResource(getResources(),
                NodeInfo.projectMaplist.get(mName)[1]);
        bitmap = buttonBitmapDisable;

        int ox = bitmap.getWidth();
        int oy = bitmap.getHeight();

        int rx, ry;

        rx = withMetrics*130/1024;
        ry = rx*oy/ox;

        orRect = new Rect(0, 0, ox, oy);
        dstRect = new Rect(0, 0, rx, ry);
        clickAble = false;
    }

    public void onProjectChange(String name){
        if (name.equalsIgnoreCase(mName)){
            Log.d(mName, name);
            bitmap = buttonBitmapEnable;
            setClickAble(true);
        } else {
            Log.d(mName, name);
            bitmap = buttonBitmapDisable;
            setClickAble(false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, orRect, dstRect, mPaint);
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
            width = getPaddingLeft() + getPaddingRight() + dstRect.width();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + dstRect.height() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    public boolean isClickAble() {
        return clickAble;
    }

    public void setClickAble(boolean clickAble) {
        this.clickAble = clickAble;
    }
}
