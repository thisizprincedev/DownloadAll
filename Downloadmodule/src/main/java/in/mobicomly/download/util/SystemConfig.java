package in.mobicomly.download.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import org.xutils.x;

import in.mobicomly.download.R;
import in.mobicomly.download.common.Const;

public class SystemConfig {
    private static SystemConfig systemConfig=null;
    private AudioManager audioManager;
    private ContentResolver contentResolver;
    private Intent batteryInfoIntent;
    private SystemConfig(){
        audioManager = (AudioManager) x.app().getSystemService(x.app().getApplicationContext().AUDIO_SERVICE);
        //setScrennManualMode();
    }

    public static synchronized SystemConfig getInstance() {
        if (systemConfig == null) {
            systemConfig = new SystemConfig();
        }
        return systemConfig;
    }

    public int getStreamMaxVolume(){
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public int getStreamVolume(){
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public void setStreamVolume(int audio){
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audio, 0);
    }

    public void setScrennManualMode() {
        try {
            contentResolver = x.app().getApplicationContext().getContentResolver();
            int mode = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
            if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getScreenBrightness() {
        contentResolver = x.app().getApplicationContext().getContentResolver();
        int defVal = 125;
        return Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, defVal);
    }
    public void setWindowBrightness(Activity activity,int brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }

    /**
     *  实时获取电量
     */
    public int getSystemBattery() {
        batteryInfoIntent = x.app().getApplicationContext().registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = 0;
        level = batteryInfoIntent.getIntExtra("level", 0);
        int batterySum = batteryInfoIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        int percentBattery = 100 * level / batterySum;
        return percentBattery;
    }
    public Boolean getSystemBatteryStatus() {
        batteryInfoIntent = x.app().getApplicationContext().registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int state = batteryInfoIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return (state == BatteryManager.BATTERY_STATUS_CHARGING);
    }

    public static int getNetType(){
        ConnectivityManager connectivityManager = (ConnectivityManager) x.app().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return Const.NET_TYPE_UNKNOW;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return Const.NET_TYPE_UNKNOW;
        }
        int type = activeNetworkInfo.getType();
        if(type==ConnectivityManager.TYPE_WIFI){
            return Const.NET_TYPE_WIFI;
        }else  if(type==ConnectivityManager.TYPE_MOBILE){
            return Const.NET_TYPE_MOBILE;
        }
        return Const.NET_TYPE_UNKNOW;
    }

    public int getBatteryIcon(){
        if(getSystemBatteryStatus()){
            return R.drawable.ic_battery_charg;
        }else if(getSystemBattery()>=100){
            return R.drawable.ic_battery_100;
        }else if(getSystemBattery()>=80){
            return R.drawable.ic_battery_80;
        }else if(getSystemBattery()>=60){
            return R.drawable.ic_battery_60;
        }else if(getSystemBattery()>=40){
            return R.drawable.ic_battery_40;
        }else if(getSystemBattery()>=10){
            return R.drawable.ic_battery_20;
        }else if(getSystemBattery()>=0){
            return R.drawable.ic_battery_0;
        }
        return 0;
    }
}
