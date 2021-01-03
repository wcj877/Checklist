package net.fkm.drawermenutest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.dao.HabitDao;
import net.fkm.drawermenutest.fragment.CountdownFragment;
import net.fkm.drawermenutest.model.HabitInfo;
import net.fkm.drawermenutest.utils.Constants;

//编辑习惯
public class HabitActivity extends AppCompatActivity {

    private Button save;//保存的视图
    private TextView title;//习惯的标题
    private EditText description;//习惯的描述
    private ImageView backtrack;//退出的视图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        save = findViewById(R.id.save);
        title = findViewById(R.id.save_title);
        description = findViewById(R.id.description);
        backtrack = findViewById(R.id.iv_return);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().length() == 0){
                    Toast.makeText(HabitActivity.this,"标题不能为空",Toast.LENGTH_LONG).show();
                } else{

                    HabitInfo habitInfo = new HabitInfo();
                    habitInfo.setListTitle(title.getText().toString());
                    habitInfo.setDescribe(description.getText().toString());
                    habitInfo.setUserId(Constants.user.getUserId());

                    HabitDao habitDao = new HabitDao(HabitActivity.this);
                    habitDao.addHabit(habitInfo);

                    CountdownFragment.instance.showHabit();

                    Toast.makeText(HabitActivity.this,"添加成功",Toast.LENGTH_LONG).show();

                    finish();
                }

            }
        });

        //退出增加习惯页面
        backtrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}