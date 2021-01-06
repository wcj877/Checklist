package net.fkm.drawermenutest.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    final static String DBNAME="experss_db.db";
    final static int VERSION=2;

    public DBOpenHelper(@Nullable Context context) {//创建数据库
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建用户表
        db.execSQL("create table 'user'(" +
                "'user_id' varvhar(20) not null ," +
                "'password' varchar(20) not null," +
                "'user_status' int not null," +
                "'user_con' int not null,"+
                "'user_total' int not null," +
                "'user_date' varchar(20)  ," +
                "primary key('user_id'));");

        db.execSQL("insert into user values('u_101','123456','0',0,'0','');");
        db.execSQL("insert into user values('u_102','123456','0',0,'0','');");

        //创建清单表
        db.execSQL("create table 'list'("+
                "`list_id` INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "`user_id` varchar(20) not null,"+
                "`list_title` varchar(40) not null,"+
                "`describe` varchar(255) ,"+
                "`list_status` int ,"+
                "`priority` int,"+
                "`isPerfection` int,"+
                "`isClocked` int,"+
                "`date` varchar(20),"+
                "`time` varchar(20) );");
        db.execSQL("insert into `list` values ('1','u_101','上课','今天有一节安卓课','1','3','0','0','2020-12-09','12:01')");
        db.execSQL("insert into `list` values ('2','u_101','开会','下午两点开小组会议','3','3','0','0','2020-12-10','16:23')");
        db.execSQL("insert into `list` values ('3','u_101','学习','看完JAVA多态','1','2','0','0','2020-12-11','09:20')");
        db.execSQL("insert into `list` values ('4','u_101','锻炼','跑步半小时','2','3','0','0','2020-12-12','10:11')");
        db.execSQL("insert into `list` values ('5','u_101','聚餐','发工资了，项目组一起吃火锅','3','1','0','0','2020-12-13','12:02')");
        db.execSQL("insert into `list` values ('6','u_101','做晚饭','下午五点做晚饭','2','2','0','0','2021-01-03','20:21')");
        db.execSQL("insert into `list` values ('7','u_101','打游戏','约起好友一起开黑','2','0','0','0','2021-01-04','18:01')");
        db.execSQL("insert into `list` values ('8','u_101','买电脑','中午吃完饭去买新电脑','3','3','0','0','2021-01-05','19:30')");
        db.execSQL("insert into `list` values ('9','u_101','骑车','和老友一起骑车车','2','2','0','0','2021-01-06','21:09')");
        db.execSQL("insert into `list` values ('10','u_101','旅游','去稻城看看','2','2','0','0','2021-01-07','14:55')");
        db.execSQL("insert into `list` values ('11','u_101','去医院看牙齿','做一次牙齿清洁','3','3','0','0','2021-01-07','12:07')");
        db.execSQL("insert into `list` values ('12','u_101','看电影','看《送你一朵小红花》','1','1','0','0','2021-01-07','17:33')");
        db.execSQL("insert into `list` values ('13','u_101','收拾行李','把厚衣服带回家','2','1','0','0','2021-01-08','11:11')");


        //创建习惯表
        db.execSQL("create table 'habit'("+
                "`habit_id` INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "`list_title` varchar(40) not null,"+
                "`habit_status` int ,"+
                "`habit_con` int ,"+
                "`habit_total` int,"+
                "`user_id` varchar(20) not null,"+
                "`describe` varchar(255),"+
                "`habit_date` varchar(20));"
        );

        //预存数据
        db.execSQL("insert into `habit` values ('1','学习','0','0','0','u_101','每天看一个小时的书','')");
        db.execSQL("insert into `habit` values ('2','跑步','0','0','0','u_101','每天跑步半个小时','')");
        db.execSQL("insert into `habit` values ('3','吃蔬菜','0','0','0','u_101','每天每天坚持多吃蔬菜','')");
        db.execSQL("insert into `habit` values ('4','早睡','0','0','0','u_101','每天22:30之前睡觉','')");
        db.execSQL("insert into `habit` values ('5','早起','0','0','0','u_101','每天07：30之前起床','')");
        db.execSQL("insert into `habit` values ('6','喝牛奶','0','0','0','u_101','每天喝一瓶纯牛奶','')");
        db.execSQL("insert into `habit` values ('7','午休','0','0','0','u_101','每天中午休息半个小时','')");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
