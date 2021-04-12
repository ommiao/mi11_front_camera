package cn.ommiao.mi11_front_camera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public abstract class BaseCoverManager {

    private WindowManager mWindowManager;

    private View floatView;

    private WindowManager.LayoutParams params;

    private boolean show = false;

    private Context mContext;

    public BaseCoverManager(Context context){
        this.mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        floatView = getView();
        initWindowParams();
    }

    protected Context getContext() {
        return mContext;
    }

    protected abstract View getView();

    private void initWindowParams() {
        params = new WindowManager.LayoutParams();
        params.gravity = getGravity();
        params.x = getStartX();
        params.y = getStartY();
        //总是出现在应用程序窗口之上
        params.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        //设置图片格式，效果为背景透明
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    protected int getStartX(){
        return 0;
    }

    protected int getStartY(){
        return 0;
    }

    protected int getGravity(){
        return Gravity.TOP | Gravity.CENTER_HORIZONTAL;
    }

    public View getFloatView() {
        return floatView;
    }

    public void show() {
        try {
            mWindowManager.addView(floatView, params);
            show = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hide() {
        try {
            mWindowManager.removeView(floatView);
            show = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean switchState(){
        if(show){
            hide();
        } else {
            show();
        }
        return show;
    }

    public boolean isShow() {
        return show;
    }
}
