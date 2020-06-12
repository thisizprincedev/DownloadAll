package in.mobicomly.download.common;

import org.xutils.x;

import in.mobicomly.download.R;
import in.mobicomly.download.util.FileTools;

public class Const {

     public static final int URL_DOWNLOAD=1;
     public static final int BT_DOWNLOAD=2;

     public static final String DB_NAME="test.db";
     public static final String DB_SDCARD_PATH= FileTools.getSDCardPath()+ x.app().getString(R.string.app_db_path);
     public static final String File_SAVE_PATH= FileTools.getSDCardPath()+ x.app().getString(R.string.app_down_path);
     public static final String VIDEO_PIC_PATH= FileTools.getSDCardPath()+ x.app().getString(R.string.app_video_thumbnail_path);
     public static final String APP_CRASH_PATH= FileTools.getSDCardPath()+ x.app().getString(R.string.app_crash_path);

    //0连接中1下载中 2下载完成 3失败
     public  static final int DOWNLOAD_CONNECTION=0;
     public  static final int DOWNLOAD_LOADING=1;
     public  static final int DOWNLOAD_SUCCESS=2;
     public  static final int DOWNLOAD_FAIL=3;
     public  static final int DOWNLOAD_STOP=4;
    public  static final int DOWNLOAD_WAIT=5;

    //
     public  static final int SUCCESS_ALERT=1;
     public  static final int ERROR_ALERT=2;
     public  static final int WARNING_ALERT=3;

    //
     public  static final int MESSAGE_TYPE_BT_DOWN_SUCCESS=1;
     public  static final int MESSAGE_TYPE_REFRESH_DATA=2;
     public  static final int MESSAGE_TYPE_SWITCH_TAB=3;
     public  static final int MESSAGE_TYPE_RES_TASK=4;
    public  static final int MESSAGE_TYPE_APP_UPDATA=5;
    public  static final int MESSAGE_TYPE_APP_UPDATA_PRESS=6;

    //
    public final static int MSG_HIDE = 0x01;
    public static final int UI_UPDATE_PLAY_STATION = 0X1000;
    public static final int TIMER_UPDATE_INTERVAL_TIME = 1000;   //定时器时间间隔，更新播放进度

    public final static int NET_TYPE_UNKNOW = 0;
    public final static int NET_TYPE_WIFI = 1;
    public final static int NET_TYPE_MOBILE = 2;

    //
    public final static String SAVE_PATH_KEY="savepath";
    public final static String MOBILE_NET_KEY="mobilenet";
    public final static String DOWN_COUNT_KEY="downcount";
    public final static String DOWN_NOTIFY_KEY="downnotify";

    public final static int MAX_DOWN_COUNT = 5;
    public final static int MIN_DOWN_COUNT = 1;

    public final static int MOBILE_NET_NOT = 0;
    public final static int MOBILE_NET_OK = 1;

    public final static String SEARCH_SORT_HOT = "hot";
    public final static String SEARCH_SORT_DATE = "date";
}
