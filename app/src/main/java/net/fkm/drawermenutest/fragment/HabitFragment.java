package net.fkm.drawermenutest.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.dao.HabitDao;
import net.fkm.drawermenutest.model.HabitInfo;
import net.fkm.drawermenutest.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HabitFragment extends Fragment {
    public static HabitFragment instance;

    private View mContentView;//布局视图
    private Unbinder unbinder;
    private ArrayList<HabitInfo> habitList;//用户创建的习惯

    @BindView(R.id.paihanglist)
    public ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_countdown, container, false);
        initView();
        initData();

        instance = this;

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        showHabit();

        return mContentView;
    }

    public void showHabit(){
        if (Constants.user == null)
            return;
         HabitDao habitDao = new HabitDao(getContext());
         habitList = habitDao.queryAll(Constants.user.getUserId());
         //创建适配器
        HabitFragment.MyListAdapter myListAdapter=new HabitFragment.MyListAdapter(getContext(),habitList);
        listView.setAdapter(myListAdapter);
    }

    private void initView() {
        unbinder = ButterKnife.bind(this, mContentView);//设置为根视图
    }

    private void initData() {

    }

    @OnClick({})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    class MyListAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        ArrayList<HabitInfo> list;

        public MyListAdapter(Context context, ArrayList<HabitInfo> list) {
            layoutInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HabitFragment.ViewHolder viewHolder = new HabitFragment.ViewHolder();
            HabitDao habitDao = new HabitDao(getContext());

            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.list_habit, null);
                viewHolder.confirm = (ImageView)convertView.findViewById(R.id.habit_confirm);
                viewHolder.title = (TextView) convertView.findViewById(R.id.habit_title);
                viewHolder.content = (TextView) convertView.findViewById(R.id.habit_content);
                viewHolder.num = (TextView) convertView.findViewById(R.id.habit_num);
                viewHolder.delete = (ImageView) convertView.findViewById(R.id.btnDelete);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (HabitFragment.ViewHolder) convertView.getTag();

            }
            HabitInfo habitInfo = list.get(position);

            /**
             * 判断该习惯上次完成时间是否为今天同时是否完成
             * 若不为今天且已完成了的习惯则将该习惯的状态改为为未完成
             * 否则不执行
             */
            if ( ! Constants.getDate().equals(habitInfo.getHabitDate()) && habitInfo.getHabitStatus() == 1){
                habitDao.updateStatus(habitInfo.getHabitId());
                habitInfo.setHabitStatus(0);
            }


            //判断习惯是否完成以显示对应的图
            switch (habitInfo.getHabitStatus()){
                case 0:
                    viewHolder.confirm.setImageDrawable(getResources().getDrawable((R.mipmap.undone)));//设置未完成图片
                    break;
                case 1:
                    viewHolder.confirm.setImageDrawable(getResources().getDrawable((R.mipmap.success)));//设置完成图片

                    break;
            }

            //设置习惯的内容
            viewHolder.title.setText(habitInfo.getListTitle());

            viewHolder.content.setText(habitInfo.getDescribe());

            //设置坚持天数
            viewHolder.num.setText(String.valueOf(habitInfo.getHabitTotal()));

            //监听习惯完成的点击事件,
            viewHolder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    habitDao.updateDate(habitInfo.getHabitId(), habitInfo.getHabitTotal(), habitInfo.getHabitStatus());
                    showHabit();
                }
            });

            //监听删除按钮
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    habitDao.deleteHabit(habitInfo.getHabitId());
                    showHabit();
                    Toast.makeText(getContext(),"删除成功",Toast.LENGTH_LONG).show();
                }
            });


            return convertView;
        }
    }


    static class ViewHolder {
        public ImageView confirm;
        public TextView title;
        public TextView content;
        public TextView num;
        public ImageView delete;
    }
}