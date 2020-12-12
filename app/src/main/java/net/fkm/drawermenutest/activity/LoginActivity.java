package net.fkm.drawermenutest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.dao.UserDao;
import net.fkm.drawermenutest.utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.registered)
    Button registered;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.login, R.id.registered})
    public void conClick(View v){
        UserDao userDao = new UserDao(LoginActivity.this);
        switch (v.getId()){
            case R.id.login:
                //登录


                if (userDao.findUser(username.getText().toString(), password.getText().toString()) != null){
                    //记录登录用户id
                    Constants.user = userDao.findUser(username.getText().toString(), password.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);//跳转至首页

                } else {
                    Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.registered:
                //注册
                Pattern patternName = Pattern.compile("[a-zA-z_0-9]{3,10}");
                Matcher matcherName = patternName.matcher(username.getText().toString());

                Pattern patternPassword = Pattern.compile("[a-zA-z_0-9]{3,10}");
                Matcher matcherPassword = patternPassword.matcher(password.getText().toString());


                if (matcherName.matches()){//判断账号是否符合正则表达式
                    if (matcherPassword.matches()){//判断密码是否符合正则表达式
                        if (!userDao.queryUser(username.getText().toString())){
                            if (userDao.addUser(username.getText().toString(), password.getText().toString()) > 0){
                                Constants.user = userDao.findUser(username.getText().toString(), password.getText().toString());
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                                startActivity(intent);//跳转至首页
                            } else {
                                Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "该账号已被注册", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "密码只能是3~10位的数字或字母", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "账号只能是3~10位的数字或字母", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}