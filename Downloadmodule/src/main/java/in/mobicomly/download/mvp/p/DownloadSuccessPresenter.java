package in.mobicomly.download.mvp.p;

import java.util.List;

import in.mobicomly.download.mvp.e.DownloadTaskEntity;

public interface DownloadSuccessPresenter {
    List<DownloadTaskEntity> getDownSuccessTaskList();
    void  deleTask(DownloadTaskEntity task, Boolean deleFile);
}
