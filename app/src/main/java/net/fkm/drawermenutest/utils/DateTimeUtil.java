package net.fkm.drawermenutest.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static Date str2Date(String src)  {
        //设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT, Locale.CHINA);
        try {
            return sdf.parse(src);//将src转换成日期格式
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
