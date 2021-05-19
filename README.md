# Android_GO
Android_Demo20210518

# 2021.05.18Demo登录页面的设计流程
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















