package net.fkm.drawermenutest.utils;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.view.WindowManager;

import net.fkm.drawermenutest.application.MemoApplication;

import static android.content.Context.KEYGUARD_SERVICE;

public class WakeLockUtil {
    /**
     * 唤醒手机屏幕并解锁
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void wakeUpAndUnlock() {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) MemoApplication.getContext()
                .getSystemService(Context.POWER_SERVICE);
        assert pm != null;//断言：当条件为true时才继续运行，否则抛出系统运行错误
        boolean screenOn = pm.isInteractive();//获取电源状态（是否有电）
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) MemoApplication.getContext()
                .getSystemService(KEYGUARD_SERVICE);
        assert keyguardManager != null;
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }
}
