package cn.ommiao.mi11_front_camera.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("AlibabaAvoidUseTimer")
public class LongPressImageView extends ImageView {

    private static final int delayMillis = 500;

    private Timer timer;
    private Handler handler;

    private Runnable r = () -> {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(pressHoldListener != null){
                    pressHoldListener.stillPress(getId());
                }
            }
        }, 0, 3);
    };

    public LongPressImageView(Context context) {
        super(context);
    }

    public LongPressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LongPressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LongPressImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if(timer != null){
                    timer.cancel();
                }
                handler.removeCallbacks(r);
                break;
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(r);
                handler.postDelayed(r, delayMillis);
                break;
        }
        return super.onTouchEvent(event);
    }

    private PressHoldListener pressHoldListener;

    public void setPressHoldListener(PressHoldListener pressHoldListener) {
        this.pressHoldListener = pressHoldListener;
    }

    public interface PressHoldListener {
        void stillPress(int viewId);
    }
}
