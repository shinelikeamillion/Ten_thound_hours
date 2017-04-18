package com.rolling.ten_thousand_hours.rxjava.apps;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class AppInfo implements Comparable<Object>{

    long mLastUpdateTime;
    String mName;
    String mIcon;

    public AppInfo (String nName, long lastUpdateTIme, String icon) {
        mName = nName;
        mIcon = icon;
        mLastUpdateTime = lastUpdateTIme;
    }

    @Override
    public int compareTo(Object another) {
        AppInfo appInfo = (AppInfo) another;

        return 0;
    }
}
