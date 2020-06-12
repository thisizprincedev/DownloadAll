package in.mobicomly.download.mvp.p;

import java.util.List;

import in.mobicomly.download.mvp.e.DownloadTaskEntity;

public interface DownloadIngPresenter {
    List<DownloadTaskEntity> getDownloadingTaskList();
    void startTask(DownloadTaskEntity task);
    void stopTask(DownloadTaskEntity task);
    void  deleTask(DownloadTaskEntity task,Boolean deleFile);
    void refreshData();
    void stopLoop();
    void clearHandler();
}
