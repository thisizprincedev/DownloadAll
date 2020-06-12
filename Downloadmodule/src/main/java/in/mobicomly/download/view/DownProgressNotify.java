package in.mobicomly.download.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import org.xutils.x;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import in.mobicomly.download.R;
import in.mobicomly.download.activity.DownloadManagementActivity;
import in.mobicomly.download.common.AppManager;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.util.FileTools;
import in.mobicomly.download.util.TimeUtil;

public class DownProgressNotify {
    private NotificationManager notificationManager;
    private Map<String,RemoteViews> downRemoteViews;
    private Map<String,Notification> downNotification;
    private Context context;
    private PendingIntent mainPendingIntent;
    private static DownProgressNotify progressNotify;
    public DownProgressNotify(){
        //this.context=context;
        notificationManager = (NotificationManager) x.app().getSystemService(x.app().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(x.app().getPackageName(), x.app().getString(R.string.app_name), NotificationManager.IMPORTANCE_LOW);
            channel.enableLights(false);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }
        downRemoteViews=new HashMap<>();
        downNotification=new HashMap<>();
    }
    public static synchronized DownProgressNotify getInstance() {
        if (progressNotify == null) {
            progressNotify = new DownProgressNotify();
        }
        return progressNotify;
    }
    public void createDowneProgressNotify(DownloadTaskEntity task){
        Notification notification=downNotification.get(task.getId());
        RemoteViews mRemoteViews;
        if(notification != null){
           return;
        }else{
            int progress=0;
            if(task.getmDownloadSize()!=0 && task.getmFileSize()!=0) {
                double f1 = new BigDecimal((float) task.getmDownloadSize() / task.getmFileSize()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                progress=(int) (f1 * 100);
            }
            mRemoteViews = new RemoteViews(x.app().getPackageName(), R.layout.item_down_progress_notify);
            String fileName=task.getFile()?task.getmFileName():"";
            mRemoteViews.setImageViewResource(R.id.file_icon, FileTools.getFileIcon(fileName));
            mRemoteViews.setTextViewText(R.id.file_name, task.getmFileName());
            mRemoteViews.setTextViewText(R.id.down_size, String.format(x.app().getResources().getString(R.string.down_count),
                    FileTools.convertFileSize(task.getmFileSize()), FileTools.convertFileSize(task.getmDownloadSize())));
            mRemoteViews.setTextViewText(R.id.down_speed, String.format(x.app().getResources().getString(R.string.down_speed),
                    FileTools.convertFileSize(task.getmDownloadSpeed())));
            mRemoteViews.setTextViewText(R.id.down_cdnspeed, String.format(x.app().getResources().getString(R.string.down_speed_up),
                    FileTools.convertFileSize(task.getmDCDNSpeed())));
            if(task.getmFileSize()!=0 && task.getmDownloadSize()!=0) {
                long speed=task.getmDownloadSpeed()==0?1:task.getmDownloadSpeed();
                long time = (task.getmFileSize() - task.getmDownloadSize()) / speed;
                mRemoteViews.setTextViewText(R.id.remaining_time,String.format(x.app().getString(R.string.remaining_time), TimeUtil.formatFromSecond((int) time)));
            }
            mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, progress, false);
            downRemoteViews.put(task.getId()+"",mRemoteViews);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(x.app().getApplicationContext(), x.app().getPackageName())
                    .setContentTitle("下载进度:"+task.getmFileName()).setSmallIcon(R.drawable.logo).setCustomContentView(mRemoteViews)
                    .build();
        }else{
            notification = new Notification.Builder(x.app().getApplicationContext())
                    .setContentTitle("下载进度:"+task.getmFileName()).setSmallIcon(R.drawable.logo).setContent(mRemoteViews)
                            .build();
        }
        Intent mainIntent = new Intent(AppManager.getAppManager().currentActivity(), DownloadManagementActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        mainPendingIntent = PendingIntent.getActivity(AppManager.getAppManager().currentActivity(), 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent=mainPendingIntent;
        downNotification.put(task.getId()+"",notification);
        notificationManager.notify(task.getId(), notification);
    }
    public void updateDownProgressNotify(DownloadTaskEntity task){
        Notification notification=downNotification.get(task.getId()+"");
        int progress=0;
        if(task.getmDownloadSize()!=0 && task.getmFileSize()!=0) {
            double f1 = new BigDecimal((float) task.getmDownloadSize() / task.getmFileSize()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            progress=(int) (f1 * 100);
        }
        notification.contentView.setTextViewText(R.id.down_size, String.format(x.app().getResources().getString(R.string.down_count),
                FileTools.convertFileSize(task.getmFileSize()), FileTools.convertFileSize(task.getmDownloadSize())));
        notification.contentView.setTextViewText(R.id.down_speed, String.format(x.app().getResources().getString(R.string.down_speed),
                FileTools.convertFileSize(task.getmDownloadSpeed())));
        notification.contentView.setTextViewText(R.id.down_cdnspeed, String.format(x.app().getResources().getString(R.string.down_speed_up),
                FileTools.convertFileSize(task.getmDCDNSpeed())));
        if(task.getmFileSize()!=0 && task.getmDownloadSize()!=0) {
            long speed=task.getmDownloadSpeed()==0?1:task.getmDownloadSpeed();
            long time = (task.getmFileSize() - task.getmDownloadSize()) / speed;
            notification.contentView.setTextViewText(R.id.remaining_time,String.format(x.app().getString(R.string.remaining_time), TimeUtil.formatFromSecond((int) time)));
        }
        notification.contentView.setProgressBar(R.id.custom_progressbar, 100, progress, false);
        notificationManager.notify(task.getId(), notification);
        if(progress>=100){
            notificationManager.cancel(task.getId());
        }
    }
    public void cancelDownProgressNotify(DownloadTaskEntity task){
        notificationManager.cancel(task.getId());
        Notification notification=downNotification.get(task.getId()+"");
        downNotification.remove(notification);
    }
}
