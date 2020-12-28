package net.fkm.drawermenutest.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.fkm.drawermenutest.db.DBOpenHelper;
import net.fkm.drawermenutest.model.HabitInfo;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.utils.Constants;

import java.util.ArrayList;

public class HabitDao {

    private SQLiteOpenHelper helper;//一个帮助程序类，用于管理数据库创建和版本管理。
    private SQLiteDatabase db;//SQLiteDatabase具有创建，删除，执行SQL命令和执行其他常见数据库管理任务的方法。
    public HabitDao(Context context){
        helper=new DBOpenHelper(context);
    }

    /**
     * 获取该用户的全部习惯
     * @param userId
     * @return
     */
    public ArrayList<HabitInfo> queryAll(String userId){
        ArrayList<HabitInfo> habitInfos = new ArrayList<>();

        db=helper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor;

        String sql = "select * from habit where user_id = '" + userId + "'";

        cursor = db.rawQuery(sql, null);


        while (cursor.moveToNext()){
            HabitInfo habit = new HabitInfo();

            habit.setHabitId(cursor.getString(cursor.getColumnIndex("habit_id")));
            habit.setListTitle(cursor.getString(cursor.getColumnIndex("list_title")));
            habit.setHabitStatus(cursor.getInt(cursor.getColumnIndex("habit_status")));
            habit.setHabitCon(cursor.getInt(cursor.getColumnIndex("habit_con")));
            habit.setHabitTotal(cursor.getInt(cursor.getColumnIndex("habit_total")));
            habit.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
            habit.setDescribe(cursor.getString(cursor.getColumnIndex("describe")));
            habit.setHabitDate(cursor.getString(cursor.getColumnIndex("habit_date")));

            habitInfos.add(habit);
        }
        db.close();
        return habitInfos;
    }


    /**
     * 增加每日习惯
     * @param habitInfo
     * @return
     */
    public long addHabit(HabitInfo habitInfo){
        db=helper.getWritableDatabase();
        long insNum=0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("list_title", habitInfo.getListTitle());
        contentValues.put("habit_status", 0);
        contentValues.put("habit_con", 0);
        contentValues.put("habit_total", 0);
        contentValues.put("user_id",habitInfo.getUserId());
        contentValues.put("describe", habitInfo.getDescribe());
        contentValues.put("habit_date", "");
//        contentValues.put("user_date",);
        insNum = db.insert("habit",null,contentValues);
        db.close();
        return insNum;
    }

    /**
     * 修改习惯完成时间
     * @param habitId
     * @param habitTotal
     */
    public void updateDate(String habitId, int habitTotal, int habitStatus){
        db = helper.getReadableDatabase();
        Cursor cursor;

        String habitDate = Constants.getDate();

        if (habitStatus == 0){
            habitStatus = 1;
            habitTotal++;
        } else {
            habitStatus = 0;
            habitTotal--;
            habitDate = "";
        }

        cursor = db.rawQuery("update habit set habit_total = '" + habitTotal + "' , habit_date = '" + habitDate  +
                "' , habit_status = '" + habitStatus + "'  where habit_id = '"+ habitId +"';",null);

        if (cursor.moveToNext()){
            cursor.close();
            db.close();
        }
        cursor.close();
        db.close();
    }

    /**
     * 用于刷新习惯是否今天完成
     * @param habitId
     */
    public void updateStatus(String habitId){
        db = helper.getReadableDatabase();
        Cursor cursor;


        cursor = db.rawQuery("update habit set habit_status = 0  where habit_id = '"+ habitId +"';",null);

        if (cursor.moveToNext()){
            cursor.close();
            db.close();
        }
        cursor.close();
        db.close();
    }

    /**
     * 删除习惯
     * @param habitId
     */
    public void deleteHabit(String habitId){
        db = helper.getReadableDatabase();

        db.delete("habit", " habit_id = ? ", new String[]{habitId});

        db.close();
    }
}
