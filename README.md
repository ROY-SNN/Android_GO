# ======================================================

# Android_GO

#### 生产实习2021.05.17~2021.05.19的内容(持续更新ing)

# ======================================================
# 2021.05.18：登录页面的设计流程
【ps】创建工程和模块省略

# 1.设计流程
### 1.1 放置控件

1. 首先，利用鼠标拖拽的方式放置组件(如：Plain Text、password、TextView、Button等)；
1. 然后，将组件四个边中间的点连接到指定位置(可以是其他组件，也可以是页面的四周)，以确定其在页面中的具体位置。	

【ps】在你托拽组件的时候，对应的.xml文件将自动生成其组件的代码。

### 1.2 控件属性的设置

常见的控件属性：
- 【android:id】：.java文件利用findViewById(R.id.名字)方法来找到该控件。
- 【android:text】：控件显示的文字。
- 【android:hint】：未输入情况下显示的文字(尽量使用strings.xml中的字符串)。
- 【android:grivity】：控件中文本的位置(例："center")。
- 【android:textSize】：控件中文本字体的大小(例："40sp")。
- 【android:background】：控件的背景色，RGB命名法(例："#F6F6C4")。
- 【android:padding】：控件的内边距。

【ps】可能出现的问题：
1. 当我们在1.1中固定好控件位置之后，可能会手动更改某个组件的id，这时如果其他组件与更改id的控键存在位置关系，那么它的app:layout_constraintXXX_toBottomOf属性不会自动更改，需要我们手动调整。

1. 在android:hint使用strings.xml的时候，注意.xml中对应标签的name属性。

1. 关于android:id命名一定要清晰明了，自己能记得住，为了方便我们组以后代码书写规范，统一在此使用**小驼峰法**：（一）除第一个单词之外，其他单词首字母大写（二）"控件类型"+"该处表示的含义"(例："editTextUserPwd"、"textViewUserName"、"buttonBMI"等等)。


### 1.3 Button的设置
由于Android开发底层的特点，在利用java编写Button这类控件的时候，不再需要那些繁琐的监听操作，仅需要在Button控件中添加**android:onClick**和在.java中编写少量代码便可实现对应的功能。	

1. 在android:onClick属性命名的时候，我们也采用**小驼峰法**，（"该处表示的含义" + "ButtononCliked"），例："onloginButtononCliked"、"BMIButtononCliked"等。
1. 创建onloginButtononCliked()方法：编写触发后执行的任务。

```java
// 组件Button触发的函数，必须是public、void、参数：View view
public void onloginButtononCliked(View view){
	// 编写触发后执行的程序
}
```

### 1.4 利用java调用控件中获取的字符串
1. 创建想要获取字符串所在控件的对象类型
1. 利用findViewById()方法找到控件对应的对象(我们的版本需要强转)
1. 利用.getText().toString()方法获取对象中的字符串值

【ps】在调用控件的时候，id的值尽量不要自己打，在自动出现列表中寻找。

```java
public class MainActivity extends AppCompatActivity {
	// 1.创建想要获取字符串所在控件的对象类型
	private EditText editTextUserName;  

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		// 2.通过finView找到控件对应的对象(ps:版本问题需要强制转换)
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
		// 3.利用.getText().toString()方法获取对象中的字符串值
		// [PS]这是举个例子，正式代码引用控件对象的值，不放在这里。
		editTextUserName = editTextUserName.getText().toString()
    }
}
```

### 1.5 利用Toast创建浮现小窗口
在屏幕上浮现出一个小窗口，显示一段时间后又消失，这个可视化组件叫做Toast，它主要用于提示用户某种事件发生了，具体的使用方法如下：

1. 定义Toast并用makeText()设置：文本和浮现时间长短
1. 用.show()将Toast显示出来

```java
// (方法一)：
Toast toast = Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_LONG);
toast.show();

// (方法二)：
Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
```

