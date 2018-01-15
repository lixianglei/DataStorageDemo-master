package com.zhangmiao.datastoragedemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhangmiao on 2016/12/19.
 */
public class SharedPreferencesDBManager {

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private final static String PREFRENCE_FILE_KEY = "com.zhangmiao.datastoragedemo.PREFRENCE_FILE_KEY";

    public SharedPreferencesDBManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences
                (PREFRENCE_FILE_KEY, Context.MODE_PRIVATE);
    }

    public void writeData(Person person) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("id", person._id);
        editor.putString("name", person.name);
        editor.putInt("age", person.age);
        editor.putString("info", person.info);
        editor.commit();
    }

    public Person readData() {
        int id = mSharedPreferences.getInt("id", 0);
        String name = mSharedPreferences.getString("name", "defaultname");
        int age = mSharedPreferences.getInt("age", 0);
        String info = mSharedPreferences.getString("info", "defaultinfo");
        Person person = new Person(id, name, age, info);
        return person;
    }
}
