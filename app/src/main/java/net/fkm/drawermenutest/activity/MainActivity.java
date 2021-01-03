package net.fkm.drawermenutest.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.fkm.drawermenutest.R;

public class MainActivity extends AppCompatActivity {

    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.date);

        //点击"日期"按钮布局 设置日期
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过自定义控件AlertDialog实现
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                //设置日期简略显示 否则详细显示 包括:星期\周
                datePicker.setCalendarViewShown(false);
                //设置date布局
                builder.setView(view);
                builder.setTitle("选择出生日期");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //日期格式
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth()+1;
                        int dayOfMonth = datePicker.getDayOfMonth();
                        date.setText(year+"年"+month+"月"+dayOfMonth+"日");
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取 消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }
}