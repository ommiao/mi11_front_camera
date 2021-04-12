package cn.ommiao.mi11_front_camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class VirtualCamera extends View {

    private final Paint mPaint = new Paint();

    public VirtualCamera(Context context) {
        this(context, null);
    }

    public VirtualCamera(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VirtualCamera(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public VirtualCamera(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mPaint.setColor(Color.BLACK);

        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeWidth(10f);

        mPaint.setDither(true);

        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(100, 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(50, 50, 10, mPaint);
        canvas.drawCircle(50, 50, 30, mPaint);
    }
}
