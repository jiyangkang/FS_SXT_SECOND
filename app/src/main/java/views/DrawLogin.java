package views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jiyangkang on 2016/4/23 0023.
 */
public class DrawLogin extends View{

    public DrawLogin(Context context) {
        this(context, null);
    }

    public DrawLogin(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawLogin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


}
