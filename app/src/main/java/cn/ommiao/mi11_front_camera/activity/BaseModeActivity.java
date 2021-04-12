package cn.ommiao.mi11_front_camera.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import cn.ommiao.mi11_front_camera.BaseCoverManager;
import cn.ommiao.mi11_front_camera.R;
import cn.ommiao.mi11_front_camera.ToastUtil;

public abstract class BaseModeActivity extends BaseActivity {

    protected SharedPreferences sp;
    private ImageView ivSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        sp = getSharedPreferences("data", MODE_PRIVATE);
        ivSwitcher = findViewById(R.id.iv_swicher);
        refreshSwitcher(coverManager().isShow());
        ivSwitcher.setOnClickListener(v -> {
            refreshSwitcher(coverManager().switchState());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isServiceOn()){
            ToastUtil.shortToast(this, getString(R.string.tips_open_accessibility_server));
            finish();
        }
    }

    protected void refreshSwitcher(boolean show){
        ivSwitcher.setAlpha(show ? 0.5f : 1f);
    }

    protected abstract BaseCoverManager coverManager();

}
