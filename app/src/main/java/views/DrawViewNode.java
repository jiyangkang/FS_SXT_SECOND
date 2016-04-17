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
import android.view.View;

import com.hqyj.dev.ji.fs_sxt.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nodes.Node;
import nodes.NodeInfo;
import project.Project;

/**
 * Created by jiyangkang on 2016/4/17 0017.
 */
public class DrawViewNode extends View {
    private Bitmap bitmap;
    private String mName;
    private Paint mPaint;
    private String projectType;
    private Rect orRect, dstRect;
    private Bitmap bitmapBlank;
    private StringBuilder valueString;


    private Node node;

    private int which = -1;

    private Context mContext;

    public DrawViewNode(Context context) {
        this(context, null);
    }

    public DrawViewNode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setmName(String mName) {
        this.mName = mName;
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), NodeInfo.viewNodeDrawable.get(mName)[which]);
        node = Project.getProject().getNodeSet().get(mName);
        node.setmOnValueReceived(new Node.OnValueReceived() {
            @Override
            public void onValueReceived(HashMap<String, String> value) {
                node.setmOnValueReceived(new Node.OnValueReceived() {
                    @Override
                    public void onValueReceived(HashMap<String, String> value) {
                        for (Object o : value.entrySet()) {
                            Map.Entry entry = (Map.Entry) o;
                            valueString.append(entry.getKey()).append(":")
                                    .append(entry.getValue()).append(".\n");
                        }
                    }
                });
                invalidate();
            }
        });
    }

    public Node getNode() {
        return node;
    }

    public DrawViewNode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.DrawViewNode, 0, 0);
        projectType = typedArray.getString(R.styleable.DrawViewNode_project);
        typedArray.recycle();

        int withMetrics = context.getResources().getDisplayMetrics().widthPixels;
        mPaint = new Paint();
        mPaint.setTextSize(18 * withMetrics / 1024);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        if (projectType != null) {
            switch (projectType) {
                case NodeInfo.PROJECTAG:
                    which = 0;
                    break;
                case NodeInfo.PROJECTHS:
                    which = 1;
                    break;
                default:
                    which = 0;
                    break;
            }
        }


        bitmapBlank = BitmapFactory.decodeResource(context.getResources(),
                NodeInfo.viewNodeDrawable.get("blank")[which]);
        bitmap = bitmapBlank;

        int ox = bitmap.getWidth();
        int oy = bitmap.getHeight();

        int rx, ry;

        rx = withMetrics * 180 / 1024;
        ry = rx * oy / ox;

        orRect = new Rect(0, 0, ox, oy);
        dstRect = new Rect(0, 0, rx, ry);

        valueString = new StringBuilder();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, orRect, dstRect, mPaint);
        if (valueString != null) {
            canvas.drawText(valueString.toString(), mPaint.getTextSize(), dstRect.height() / 2, mPaint);
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

}
