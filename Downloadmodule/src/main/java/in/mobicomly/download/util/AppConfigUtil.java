package in.mobicomly.download.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.xutils.x;

import java.util.List;

import in.mobicomly.download.mvp.e.MagnetRule;

public class AppConfigUtil {
    private static AppConfigUtil appConfigUtil;
    private List<MagnetRule> rules;

    public AppConfigUtil() {

    }

    public static synchronized AppConfigUtil getInstance() {
        if (appConfigUtil == null) {
            appConfigUtil = new AppConfigUtil();
        }
        return appConfigUtil;
    }

    public List<MagnetRule> getRules() {
        if (null == rules) {
            GsonUtil.getRule(x.app().getApplicationContext(), "rule.json");
        }
        return rules;
    }

    public void setRules(List<MagnetRule> rules) {
        this.rules = rules;
    }

    public static int getLocalVersion() {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = x.app().getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(x.app().getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static String getLocalVersionName() {
        String localVersion = "";
        try {
            PackageInfo packageInfo = x.app().getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(x.app().getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;

    }

}
