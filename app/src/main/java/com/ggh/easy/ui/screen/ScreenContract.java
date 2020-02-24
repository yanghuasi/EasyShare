package com.ggh.easy.ui.screen;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ggh.easy.base.BaseView;

public class ScreenContract {
    public final static int PREPAR = 1001;
    public static int STARD_RECORD = 1002;
    public static int RECORDING = 1003;
    public static int STOP_RECORD = 1004;
    public static int DESTORY = 1005;

    public interface MainView extends BaseView {
        void screenRecordStatus(int status);

    }

    public interface MainPresenter {
        void init(SurfaceHolder surfaceView);

        void startOrStop();

        void destory();
    }
}
