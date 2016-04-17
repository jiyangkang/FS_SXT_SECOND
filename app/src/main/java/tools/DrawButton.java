package tools;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import project.Project;

/**
 * Created by jiyangkang on 2016/4/15 0015.
 */
public class DrawButton extends View{
    private Project mProject;

    public DrawButton(Context context) {
        super(context);
    }

    public DrawButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Project mProject = Project.getProject();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
