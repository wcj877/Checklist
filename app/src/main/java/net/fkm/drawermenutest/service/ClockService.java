package net.fkm.drawermenutest.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import net.fkm.drawermenutest.activity.ClockActivity;
import net.fkm.drawermenutest.dao.ListDao;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.utils.WakeLockUtil;

//时钟服务
public class ClockService extends Service {
    private static final String TAG = "ClockService";
    public static final String EXTRA_List_ID = "extra.list.id";
    public static final String EXTRA_List_REMIND_TIME = "extra.list.remind.time";
    public static final String EXTRA_List = "extra.list";
    private ListDao listDao = new ListDao();
    public ClockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WakeLockUtil.wakeUpAndUnlock();
        postToClockActivity(getApplicationContext(), intent);
        return super.onStartCommand(intent, flags, startId);
    }

    //发布时钟活动
    private void postToClockActivity(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClass(context, ClockActivity.class);//设置调用类
        i.putExtra(EXTRA_List_ID, intent.getIntExtra(EXTRA_List_ID, -1));//将清单id添加至i中
        ListInfo list = listDao.findById(intent.getIntExtra(EXTRA_List_ID, -1));//从database中获取指定清单
        if (list == null) {
            return;
        }
        i.putExtra(EXTRA_List_REMIND_TIME, intent.getStringExtra(EXTRA_List_REMIND_TIME));//时钟响应时间添加到i中
        i.putExtra(EXTRA_List, list);//将list添加到i中
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//标记，为了能在service中使用activity
        context.startActivity(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
