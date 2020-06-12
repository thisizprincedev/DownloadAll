package in.mobicomly.download.thread;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.mobicomly.download.activity.TorrentInfoActivity;
import in.mobicomly.download.common.AppManager;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.common.MessageEvent;
import in.mobicomly.download.common.Msg;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.mvp.m.DownLoadModel;
import in.mobicomly.download.mvp.m.DownLoadModelImp;
import in.mobicomly.download.mvp.m.TaskModel;
import in.mobicomly.download.mvp.m.TaskModelImp;
import in.mobicomly.download.util.AppSettingUtil;
import in.mobicomly.download.util.DownUtil;
import in.mobicomly.download.util.FileTools;
import in.mobicomly.download.util.SystemConfig;
import in.mobicomly.download.view.DownProgressNotify;

public class DownUpdateUI {
    private static DownUpdateUI updateUI;
    private TaskModel taskModel;
    private DownLoadModel downLoadModel;
    private List<DownloadTaskEntity> tasks;
    public DownUpdateUI(){
        taskModel=new TaskModelImp();
        downLoadModel=new DownLoadModelImp();
        tasks=new ArrayList<>();
    }
    public static synchronized DownUpdateUI getInstance() {
        if (updateUI == null) {
            updateUI = new DownUpdateUI();
        }
        return updateUI;
    }

    public void downUpdate(){
        TaskModel taskModel=new TaskModelImp();
        DownLoadModel downLoadModel=new DownLoadModelImp();
        tasks=taskModel.findLoadingTask();
        if(tasks!=null) {
            int netType= SystemConfig.getNetType();
            if(!AppSettingUtil.getInstance().isDown()){
                //downLoadIngView.alert("没有网络,下载暂停", Const.ERROR_ALERT);
                for (DownloadTaskEntity task : tasks) {
                    if (task.getmTaskStatus() != Const.DOWNLOAD_STOP) {
                        downLoadModel.stopTask(task);
                        task.setmTaskStatus(Const.DOWNLOAD_WAIT);
                        taskModel.upDataTask(task);
                    }
                }
            }else {
                int downCount= AppSettingUtil.getInstance().getDownCount();
                List<DownloadTaskEntity> downs=taskModel.findDowningTask();
                int wait=downs==null?0:downs.size()-downCount;
                for (DownloadTaskEntity task : tasks) {
                    if(wait>0){
                        if(task.getmTaskStatus()!=Const.DOWNLOAD_WAIT && task.getmTaskStatus()!=Const.DOWNLOAD_FAIL) {
                            wait--;
                            downLoadModel.stopTask(task);
                            task.setmTaskStatus(Const.DOWNLOAD_WAIT);
                            taskModel.upDataTask(task);
                            continue;
                        }
                    }
                    if (task.getmTaskStatus() != Const.DOWNLOAD_STOP && task.getmTaskStatus() != Const.DOWNLOAD_WAIT  && task.getTaskId()!=0) {
                        XLTaskInfo taskInfo = XLTaskHelper.instance(x.app().getApplicationContext()).getTaskInfo(task.getTaskId());
                        task.setTaskId(taskInfo.mTaskId);
                        task.setmTaskStatus(taskInfo.mTaskStatus);
                        task.setmDCDNSpeed(taskInfo.mAdditionalResDCDNSpeed);
                        task.setmDownloadSpeed(taskInfo.mDownloadSpeed);
                        if (taskInfo.mTaskId != 0) {
                            task.setmFileSize(taskInfo.mFileSize);
                            task.setmDownloadSize(taskInfo.mDownloadSize);
                        }
                        taskModel.upDataTask(task);
                        if (DownUtil.isDownSuccess(task)) {
                            downLoadModel.stopTask(task);
                            task.setmTaskStatus(Const.DOWNLOAD_SUCCESS);
                            taskModel.upDataTask(task);
                            EventBus.getDefault().postSticky(new MessageEvent(new Msg(Const.MESSAGE_TYPE_REFRESH_DATA, task)));
                            String suffix = task.getmFileName().substring(task.getmFileName().lastIndexOf(".") + 1).toUpperCase();
                            if ("TORRENT".equals(suffix)) {
                                openTorrentFile(task);
                            }
                        }
                        if(AppSettingUtil.getInstance().isShowDownNotify()) {
                            DownProgressNotify.getInstance().createDowneProgressNotify(task);
                            DownProgressNotify.getInstance().updateDownProgressNotify(task);
                        }else{
                            DownProgressNotify.getInstance().cancelDownProgressNotify(task);
                        }
                    }else{
                        DownProgressNotify.getInstance().cancelDownProgressNotify(task);
                        if(wait<0 && task.getmTaskStatus()==Const.DOWNLOAD_WAIT){
                            downLoadModel.startTask(task);
                        }
                    }
                }
            }
            getDownMovieThumbnails();
            EventBus.getDefault().postSticky(new MessageEvent(new Msg(Const.MESSAGE_TYPE_APP_UPDATA_PRESS, tasks)));
            //downLoadIngView.refreshData(tasks);
        }
    }
    public void getDownMovieThumbnails(){
        List<DownloadTaskEntity> tasks=tasks=taskModel.findAllTask();
        if(tasks!=null) {
            for (DownloadTaskEntity task : tasks) {
                String filePath = task.getLocalPath() + File.separator + task.getmFileName();
                if (FileTools.isVideoFile(task.getmFileName()) && (!FileTools.exists(task.getThumbnailPath()) || task.getThumbnailPath() == null)) {
                    Bitmap bitmap = FileTools.getVideoThumbnail(filePath, 250, 150, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                    if (bitmap != null) {
                        String thumbnailPath = FileTools.saveBitmap(bitmap, System.currentTimeMillis() + ".jpg");
                        task.setThumbnailPath(thumbnailPath);
                        taskModel.upDataTask(task);
                    }
                }
            }
        }
    }
    public void openTorrentFile(DownloadTaskEntity task) {
        String suffix = task.getmFileName().substring(task.getmFileName().lastIndexOf(".") + 1).toUpperCase();
        if("TORRENT".equals(suffix)) {
            Intent intent = new Intent(AppManager.getAppManager().currentActivity(), TorrentInfoActivity.class);
            intent.putExtra("torrentPath", task.getLocalPath()+ File.separator+task.getmFileName());
            intent.putExtra("isDown", true);
            AppManager.getAppManager().currentActivity().startActivity(intent);
        }
    }

    public List<DownloadTaskEntity> getTasks(){
        return tasks;
    }
}
