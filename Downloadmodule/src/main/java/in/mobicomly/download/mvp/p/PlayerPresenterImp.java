package in.mobicomly.download.mvp.p;

import com.aplayer.aplayerandroid.APlayerAndroid;

import java.util.List;

import in.mobicomly.download.mvp.e.PlayerVideoEntity;
import in.mobicomly.download.mvp.m.PlayerVideoModel;
import in.mobicomly.download.mvp.m.PlayerVideoModelImp;
import in.mobicomly.download.mvp.v.PlayerView;
import in.mobicomly.download.util.FileTools;

public class PlayerPresenterImp implements PlayerPresenter{
    private PlayerView playerView;
    private String videoPath;
    private PlayerVideoModel playerVideoModel;
    private  APlayerAndroid aPlayer;
    private PlayerVideoEntity video;
    public PlayerPresenterImp(PlayerView playerView, APlayerAndroid aPlayer, String videoPath){
        this.playerView=playerView;
        this.videoPath=videoPath;
        this.aPlayer=aPlayer;
        playerVideoModel=new PlayerVideoModelImp();
        List<PlayerVideoEntity> videos=playerVideoModel.findVideoByPath(videoPath);
        if(videos!=null && videos.size()>0){
            video=videos.get(0);
        }else{
            video=new PlayerVideoEntity();
            video.setLocalPath(videoPath);
            video.setName(FileTools.getFileName(videoPath));
            video=playerVideoModel.saveOrUpdata(video);
        }
        playerView.initPlayer();
        playerView.openVideo(video.getLocalPath());
        playerView.setVideoTile(video.getName());
    }

    @Override
    public void setHistoryCurrentPlayTimeMs() {
        if(video.getCurrentPlayTimeMs()>0){
            playerView.userSeekPlayProgress(video.getCurrentPlayTimeMs());
        }
    }

    @Override
    public void uaDataPlayerTime(int currentPlayTimeMs, int durationTimeMs) {
        if(currentPlayTimeMs>0 && durationTimeMs>0) {
            video.setCurrentPlayTimeMs(currentPlayTimeMs);
            video.setDurationTimeMs(durationTimeMs);
            video = playerVideoModel.saveOrUpdata(video);
        }
    }
}
