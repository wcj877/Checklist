package net.fkm.drawermenutest.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.dao.UserDao;
import net.fkm.drawermenutest.fragment.CalendarFragment;
import net.fkm.drawermenutest.fragment.HabitFragment;
import net.fkm.drawermenutest.fragment.ListFragment;
import net.fkm.drawermenutest.utils.Constants;
import net.fkm.drawermenutest.utils.GlideUtil;
import net.fkm.drawermenutest.utils.PerfectClickListener;

//import net.fkm.drawermenutest.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {
    //此Activity的实例
    public static HomeActivity instance;
    // 头像URL
    public static final String IC_AVATAR = "https://clouddisc.oss-cn-hongkong.aliyuncs.com/image/ic_user.png?x-oss-process=style/thumb";

    private long exitTime = 0;//当前时间（毫秒）
    private View headerView;//抽屉视图
    TextView username;//显示用户名的TextView

    //将字段绑定到指定ID的视图
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    //将字段绑定到指定ID的视图
    @BindView(R.id.nav_view)
    NavigationView navView;
    //将字段绑定到指定ID的视图
    @BindView(R.id.vp_content)
    ViewPager vp_content;

    private List<Fragment> mFragment;//保存fragment视图

    //将字段绑定到指定ID的视图
    @BindView(R.id.iv_title_one)
    ImageView ivTitleOne;
    //将字段绑定到指定ID的视图
    @BindView(R.id.iv_title_two)
    ImageView ivTitleTwo;
    //将字段绑定到指定ID的视图
    @BindView(R.id.iv_title_three)
    ImageView ivTitleThree;
    //将字段绑定到指定ID的视图
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.activity_home_layout;
    }

    @Override
    protected void initView() {
        //指定的{@link Activity}中BindView带注释的字段和方法。当前内容视图用作视图根。
        ButterKnife.bind(this);
//        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, drawerLayout,
//                this.getResources().getColor(R.color.colorPrimary));
        initDrawerLayout();//设置布局
        initContentFragment();//设置初始化的内容
    }

    @Override
    protected void initData() {
        UserDao userDao = new UserDao(HomeActivity.this);
        Constants.user = userDao.getUser();
        if (Constants.user != null)
            username.setText(Constants.user.getUserId());

    }

    private void initDrawerLayout() {
        //添加抽屉布局
        navView.inflateHeaderView(R.layout.nav_header_main);

        headerView = navView.getHeaderView(0);

        ImageView iv_avatar = headerView.findViewById(R.id.iv_avatar);
        iv_avatar.setOnClickListener(listener);

        GlideUtil.displayCircle(iv_avatar, IC_AVATAR);

        LinearLayout ll_nav_account = headerView.findViewById(R.id.ll_nav_account);
        ll_nav_account.setOnClickListener(listener);

        LinearLayout ll_nav_password = headerView.findViewById(R.id.ll_nav_password);
        ll_nav_password.setOnClickListener(listener);

        LinearLayout ll_nav_feedback = headerView.findViewById(R.id.ll_nav_feedback);
        ll_nav_feedback.setOnClickListener(listener);

        LinearLayout ll_nav_version_update = headerView.findViewById(R.id.ll_nav_version_update);
        ll_nav_version_update.setOnClickListener(listener);

        LinearLayout ll_nav_score = headerView.findViewById(R.id.ll_nav_score);
        ll_nav_score.setOnClickListener(listener);

        LinearLayout ll_nav_account_switch = headerView.findViewById(R.id.ll_nav_account_switch);
        ll_nav_account_switch.setOnClickListener(listener);

        LinearLayout ll_nav_logout = headerView.findViewById(R.id.ll_nav_logout);
        ll_nav_logout.setOnClickListener(listener);

        LinearLayout ll_nav_inbox = headerView.findViewById(R.id.ll_nav_inbox);
        ll_nav_inbox.setOnClickListener(listener);

        LinearLayout ll_nav_today = headerView.findViewById(R.id.ll_nav_today);
        ll_nav_today.setOnClickListener(listener);

        LinearLayout ll_nav_learn = headerView.findViewById(R.id.ll_nav_learn);
        ll_nav_learn.setOnClickListener(listener);

        LinearLayout ll_nav_workout = headerView.findViewById(R.id.ll_nav_workout);
        ll_nav_workout.setOnClickListener(listener);

        LinearLayout ll_nav_job = headerView.findViewById(R.id.ll_nav_job);
        ll_nav_job.setOnClickListener(listener);

        LinearLayout ll_nav_week = headerView.findViewById(R.id.ll_nav_week);
        ll_nav_week.setOnClickListener(listener);

        LinearLayout ll_nav_month = headerView.findViewById(R.id.ll_nav_month);
        ll_nav_month.setOnClickListener(listener);

        toolbar.setTitle("");//设置标题为空
        //用toolbar替换actionbar
        setSupportActionBar(toolbar);

        username = headerView.findViewById(R.id.tv_username);


        //设置菜单图标颜色
        Drawable moreIcon = ContextCompat.getDrawable(toolbar.getContext(), R.drawable.abc_ic_menu_overflow_material);
        //设置颜色为蓝色
        moreIcon.setColorFilter(ContextCompat.getColor(toolbar.getContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        toolbar.setOverflowIcon(moreIcon);

    }

    private PerfectClickListener listener = new PerfectClickListener() {

        @Override
        protected void onNoDoubleClick(final View v) {
            drawerLayout.closeDrawer(GravityCompat.START);
            drawerLayout.postDelayed(() -> {
                switch (v.getId()) {
                    case R.id.ll_nav_account://个人中心
                        Intent accountIntent = new Intent(HomeActivity.this, TestActivity.class);
                        accountIntent.putExtra("text", "个人中心");
                        startActivity(accountIntent);
                        break;
                    case R.id.ll_nav_password://密码修改
                        Intent passwordIntent = new Intent(HomeActivity.this, PasswordActivity.class);
                        startActivity(passwordIntent);
                        break;
                    case R.id.ll_nav_feedback://意见反馈
                        Intent feedbackIntent = new Intent(HomeActivity.this, TestActivity.class);
                        feedbackIntent.putExtra("text", "意见反馈");
                        startActivity(feedbackIntent);
                        break;
                    case R.id.ll_nav_version_update://版本更新
//                        ToastUtil.showToast("版本更新");
                        Intent updateIntent = new Intent(HomeActivity.this, TestActivity.class);
                        updateIntent.putExtra("text", "版本更新");
                        startActivity(updateIntent);
                        break;
                    case R.id.ll_nav_score://给个评分
                        Intent scoreIntent = new Intent(HomeActivity.this, TestActivity.class);
                        scoreIntent.putExtra("text", "给个评分呗");
                        startActivity(scoreIntent);
                        break;
                    case R.id.ll_nav_account_switch://切换账号

                        //已登录则执行
                        if (Constants.user != null){
                            UserDao userDao = new UserDao(HomeActivity.this);
                            Constants.user.setUserStutas(0);//设置当前用户的登录状态为未登录
                            userDao.updateStatus(Constants.user);//同步至数据库

                            //跳转至登录界面
                            Intent switchIntent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(switchIntent);
                        } else//否则跳转至登录
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        break;
                    case R.id.iv_avatar://登陆
                        Intent avatarIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(avatarIntent);
                        break;
                    case R.id.ll_nav_inbox:
                        //清单切换为收集箱
                        Constants.listStatus = 0;
                        ListFragment.instance.showList();//刷新显示的清单
                        drawerLayout.closeDrawer(GravityCompat.START);//关闭抽屉
                        break;
                    case R.id.ll_nav_today:
                        //清单切换为今天
                        Constants.listStatus = 0;
                        Constants.isToDay = true;//设置清单显示为今天
                        ListFragment.instance.showList();//刷新显示的清单
                        Constants.isToDay = false;//取消为今天
                        drawerLayout.closeDrawer(GravityCompat.START);//关闭抽屉
                        break;
                    case R.id.ll_nav_week:
                        //清单切换为最近一周
                        System.out.println("一周前："+Constants.getWeekDate());
                        Constants.isWeek = true;
                        ListFragment.instance.showList();//刷新显示的清单
                        Constants.isWeek = false;
                        drawerLayout.closeDrawer(GravityCompat.START);//关闭抽屉
                        break;
                    case R.id.ll_nav_month:
                        //清单切换为最近一个月
                        System.out.println("一个月前:"+Constants.getMonthDate());
                        Constants.isMonth = true;
                        ListFragment.instance.showList();//刷新显示的清单
                        Constants.isMonth = false;
                        drawerLayout.closeDrawer(GravityCompat.START);//关闭抽屉
                        break;
                    case R.id.ll_nav_learn:
                        //清单切换为学习清单
                        Constants.listStatus = 1;
                        ListFragment.instance.showList();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ll_nav_workout:
                        //清单切换为锻炼清单
                        Constants.listStatus = 2;
                        ListFragment.instance.showList();//刷新显示的清单
                        drawerLayout.closeDrawer(GravityCompat.START);//关闭抽屉
                        break;
                    case R.id.ll_nav_job:
                        //清单切换为工作清单
                        Constants.listStatus = 3;
                        ListFragment.instance.showList();//刷新显示的清单
                        drawerLayout.closeDrawer(GravityCompat.START);//关闭抽屉
                        break;
                    case R.id.ll_nav_logout:
                        // 退出登录
                        //已登录则执行
                        if (Constants.user != null){
                            UserDao userDao = new UserDao(HomeActivity.this);
                            Constants.user.setUserStutas(0);//设置当前用户的登录状态为未登录
                            userDao.updateStatus(Constants.user);//同步至数据库
                            Constants.user = null;
                            ListFragment.instance.showList();//刷新显示的清单
                            HabitFragment.instance.showHabit();//刷新显示的习惯
                        } else//否则提示
                            Toast.makeText(HomeActivity.this, "请先登录",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }, 260);
        }
    };

    private void initContentFragment() {
        mFragment = new ArrayList<>();
        mFragment.add(new ListFragment());
        mFragment.add(new CalendarFragment());
        mFragment.add(new HabitFragment());
        //预加载最多的数量
        vp_content.setOffscreenPageLimit(2);
        //设置适配器
        vp_content.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }
        });
        // 设置默认加载第1个Fragment
        setCurrentItem(1);
        // ViewPager的滑动监听
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    //设置点击事件
    @OnClick({R.id.ll_title_menu, R.id.iv_title_one, R.id.iv_title_two, R.id.iv_title_three})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:
                // 开启抽屉式菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_one://显示清单视图
                // 这样做的目的是减少cpu的损耗
                if (vp_content.getCurrentItem() != 0) {
                    setCurrentItem(0);//设置当前显示的视图
                }
                break;
            case R.id.iv_title_two://显示日历视图
                if (vp_content.getCurrentItem() != 1) {
                    setCurrentItem(1);//设置当前显示的视图
                }
                break;
            case R.id.iv_title_three://显示习惯视图
                if (vp_content.getCurrentItem() != 2) {
                    setCurrentItem(2);//设置当前显示的视图
                }
                break;

            default:
                break;
        }
    }

    /**
     * 切换页面
     *
     * @param position 分类角标
     */
    private void setCurrentItem(int position) {
        boolean isOne = false;
        boolean isTwo = false;
        boolean isThree = false;
        switch (position) {
            case 0:
                isOne = true;
                break;
            case 1:
                isTwo = true;
                break;
            case 2:
                isThree = true;
                break;
            default:
                isTwo = true;
                break;
        }
        vp_content.setCurrentItem(position);//设置当前视图的Fragment
        ivTitleOne.setSelected(isOne);//设置此视图是否选中
        ivTitleTwo.setSelected(isTwo);
        ivTitleThree.setSelected(isThree);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //是否按下的返回键并按键已经被按下
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            //若两次按下间隔时间超过两秒则提示“再按一次退出应用”
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(HomeActivity.this, "在按一次退出应用",Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {//否则关闭此Activity（即退出应用）
//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addCategory(Intent.CATEGORY_HOME);//添加意图到首页
//                    startActivity(intent);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 菜单；这会将项目添加到操作栏（如果有）。
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //重写onOptionsItemSelected方法监听菜单栏点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.menu_add_list://添加清单
                Intent intent = new Intent(HomeActivity.this, ChecklistActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_add_habit://添加习惯
                startActivity(new Intent(HomeActivity.this, HabitActivity.class));
                break;
            case R.id.show_complete://显示已完成的清单
                Constants.showCompleted = true;
                ListFragment.instance.showList();
                break;
            case R.id.hide_complete://隐藏已完成的清单
                Constants.showCompleted = false;
                ListFragment.instance.showList();
                break;
            case R.id.sort_default://默认排序
                Constants.sortBy = null;
                ListFragment.instance.showList();
                break;
            case R.id.sort_priority://按优先级排序
                Constants.sortBy = "priority";
                ListFragment.instance.showList();
                break;
            case R.id.sort_title://按标题排序
                Constants.sortBy = "list_title";
                ListFragment.instance.showList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
