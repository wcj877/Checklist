package net.fkm.drawermenutest.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import net.fkm.drawermenutest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    private String text;
    //将字段绑定到指定ID的视图
//    @BindView(R.id.tv_text)
//    TextView tv_text;

    @Override
    protected int getLayoutId() {
        if ("版本更新".equals(getIntent().getStringExtra("text"))){
            text = "version";
            return R.layout.version;
        }else if ("个人中心".equals(getIntent().getStringExtra("text"))){
            text = "personal";
            return R.layout.personal;
        }else if ("意见反馈".equals(getIntent().getStringExtra("text"))){
            return R.layout.opinion;
        }
        else if ("给个评分呗".equals(getIntent().getStringExtra("text"))){
            text = "score";
            return R.layout.score;
        } else {
            return R.layout.cs;
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        if ("version".equals(text)){
            TextView update = findViewById(R.id.update);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestActivity.this,"当前已是最新版本",Toast.LENGTH_LONG).show();
                }
            });
        } else if ("personal".equals(text)){
            TableRow tableRow = findViewById(R.id.guide);

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestActivity.this,"新手指南",Toast.LENGTH_LONG).show();
                }
            });

            TableRow tableRow1 = findViewById(R.id.use);

            tableRow1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestActivity.this,"进阶使用",Toast.LENGTH_LONG).show();
                }
            });

            TableRow tableRow2 = findViewById(R.id.time);

            tableRow2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestActivity.this,"时间管理",Toast.LENGTH_LONG).show();
                }
            });

            TableRow tableRow3 = findViewById(R.id.problem);

            tableRow3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestActivity.this,"常见问题",Toast.LENGTH_LONG).show();
                }
            });

            TableRow tableRow4 = findViewById(R.id.manual);

            tableRow4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestActivity.this,"用户手册",Toast.LENGTH_LONG).show();
                }
            });

            TableRow tableRow5 = findViewById(R.id.forum);

            tableRow5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestActivity.this,"论坛",Toast.LENGTH_LONG).show();
                }
            });
        }else if ("score".equals(text)){
            TextView submit = findViewById(R.id.submit);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestActivity.this,"提交成功",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }

    @Override
    protected void initData() {
//        Intent intent = getIntent();
//        text = intent.getStringExtra("text");
//        tv_text.setText(String.format("%s测试界面", text));
    }

    @OnClick({R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
