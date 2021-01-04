package net.fkm.drawermenutest.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import net.fkm.drawermenutest.R;

public class AlertDialogUtil {
    //显示自定义对话框
    public static AlertDialog showDialog(Context context, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);//设置对话框的视图
        builder.setCancelable(false);//取消对话框的取消按钮
        return builder.create();//创建并返回此对话框
    }
}
