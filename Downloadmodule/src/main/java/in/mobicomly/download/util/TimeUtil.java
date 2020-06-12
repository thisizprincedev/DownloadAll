package in.mobicomly.download.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/7/6.
 */
public class TimeUtil {
    public static String formatFromSecond(int totalSecond)
    {
        int hour = totalSecond / 3600;
        int min = (totalSecond % 3600) / 60;
        int second = totalSecond % 60;

        String fortmatStr = "";
        if(hour > 0)
        {
            fortmatStr = String.format("%02d:%02d:%02d",hour, min, second);
        }
        else
        {
            fortmatStr = String.format("%02d:%02d", min, second);
        }

        return fortmatStr;
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "00:00:00";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
    public static String getNowTime(String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