[【Android开发Toast】参考1](https://blog.csdn.net/zxfhahaha/article/details/78915477 "【Android开发Toast】参考1")

[【Android开发Toast】参考2](https://www.cnblogs.com/liyiran/p/4655303.html"【Android开发Toast】参考2")

[【Android开发Toast】参考3](https://blog.csdn.net/fitaotao/article/details/82251750 "【Android开发Toast】参考3")


### 1.6 利用Intent(意图)实现页面跳转
```java
Intent intert = new Intent(MainActivity.this,Main2Activity.class);
startActivity(intert);
```


### 1.7 实现多页面的控件值传送

MainActivity.java文件下的代码：
```java
Intent intert = new Intent(MainActivity.this,Main2Activity.class);
// 利用putExtra()方法：
//    第一个参数：跳转到下一页面时，用来调用的名字；
//    第二个参数：从此页面传入的某一控件值
intert.putExtra("useName",editTextUserName.getText().toString());
startActivity(intert);
```

Main2Activity.java文件下的代码：
```java
public class Main2Activity extends AppCompatActivity {
	private TextView textViewshowusername;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
		
		textViewshowusername = (TextView) findViewById(R.id.textViewshowusername);
		// 1.首先，获取Intent(意图)的对象
		Intent intent = getIntent();
		// 2.然后，利用getStringExtra()方法来获取对应的值
       textViewshowusername.setText(intent.getStringExtra("useName"));
    }
}
```

# 2.其他
### 2.1 同一module下多个页面谁先运行的问题
manifests文件夹内的AndroidManifest.xml文件，注意其中的<activity></activity>标签中的android:name属性所指代的就是所对应的页面.java程序，谁的内部有<intent-filter>~~~</intent-filter>标签，谁就优先执行。

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.module_demo_0518">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".Main2Activity"></activity>
    </application>

</manifest>
```




# ======================================================

# 2021.05.19：轻量级存储、远程登录及获取插座权限
【ps】下列所以代码均为最简代码，只有将最简的调试好，再添加上之前的多种功能才能更清楚的理解！

## 1.登录页面“记住密码”功能
### 1.1 轻量级的数据存储SharedPreferences简介
SharedPreferences是一种轻量级的数据存储方式，采用**键值对**的存储方式。SharedPreferences只能存储少量数据，大量数据不能使用该方式存储，支持存储的数据类型：booleans、floats、ints、longs、strings，将其存储到一个XML文件中的，路径在/data/data/<packagename>/shared_prefs/。

### 1.2 SharedPreferences的基本用法
1. 【获取SharedPreferences对象】：利用getSharedPreferences()方法
1. 【数据更新】：利用edit()结合putString()方法
1. 【数据获取】：利用getString()


[数据存储SharedPreferences的操作流程参考1](https://www.cnblogs.com/fanglongxiang/p/11390013.html "数据存储SharedPreferences的操作流程")

[数据存储SharedPreferences的操作流程参考2](https://www.cnblogs.com/fanglongxiang/p/11390013.html "数据存储SharedPreferences的操作流程")



### 1.3 练习：登陆页面实现“记住密码”的功能
关于控件CheckBox的放置这一步骤不再详细描述，注意id值的问题就可以了，接下来的步骤步骤大致如下：

1. UI设计
1. 创建CheckBox类型的成员变量、并获取具体对应的CheckBox
1. 创建一个新的类AboxCons，来利用常量存放“键”的名称
1. 获取SharedPreferences对象：getSharedPreferences()方法
1. 数据获取：getString方法
1. 数据更新：edit()、putString()、commit()三个方法


```java
public class AboxCons {
    public static String SP_USER_NAME = "name";          // 用于Preferences文件中的键
    public static String SP_USER_PSD  = "pwd";           // 用于Preferences文件中的键
}
```

【ps】这里的SP_USER_NAME、SP_USER_PSD一定要有值，不能为空，如果为空后面更新数据的时候，传入两个空的键，等到想利用键获取数据的时候，将会无法根据键来匹配正确的值。

```java
public class MainActivity extends AppCompatActivity {
	
	private EditText editTextUserName;  
    private EditText editTextUserPwd;   
	// ==================================================================================
	private CheckBox checkBoxRemmberMe;  // 【登录页面】记住我！
    private SharedPreferences sp;        //  1.SharedPreferences对象
	// ==================================================================================
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
		// ==================================================================================
		// 1.【获取SharedPreferences对象】：
		//      参数1：数据存储的文件名； 参数2：操作模式
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		// 2.【数据获取sp.getString】
		//     参数1：键；参数2：如果文件中没有找到对应的键，则用“~~~~~”代替；如果存在，则取出
		// 【ps】这里我们将获取数据与将数据放置到文本框内写在一起了，所以看的有点多。
        editTextUserName.setText(sp.getString(AboxCons.SP_USER_NAME, "nobody"));
        editTextUserPwd.setText(sp.getString(AboxCons.SP_USER_PSD, "nopass"));
		// ==================================================================================
    }
	
	private void findView() {
        editTextUserName  = (EditText) findViewById(R.id.editTextUserName);
        editTextUserPwd   = (EditText) findViewById(R.id.editTextUserPwd);
		// ==================================================================================
        checkBoxRemmberMe = (CheckBox) findViewById(R.id.checkBoxRemmberMe); // 获取“记住我”对应checkBox的值
		// ==================================================================================
    }
	
	public void onloginButtononCliked(View view){
        boolean authOk = false;
        authOk = loginAuth(editTextUserName.getText().toString(),editTextUserPwd.getText().toString());
        if(authOk){
			// ==================================================================================
            // 如果登录成功
            if(checkBoxRemmberMe.isChecked()){ // 且“记住我”已经勾选
				// 3.【数据更新】
				//   putString()方法  参数1：键；参数2：值
				//   【ps】edit()、putString()、commit()三个方法结合使用。
                sp.edit().putString(AboxCons.SP_USER_NAME, editTextUserName.getText().toString())
                        .putString(AboxCons.SP_USER_PSD, editTextUserPwd.getText().toString())
                        .commit();
            }
			// ==================================================================================
            Intent intert = new Intent(MainActivity.this,Main2Activity.class);
            intert.putExtra("useName",editTextUserName.getText().toString());
            startActivity(intert);
            Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
        }else{
			~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~略~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        }
    }
	
	public boolean loginAuth(String userName, String userPwd){~~~~~略~~~~~}
}
```





## 2.“远程连接网络”的登录

之前，我们编写的程序登陆方式都是根据本地某个变量中，定义好的用户名与密码进行验证登录；但是实际情况是：让程序在接入对应网络的情况下，可以访问网络上的数据(提前在网络上注册好的用户名与密码)然后进行验证与登录，进而控制传感器进行传输与接收数据的功能。

### 2.1 导入libABSDK0630.jar
1. 以Project视角查看工程目录
1. 找到对应的module-libs，将.jar包复制进去
1. 右键.jar包再添加进去

### 2.2 查看文件：ABSDK接口

|  0   |  取得实例对象 |
|  ----  | ----  |
| 名称  | ABSDK getInstance() |
| 参数  | - |
| 返回值  | ABSDK实例 |


|  1   |  登录 |
|  ----  | ----  |
| 名称  | ABRet loginWithUsername(String username, String password) |
| 参数  | username:用户名、password:密码 |
| 返回值  | ABRet: code String 处理结果(00000成功，00000以外失败)、token String ~~ 等 |

|  CODE   |  含义 |
|  ----  | ----  |
| 00000  | 处理成功 |
| 20000  | 请求超时 |
| 20001  | 用户名或密码不存在 |
| 20002  | 用户不存在或已暂停使用 |
| 20003  | 登录失败次数过多 |
| 20004  | 用户名或密码不正确 |


【ps】注意一个token的变量(属于密钥)，如果你的程序没有从loginWithUsername()进入，而是从别的地方强行进入，你不会有token值(非法入侵)，所以后续的工作都会出现问题！！！

### 2.3 根据接口文件编写程序

1. 创建一个类LoginAsysnTask，继承AsyncTask
1. 创建这个类的含参构造方法
1. 重写doInBackground方法
1. 重写onPostExecute方法
1. 实例化新建的LoginAsysnTask类
1. 执行！！！

【ps】

1. 有关函数间具体的运行关系，其实不需要掌握，例如：getInstance()获取实例化对象的时候，这里与反射机制十分类似，没有必要深挖，就我们初学阶段只要掌握如何调用各个接口就可以了。如果想了解一下反射机制可以参考[lys同学对反射机制的认识与理解](http://www.lysblog.cn:8080/blog/58 "lys同学对反射机制的认识与理解")。
1. 下列代码我省略了“记住密码”、“将本页面的数值传入下一页面”等功能，仅保留了登陆成功页面跳转的功能，方便大家理解。

```java
public class MainActivity extends AppCompatActivity {
    private EditText editTextUserName;  
    private EditText editTextUserPwd;   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
        findView();
    }

    private void findView() {
        editTextUserName  = (EditText) findViewById(R.id.editTextUserName);
        editTextUserPwd   = (EditText) findViewById(R.id.editTextUserPwd);
    }

    public void onloginButtononCliked(View view){
        String userName = editTextUserName.getText().toString();
        String userPwd  = editTextUserPwd.getText().toString();

		// 5.实例化新建的LoginAsysnTask类，将获取的用户名与密码传入构造器
        LoginAsysnTask loginAsysnTask = new LoginAsysnTask(userName,userPwd);
		// 6.执行！！！
        loginAsysnTask.execute();
        // 【ps】注意excute之后不能有语句
    }

	// 1.创建一个类，继承AsyncTask(注意：泛型！)
    class LoginAsysnTask extends AsyncTask<String, Void, ABRet> {
        private String m_userName;
        private String m_userPwd;
		
		// 2.创建含参(2个参数)构造方法
		// 【ps】上课的时候，第一遍编写的时候没有创建这个构造方法，是因为继承的AsyncTask内部已经存在含有泛型的构造方法，它的好处是无论多少个参数都可以直接构造，将变量传进对象之中。而我们这里这样写的缺点：只能传入定义个数的参数。
        public LoginAsysnTask(String m_userName, String m_userPwd) {
            this.m_userName = m_userName;
            this.m_userPwd = m_userPwd;
        }
		
		// 3.重写doInBackground方法
		//   利用ABSDK.getInstance()获取实例对象；利用loginWithUsername()登录
		//         参数1，2：从输入框内取到的用户名、密码
		//         返回值：ABRet类型，它含有4个成员变量：code、msg、dicdatas、token
        @Override
        protected ABRet doInBackground(String... userInfo) {
            ABRet abRet = ABSDK.getInstance().loginWithUsername(m_userName, m_userPwd);
            return abRet;
        }
		
		// 4.重写onPostExecute方法
		// 【ps】doInBackground方法的返回值ABRet会自动传入onPostExecute方法，我们不需要考虑如何传入。
        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
			
			// 如果从doInBackground方法中拿到的返回值abRet中的code="00000"，则登录成功
            if (abRet.getCode().equals("00000")){
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }else{
				// 如果从doInBackground方法中拿到的返回值abRet中的code不是"00000"，
				// 则登录失败，并打印错误代码(方便以后我们进行调试，做进一步修正或改成对应的文字说明来提醒用户)
                Toast.makeText(MainActivity.this,"登录失败"+abRet.getCode(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}

```


### 2.4 测试程序

1. 电脑连接对应的网络(一共有2个网络)
1. 通过ip地址访问后台，进行用户的创建
1. 将程序下载到安卓手机(不要使用虚拟手机，太慢！！！！)
1. 安卓手机需要连接对应的网络
1. 输入刚刚创建的用户名与密码，登录





## 3.登录后，“获取控制插座的权限”
我们在2中成功实现了远程登录，现在我们远程距离控制“插座”还差2步：(1)获取控制权(2)传达指令，这一节我们仅仅来实现获取控制权这一步骤。

### 3.1 查看文件：ABSDK接口

|  12   |  取得插座状态（请在登录后使用） |
|  ----  | ----  |
| 名称  | ABRet getSockStatus(String soDevName) |
| 参数  | soDevName: 插座名称 |
| 返回值  | ABRet: code String 处理结果(00000成功，00000以外失败)、status String 插座状态(0:关闭 1:开启)~等|

|  CODE   |  含义 |
|  ----  | ----  |
| 10001  | TOKEN不存在或长度不足 |
| 10002  | TOKEN已失效 |
| 20502  | 插座设备不存在 |
| 20503  | 插座设备控制失败 |
| 20504  | 插座设备已离线 |

### 3.2 根据接口文件编写程序

1. 创建一个类SocketStatusTask，继承AsyncTask
1. 重写doInBackground方法
1. 重写onPostExecute方法
1. 实例化新建的LoginAsysnTask类
1. 执行！！！


```java
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void ChaZuoButtononCliked(View view){
		// 4.实例化新建的SocketStatusTask类
        SocketStatusTask socketStatusTask = new SocketStatusTask();
		// 5.执行！！！
        socketStatusTask.execute();
        //注意excute之后不能有语句
    }
	
	// 1.创建一个类，继承AsyncTask(注意：泛型！)
    class SocketStatusTask extends AsyncTask<Void, Void, ABRet> {
		
		// 2.重写doInBackground方法
		//	利用ABSDK.getInstance()获取实例对象；利用getSockStatus()选择设备
		//       参数：插座设备的名称(需要在后台查看设备的名称！！！)
        @Override
        protected ABRet doInBackground(Void... Voids) {
            ABRet abRet = ABSDK.getInstance().getSockStatus("s1");
            return abRet;
        }
		
		// 3.重写onPostExecute方法
        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
			
            if (abRet.getCode().equals("00000")){
                Toast.makeText(Main2Activity.this,"控制插座权限获取成功",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Main2Activity.this,"控制插座权限获取失败"+abRet.getCode(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
```

### 3.3 测试
在上一步手机登录后按button后，如果屏幕浮现出“控制插座权限获取成功”，则成功；如果屏幕浮现"控制插座权限获取失败"，根据错误代码通过查表，继续调试！





# ======================================================











