package com.zhangmiao.datastoragedemo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

/**
 * Created by zhangmiao on 2016/12/20.
 */
public class FileDBManager {

    private File mFile;

    private Context mContext;

    private String mFileName = "myfile";

    public FileDBManager(Context context) {
        mContext = context;
    }

    public void write(String info) {
        try {
            FileOutputStream fos =
                    mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            fos.write(info.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String read() {
        try {
            FileInputStream fis = mContext.openFileInput(mFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String info = reader.readLine();
            fis.close();
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
