package in.mobicomly.download.mvp.v;

import java.util.List;

import in.mobicomly.download.mvp.e.DownloadTaskEntity;

public interface DownLoadSuccessView {
    void initTaskListView(List<DownloadTaskEntity> list);
    void deleTask(DownloadTaskEntity task);
    void openFile(DownloadTaskEntity task);
    void refreshData();
    void alert(String msg, int msgType);
}
