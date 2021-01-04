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

//唤醒手机
public class WakeLockUtil {
    /**
     * 唤醒手机屏幕并解锁
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void wakeUpAndUnlock() {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) MemoApplication.getContext()
                .getSystemService(Context.POWER_SERVICE);

        if (pm != null)
            return;

        boolean screenOn = pm.isInteractive();//获取电源状态（是否有电）
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) MemoApplication.getContext().getSystemService(KEYGUARD_SERVICE);//获取键盘管理对象

        if (keyguardManager != null)
            return;

        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");//获取设置键盘锁定/解锁对象
        // 屏幕锁定
        keyguardLock.reenableKeyguard();//锁定
        keyguardLock.disableKeyguard(); // 解锁
    }
}
