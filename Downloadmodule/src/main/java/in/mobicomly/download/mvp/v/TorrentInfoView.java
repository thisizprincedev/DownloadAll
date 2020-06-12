package in.mobicomly.download.mvp.v;

import java.util.List;

import in.mobicomly.download.mvp.e.TorrentInfoEntity;

public interface TorrentInfoView {
    void initTaskListView(List<TorrentInfoEntity> list);
    void itemClick(int index);
    void startTaskSuccess();
    void startTaskFail(String msg);
    boolean getIsDown();
    void playerViedo(TorrentInfoEntity te);
}
