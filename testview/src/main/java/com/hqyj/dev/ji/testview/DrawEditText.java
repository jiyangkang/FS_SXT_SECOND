package com.hqyj.dev.ji.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 *
 * Created by jiyangkang on 2016/4/23 0023.
 */
public class DrawEditText extends EditText {

    public DrawEditText(Context context) {
        super(context);
    }

    public DrawEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
