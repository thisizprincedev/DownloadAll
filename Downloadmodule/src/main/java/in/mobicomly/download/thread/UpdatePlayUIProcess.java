package in.mobicomly.download.thread;

import android.os.Handler;
import android.os.Message;

import com.aplayer.aplayerandroid.APlayerAndroid;

import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.v.PlayerView;

public class UpdatePlayUIProcess implements Runnable {
    private PlayerView playerView;
    private Handler handler;
    private APlayerAndroid aPlayer;
    public UpdatePlayUIProcess(PlayerView playerView, Handler handler,APlayerAndroid aPlayer){
        this.playerView=playerView;
        this.handler=handler;
        this.aPlayer=aPlayer;
    }
    @Override
    public void run() {
        while (playerView.ismIsNeedUpdateUIProgress()) {
            if (!playerView.ismIsTouchingSeekbar()) {
                int currentPlayTime = 0;
                int durationTime = 0;
                if (null != aPlayer) {
                    currentPlayTime = aPlayer.getPosition();
                    durationTime = aPlayer.getDuration();
                }
                Message msg = handler.obtainMessage(Const.UI_UPDATE_PLAY_STATION, currentPlayTime, durationTime);
                handler.sendMessage(msg);
            }
            try {
                Thread.sleep(Const.TIMER_UPDATE_INTERVAL_TIME);
            } catch (InterruptedException e) {
            }
        }
    }
}
