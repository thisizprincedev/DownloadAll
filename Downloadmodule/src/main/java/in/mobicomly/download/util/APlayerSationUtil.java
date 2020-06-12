package in.mobicomly.download.util;

import com.aplayer.aplayerandroid.APlayerAndroid;

public class APlayerSationUtil {
    public static boolean isStopByUserCall(String playRet) {
        long stopCode = StringUtil.StringToLong(APlayerAndroid.PlayCompleteRet.PLAYRE_RESULT_CLOSE);
        long currentCode = StringUtil.StringToLong(playRet);

        boolean isUseCallStop = (stopCode == currentCode);
        return isUseCallStop;
    }

}
