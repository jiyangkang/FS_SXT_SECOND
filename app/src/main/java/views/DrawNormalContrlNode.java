package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hqyj.dev.ji.fs_sxt.R;

import java.util.HashMap;
import java.util.Map;

import nodes.Node;
import nodes.NodeInfo;
import project.Project;

/**
 * Created by jiyangkang on 2016/4/18 0018.
 */
public class DrawNormalContrlNode extends View {
    private Context mContext;
    private Paint mPaint;
    private String mName;
    private Bitmap bitmap;
    private Bitmap button, buttonDisable, buttonOpenEnable, buttonCloseEnable;

    private String value;

    private Node node;

    private int which = -1;

    private Rect orRect, dstRect;
    private Rect obRect, dbRect;
    private OnReceived onReceived;
    private boolean clickable = false;

    public static interface OnReceived {
        void received(StringBuilder stringBuilder);
    }

    public void setOnReceived(OnReceived onReceived) {
        this.onReceived = onReceived;
    }

    public void setmName(String mName) {
        this.mName = mName;
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), NodeInfo.normalCtrlDrable.get(mName));
        node = Project.getProject().getNodeSet().get(mName);
//        Log.d(mName, mName);

        if (node == null) {
            Log.d(mName, mName);
        }

        if (node != null) {
            node.setmOnValueReceived(new Node.OnValueReceived() {
                @Override
                public void onValueReceived(HashMap<String, String> value) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Object o : value.entrySet()) {
                        Map.Entry entry = (Map.Entry) o;
                        stringBuilder.append(entry.getKey())
                                .append(":").append(entry.getValue()).append('\n');

                        if (entry.getValue().equals("打开")) {
                            button = buttonCloseEnable;
                        } else {
                            button = buttonOpenEnable;
                        }
                        clickable = true;
                    }

                    if (onReceived != null) {
                        onReceived.received(stringBuilder);
                    }
                }
            });
            node.setmIsConnect(new Node.IsConnect() {
                @Override
                public void isConnect(boolean isConncet) {
                    if (onReceived != null && !isConncet) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("断开连接");
                        button = buttonDisable;
                        clickable = false;
                        onReceived.received(stringBuilder);
                    }
                }
            });
        }
    }

    public DrawNormalContrlNode(Context context) {
        this(context, null);
    }

    public DrawNormalContrlNode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawNormalContrlNode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.DrawNormalContrlNode, 0, 0);
        mName = typedArray.getString(R.styleable.DrawNormalContrlNode_controlpn);
        typedArray.recycle();
        setmName(mName);

        int withMetrics = context.getResources().getDisplayMetrics().widthPixels;
        mPaint = new Paint();
        mPaint.setTextSize(18 * withMetrics / 1024);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        bitmap = BitmapFactory.decodeResource(context.getResources(), NodeInfo.normalCtrlDrable.get(mName));

        int ox = bitmap.getWidth();
        int oy = bitmap.getHeight();

        int rx, ry;

        rx = withMetrics * 256 / 1024;
        ry = rx * oy / ox;

        orRect = new Rect(0, 0, ox, oy);
        dstRect = new Rect(0, 0, rx, ry);

        buttonCloseEnable = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_close_able);
        buttonOpenEnable = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_open_able);
        buttonDisable = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_dis);

        button = buttonDisable;
        int obx = button.getWidth();
        int oby = button.getHeight();

        int rbx, rby;
        rbx = withMetrics * 187 / 1024;
        rby = rbx * oby / obx;
        obRect = new Rect(0, 0, rbx, rby);
        dbRect = new Rect((dstRect.width() - rbx) / 2, dstRect.height() / 2, (dstRect.width() + rbx) / 2, dstRect.height() + obRect.height());
        value = "未连接";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, orRect, dstRect, mPaint);
        canvas.drawBitmap(button, obRect, dbRect, mPaint);
        if (value != null) {
            canvas.drawText(value, 2 * dstRect.width() / 3, dstRect.height() / 4, mPaint);
        }
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
            width = getPaddingLeft() + getPaddingRight() + dstRect.width();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + dstRect.height() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        Bitmap record = button;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (clickable && (x > dbRect.left && x < dbRect.right && y > dbRect.top && y < dbRect.bottom)) {
                    button = buttonDisable;
                }
                break;
            case MotionEvent.ACTION_UP:
                button = record;
                if (clickable && (x > dbRect.left && x < dbRect.right && y > dbRect.top && y < dbRect.bottom)) {
                    if (record == buttonOpenEnable) {
                        node.getmSendCMD().sendCMD(0x31);
                    } else if (record == buttonDisable) {
                        node.getmSendCMD().sendCMD(0x30);
                    }
                }
                break;
        }

        return true;
    }
}
