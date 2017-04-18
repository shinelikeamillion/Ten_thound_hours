package com.rolling.ten_thousand_hours.rxjava.apps;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class ApplicationsList {

    private static ApplicationsList ourInstance = new ApplicationsList();

    @Getter
    @Setter
    private List<AppInfo> mList;

    private ApplicationsList () {
    }

    public static ApplicationsList getOurInstance() {
        return ourInstance;
    }
}
