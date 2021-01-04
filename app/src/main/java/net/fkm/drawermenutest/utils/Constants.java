package net.fkm.drawermenutest.utils;

import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.model.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Constants {
    public static UserInfo user = null;
    public static int listStatus = 0;
    public static String sortBy = null;
    public static boolean showCompleted = false;
    public static boolean isToDay = false;//是否今天
    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static boolean isWeek = false;//是否一周内
    public static boolean isMonth = false;//是否一个月内

    //获取当前日期
    public static String getDate(){
        SimpleDateFormat formatter= new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    //获取一周前的日期
    public static String getWeekDate(){
        SimpleDateFormat formatter= new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        Date date = calendar.getTime();
        return formatter.format(date);
    }

    public static String getMonthDate(){
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        return format.format(date);
    }
}
