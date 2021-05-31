package com.example.module_demo_0518;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // =======================================================================
    private FrameLayout mainFrame;                  // 布局(组件)
    private BottomNavigationView bottomNavigation;  // 导航栏
    private DeviceFragment deviceFragment;          // 碎片1
    private ModeFragment modeFragment;            // 碎片2
    private OtherFragment otherFragment;                  // 碎片3
    private Fragment[] fragments;                   // 将碎片放入列表
    private int lastfragment = 0;


    private void initView() {
        // 初始化
        mainFrame = (FrameLayout) findViewById(R.id.content);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);

        // 碎片fragment实例化
        deviceFragment = new DeviceFragment();
        modeFragment = new ModeFragment();
        otherFragment = new OtherFragment();
        fragments = new Fragment[]{deviceFragment, modeFragment, otherFragment};
        // 设置fragment到布局
        getSupportFragmentManager().beginTransaction().replace(R.id.content, deviceFragment).show(deviceFragment).commit();

        // bottomnavigationview监听器
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    // 导航栏的监听器
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_device:
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                case R.id.navigation_mode:
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return true;
                case R.id.navigation_other:
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    /**
     *【切换fragment】
     */
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[lastfragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.content, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
    // ====================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        // ==============================
        initView();
        // ==============================

        // 顶部的工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 抽屉布局
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 操作栏抽屉切换
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // 设置监听
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理操作栏项目点击这里。
        // 操作栏会自动处理Home/Up按钮的点击，
        // 只要你在AndroidManifest.xml中指定父活动。
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // 处理点击这里的导航视图项
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            new AlertDialog.Builder(MainActivity.this).setTitle("开发人员正在Coding！尽情期待")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (id == R.id.nav_gallery) {
            new AlertDialog.Builder(MainActivity.this).setTitle("开发人员正在Coding！尽情期待")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (id == R.id.nav_slideshow) {
            new AlertDialog.Builder(MainActivity.this).setTitle("开发人员正在Coding！尽情期待")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (id == R.id.nav_manage) {
            new AlertDialog.Builder(MainActivity.this).setTitle("开发人员正在Coding！尽情期待")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(MainActivity.this, ConnectUsActivity.class);  // 彩蛋
            startActivity(intent);
        }else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, ColoredEggsActivity.class);  // 彩蛋
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
