package in.mobicomly.download.thread;

import android.os.Handler;
import android.os.Message;

import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.v.PlayerView;

public class HidePlayControl {
    private PlayerView playerView;
    private HideHandler mHideHandler;

    public HidePlayControl(PlayerView playerView) {
        this.playerView=playerView;
        mHideHandler = new HideHandler();
    }

    public class HideHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Const.MSG_HIDE:
                    playerView.controlViewHide();
                    break;
            }

        }
    }

    private Runnable hideRunable = new Runnable() {

        @Override
        public void run() {
            mHideHandler.obtainMessage(Const.MSG_HIDE).sendToTarget();
        }
    };

    public void startHideTimer() {//开始计时,三秒后执行runable
        mHideHandler.removeCallbacks(hideRunable);
        playerView.controlViewToggle();
        mHideHandler.postDelayed(hideRunable, 3000);
    }

    public void endHideTimer() {//移除runable,将不再计时
        mHideHandler.removeCallbacks(hideRunable);
    }

    public void resetHideTimer() {//重置计时
        mHideHandler.removeCallbacks(hideRunable);
        mHideHandler.postDelayed(hideRunable, 3000);
    }

}
