package cn.ommiao.mi11_front_camera;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;

import static cn.ommiao.mi11_front_camera.activity.VirtualCameraActivity.SP_KEY_CAMERA_ALPHA;
import static cn.ommiao.mi11_front_camera.activity.VirtualCameraActivity.SP_KEY_CAMERA_T_X;
import static cn.ommiao.mi11_front_camera.activity.VirtualCameraActivity.SP_KEY_CAMERA_T_Y;

public class FloatService extends AccessibilityService {

    protected SharedPreferences sp;

    @SuppressLint("StaticFieldLeak")
    private static VirtualCameraManager virtualCameraManager;

    @Override
    protected void onServiceConnected() {
        virtualCameraManager = new VirtualCameraManager(this);
        virtualCameraManager.show();
        sp = getSharedPreferences("data", MODE_PRIVATE);
        float alpha = sp.getFloat(SP_KEY_CAMERA_ALPHA, 0.5f);
        int translationX = sp.getInt(SP_KEY_CAMERA_T_X, 0);
        int translationY = sp.getInt(SP_KEY_CAMERA_T_Y, 0);
        virtualCameraManager.setViewAlpha(alpha);
        virtualCameraManager.setTranslationX(translationX);
        virtualCameraManager.setTranslationY(translationY);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
        return super.startInstrumentation(className, profileFile, arguments);
    }

    public static VirtualCameraManager getVirtualCameraManager(){
        return virtualCameraManager;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            virtualCameraManager.show();
        } else {
            virtualCameraManager.hide();
        }
    }

}
