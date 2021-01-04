package net.fkm.drawermenutest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import net.fkm.drawermenutest.activity.ClockActivity;
import net.fkm.drawermenutest.dao.ListDao;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.utils.WakeLockUtil;

//时钟接受器
public class ClockReceiver extends BroadcastReceiver {
    private static final String TAG = "ClockService";
    public static final String EXTRA_List_ID = "extra.list.id";
    public static final String EXTRA_List_REMIND_TIME = "extra.list.remind.time";
    public static final String EXTRA_List = "extra.list";
    private ListDao listDao = new ListDao();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onReceive(Context context, Intent intent) {
        WakeLockUtil.wakeUpAndUnlock();//唤醒手机屏幕
        postToClockActivity(context, intent);//发布时钟活动
    }

    //发布时钟活动
    private void postToClockActivity(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClass(context, ClockActivity.class);

        i.putExtra(EXTRA_List_ID, intent.getIntExtra(EXTRA_List_ID, -1));

        ListInfo list = listDao.findById(intent.getIntExtra(EXTRA_List_ID, -1));

        if (list == null) {
            return;
        }

        i.putExtra(EXTRA_List_REMIND_TIME, intent.getStringExtra(EXTRA_List_REMIND_TIME));
        i.putExtra(EXTRA_List, list);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//标记，为了能在service中使用activity
        context.startActivity(i);
    }

    public ClockReceiver() {
        super();
    }
}
