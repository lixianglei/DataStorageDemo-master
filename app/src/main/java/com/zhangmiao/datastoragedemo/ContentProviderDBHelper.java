package com.zhangmiao.datastoragedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhangmiao on 2016/12/20.
 */
public class ContentProviderDBHelper extends SQLiteOpenHelper implements IProviderMetaData {

    private static final String TAG = "ContentProviderDBHelper";

    public ContentProviderDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLESQL = "CREATE TABLE IF NOT EXISTS "
                + BookTableMetaData.TABLE_NAME + " ("
                + BookTableMetaData.BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BookTableMetaData.BOOK_NAME + " VARCHAR,"
                + BookTableMetaData.BOOK_PUBLISHER + " VARCHAR)";
        db.execSQL(TABLESQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + "to"
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }
}