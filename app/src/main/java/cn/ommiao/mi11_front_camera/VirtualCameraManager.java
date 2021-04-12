package cn.ommiao.mi11_front_camera;

import android.content.Context;
import android.view.View;

public class VirtualCameraManager extends BaseCoverManager {

    private View iv;

    public VirtualCameraManager(Context context) {
        super(context);
    }

    @Override
    protected View getView() {
        if(iv == null){
            iv = new VirtualCamera(getContext());
        }
        return iv;
    }

    public void setViewAlpha(float alpha){
        iv.setAlpha(alpha);
    }

    public void setTranslationX(int translationX){
        iv.setTranslationX(translationX);
    }

    public void setTranslationY(int translationY){
        iv.setTranslationY(translationY);
    }
}
