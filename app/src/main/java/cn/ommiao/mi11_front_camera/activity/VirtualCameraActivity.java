package cn.ommiao.mi11_front_camera.activity;

import android.os.Build;
import android.os.Handler;
import android.widget.SeekBar;

import cn.ommiao.mi11_front_camera.FloatService;
import cn.ommiao.mi11_front_camera.R;
import cn.ommiao.mi11_front_camera.VirtualCameraManager;
import cn.ommiao.mi11_front_camera.widget.LongPressImageView;


public class VirtualCameraActivity extends BaseModeActivity implements LongPressImageView.PressHoldListener {

    public static final String SP_KEY_CAMERA_ALPHA = "camera_alpha";
    public static final String SP_KEY_CAMERA_T_X = "burn_t_x";
    public static final String SP_KEY_CAMERA_T_Y = "burn_t_y";

    private float alpha;

    private int translationX, translationY;

    @Override
    protected void initViews() {
        if(!isServiceOn()){
            finish();
            return;
        }
        super.initViews();
        alpha = sp.getFloat(SP_KEY_CAMERA_ALPHA, 0.5f);
        translationX = sp.getInt(SP_KEY_CAMERA_T_X, 0);
        translationY = sp.getInt(SP_KEY_CAMERA_T_Y, 0);
        coverManager().setViewAlpha(alpha);
        coverManager().setTranslationX(translationX);
        coverManager().setTranslationY(translationY);

        Handler handler = new Handler();

        LongPressImageView ivUp = findViewById(R.id.iv_up);
        ivUp.setOnClickListener(v -> {
            up();
        });
        ivUp.setHandler(handler);
        ivUp.setPressHoldListener(this);

        LongPressImageView ivDown = findViewById(R.id.iv_down);
        ivDown.setOnClickListener(v -> {
            down();
        });
        ivDown.setHandler(handler);
        ivDown.setPressHoldListener(this);


        LongPressImageView ivLeft = findViewById(R.id.iv_left);
        ivLeft.setOnClickListener(v -> {
            left();
        });
        ivLeft.setHandler(handler);
        ivLeft.setPressHoldListener(this);


        LongPressImageView ivRight = findViewById(R.id.iv_right);
        ivRight.setOnClickListener(v -> {
            right();
        });
        ivRight.setHandler(handler);
        ivRight.setPressHoldListener(this);

        SeekBar seekBar = findViewById(R.id.seek_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            seekBar.setProgress((int) (alpha * 100), true);
        } else {
            seekBar.setProgress((int) (alpha * 100));
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alpha = progress / 100f;
                coverManager().setViewAlpha(alpha);
                sp.edit().putFloat(SP_KEY_CAMERA_ALPHA, alpha).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void right() {
        translationX++;
        coverManager().setTranslationX(translationX);
        sp.edit().putInt(SP_KEY_CAMERA_T_X, translationX).apply();
    }

    private void left() {
        translationX--;
        coverManager().setTranslationX(translationX);
        sp.edit().putInt(SP_KEY_CAMERA_T_X, translationX).apply();
    }

    private void down() {
        translationY++;
        coverManager().setTranslationY(translationY);
        sp.edit().putInt(SP_KEY_CAMERA_T_Y, translationY).apply();
    }

    private void up() {
        translationY--;
        coverManager().setTranslationY(translationY);
        sp.edit().putInt(SP_KEY_CAMERA_T_Y, translationY).apply();
    }

    @Override
    protected VirtualCameraManager coverManager() {
        return FloatService.getVirtualCameraManager();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_burn;
    }

    @Override
    public void stillPress(int viewId) {
        switch (viewId){
            case R.id.iv_up:
                up();
                break;
            case R.id.iv_down:
                down();
                break;
            case R.id.iv_left:
                left();
                break;
            case R.id.iv_right:
                right();
                break;
        }
    }
}
