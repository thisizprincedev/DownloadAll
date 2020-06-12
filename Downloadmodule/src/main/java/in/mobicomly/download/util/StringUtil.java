package in.mobicomly.download.util;

import java.text.DecimalFormat;

public class StringUtil {
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }


    public static double stringToDouble(String a) {
        double b = Double.valueOf(a);
        DecimalFormat df = new DecimalFormat("#.0");
        String temp = df.format(b);
        b = Double.valueOf(temp);
        return b;
    }
    public static long StringToLong(String str) {
        String lowerCase = str.toLowerCase();
        final String HEX_PREF = "0x";

        int radix = 10;     //默认十进制
        if(str.startsWith(HEX_PREF)){
            str = str.substring(HEX_PREF.length());
            radix = 16;
        }

        Long lval = Long.parseLong(str, radix);
        return lval;
    }

    public static int StringToInt(String str) {
        Long lval = StringToLong(str);
        return (int)lval.longValue();
    }

    public static boolean equalsIgnoreCase(String str0, String str1) {
        if(null == str0 && null == str1) {
            return true;
        }

        if( (null == str0 && null != str1) ||
            (null != str0 && null == str1)) {
            return false;
        }
        return str0.equalsIgnoreCase(str1);
    }
}
