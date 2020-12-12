package net.fkm.drawermenutest.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.fkm.drawermenutest.db.DBOpenHelper;
import net.fkm.drawermenutest.model.UserInfo;
import net.fkm.drawermenutest.utils.L;

public class UserDao {
    private SQLiteOpenHelper helper;//一个帮助程序类，用于管理数据库创建和版本管理。
    private SQLiteDatabase db;//SQLiteDatabase具有创建，删除，执行SQL命令和执行其他常见数据库管理任务的方法。
    public UserDao(Context context){
        helper=new DBOpenHelper(context);
    }


    /**
     * 获取id为 userId的用户
     * @param userId
     * @return
     */

    public UserInfo findUser(String userId, String password){
        db=helper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor = db.rawQuery("select * from user where user_id='" + userId + "' and password='" + password + "';", null);
        if (cursor.moveToNext()){
            UserInfo userInfo=new UserInfo();
            userInfo.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
            userInfo.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            userInfo.setTeme(cursor.getInt(cursor.getColumnIndex("teme")));
            userInfo.setUserCon(cursor.getInt(cursor.getColumnIndex("user_con")));
            userInfo.setUserDate(cursor.getString(cursor.getColumnIndex("user_date")));
            userInfo.setUserStutas(cursor.getInt(cursor.getColumnIndex("user_status")));
            userInfo.setUserTotal(cursor.getInt(cursor.getColumnIndex("user_total")));
            return userInfo;
        }
        return null;
    }

    public Boolean queryUser(String userId){
        db=helper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor = db.rawQuery("select * from user where user_id='" + userId + "'", null);
        return cursor.moveToNext();
    }

    /**
     * 增加一个用户
     * @param userId
     * @param password
     * @return
     */
    public Long addUser(String userId, String password){
        db=helper.getWritableDatabase();
        long insNum=0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id",userId);
        contentValues.put("password",password);
        contentValues.put("user_status",0);
        contentValues.put("teme",1);
        contentValues.put("user_con",0);
        contentValues.put("user_total",0);
//        contentValues.put("user_date",);
        insNum = db.insert("user",null,contentValues);
        return insNum;
    }
}