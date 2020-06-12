package in.mobicomly.download.mvp.p;

import java.util.List;

import in.mobicomly.download.mvp.e.TorrentInfoEntity;

public interface TorrentInfoPresenter {
    void startTask(List<TorrentInfoEntity> checkList);
}
