package net.fkm.drawermenutest.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import net.fkm.drawermenutest.activity.ClockActivity;
import net.fkm.drawermenutest.dao.ListDao;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.utils.WakeLockUtil;

public class ClockService extends Service {
    private static final String TAG = "ClockService";
    public static final String EXTRA_List_ID = "extra.list.id";
    public static final String EXTRA_List_REMIND_TIME = "extra.list.remind.time";
    public static final String EXTRA_List = "extra.list";
    private ListDao listDao = new ListDao();
    public ClockService() {
        Log.d(TAG, "ClockService: Constructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: onStartCommand");
        WakeLockUtil.wakeUpAndUnlock();
        postToClockActivity(getApplicationContext(), intent);
        return super.onStartCommand(intent, flags, startId);
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
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
