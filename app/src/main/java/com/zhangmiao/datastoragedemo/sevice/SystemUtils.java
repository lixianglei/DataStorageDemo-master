package com.zhangmiao.datastoragedemo.sevice;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by zsz on 2018/1/12.
 */

class SystemUtils {
    /**
     * 方法描述：判断某一应用是否正在运行
     * Created by cafeting on 2017/2/4.
     *
     * @paramcontext上下文
     * @parampackageName 应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}