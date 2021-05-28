package com.example.module_demo_0518;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class Socket_many_Activity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Fragment [] socketFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_socket);
        socketFragments = new Fragment[]{new PlaceholderFragment(),new PlaceholderFragment2()};
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 创建适配器，该适配器将为这两个中的每一个返回一个片段活动的主要部分。
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // 使用sections适配器设置ViewPager
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ===============================================================
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //调用getItem实例化给定页面的片段。
            //返回一个PlaceholderFragment(定义为下面的静态内部类)。
            return socketFragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "插座1";
                case 1:
                    return "插座2";
            }
            return null;
        }
    }

    // ==================================【第一个片段】===============================================
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private View rootView;
        private Button buttonSocket;
        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final TextView textView = (TextView) rootView.findViewById(R.id.textViewStauts);
            final View rootView_final = rootView;
            buttonSocket = (Button) rootView.findViewById(R.id.buttonSocketInFragment);
            buttonSocket.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String status =  textView.getText().toString(); //rootView_final.findViewById(R.id.textViewStauts);
                    if(status.equals("开")){
                        status = "1";
                    }else{
                        status = "0";
                    }
                    ConrolSocketStatusTask conrolSocketStatusTask = new ConrolSocketStatusTask("s1", rootView_final, status);
                    conrolSocketStatusTask.execute();
                }
            });

            // 插座实例化：获取权限、现在的状态
            final SocketStatusTask socketStatusTask = new SocketStatusTask("s1", rootView);
            socketStatusTask.execute();

            return rootView;
        }
        // ======插座获取权限,并获取当前状态！=======
        class SocketStatusTask extends AsyncTask<String, Void, ABRet> {
            private String devise;
            private View rootView;
            private TextView textView;
            private ImageView imageViewSocket;

            public SocketStatusTask(String devise, View rootView) {
                this.devise = devise;
                this.rootView = rootView;
                textView = (TextView) rootView.findViewById(R.id.textViewStauts);
                imageViewSocket = (ImageView) rootView.findViewById(R.id.imageViewButton);
            }

            @Override
            protected ABRet doInBackground(String... Voids) {
                ABRet abRet = ABSDK.getInstance().getSockStatus(devise);
                return abRet;
            }

            @Override
            protected void onPostExecute(ABRet abRet) {
                super.onPostExecute(abRet);

                if (abRet.getCode().equals("00000")) {
                    Toast.makeText(getContext(), "s1插座权限获取成功", Toast.LENGTH_SHORT).show();
                    if(abRet.getDicDatas().get("status").toString().equals("0")){
                        textView.setText("关");
                    }else{
                        textView.setText("开");
                    }
                    imageViewSocket.setImageResource(abRet.getDicDatas().get("status").toString().equals("1")
                            ? R.drawable.sock_on  : R.drawable.sock_off);
                } else {
                    Toast.makeText(getContext(), "插座权限获取失败" + abRet.getCode(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        // ======插座更改状态=======
        class ConrolSocketStatusTask extends AsyncTask<String, Void, ABRet> {
            private String devise;
            private String SocketStatus;
            private TextView textView;
            private ImageView imageViewSocket;

            public ConrolSocketStatusTask(String devise, View rootView, String SocketStatus) {
                this.devise = devise;
                this.SocketStatus = SocketStatus;
                textView = (TextView) rootView.findViewById(R.id.textViewStauts);
                imageViewSocket = (ImageView) rootView.findViewById(R.id.imageViewButton);
            }

            @Override
            protected ABRet doInBackground(String... Voids) {
                if (SocketStatus.equals("0")) {
                    SocketStatus = "1";
                } else {
                    SocketStatus = "0";
                }
                ABRet abRet = ABSDK.getInstance().sockCtrl(devise, SocketStatus);
                return abRet;
            }

            @Override
            protected void onPostExecute(ABRet abRet) {
                super.onPostExecute(abRet);

                if (abRet.getCode().equals("00000")) {
                    if(SocketStatus.equals("0")){
                        textView.setText("关");
                    }else{
                        textView.setText("开");
                    }
                    imageViewSocket.setImageResource(SocketStatus.equals("1")
                            ? R.drawable.sock_on  : R.drawable.sock_off);
                } else {
                    Toast.makeText(getContext(), "插座状态“更改”失败！！！！" + AboxCons.errorMap.get(abRet.getCode()), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }










    // ==================================【第一个片段】===============================================
    public static class PlaceholderFragment2 extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private View rootView;
        private Button buttonSocket;
        public PlaceholderFragment2() {
        }

        public static PlaceholderFragment2 newInstance(int sectionNumber) {
            PlaceholderFragment2 fragment = new PlaceholderFragment2();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final TextView textView = (TextView) rootView.findViewById(R.id.textViewStauts);
            final View rootView_final = rootView;
            buttonSocket = (Button) rootView.findViewById(R.id.buttonSocketInFragment);
            buttonSocket.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String status =  textView.getText().toString();
                    if(status.equals("开")){
                        status = "1";
                    }else{
                        status = "0";
                    }
                    ConrolSocketStatusTask conrolSocketStatusTask = new ConrolSocketStatusTask("s2", rootView_final, status);
                    conrolSocketStatusTask.execute();
                }
            });

            // 插座实例化：获取权限、现在的状态
            final SocketStatusTask socketStatusTask = new SocketStatusTask("s2", rootView);
            socketStatusTask.execute();

            return rootView;
        }
        // ======插座获取权限,并获取当前状态！=======
        class SocketStatusTask extends AsyncTask<String, Void, ABRet> {
            private String devise;
            private View rootView;
            private TextView textView;
            private ImageView imageViewSocket;

            public SocketStatusTask(String devise, View rootView) {
                this.devise = devise;
                this.rootView = rootView;
                textView = (TextView) rootView.findViewById(R.id.textViewStauts);
                imageViewSocket = (ImageView) rootView.findViewById(R.id.imageViewButton);
            }

            @Override
            protected ABRet doInBackground(String... Voids) {
                ABRet abRet = ABSDK.getInstance().getSockStatus(devise);
                return abRet;
            }

            @Override
            protected void onPostExecute(ABRet abRet) {
                super.onPostExecute(abRet);

                if (abRet.getCode().equals("00000")) {
                    Toast.makeText(getContext(), "s1插座权限获取成功", Toast.LENGTH_SHORT).show();
                    if(abRet.getDicDatas().get("status").toString().equals("0")){
                        textView.setText("关");
                    }else{
                        textView.setText("开");
                    }
                    imageViewSocket.setImageResource(abRet.getDicDatas().get("status").toString().equals("1")
                            ? R.drawable.sock_on  : R.drawable.sock_off);
                } else {
                    Toast.makeText(getContext(), "插座权限获取失败" + abRet.getCode(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        // ======插座更改状态=======
        class ConrolSocketStatusTask extends AsyncTask<String, Void, ABRet> {
            private String devise;
            private String SocketStatus;
            private TextView textView;
            private ImageView imageViewSocket;

            public ConrolSocketStatusTask(String devise, View rootView, String SocketStatus) {
                this.devise = devise;
                this.SocketStatus = SocketStatus;
                textView = (TextView) rootView.findViewById(R.id.textViewStauts);
                imageViewSocket = (ImageView) rootView.findViewById(R.id.imageViewButton);
            }

            @Override
            protected ABRet doInBackground(String... Voids) {
                if (SocketStatus.equals("0")) {
                    SocketStatus = "1";
                } else {
                    SocketStatus = "0";
                }
                ABRet abRet = ABSDK.getInstance().sockCtrl(devise, SocketStatus);
                return abRet;
            }

            @Override
            protected void onPostExecute(ABRet abRet) {
                super.onPostExecute(abRet);

                if (abRet.getCode().equals("00000")) {
                    if(SocketStatus.equals("0")){
                        textView.setText("关");
                    }else{
                        textView.setText("开");
                    }
                    imageViewSocket.setImageResource(SocketStatus.equals("1")
                            ? R.drawable.sock_on  : R.drawable.sock_off);
                } else {
                    Toast.makeText(getContext(), "插座状态“更改”失败！！！！" + AboxCons.errorMap.get(abRet.getCode()), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
