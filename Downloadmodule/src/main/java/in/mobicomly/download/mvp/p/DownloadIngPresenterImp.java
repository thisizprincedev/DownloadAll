package in.mobicomly.download.mvp.p;

import java.util.List;

import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.mvp.m.DownLoadModel;
import in.mobicomly.download.mvp.m.DownLoadModelImp;
import in.mobicomly.download.mvp.m.TaskModel;
import in.mobicomly.download.mvp.m.TaskModelImp;
import in.mobicomly.download.mvp.v.DownLoadIngView;
import in.mobicomly.download.util.AppSettingUtil;
import in.mobicomly.download.util.SystemConfig;
import in.mobicomly.download.view.DownProgressNotify;

public class DownloadIngPresenterImp implements DownloadIngPresenter {
    private DownLoadIngView downLoadIngView;
    private TaskModel taskModel;
    private DownLoadModel downLoadModel;
    private List<DownloadTaskEntity> list;
    private Boolean isLoop=true;

    public DownloadIngPresenterImp(DownLoadIngView downLoadIngView){
        this.downLoadIngView=downLoadIngView;
        taskModel=new TaskModelImp();
        downLoadModel=new DownLoadModelImp();
        list=taskModel.findLoadingTask();
        //downLoadIngView.initTaskListView(list);
        //refreshData();
    }
    @Override
    public List<DownloadTaskEntity> getDownloadingTaskList() {
        return list;
    }

    @Override
    public void startTask(DownloadTaskEntity task) {
        int netType=SystemConfig.getNetType();
        if(netType==Const.NET_TYPE_UNKNOW) {
            downLoadIngView.alert("没有网络,下载暂停", Const.ERROR_ALERT);
            return;
        }else if(!AppSettingUtil.getInstance().isMobileNetDown() && netType==Const.NET_TYPE_MOBILE){
            downLoadIngView.alert("设置不允许允许流量下载,请在设置里开启流量下载", Const.ERROR_ALERT);
            return;
        }
        int downCount= AppSettingUtil.getInstance().getDownCount();
        List<DownloadTaskEntity> downs=taskModel.findDowningTask();
        if(downCount<=downs.size()){
            task.setmTaskStatus(Const.DOWNLOAD_WAIT);
            taskModel.upDataTask(task);
            return;
        }
        //List<DownloadTaskEntity> tasks=taskModel.findLoadingTask();
        boolean b=downLoadModel.startTask(task);
        if(!b)
            downLoadIngView.alert("开始任务失败,无法获取下载资源,可尝试多点几次开始任务", Const.ERROR_ALERT);
    }

    @Override
    public void stopTask(DownloadTaskEntity task) {
        downLoadModel.stopTask(task);
    }

    @Override
    public void deleTask(DownloadTaskEntity task,Boolean deleFile) {
        downLoadModel.deleTask(task,true,deleFile);
        DownProgressNotify.getInstance().cancelDownProgressNotify(task);
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void stopLoop() {

    }

    @Override
    public void clearHandler() {

    }
}
