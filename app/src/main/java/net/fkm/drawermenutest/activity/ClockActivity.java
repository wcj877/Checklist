package net.fkm.drawermenutest.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.service.ClockService;
import net.fkm.drawermenutest.utils.AlertDialogUtil;

//设置对话框视图
public class ClockActivity extends BaseActivity {
    //震动
    private Vibrator mVibrator;
    private ListInfo list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_clock;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        list = getIntent().getParcelableExtra(ClockService.EXTRA_List);
        if (list == null) {
            finish();
        }
    }

    private void clock() {
        long[] pattern = new long[]{1500, 1000};
        mVibrator.vibrate(pattern, 0);
        //获取自定义布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_alarm_layout, null);//获取时钟的布局
        TextView textView = inflate.findViewById(R.id.tv_event);//获取提示内容的视图
        textView.setText(String.format(getString(R.string.clock_event_msg_template), list.getDescribe()));//设置提示的内容
        Button btnConfirm = inflate.findViewById(R.id.btn_confirm);//获取确认视图
        final AlertDialog alertDialog = AlertDialogUtil.showDialog(this, inflate);//设置inflate为自定义对话框
        btnConfirm.setOnClickListener(new View.OnClickListener() {//点击确认关闭对话框
            @Override
            public void onClick(View v) {
                mVibrator.cancel();//关闭震动
                alertDialog.dismiss();//关闭退出对话框
                finish();
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        clock();
    }


    @Override
    protected void onStart() {
        super.onStart();
        clock();
    }
}