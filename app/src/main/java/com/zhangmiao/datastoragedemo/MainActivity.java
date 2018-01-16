package com.zhangmiao.datastoragedemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.*;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVOSCloud;
import com.zhangmiao.datastoragedemo.sevice.AliveJobService;
import com.zhangmiao.datastoragedemo.sevice.JobSchedulerManager;
import com.zhangmiao.datastoragedemo.smack.SmackManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Person> mPersons;

    private List<Person> addPersons;
    private SQLiteDBManager mSQLiteManager;
    private SharedPreferencesDBManager mSPManager;
    private FileDBManager mFileManager;
    private ContentResolver mContentResolver;
    private BookContentProvider mBookContentProvider;
    private NetworkDBManager mNetworkDBManager;

    private TextView mTableInfo;
    private  Button mBtn;
    private AliveJobService aliveJobService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        JobSchedulerManager jobSchedulerManager=JobSchedulerManager.getJobSchedulerInstance(this);
        jobSchedulerManager.startJobScheduler();


//        SmackManager.getConnection("shebei_1","shebei_1");//建立连接
//        SmackManager.login("shebei_1","shebei_1");//Smack登录


        Log.v("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mBtn.findViewById(R.id.Test);
//        mBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        /**
         * TODO 检测权限 没有申请
         */


        AVOSCloud.initialize(this, "yMNUazdBt872mNtC9aSakjYy-gzGzoHsz", "d4vw3VYdMCjLpsXRhHTBRutC");

        SdcardHelper dbContext = new SdcardHelper(this);
       SQLiteDBHelper dbHelper = new SQLiteDBHelper(dbContext);

        mSQLiteManager = new SQLiteDBManager(this);
  //      mSPManager = new SharedPreferencesDBManager(this);
        mFileManager = new FileDBManager(this);
        mContentResolver = getContentResolver();
        mBookContentProvider = new BookContentProvider();
    //    mNetworkDBManager = new NetworkDBManager();

        Button sqliteInsert = (Button) findViewById(R.id.sqlite_insert);
        Button sqliteRead = (Button) findViewById(R.id.sqlite_read);
        Button sqliteUpdate = (Button) findViewById(R.id.sqlite_update);
        Button sqliteDelete = (Button) findViewById(R.id.sqlite_delete);

//        Button spWrite = (Button) findViewById(R.id.shared_read);
//        Button spRead = (Button) findViewById(R.id.shared_write);
//
//        Button fileWrite = (Button) findViewById(R.id.file_write);
//        Button fileRead = (Button) findViewById(R.id.file_read);

        Button cpAdd = (Button) findViewById(R.id.provider_add);
        Button cpDelete = (Button) findViewById(R.id.provider_delete);
        Button cpUpdate = (Button) findViewById(R.id.provider_update);
        Button cpQuery = (Button) findViewById(R.id.provider_query);
//
//        Button networkGet = (Button) findViewById(R.id.network_get);
//        Button networkPut = (Button) findViewById(R.id.network_put);

        mTableInfo = (TextView) findViewById(R.id.table_info);

        sqliteDelete.setOnClickListener(this);
        sqliteInsert.setOnClickListener(this);
        sqliteRead.setOnClickListener(this);
        sqliteUpdate.setOnClickListener(this);

//        spWrite.setOnClickListener(this);
//        spRead.setOnClickListener(this);
//        fileWrite.setOnClickListener(this);
//        fileRead.setOnClickListener(this);

        cpAdd.setOnClickListener(this);
        cpDelete.setOnClickListener(this);
        cpQuery.setOnClickListener(this);
        cpUpdate.setOnClickListener(this);

//        networkGet.setOnClickListener(this);
//        networkPut.s
        mPersons = new ArrayList<>();
//        addPersons = new ArrayList<>();
        initData();
//        initadddData();
    }

    private void initData() {
        String[] names = new String[]{"zhang", "zhao", "li", "wu"};
        int[] ages = new int[]{20, 21, 19, 28};
        String[] infos = new String[]{"women", "men", "men", "women"};

        for (int i = 0; i < names.length; i++) {
            Person person = new Person(names[i], ages[i], infos[i]);
            mPersons.add(person);
        }
    }

    private void initadddData() {
        String[] names = new String[]{"lxl", "swk", "zbj", "lzs"};
        int[] ages = new int[]{18, 19, 20, 21};
        String[] infos = new String[]{"man", "man", "man", "woman"};

        for (int i = 0; i < names.length; i++) {
            Person person = new Person(names[i], ages[i], infos[i]);
            mPersons.add(person);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sqlite_insert:
                mSQLiteManager.add(mPersons);
                break;
            case R.id.sqlite_read:
                writeTableInfo(mSQLiteManager.query());
                break;
            case R.id.sqlite_update:
                Person person = new Person("lxl", 21, "women");
                mSQLiteManager.updateAge(person);
                break;
            case R.id.sqlite_delete:
                Person person1 = new Person();
                person1.age = 15;
                mSQLiteManager.deleteOldPerson(person1);
                break;
//            case R.id.shared_read:
//                writeTableInfo(mSPManager.readData());
//                break;
//            case R.id.shared_write:
//                Person person2 = new Person(1, "zhang", 18, "women");
//                mSPManager.writeData(person2);
//                break;
//            case R.id.file_write:
//                mFileManager.write("hello world!");
//                break;
//            case R.id.file_read:
//                mTableInfo.setText(mFileManager.read());
//                break;

            //这里是ContentProvider的数据增删改查

            case R.id.provider_add:
                mContentResolver = getContentResolver();
                String[] bookNames = new String[]{"Chinese", "Math", "English", "Sports"};
                String[] bookPublishers = new String[]{"XinHua", "GongXin", "DianZi", "YouDian"};
                for (int i = 0; i < bookNames.length; i++) {
                    ContentValues values = new ContentValues();
                    values.put(IProviderMetaData.BookTableMetaData.BOOK_NAME, bookNames[i]);
                    values.put(IProviderMetaData.BookTableMetaData.BOOK_PUBLISHER, bookPublishers[i]);
                    mContentResolver.insert(IProviderMetaData.BookTableMetaData.CONTENT_URI, values);
                }
                break;
            case R.id.provider_delete:
                String bookId = "3";
                if (!"".equals(bookId)) {
                    ContentValues values1 = new ContentValues();
                    values1.put(IProviderMetaData.BookTableMetaData.BOOK_ID,
                            bookId);
                    mContentResolver.delete(
                            Uri.withAppendedPath(
                                    IProviderMetaData.BookTableMetaData.CONTENT_URI,
                                    bookId
                            ), "_id = ?",
                            new String[]{bookId}
                    );
                } else {
                    mContentResolver.delete(
                            IProviderMetaData.BookTableMetaData.CONTENT_URI,
                            null,
                            null
                    );
                }
                break;
            case R.id.provider_query:
                Cursor cursor = mContentResolver.query(IProviderMetaData.BookTableMetaData.CONTENT_URI, null, null, null, null);
                String text = "";
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String bookIdText =
                                cursor.getString(cursor.getColumnIndex(IProviderMetaData.BookTableMetaData.BOOK_ID));
                        String bookNameText =
                                cursor.getString(cursor.getColumnIndex(IProviderMetaData.BookTableMetaData.BOOK_NAME));
                        String bookPublisherText =
                                cursor.getString(cursor.getColumnIndex(IProviderMetaData.BookTableMetaData.BOOK_PUBLISHER));
                        text += "id = " + bookIdText + ",name = " + bookNameText + ",publisher = " + bookPublisherText + "\n";
                    }
                    cursor.close();
                    mTableInfo.setText(text);
                }
                break;
            case R.id.provider_update:
                String bookId1 = "7";
                String bookName = "化学";
                String bookPublisher = "呵呵哒";
                ContentValues values2 = new ContentValues();
                values2.put(IProviderMetaData.BookTableMetaData.BOOK_NAME,
                        bookName);
                values2.put(IProviderMetaData.BookTableMetaData.BOOK_PUBLISHER,
                        bookPublisher);
                if ("".equals(bookId1)) {
                    mContentResolver.update(
                            IProviderMetaData.BookTableMetaData.CONTENT_URI,
                            values2, null, null);
                } else {
                    mContentResolver.update(
                            Uri.withAppendedPath(
                                    IProviderMetaData.BookTableMetaData.CONTENT_URI, bookId1),
                            values2, "_id = ? ", new String[]{bookId1});
                }
                break;
//            case R.id.network_put:
//                Person person3 = new Person("xiao", 23, "women");
//                Person person4 = new Person("zhao", 24, "men");
//                mNetworkDBManager.putData(person3);
//                mNetworkDBManager.putData(person4);
//                break;
//            case R.id.network_get:
//                mNetworkDBManager.getData(mTableInfo);
//                break;
            default:
                Log.v("MainActivity", "default");
                break;
        }
    }

    public void writeTableInfo(List<Person> persons) {
        String message = "";
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            message += "id: " + person._id + " name: " + person.name
                    + " age: " + person.age + " info: " + person.info + "\n";
        }
        mTableInfo.setText(message);
    }

    private void writeTableInfo(Person person) {
        String message = "";
        message += "id: " + person._id + " name: " + person.name
                + " age: " + person.age + " info: " + person.info + "\n";
        mTableInfo.setText(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSQLiteManager.closeDB();
    }
}
