package in.mobicomly.download.mvp.m;

import java.util.List;

import in.mobicomly.download.mvp.e.PlayerVideoEntity;

public interface PlayerVideoModel {
    List<PlayerVideoEntity> findAllVideo();
    List<PlayerVideoEntity> findVideoByPath(String path);
    PlayerVideoEntity findVideoById(int id);
    PlayerVideoEntity saveOrUpdata(PlayerVideoEntity video);
    PlayerVideoEntity upDataVideo(PlayerVideoEntity video);
    Boolean deleVideo(PlayerVideoEntity video);
}
