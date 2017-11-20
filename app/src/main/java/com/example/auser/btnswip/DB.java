package com.example.auser.btnswip;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.Date;

/**
 * Created by auser on 2017/11/7.
 */

public class DB {
    private static final String DATABASE_NAME = "landlordtool.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE = "streetname";
    private static final String NEW_DATABASE_TABLE = "streetname";
    //    private static final String DATABASE_TABLE = "notdb";
    //    private static final String DATABASE_CREATE =
//            "CREATE TABLE NOTES(_id INTEGER PRIMARY KEY,NOTE TEXT,CREATED INTEGER);";
    private static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS  streetname  "
              + " (  uid int(10) NOT NULL "
              + " ,mailcode varchar(10)"
              + "  ,city varchar(10)"
              + " , country varchar(10) "
              + " , road varchar(10) "
              + " , PRIMARY KEY  (uid)"
              + ");";
//              + ") ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=40249;";

    //這裏的欄位名稱一定要小寫才可以
    private static class DatabaseHelper extends SQLiteOpenHelper {//Helper

        public DatabaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);//1.2.DATABASE NAME3.用不到4.版
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("onCreate","=====1onCreate");
            db.execSQL(DATABASE_CREATE);
//            Toast.makeText(mCtx,"update success",Toast.LENGTH_SHORT).show();;


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("onUpgrade","=====onUpgrade");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }
    }

    private Context mCtx = null;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DB(Context ctx) {
        this.mCtx = ctx;
    }

    public DB open() throws SQLException {
        dbHelper = new DatabaseHelper(mCtx);  //建立helper物件
        db = dbHelper.getWritableDatabase();//利用helper取得資料庫連結,.若資料庫存在,則先建立後再做連線

        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public static final String KEY_ROWID="uid";
    public static final String KEY_NOTE="mailcode";
    public static final String KEY_CREATED="city";
    String[] strCols={"uid","mailcode","city","country","road"};

    //下面兩個方法可選一
    public Cursor getAll(){
        return db.query(DATABASE_TABLE,
                strCols//欲查詢欄位
                ,null,null,null,null,null);
    }

    public Cursor getRow(long rowId){
        return db.query(DATABASE_TABLE,
                strCols//欲查詢欄位
                ,KEY_ROWID+ "=" + rowId,null,null,null,null);


    }


    public boolean update(long rowId,String note){
        ContentValues args=new ContentValues();
        args.put(KEY_NOTE,note);
        return db.update(DATABASE_TABLE,args,KEY_ROWID+ "=" + rowId,null)>0;
    }

    public boolean deleteAll(){
//        return db.delete(DATABASE_TABLE,"1=1" ,null)>0;
//        return db.delete(DATABASE_TABLE,null ,null)>0;
        return delete(-1);
    }

    public boolean delete(long rowId){
        if (rowId>0)
            return db.delete(DATABASE_TABLE,KEY_ROWID + "=" + rowId,null)>0;
        else
            return  db.delete(DATABASE_TABLE,null ,null)>0;

    }
    public long create(String noteName){

        Date now=new Date();
        ContentValues args=new ContentValues();
        args.put(KEY_NOTE,noteName);

        args.put(KEY_CREATED,now.getMonth()+"-"+now.getDate()+" " +now.getHours()+":"+now.getMinutes()+":"+now.getSeconds());
        return  db.insert(DATABASE_TABLE,null,args);  //insert 完畢後會傳回一個id值,即是每一筆記錄的id
    }

}