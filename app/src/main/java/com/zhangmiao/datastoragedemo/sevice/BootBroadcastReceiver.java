package com.zhangmiao.datastoragedemo.sevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by zsz on 2018/1/12.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service=new Intent(context,GrayService.class);
        context.startService(service);
        Log.v("测试一下","开机自动服务自动启动");
    }
}
