package net.fkm.drawermenutest.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.dao.ListDao;
import net.fkm.drawermenutest.model.ListInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.Unbinder;

/**
 * 清单页面
 * 显示学习，工作，锻炼等清单
 */
public class ListFragment extends Fragment {

    private Unbinder unbinder;
    private Activity mContext;

    ListView listView;
    ArrayList<ListInfo> list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //给fragment添加布局文件（1.resource:布局的资源id，2.root 填充的根视图，3.attachToRoot 是否将载入的视图绑定到根视图中）
        View view = inflater.inflate(R.layout.fragment_list, container, false);

//        initView();
        listView = view.findViewById(R.id.paihanglist);

        ListDao listDao = new ListDao(getContext());
        list = listDao.queryAll("u_101");

        MyListAdapter myListAdapter=new MyListAdapter(getContext(),list);
        listView.setAdapter(myListAdapter);
        //这个接口定义了当AdapterView中一元素被点击时，一个回调函数被调用
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ListInfo listInfo=list.get(position);
//                Intent intent=new Intent();
//                intent.putExtra("ispsy","2");
//                intent.putExtra("name",distributor.getDistributor_name());
//                intent.putExtra("phone",distributor.getDistributor_tel()+"");
//                intent.putExtra("idcar",distributor.getDistributor_idcard()+"");
//                intent.putExtra("jdcs",distributor.getDistributor_singularnum()+"");
//                Log.i("data",distributor.getDistributor_idcard()+distributor.getDistributor_tel()+"");
////                intent.setClass(getContext(), PersonInfoActivity.class);
////                startActivity(intent);
//            }
//        });

        return view;
    }

//    private void initView() {
//        unbinder = ButterKnife.bind(this, mContentView);
//    }
//
//
//    @OnClick({})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

    class MyListAdapter extends BaseAdapter{
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
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.list_checklist, null);
                viewHolder.confirm = (ImageView)convertView.findViewById(R.id.list_confirm);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_title);
                viewHolder.priority = (ImageView)convertView.findViewById(R.id.list_priority);

                convertView.setTag(viewHolder);
            }else{
                 viewHolder = (ViewHolder) convertView.getTag();

            }
            ListInfo listInfo = list.get(position);


//            Bitmap unfinished = BitmapFactory.decodeResource(getResources(), R.mipmap.unfinished);//自己本地的图片可以是drawabe/mipmap
//            Bitmap accomplish = BitmapFactory.decodeResource(getResources(), R.mipmap.accomplish);//自己本地的图片可以是drawabe/mipmap
//
//            Bitmap red = BitmapFactory.decodeResource(getResources(), R.mipmap.red);//自己本地的图片可以是drawabe/mipmap
//            Bitmap yellow = BitmapFactory.decodeResource(getResources(), R.mipmap.yellow);//自己本地的图片可以是drawabe/mipmap
//            Bitmap blue = BitmapFactory.decodeResource(getResources(), R.mipmap.blue);//自己本地的图片可以是drawabe/mipmap
//            Bitmap gray = BitmapFactory.decodeResource(getResources(), R.mipmap.gray);//自己本地的图片可以是drawabe/mipmap

//            switch (listInfo.getListStatus()){
//                case 0:
//                    viewHolder.confirm.setImageURI(Uri.fromFile(new File(imageTranslateUri(R.mipmap.unfinished))));
//                    break;
//                case 1:
//                    viewHolder.confirm.setImageURI(Uri.fromFile(new File(imageTranslateUri(R.mipmap.accomplish))));
//                    break;
//            }


            switch (listInfo.getListStatus()){
                case 0:
                    viewHolder.confirm.setImageDrawable(getResources().getDrawable((R.mipmap.unfinished)));
                    break;
                case 1:
                    viewHolder.confirm.setImageDrawable(getResources().getDrawable((R.mipmap.accomplish)));
                    break;
            }


            System.out.println(viewHolder.confirm);

            viewHolder.title.setText(listInfo.getListTitle());

            switch (listInfo.getPriority()){
                case 0:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.red)));
                    break;
                case 1:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.yellow)));
                    break;
                case 2:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.blue)));
                    break;
                case 4:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.gray)));
                    break;
            }
            System.out.println(viewHolder.priority);

            return convertView;
        }
    }

    /**
     * res/drawable(mipmap)/xxx.png::::uri－－－－>url
     *
     * @return
     */
    private Uri imageTranslateUri(int resId) {

        Resources r = getResources();
        Uri uri = Uri.parse(r.getResourcePackageName(resId) + "/"
                + r.getResourceTypeName(resId) + "/"
                + r.getResourceEntryName(resId));

        return uri;
    }

    static class ViewHolder {
        public ImageView confirm;
        public TextView title;
        public ImageView priority;
    }
}


