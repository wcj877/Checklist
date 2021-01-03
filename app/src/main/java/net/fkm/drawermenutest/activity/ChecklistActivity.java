package net.fkm.drawermenutest.activity;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.dao.ListDao;
import net.fkm.drawermenutest.fragment.ListFragment;
import net.fkm.drawermenutest.manager.ClockManager;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.service.ClockService;
import net.fkm.drawermenutest.utils.Constants;
import net.fkm.drawermenutest.utils.DateTimeUtil;
import net.fkm.drawermenutest.receiver.ClockReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChecklistActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<String> list = new ArrayList<String>();//清单分类的下拉列表中的内容
    List<Map<String, Object>> mapList;//下拉列表的内容
    private ClockManager clockManager = ClockManager.getInstance();//时钟管理器
    private Spinner spinnertext;//清单分类的下拉列表
    private Spinner spinnerimg;//优先的下拉列表
    private ArrayAdapter<String> adapter;//下拉列表的适配器
    public static ListInfo checklist;//保存此清单中的全部信息
    private Button bt;//时间选择控件
    private Button save;//保存的控件
    private Button delete;//删除控件
    private ImageView backtrack;//退出控件
    private EditText title;//清单标题
    private EditText description;//清单描述的控件
    private boolean isIncrease = true;//是否为添加清单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_checklist);

        checklist = new ListInfo();
        Intent intent = getIntent();
        ListInfo listInfo = (ListInfo) intent.getParcelableExtra("listInfo");//获取传递的清单
        if (listInfo != null){//若为清单编辑页则会传递清单的内容，否则为null
            this.checklist = listInfo;
            isIncrease = false;
        }

        title = findViewById(R.id.save_title);
        description = findViewById(R.id.description);
        save = findViewById(R.id.save);
        delete = findViewById(R.id.bt_delete);

        //判断是否登录
        if (Constants.user == null){
            Toast.makeText(ChecklistActivity.this, "请先登录",Toast.LENGTH_LONG).show();
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

        /*spinnertext加载适配器*/
        spinnertext.setAdapter(adapter);

        /*soDown的监听器*/
        spinnertext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String info = (String) spinnertext.getSelectedItem();
                int i = list.indexOf(info);//获取选择的是第几个分类
                checklist.setListStatus(i);//设置获取的分类

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

        //为spinnerimg设置侦听器
        spinnerimg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //parent为一个Map结构的和数据
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                int i = mapList.indexOf(map);//获取选择的几级优先级
                checklist.setPriority(i);//设置清单的优先级

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


        //监听保存点击事件
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checklist.setUserId(Constants.user.getUserId());
                checklist.setDescribe(description.getText().toString());
                checklist.setListTitle(title.getText().toString());
                ListDao listDao = new ListDao(ChecklistActivity.this);

                if (checklist.getTime() == null){//但没选择清单时间时设置为空字符串
                    checklist.setTime("");
                }

                //判断是否增加
                if (isIncrease){
                    listDao.addList(checklist);//添加清单

                } else {
                    listDao.updateList(checklist);//修改清单
                    //添加时钟
                    clockManager.addAlarm(buildIntent(checklist.getListId()), DateTimeUtil.str2Date(checklist.getTime()));
                }

                ListFragment.instance.showList();//刷新显示的清单
                finish();
            }
        });


        //监听删除按钮
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断该清单是否为新建的，如果为新建的直接关闭该Activity，不是则先删除在关闭Activity
                if (0 == checklist.getListId()){
                    finish();
                } else {
                    ListDao dao = new ListDao(ChecklistActivity.this);
                    dao.deleteList(String.valueOf(checklist.getListId()));//删除指定清单
                    ListFragment.instance.showList();//刷新显示的清单
                    Toast.makeText(ChecklistActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                    finish();//关闭当前Activity
                }
            }
        });


        //加载清单的内容
        if (!isIncrease){
            spinnertext.setSelection(checklist.getListStatus());
            spinnerimg.setSelection(checklist.getPriority());
            if ( ! "".equals(checklist.getDate()) && checklist.getDate() != null){
                bt.setText(checklist.getDate());
            }
            title.setText(checklist.getListTitle());
            description.setText(checklist.getDescribe());
        }
    }

    private PendingIntent buildIntent(int id) {//建立意图
        Intent intent = new Intent();
        intent.putExtra(ClockReceiver.EXTRA_List_ID, id);
        intent.setClass(this, ClockService.class);

        return PendingIntent.getService(this, 0x001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(ChecklistActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                String month = String.valueOf(monthOfYear);
                if (monthOfYear < 10){
                    month = "0"+month;
                }

                String day = String.valueOf(dayOfMonth);
                if (dayOfMonth<10){
                    day = "0"+day;
                }

                ChecklistActivity.this.bt.setText(year + "-" + month + "-" + day);//设置按钮的内容为年月日
                checklist.setTime(year + "-" + month + "-" + month);
                checklist.setDate(year + "-" + month + "-" + month);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));//返回年， 月， 日



        TimePickerDialog timePickerDialog = new TimePickerDialog(ChecklistActivity.this, new TimePickerDialog.OnTimeSetListener() {
            //实现监听方法
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                //设置文本显示内容
                System.out.println(("当前时间：日   " + i + ":" + i1));
                checklist.setTime(checklist.getTime()+" "+i+":"+i1);
                ChecklistActivity.this.bt.setText(checklist.getTime());
                System.out.println("时间："+checklist.getTime());
            }
        },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);//记得使用show才能显示！

        timePickerDialog.show();//将时间选择显示在页面上

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