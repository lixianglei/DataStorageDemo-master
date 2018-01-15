package com.zhangmiao.datastoragedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangmiao on 2016/12/16.
 */
public class SQLiteDBManager {
    private SQLiteDatabase mDb;

    public SQLiteDBManager(Context context) {

        SdcardHelper dbContext = new SdcardHelper(context);
        SQLiteDBHelper helper = new SQLiteDBHelper(dbContext);
        mDb = helper.getWritableDatabase();
    }

    public void add(List<Person> persons) {
        /*
        db.beginTransaction();//开始事务
        try {
            for(Person person:persons){
                db.execSQL("INSERT INTO person VALUE(null,?,?,?)",
                        new Object[]{person.name,person.age,person.info});
            }
            db.setTransactionSuccessful();//设置事务成功完成
        }finally {
            db.endTransaction();//结束事务
        }
        */

        for (int i = 0; i < persons.size(); i++) {
            ContentValues values = new ContentValues();
            Person person = persons.get(i);
            values.put("name", person.name);
            values.put("age", person.age);
            values.put("info", person.info);
            mDb.insert("person", null, values);
        }
    }


    public void updateAge(Person person) {
        ContentValues cv = new ContentValues();
        cv.put("age", person.age);
        mDb.update("person", cv, "name = ?", new String[]{person.name});
    }

    public void deleteOldPerson(Person person) {
        mDb.delete("person", "age >= ?", new String[]{String.valueOf(person.age)});
    }

    public List<Person> query() {
        ArrayList<Person> persons = new ArrayList<>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Person person = new Person();
            person._id = c.getInt(c.getColumnIndex("_id"));
            person.name = c.getString(c.getColumnIndex("name"));
            person.age = c.getInt(c.getColumnIndex("age"));
            person.info = c.getString(c.getColumnIndex("info"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    public Cursor queryTheCursor() {
        Cursor c = mDb.rawQuery("SELECT * FROM person", null);
        return c;
    }

    public void closeDB() {
        mDb.close();
    }

}
