package cn.ommiao.mi11_front_camera.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import cn.ommiao.mi11_front_camera.FloatService;
import cn.ommiao.mi11_front_camera.R;
import cn.ommiao.mi11_front_camera.ToastUtil;
import cn.ommiao.mi11_front_camera.UiUtil;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusBar();
        setContentView(getLayoutId());
        initViews();
        initData();
    }

    protected abstract void initViews();

    protected void initData(){}

    private void transparentStatusBar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//表示让应用主题内容占据系统状态栏的空间
        decorView.setSystemUiVisibility(option);
        int vis = getWindow().getDecorView().getSystemUiVisibility();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getStatusBarColor());//设置状态栏颜色为透明
            if(isStatusBarForeDark()){
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getWindow().setNavigationBarColor(getNavigationBarColor());
            if (isNavigationBarForeDark()) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;  // 黑色
            } else {
                //白色
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
        }
        getWindow().getDecorView().setSystemUiVisibility(vis);
        ActionBar actionBar  = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected int getNavigationBarColor() {
        return Color.WHITE;
    }

    protected boolean isNavigationBarForeDark() {
        return true;
    }

    protected boolean isStatusBarForeDark() {
        return true;
    }

    protected abstract int getLayoutId();

    protected int getStatusBarColor(){
        return Color.TRANSPARENT;
    }

    protected int getStatusBarHeight(){
        return UiUtil.getStatusBarHeight(this);
    }

    protected void initStatusBar(int resId) {
        View statusBar = findViewById(resId);
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = getStatusBarHeight();
        statusBar.setLayoutParams(layoutParams);
    }

    public void openAccessibilityPage(){
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isServiceOn() {
        boolean on = false;
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + FloatService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(
                    getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        on = true;
                    }
                }
            }
        }
        if(!on){
            ToastUtil.shortToast(this, getString(R.string.tips_open_accessibility_server));
            openAccessibilityPage();
        }
        return on;
    }
}
