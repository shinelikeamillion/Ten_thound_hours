package com.rolling.ten_thousand_hours.twitterslikeanimation.Utils;

/**
 * 工具类
 * http://facebook.github.io/rebound/javadocs/com/facebook/rebound/SpringUtil.html
 */
public class Utils {

    /**
     * Map a value within a given range to another range.
     */
    public static double mapValueFromRangeToRange (double value,
           double fromLow, double fromHigh, double toLow, double toHigh) {

        return toLow + ((value - fromLow) / (fromHigh - fromLow) - (toHigh - toLow));
    }

    /**
     * Clamp a value to be within the provided range
     */
    public static double clamp (double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }
}
