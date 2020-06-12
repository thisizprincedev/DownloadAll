package in.mobicomly.download.mvp.p;

import org.xutils.x;

import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.mvp.m.DownLoadModel;
import in.mobicomly.download.mvp.m.DownLoadModelImp;
import in.mobicomly.download.mvp.m.TaskModel;
import in.mobicomly.download.mvp.m.TaskModelImp;
import in.mobicomly.download.mvp.v.DownLoadSuccessView;

public class DownloadSuccessPresenterImp implements DownloadSuccessPresenter {
    private DownLoadSuccessView downLoadSuccessView;
    private TaskModel taskModel;
    private DownLoadModel downLoadModel;
    private List<DownloadTaskEntity> list;


    public DownloadSuccessPresenterImp(DownLoadSuccessView downLoadSuccessView){
        this.downLoadSuccessView=downLoadSuccessView;
        taskModel=new TaskModelImp();
        downLoadModel=new DownLoadModelImp();
        list=taskModel.findSuccessTask();
        downLoadSuccessView.initTaskListView(list);
    }
    @Override
    public List<DownloadTaskEntity> getDownSuccessTaskList() {
        list=taskModel.findSuccessTask();
        return list;
    }



    @Override
    public void deleTask(DownloadTaskEntity task,Boolean deleFile) {
        Boolean b=downLoadModel.deleTask(task,deleFile);
        if(b) {
            downLoadSuccessView.refreshData();
            downLoadSuccessView.alert(x.app().getString(R.string.dele_success), Const.SUCCESS_ALERT);
        }else{
            downLoadSuccessView.alert(x.app().getString(R.string.dele_fail), Const.ERROR_ALERT);
        }
    }


}
