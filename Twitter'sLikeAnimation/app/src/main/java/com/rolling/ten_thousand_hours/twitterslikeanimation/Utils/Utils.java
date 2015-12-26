package com.rolling.ten_thousand_hours.twitterslikeanimation.Utils;

/**
 * 工具类
 * Created by 10000_hours on 2015/12/26.
 */
public class Utils {
    public static double mapValueFromRangeToRange (double value,
           double fromLow, double fromHigh, double toLow, double toHigh) {

        return toLow + ((value - fromLow) / (fromHigh - fromLow) - (toHigh - toLow));
    }

    public static double clamp (double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }
}
