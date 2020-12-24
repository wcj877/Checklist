package net.fkm.drawermenutest.utils;

import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.model.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
    public static UserInfo user = null;
    public static int listStatus = 0;
    public static String sortBy = null;
    public static boolean showCompleted = false;
    public static boolean isToDay = false;


    public static String getDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
