package net.fkm.drawermenutest.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import net.fkm.drawermenutest.db.DBOpenHelper;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListDao {
    private SQLiteOpenHelper helper;//一个帮助程序类，用于管理数据库创建和版本管理。
    private SQLiteDatabase db;//SQLiteDatabase具有创建，删除，执行SQL命令和执行其他常见数据库管理任务的方法。
    public ListDao(Context context){
        helper=new DBOpenHelper(context);
    }

    //获取id为userId的用户的指定状态的清单
    public ArrayList<ListInfo> queryAll(String userId){
        ArrayList<ListInfo> listInfoList = new ArrayList<>();

        db=helper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor;

        String sql = "select * from list where user_id = '" + userId + "' ";

        if (Constants.listStatus>0){ //显示分类后的清单
            sql += " and list_status= '" + Constants.listStatus + "'";
            if (!Constants.showCompleted){ //是否显示已完成的订单
                sql += " and isPerfection =0  ";
                if (Constants.sortBy != null){//排序的字段不为空则执行
                    sql += "' ORDER BY " + Constants.sortBy + " DESC";
                }
            } else{
                if (Constants.sortBy != null){//排序的字段不为空则执行
                    sql += "' ORDER BY " + Constants.sortBy + " DESC";
                }
            }
        } else if (Constants.isToDay){

            String format = Constants.getDate();

            sql += " and time = '" + format + "'";
            if (!Constants.showCompleted){ //是否显示已完成的订单
                sql += " and isPerfection =0  ";
                if (Constants.sortBy != null){//排序的字段不为空则执行
                    sql += "' ORDER BY " + Constants.sortBy + " DESC";
                }
            } else{
                if (Constants.sortBy != null){//排序的字段不为空则执行
                    sql += "' ORDER BY " + Constants.sortBy + " DESC";
                }
            }

        }else { //显示收集箱中的清单（即全部清单）
            if (!Constants.showCompleted){ //是否显示已完成的订单
                sql += " and isPerfection =0  ";
                if (Constants.sortBy != null){//排序的字段不为空则执行
                    sql += " ORDER BY " + Constants.sortBy + " DESC";
                }
            } else{
                if (Constants.sortBy != null){//排序的字段不为空则执行
                    sql += " ORDER BY " + Constants.sortBy + " DESC";
                }
            }
        }

        cursor = db.rawQuery(sql, null);


        while (cursor.moveToNext()){
            ListInfo list=new ListInfo();

            list.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
            list.setListId(cursor.getInt(cursor.getColumnIndex("list_id")));
            list.setListTitle(cursor.getString(cursor.getColumnIndex("list_title")));
            list.setDescribe(cursor.getString(cursor.getColumnIndex("describe")));
            list.setListStatus(cursor.getInt(cursor.getColumnIndex("list_status")));
            list.setPriority(cursor.getInt(cursor.getColumnIndex("priority")));
            list.setIsPerfection(cursor.getInt(cursor.getColumnIndex("isPerfection")));
            list.setTime(cursor.getString(cursor.getColumnIndex("time")));
            listInfoList.add(list);
        }
        cursor.close();
        db.close();
        return listInfoList;
    }

    /**
     * 获取该用户的全部清单
     * @param userId
     * @param date
     * @return
     */
    public ArrayList<ListInfo> queryAll(String userId, String date){
        ArrayList<ListInfo> listInfoList = new ArrayList<>();

        db=helper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor;

        String sql = "select * from list where user_id = '" + userId + "' and  time = '" + date +"'";

        cursor = db.rawQuery(sql, null);


        while (cursor.moveToNext()){
            ListInfo list=new ListInfo();

            list.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
            list.setListId(cursor.getInt(cursor.getColumnIndex("list_id")));
            list.setListTitle(cursor.getString(cursor.getColumnIndex("list_title")));
            list.setDescribe(cursor.getString(cursor.getColumnIndex("describe")));
            list.setListStatus(cursor.getInt(cursor.getColumnIndex("list_status")));
            list.setPriority(cursor.getInt(cursor.getColumnIndex("priority")));
            list.setIsPerfection(cursor.getInt(cursor.getColumnIndex("isPerfection")));
            list.setTime(cursor.getString(cursor.getColumnIndex("time")));
            listInfoList.add(list);
        }
        db.close();
        return listInfoList;
    }

    /**
     * 修改清单是否完成
     * @param listId
     * @param isPerfection
     */
    public void updateStatus(int listId, int isPerfection){
        db = helper.getReadableDatabase();
        Cursor cursor;
        if (isPerfection == 1){
            isPerfection =0;
        } else {
            isPerfection = 1;
        }

        cursor = db.rawQuery("update list set isPerfection = '" + isPerfection + "'  where list_id = '"+ listId +"';",null);

        if (cursor.moveToNext()){
            cursor.close();
            db.close();
        }
        cursor.close();
        db.close();
    }

    /**
     * 增加清单
     * @param listInfo
     * @return
     */
    public long addList(ListInfo listInfo){
        db=helper.getWritableDatabase();
        long insNum=0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id",listInfo.getUserId());
        contentValues.put("list_title",listInfo.getListTitle());
        contentValues.put("describe", listInfo.getDescribe());
        contentValues.put("list_status", listInfo.getListStatus());
        contentValues.put("priority", listInfo.getPriority());
        contentValues.put("isPerfection",0);
        contentValues.put("time",listInfo.getTime());
//        contentValues.put("user_date",);
        insNum = db.insert("list",null,contentValues);
        db.close();
        return insNum;
    }

    /**
     * 修改清单
     * @param listInfo
     */
    public void updateList(ListInfo listInfo){
        db = helper.getReadableDatabase();

//        cursor = db.update("update list set list_title =? and describe =?   and list_status = ?  and priority =?  and time =?   where list_id =? ;",
//                new String(){listInfo.getListTitle(), listInfo.getDescribe(), listInfo.getListStatus(), listInfo.getPriority(), listInfo.getTime(), listInfo.getListId()});

        ContentValues contentValues = new ContentValues();
        contentValues.put("list_title", listInfo.getListTitle());
        contentValues.put("describe", listInfo.getDescribe());
        contentValues.put("list_status", listInfo.getListStatus());
        contentValues.put("priority", listInfo.getPriority());
        contentValues.put("time", listInfo.getTime());

        db.update("list", contentValues, " list_id = ?", new String[]{String.valueOf(listInfo.getListId())});

        db.close();
    }

    /**
     * 删除清单
     * @param listId
     */
    public void deleteList(String listId){
        db = helper.getReadableDatabase();

        db.delete("list", " list_id = ? ", new String[]{listId});

        db.close();
    }
}
