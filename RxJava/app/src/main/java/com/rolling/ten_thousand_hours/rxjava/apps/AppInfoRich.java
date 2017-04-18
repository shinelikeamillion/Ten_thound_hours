package com.rolling.ten_thousand_hours.rxjava.apps;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.DisplayMetrics;

import java.util.Locale;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class AppInfoRich implements Comparable<Object> {

    @Setter
    String mName = null;

    private Context mContext;

    private ResolveInfo mResolveInfo;

    private ComponentName mComponentName = null;

    private PackageInfo pi = null;

    private Drawable icon = null;

    public AppInfoRich(Context ctx, ResolveInfo ri) {
        mContext = ctx;
        mResolveInfo = ri;

        mComponentName = new ComponentName(ri.activityInfo.applicationInfo.packageName, ri.activityInfo.name);

        try {
            pi = ctx.getPackageManager().getPackageInfo(getPackagename(), 0);
        } catch (NameNotFoundException e) {
        }
    }

    public String getName() {
        if (mName != null) {
            return mName;
        } else {
            try {
                return getNameFromResolveInfo(mResolveInfo);
            } catch (NameNotFoundException e) {
                return getPackagename();
            }
        }
    }

    public String getActivityName() {
        return mResolveInfo.activityInfo.name;
    }

    public String getPackagename(){
        return mResolveInfo.activityInfo.packageName;
    }

    public ComponentName getmComponentName() {
        return mComponentName;
    }

    public String getCompentInfo() {
        if (getmComponentName() != null)  {
            return getmComponentName().toString();
        } else {
            return "";
        }
    }

    public ResolveInfo getResolveInfo(){
        return mResolveInfo;
    }

    public PackageInfo getPacageInfo () {
        return pi;
    }

    public String getVersionName() {
        PackageInfo pi = getPacageInfo();
        if (pi != null) {
            return pi.versionName;
        } else {
            return "";
        }
    }

    public int getVersionCode() {
        PackageInfo pi = getPacageInfo();
        if (pi != null) {
            return pi.versionCode;
        } else {
            return 0;
        }
    }

    public Drawable getIcon() {
        if (icon == null) {
            icon = getResolveInfo().loadIcon(mContext.getPackageManager());
        }
        return icon;
    }

    public long getFirstInstallTime(){
        PackageInfo pi = getPacageInfo();
        if (pi != null &&
                VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            return pi.firstInstallTime;
        } else {
            return 0;
        }
    }

    public long getLastUpdateTime() {
        PackageInfo pi = getPacageInfo();
        if (pi != null &&
                VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            return pi.lastUpdateTime;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Helper method to get an application name
     * @param ri
     * @return
     * @throws NameNotFoundException
     */
    public String getNameFromResolveInfo(ResolveInfo ri) throws NameNotFoundException {
        String name = ri.resolvePackageName;
        if (ri.activityInfo != null) {
            Resources res = mContext.getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
            Resources enRes = getEnglishRessources(res);

            if (ri.activityInfo.labelRes != 0) {
                name = enRes.getString(ri.activityInfo.labelRes);

                if (name == null || name.equals("")) {
                    name = res.getString(ri.activityInfo.labelRes);
                }
            } else {
                name = ri.activityInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
            }
        }
        return name;
    }

    public Resources getEnglishRessources(Resources standardResources) {
        AssetManager assetManager = standardResources.getAssets();
        DisplayMetrics metrics = standardResources.getDisplayMetrics();
        Configuration configuration = new Configuration(standardResources.getConfiguration());
        configuration.locale = Locale.US;
        return new Resources(assetManager, metrics, configuration);
    }

    @Override
    public int compareTo(Object another) {
        AppInfoRich f = (AppInfoRich) another;
        return getName().compareTo(f.getName());
    }
}
