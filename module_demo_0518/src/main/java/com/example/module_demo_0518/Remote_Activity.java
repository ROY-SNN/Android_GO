package com.example.module_demo_0518;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

import java.util.ArrayList;

/**
 * 【遥控器按键页面】：
 *      功能：(1)红外遥控按键的学习、测试与命名
 *            (2)红外遥控按键的发射
 *            (3)红外遥控按键的删除
 */
public class Remote_Activity extends AppCompatActivity {
    ArrayList<String> arrayListRemoteButtons = new ArrayList<String>(); // 本地集合用来存放功能名(keyName)
    private GridView gridViewRemotes;                              // 遥控按键的网格控件
    private GridViewRemoteAdapter gridViewRemoteAdapter;           // 遥控按键的网格控件的适配器
    AboxSQLDatabase aboxSQLDatabase;
    AboxSQLOpenHelper aboxSQLOpenHelper;
    SharedPreferences sp;                                          // 轻量级存储，用来判断是否为第一次使用遥控

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        // 【数据库实例化
        aboxSQLOpenHelper = new AboxSQLOpenHelper(this);
        aboxSQLDatabase = new AboxSQLDatabase(aboxSQLOpenHelper.getWritableDatabase());
        // 【数据表初始化】：存入30个：Key_id(0~29)、Key_Name(“未学习”+id)
        // 【ps】如果当前为第一次使用遥控，需要初始化数据表
        if(!(isUsed())){
            DataInit();
            // 弹出使用介绍
            new AlertDialog.Builder(Remote_Activity.this)
                    .setTitle("短按学习/发射，长按删除")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
        // 【将数据表中的数据读取到集合中】
        getDataToList();
        // 【初始化配置器】
        gridViewRemoteAdapter = new GridViewRemoteAdapter(this);
        gridViewRemotes =  (GridView) findViewById(R.id.gridViewRemotes);
        gridViewRemotes.setAdapter(gridViewRemoteAdapter);   // 绑定配置器
        // =============================【为GridView设置监听器】====================================
        /**
         * 【setOnItemClickListener()方法】：短按进行红外按键学习、测试与命名按键的操作
         *       如果当前按键之前学过，直接进行红外发射
         *       如果当前按键之前没有学过，先学习测试，再命名
         */
        gridViewRemotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(ifLeant(position)){
                    Toast.makeText(Remote_Activity.this,"当前按键为：" + String.valueOf(arrayListRemoteButtons.get(position)), Toast.LENGTH_SHORT).show();
                    InfraredTestTask infraredTestTask = new InfraredTestTask(String.valueOf(position));
                    infraredTestTask.execute();
                }else {
                    Toast.makeText(Remote_Activity.this,"当前按键为：" + String.valueOf(position) + "，请使用遥控！", Toast.LENGTH_SHORT).show();
                    InfraredLearnTask infraredLearnTask = new InfraredLearnTask(String.valueOf(position));
                    infraredLearnTask.execute();
                }
            }
        });

        /**
         * 【setOnItemLongClickListener()方法】：长按进行删除按键的操作
         *       如果已学习，就将数据库中对应的Key_Name的值，更新为“未学习i”
         *       如果没有学习，只打印提示信息。
         */
        gridViewRemotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            LayoutInflater inflater = LayoutInflater.from(Remote_Activity.this);
            //final View viewDlg = inflater.inflate(R.layout.dialog_remote_inputfunction,null);
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(ifLeant(position)){
                    // 弹窗
                    LayoutInflater inflater = LayoutInflater.from(Remote_Activity.this);
                    final View viewDlg = inflater.inflate(R.layout.dialog_if_delete,null);
                    final int position_final = position;
                    TextView viewTextKeyName = (TextView) viewDlg.findViewById(R.id.textViewRemote);
                    viewTextKeyName.setText(arrayListRemoteButtons.get(position_final));
                    new AlertDialog.Builder(Remote_Activity.this)
                            .setTitle("你确定要删除该按键？")
                            .setView(viewDlg)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 实例化
                                    AboxRemoteButtonBean aboxRemoteButtonBean = new AboxRemoteButtonBean(position_final,
                                            arrayListRemoteButtons.get(position_final), String.valueOf(position_final));
                                    // 数据表删除完成
                                    aboxSQLDatabase.deleteButton(aboxRemoteButtonBean);
                                    // 查询该行，并将删除的行更新到集合中
                                    Cursor cursor = aboxSQLDatabase.queryButton(aboxRemoteButtonBean);
                                    while (cursor.moveToNext()) {
                                        arrayListRemoteButtons.set(position_final, cursor.getString(1));
                                    }
                                    // 刷新适配器
                                    gridViewRemotes.setAdapter(gridViewRemoteAdapter);
                                    Toast.makeText(Remote_Activity.this,"删除成功", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }else{
                    Toast.makeText(Remote_Activity.this,"当前按键为：" + String.valueOf(arrayListRemoteButtons.get(position)) + "还未学习！", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    /**
     * 【isUsed()方法】：判断是否为第一次使用遥控
     *      利用轻量级存储，本地文件名为"isused"，判断该文件里面存不存在键值对："If_Used"-"NOT_USED"
     */
    private boolean isUsed(){
        sp = getSharedPreferences("isused", MODE_PRIVATE);  // 文件名："isused"，其中的键：AboxCons.SP_IF_USED
        String str = sp.getString(AboxCons.SP_IF_USED, "NOT_USED"); // 如果不存在，则传入"NOT_USED"
        if(str.equals("NOT_USED")){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 【DataInit()方法】：第一次使用遥控需要初始化数据表，并生成一个文件
     */
    private void DataInit() {
        aboxSQLDatabase.insertButton();
        sp.edit().putString(AboxCons.SP_IF_USED, "USED").commit(); // 文件中的值为“USED”
    }

    /**
     * 【getDataToList()方法】：将数据表中的数据复制到本地列表中
     */
    private void getDataToList(){
        Cursor cursor = aboxSQLDatabase.queryButtons();
        while (cursor.moveToNext()) {
            String key_name = cursor.getString(1);
            arrayListRemoteButtons.add(key_name);
            //Log.v("数据库日志：", "arrayListRemoteButtons：" + key_name);
        }
        Log.v("数据库日志：", "数据表中的Key_Name已经保存到本地列表中...");
    }

    /**
     * 【ifLeant()方法】：判断当前按键是否学习过？
     *      【ps】根据传入的position值，判断本地列表中的值是否等于“未学习i”
     */
    public boolean ifLeant(int position){
        if(arrayListRemoteButtons.get(position).equals("未学习" + String.valueOf(position))){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 【GridViewRemoteAdapter类】：自定义适配器
     *      【ps】：创建一个新的类继承BaseAdapter，并重写4个方法
     */
    class GridViewRemoteAdapter extends BaseAdapter{
        Context context;  // 上下文
        LayoutInflater layoutInflater;

        public GridViewRemoteAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context); // 【ps】：这里引用的是网格控件的文件
        }

        // 适配器中对应数据的个数
        @Override
        public int getCount() {
            return 30;
        }

        // 获取数据集中与索引对应的数据项
        @Override
        public Object getItem(int position) {
            return position;
        }

        // 获取指定行对应的ID
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 【getView()方法】：获取每一行Item的显示内容
         *   【ps】这个getView()方法，我们刷新textView的地方，要想实现实时刷新数据，在每次数据更新后，我们都需要调用一次。
         *         这里的调用并不是我们真正的亲自调用，而是我们通过绑定适配器的语句后，由安卓系统自动调用该方法。
         *         当时，我对长按监听与短按监听进行log测试，他们之间好像存在一点区别，
         *         为何直接利用setText()方法设置文本框的值现象不一样？
         *         还有gitView()的调用次数有点奇怪，后期深入学习的时候，还需要对这里打断点了解一下。
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.remotes_layout, null); // 布局对象变为View对象【ps】：这里引用的是一个按键的文件
            }
            //Log.v("gridLog", "arrayListRemoteButtons");

            // 遥控按键上面的文本显示
            TextView textViewKey = (TextView) convertView.findViewById(R.id.textViewKey);
            textViewKey.setText(arrayListRemoteButtons.get(position));
            // 遥控按键的图片显示
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewRemoteButton);
            imageView.setImageResource(arrayListRemoteButtons.get(position).equals("未学习" + String.valueOf(position))
                    ? R.drawable.remote_button1a_b  : R.drawable.remote_button1a_a);

            return convertView;
        }
    }

    /**
     * 【InfraredLearnTask类】：红外学习
     *      【ps】需要引入jar包
     */
    class InfraredLearnTask extends AsyncTask<String, Void, ABRet> {
        private String position;

        public InfraredLearnTask(String position){
            this.position = position;
        }

        @Override
        protected ABRet doInBackground(String... Voids) {
            ABRet abRet = ABSDK.getInstance().studyIrByIrDevName(AboxCons.INFRARED_DEVICE, position);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            if (abRet.getCode().equals("00000")) {
                InfraredTestTask infraredTestTask = new InfraredTestTask(String.valueOf(position));
                infraredTestTask.execute();
            } else {
                Toast.makeText(Remote_Activity.this,"红外学习失败,请尝试重新学习！" + AboxCons.errorMap.get(abRet.getCode()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 【InfraredTestTask类】：红外发射
     *      【ps】需要引入jar包
     */
    class InfraredTestTask extends AsyncTask<String, Void, ABRet> {
        private String position;

        public InfraredTestTask(String position){
            this.position = position;
        }

        @Override
        protected ABRet doInBackground(String... Voids) {
            ABRet abRet = ABSDK.getInstance().sendIr(AboxCons.INFRARED_DEVICE, position);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            if (abRet.getCode().equals("00000")) {
                Toast.makeText(Remote_Activity.this,"红外发射成功", Toast.LENGTH_SHORT).show();
                // 如果该按键以及学习过了，弹出dialog
                if(!(ifLeant(Integer.parseInt(position)))){
                    LayoutInflater inflater = LayoutInflater.from(Remote_Activity.this);
                    final View viewDlg = inflater.inflate(R.layout.dialog_remote_inputfunction,null);
                    new AlertDialog.Builder(Remote_Activity.this)
                            .setTitle("命名按钮")
                            .setView(viewDlg)
                            .setPositiveButton("保存", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EditText editTextKeyName = (EditText) viewDlg.findViewById(R.id.textViewRemoteInputFunction);
                                    // 实例化
                                    AboxRemoteButtonBean aboxRemoteButtonBean = new AboxRemoteButtonBean(Integer.parseInt(position),
                                            editTextKeyName.getText().toString(), String.valueOf(position));
                                    // 数据表更新
                                    aboxSQLDatabase.alterButton(aboxRemoteButtonBean);
                                    // 获取该值
                                    Cursor cursor = aboxSQLDatabase.queryButton(aboxRemoteButtonBean);
                                    while (cursor.moveToNext()) {
                                        arrayListRemoteButtons.set(Integer.parseInt(position), cursor.getString(1));
                                    }
                                    gridViewRemotes.setAdapter(gridViewRemoteAdapter);
                                    //editTextKeyName.setText(arrayListRemoteButtons.get(Integer.parseInt(position)));
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            } else {
                Toast.makeText(Remote_Activity.this,"红外发射失败" + AboxCons.errorMap.get(abRet.getCode()) , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
