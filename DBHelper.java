package com.thkang.svdr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thkan on 2017-12-03.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sovendori.db";
    private static final String TABLE_NAME = "UserInfo";
    private static final String COLUMN_ID = "Userid";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_PROTECTNAME = "Protectname";
    private static final String COLUMN_PHONE = "Phone";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "CREATE TABLE UserInfo (Userid text primary key not null, Password text not null, Name text not null, Protectname text not null, Phone text not null)";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 UserInfo이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " +TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insert(String Userid, String Password) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO UserInfo VALUES(null, '" + Userid + "', " + Password + "';");
        db.close();
    }

    public void update(String Userid, String Password) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE UserInfo SET Userid=" + Userid + " WHERE Password='" + Password + "';");
        db.close();
    }

    public void delete(String Userid) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM UserInfo WHERE Userid='" + Userid + "';");
        db.close();
    }

    public String searchPassword(String userid){
        db = this.getReadableDatabase();
        String query = "SELECT Userid, Password from "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String uid, pass;

        pass = "not found";
        if(cursor.moveToFirst()){
            do{
                uid = cursor.getString(0);

                if(uid.equals(userid)){
                    pass = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }

        return pass;
    }


    public void insertContact(Contact c){
        db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_ID, c.getUserid());
        values.put(COLUMN_PASSWORD, c.getPassword());
        values.put(COLUMN_PROTECTNAME, c.getProtectname());
        values.put(COLUMN_PHONE, c.getPhone());

        db.insert(TABLE_NAME, null, values);
    }

}
