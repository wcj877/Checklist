package net.fkm.drawermenutest.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.dao.UserDao;
import net.fkm.drawermenutest.utils.Constants;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordActivity extends BaseActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_password);
//    }

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.new_password)
    EditText newPassword;

    @BindView(R.id.new_password_verification)
    EditText newPasswordVerification;

    @BindView(R.id.modify)
    Button modify;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (Constants.user == null){
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(PasswordActivity.this, HomeActivity.class);
//            startActivity(intent);
            finish();//关闭此activity页面
        }
    }

    @Override
    protected void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.modify)
    public void conClick(View v){
//        switch (v.getId()){
//            case R.id.modify:
//                //登录
                System.out.println("点击点击");
                UserDao userDao = new UserDao(PasswordActivity.this);

                String strPassword = password.getText().toString();//获取原密码
                String strNewPassword = newPassword.getText().toString();//获取第二次输入的新密码
                String strNewPasswordVerification = newPasswordVerification.getText().toString();//获取第一次输入的新密码

                Pattern patternName = Pattern.compile("[a-zA-z_0-9]{3,10}");//正则表达式
                Matcher matcherName = patternName.matcher(strNewPassword);//将strNewPassword匹配正则表达式

                if (strPassword.equals(Constants.user.getPassword())){ //判断输入的原密码是否与登录的密码一致
                    if (Objects.equals(strNewPassword, strNewPasswordVerification)){//判断两个新密码是否相同
                        if (matcherName.matches()){//判断strNewPassword与正则表达式是否匹配
                            Constants.user.setPassword(strNewPassword);//将保存在COnstants中用户的密码修改为新的密码
                            userDao.updatePassword(Constants.user.getUserId(), Constants.user.getPassword());//修改密码
                            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PasswordActivity.this, HomeActivity.class);
                            startActivity(intent);

                        } else{
                            Toast.makeText(this, "新密码只能为3-10位的数字字母", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "两个新密码不相同", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                }
//                break;
//            default:
//                break;
//        }
    }
}