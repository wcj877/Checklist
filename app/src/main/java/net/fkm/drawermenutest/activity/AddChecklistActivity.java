package net.fkm.drawermenutest.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.cj.xdevapi.AddStatement;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.dao.ListDao;
import net.fkm.drawermenutest.fragment.ListFragment;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AddChecklistActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<String> list = new ArrayList<String>();
    List<Map<String, Object>> mapList;
    private Spinner spinnertext;
    private Spinner spinnerimg;
    private ArrayAdapter<String> adapter;
    private ListInfo checklist;
    private Button bt;
    private Button save;
    private ImageView backtrack;
    private EditText title;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_checklist);

        title = findViewById(R.id.save_title);
        description = findViewById(R.id.description);
        save = findViewById(R.id.save);

        //判断是否登录
        if (Constants.user == null){
            Toast.makeText(AddChecklistActivity.this, "请先登录",Toast.LENGTH_LONG).show();
            finish();
        }

        //退出增加清单页面
        backtrack = findViewById(R.id.iv_return);
        backtrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checklist = new ListInfo();

        //------------------------------------------加载清单分类下拉框
        spinnertext=(Spinner) findViewById(R.id.spinner1);

        /*设置数据源*/
        list=new ArrayList<String>();
        list.add("收集箱");
        list.add("学习");
        list.add("锻炼");
        list.add("工作");

        /*新建适配器*/
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        /*spDown加载适配器*/
        spinnertext.setAdapter(adapter);

        /*soDown的监听器*/
        spinnertext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String info = (String) spinnertext.getSelectedItem();
                int i = list.indexOf(info);
//                Toast.makeText(AddChecklistActivity.this, i+"" ,Toast.LENGTH_LONG).show();
                checklist.setListStatus(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //------------------------------加载优先级下拉框
        spinnerimg = (Spinner) findViewById(R.id.spinner_img);

        //声明一个简单simpleAdapter
        SimpleAdapter simpleAdapter =new SimpleAdapter(this, getListData(), R.layout.pic_item,
                new String[]{"npic","namepic"}, new int[]{R.id.imageview,R.id.textview});

        //绑定到spinnerimg
        spinnerimg.setAdapter(simpleAdapter);

        spinnerimg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //parent为一个Map结构的和数据
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                int i = mapList.indexOf(map);
//                Toast.makeText(AddChecklistActivity.this, i+""
//                        ,Toast.LENGTH_SHORT).show();
//                map.get("namepic").toString()
                checklist.setPriority(i);

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        //------------------------加载获取时间
        bt = findViewById(R.id.BT);//获取获取时间按钮

        bt.setOnClickListener(new View.OnClickListener() {//设置获取时间按钮点击事件
            @Override
            public void onClick(View v) {
                showDatePickDlg();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checklist.setUserId(Constants.user.getUserId());
//                checklist.setIsPerfection(0);
                checklist.setDescribe(description.getText().toString());
                checklist.setListTitle(title.getText().toString());
                ListDao listDao = new ListDao(AddChecklistActivity.this);
                listDao.addList(checklist);
                ListFragment.instance.showList();
                finish();
            }
        });
    }

    public List<Map<String, Object>> getListData() {

        mapList = new ArrayList<Map<String, Object>>();

        //每个Map结构为一条数据，key与Adapter中定义的String数组中定义的一一对应。

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("npic", R.drawable.gray);
        map.put("namepic", "无优先级");
        mapList.add(map);

        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("npic", R.drawable.blue);
        map2.put("namepic", "低优先级");
        mapList.add(map2);

        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("npic", R.drawable.yellow);
        map3.put("namepic", "中优先级");
        mapList.add(map3);

        HashMap<String, Object> map4 = new HashMap<String, Object>();
        map4.put("npic", R.drawable.red);
        map4.put("namepic", "高优先级");
        mapList.add(map4);

        return mapList;
    }

    public void showDatePickDlg () {
        //使用默认时区和区域设置获取日历(当前时区)
        Calendar calendar = Calendar.getInstance();
        //设置日期选择的内容为年月日
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddChecklistActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AddChecklistActivity.this.bt.setText(year + "-" + monthOfYear + "-" + dayOfMonth);//设置按钮的内容为年月日
                checklist.setTime(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));//返回年， 月， 日

        datePickerDialog.show();//将日期选择显示在页面上

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_main, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        String cityName=adapter.getItem(position);   //获取选中的那一项
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.menu_delete_list:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}