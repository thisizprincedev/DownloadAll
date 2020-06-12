package in.mobicomly.download.mvp.p;

import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentInfo;

import org.xutils.x;
import java.io.File;
import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.mvp.e.TorrentInfoEntity;
import in.mobicomly.download.mvp.m.DownLoadModel;
import in.mobicomly.download.mvp.m.DownLoadModelImp;
import in.mobicomly.download.mvp.m.TaskModel;
import in.mobicomly.download.mvp.m.TaskModelImp;
import in.mobicomly.download.mvp.v.TorrentInfoView;
import in.mobicomly.download.util.FileTools;

public class TorrentInfoPresenterImp implements TorrentInfoPresenter {
    private TorrentInfoView torrentInfoView;
    private String torrentPath;
    private TaskModel taskModel;
    private DownLoadModel downLoadModel;
    private List<TorrentInfoEntity> list=null;
    public TorrentInfoPresenterImp(TorrentInfoView torrentInfoView,String torrentPath){
        this.torrentInfoView=torrentInfoView;
        this.torrentPath=torrentPath;
        taskModel=new TaskModelImp();
        downLoadModel=new DownLoadModelImp();
        list=downLoadModel.getTorrentInfo(torrentPath);
        torrentInfoView.initTaskListView(list);

    }

    @Override
    public void startTask(List<TorrentInfoEntity> checkList) {
        //String path=task.getLocalPath()+ File.separator+task.getmFileName();
        TorrentInfo torrentInfo= XLTaskHelper.instance(x.app().getApplicationContext()).getTorrentInfo(torrentPath);
        List<DownloadTaskEntity> tasks=taskModel.findTaskByHash(torrentInfo.mInfoHash);
        if(tasks!=null && tasks.size()>0){
            DownloadTaskEntity task=tasks.get(0);
            if(!FileTools.exists(task.getLocalPath()+ File.separator+task.getmFileName())) {
                downLoadModel.startTorrentTask(task);
                torrentInfoView.startTaskSuccess();
            }else if(task.getmTaskStatus()== Const.DOWNLOAD_CONNECTION
                    || task.getmTaskStatus()== Const.DOWNLOAD_LOADING
                    || task.getmTaskStatus()== Const.DOWNLOAD_FAIL
                    || task.getmTaskStatus()== Const.DOWNLOAD_STOP
                    || task.getmTaskStatus()== Const.DOWNLOAD_WAIT){
                torrentInfoView.startTaskFail(x.app().getString(R.string.task_earlier_has));
            }else if(task.getmTaskStatus()== Const.DOWNLOAD_SUCCESS){
                torrentInfoView.startTaskFail(x.app().getString(R.string.task_earlier_success));
            }
        }else{
            downLoadModel.startTorrentTask(torrentPath);
            torrentInfoView.startTaskSuccess();
        }
    }
}
