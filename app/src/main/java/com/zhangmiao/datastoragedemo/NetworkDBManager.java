package com.zhangmiao.datastoragedemo;

import android.util.Log;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.List;

/**
 * Created by zhangmiao on 2016/12/22.
 */
public class NetworkDBManager {

    private static final String TAG = "NetworkDBManager";

    private final static String TABLENAME = "person";
    private final static String NAME = "name";
    private final static String AGE = "age";
    private final static String INFO = "info";

    public void putData(Person person) {
        AVObject personObject = new AVObject(TABLENAME);
        personObject.put(NAME, person.name);
        personObject.put(AGE, person.age);
        personObject.put(INFO, person.info);
        personObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.v(TAG, "put data success!");
                } else {
                    Log.v(TAG, "put data failed!error:" + e.getMessage());
                }
            }
        });
    }

    public void getData(final TextView textView) {
        AVQuery<AVObject> avQuery = new AVQuery<>(TABLENAME);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Log.v(TAG, "get data success!");
                    String message = "";
                    for (int i = 0; i < list.size(); i++) {
                        String name = list.get(i).getString(NAME);
                        int age = list.get(i).getInt(AGE);
                        String info = list.get(i).getString(INFO);

                        message += "name:" + name + ",age:" + age + ",info:" + info + ".\n";
                    }
                    textView.setText(message);
                }
            }
        });
    }
}
