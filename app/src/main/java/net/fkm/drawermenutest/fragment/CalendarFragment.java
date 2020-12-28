package net.fkm.drawermenutest.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.activity.ChecklistActivity;
import net.fkm.drawermenutest.dao.ListDao;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.utils.Constants;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CalendarFragment extends Fragment {

    private View mContentView;
    private Unbinder unbinder;
    private Activity mContext;
    private CalendarView calendarView;
    ArrayList<ListInfo> list;
    private ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_calendar, container, false);
        initView();
        initData();

        listView = mContentView.findViewById(R.id.paihanglist);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //绑定控件
        calendarView=mContentView.findViewById(R.id.calenderView);
//        calendarView.setDateTextAppearance()
        //点击日期触发
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
//                Toast.makeText(getContext(), "" + i + "年" + i1 + "月" + i2 + "日", Toast.LENGTH_SHORT).show();
                i1+=1;

                String date = "" + i + "-";
                if (i1 < 10){
                    date = date + "0" + i1;
                }else{
                    date = date + i1;
                }

                if (i2<10){
                    date = date + "-0" +i2;
                }else{
                    date = date + "-" +i2;
                }

                showList(date);

            }
        });

        showList(Constants.getDate());

        return mContentView;
    }

    public void showList(String date){
        if (Constants.user == null)
            return;
        ListDao listDao = new ListDao(getContext());
        list = listDao.queryAll(Constants.user.getUserId(), date);

        CalendarFragment.MyListAdapter myListAdapter=new CalendarFragment.MyListAdapter(getContext(),list);
        listView.setAdapter(myListAdapter);
    }

    private void initView() {
        unbinder = ButterKnife.bind(this, mContentView);
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
        ArrayList<ListInfo> list;

        public MyListAdapter(Context context, ArrayList<ListInfo> list) {
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListFragment.ViewHolder viewHolder = new ListFragment.ViewHolder();
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.list_checklist, null);
                viewHolder.confirm = (ImageView)convertView.findViewById(R.id.list_confirm);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_title);
                viewHolder.priority = (ImageView)convertView.findViewById(R.id.list_priority);
                viewHolder.line = (ConstraintLayout) convertView.findViewById(R.id.line);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ListFragment.ViewHolder) convertView.getTag();

            }
            ListInfo listInfo = list.get(position);

            //判断清单是否完成以显示对应的图
            switch (listInfo.getIsPerfection()){
                case 0:
                    viewHolder.confirm.setImageDrawable(getResources().getDrawable((R.mipmap.unfinished)));
                    break;
                case 1:
                    viewHolder.confirm.setImageDrawable(getResources().getDrawable((R.mipmap.accomplish)));
                    break;
            }

            //设置清单的内容
            viewHolder.title.setText(listInfo.getListTitle());

            //判断清单的优先级以显示对应的图
            switch (listInfo.getPriority()){
                case 0:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.gray)));
                    break;
                case 1:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.blue)));
                    break;
                case 2:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.yellow)));
                    break;
                case 4:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.red)));
                    break;
            }
            return convertView;
        }
    }
    static class ViewHolder {
        public ImageView confirm;
        public TextView title;
        public ImageView priority;
        public ConstraintLayout line;
    }

}