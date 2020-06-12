package in.mobicomly.download.mvp.v;

import java.util.List;

import in.mobicomly.download.mvp.e.DownloadTaskEntity;

public interface DownLoadIngView {
    void startTask(DownloadTaskEntity task);
    void sopTask(DownloadTaskEntity task);
    void openFile(DownloadTaskEntity task);
    void deleTask(DownloadTaskEntity task);
    void refreshData( List<DownloadTaskEntity> tasks);
    void alert(String msg,int msgType);
}
