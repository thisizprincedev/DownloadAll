package in.mobicomly.download.service;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import androidx.annotation.Nullable;

import in.mobicomly.download.thread.DownLoadingTask;

public class DownService extends Service {
    private static int anHour = 1000;
    private AlarmManager manager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new DownLoadingTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return super.onStartCommand(intent, flags, startId);
    }
}
