package in.mobicomly.download.util;

import net.steamcrafted.loadtoast.LoadToast;

import org.xutils.x;

import in.mobicomly.download.R;
import in.mobicomly.download.common.AppManager;

public class AlertUtil {
    private static LoadToast lt;
    public static void showLoading(){
        lt = new LoadToast(AppManager.getAppManager().currentActivity());
        lt.setTranslationY(200).setBackgroundColor(x.app().getResources().getColor(R.color.colorMain)).setProgressColor(x.app().getResources().getColor(R.color.white));
        lt.show();
    }

    public static void hideLoading(){
        lt.success();
        lt.hide();
    }
}
