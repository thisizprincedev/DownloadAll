package in.mobicomly.download.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.irozon.sneaker.Sneaker;

import org.xutils.x;

import in.mobicomly.download.R;
import in.mobicomly.download.common.Const;

public class Util {

    public static void alert(Activity activity, String msg, int msgType){
        if(Const.ERROR_ALERT==msgType) {
            Sneaker.with(activity)
                    .setTitle(activity.getResources().getString(R.string.title_dialog), R.color.white)
                    .setMessage(msg, R.color.white)
                    .setDuration(2000)
                    .autoHide(true)
                    .setIcon(R.drawable.ic_error, R.color.white, false)
                    .sneak(R.color.colorAccent);
        }else if(Const.SUCCESS_ALERT==msgType) {
            Sneaker.with(activity)
                    .setTitle(activity.getResources().getString(R.string.title_dialog), R.color.white)
                    .setMessage(msg, R.color.white)
                    .setDuration(2000)
                    .autoHide(true)
                    .setIcon(R.drawable.ic_success, R.color.white, false)
                    .sneak(R.color.success);
        }else if(Const.WARNING_ALERT==msgType) {
            Sneaker.with(activity)
                    .setTitle(activity.getResources().getString(R.string.title_dialog), R.color.white)
                    .setMessage(msg, R.color.white)
                    .setDuration(2000)
                    .autoHide(true)
                    .setIcon(R.drawable.ic_warning, R.color.white, false)
                    .sneak(R.color.warning);
        }
    }
    public static boolean checkApkExist( String packageName){
        if (StringUtil.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = x.app().getApplicationContext().getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void putTextIntoClip(String text){
        ClipboardManager clipboardManager = (ClipboardManager) x.app().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(text, text);
        clipboardManager.setPrimaryClip(clipData);
    }

    public static String getFileSuffix(String name){
        return name.substring(name.lastIndexOf(".") + 1).toUpperCase();
    }


}
