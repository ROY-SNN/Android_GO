package com.example.module_demo_0518;

import java.util.HashMap;

/**
 * 【AboxCons常量类】：用于集中存放各种常量名称
 */
public class AboxCons {
    // ========================Preferences文件中的键名=========================
    // 【ps】文件中的数据：键值对的存储方法
    public static String SP_USER_NAME = "name";            // 记住用户名
    public static String SP_USER_PSD  = "pwd";             // 记住密码
    public static String SP_AUTO_LOGIN  = "auto_login";    // 自动登录
    public static String SP_IF_USED = "If_Used";           // 是否为第一次使用遥控设备

    // ==============================传感器设备名称=============================
    // 【ps】注意每个设备的实际命名情况，需要登录网页的控制台查看
    public static String TEMP_HUMIDITY_DEVICE = "TH1";   // 温湿度传感器名称
    public static String SOCKET_DEVICE1 = "s1";           // 插座名称1
    public static String SOCKET_DEVICE2 = "s2";           // 插座名称2
    public static String DOOR_DEVICE = "door";           // 门磁传感器名称
    public static String INFRARED_DEVICE = "HW";         // 红外设备名称

    // ===========================数据库的相关变量名============================
    // 【ps】存放学习的遥控按键，2个字段
    public static String DATABASES_NAME = "Database052566";  // 数据库名称
    public static String TABLE_NAME = "Table_HW";            // 数据表的名称
    public static String Field_NAME1 = "Key_id";             // 字段0的名称
    public static String Field_NAME2 = "Key_Name";           // 字段1的名称

    // ==========================打印报错代码对应的原因=========================
    // 【ps】根据提供的接口手册，查看函数返回值中Code对应错误原因
    public static HashMap<String,String> errorMap = new HashMap<String,String>();
    {
        errorMap.put("00000","处理成功");
        errorMap.put("00001","数据库操作失败");
        errorMap.put("10001","TOKEN不存在或长度不足");
        errorMap.put("10002","TOKEN已失效");
        errorMap.put("10003","TOKEN中的SN号不正");
        errorMap.put("10004","签名不存在");
        errorMap.put("10005","时间戳不存在");
        errorMap.put("10006","签名不正确");
        errorMap.put("20000","请求超时");
        errorMap.put("20001","用户名或密码不存在");
        errorMap.put("20002","用户不存在或已暂停使用");
        errorMap.put("20003","登录失败次数过多");
        errorMap.put("20004","用户名或密码不正确");
        errorMap.put("20005","本地用户权限不足");
        errorMap.put("20101","服务器端URL或酒店名或房间号或APIKEY不存在");
        errorMap.put("20201 ","红外KEY不存在");
        errorMap.put("20202 ","红外码值不存在");
        errorMap.put("20203 ","红外设备不存在");
        errorMap.put("20204 ","红外码库导入失败");
        errorMap.put("20205 ","红外发射失败");
        errorMap.put("20206 ","红外设备已离线");
        errorMap.put("20301 ","参数不正_CMD0不存在");
        errorMap.put("20302 ","参数不正_CMD1不存在");
        errorMap.put("20303 ","参数不正_PAYLOAD不存在");
        errorMap.put("20304 ","窗帘设备不存在");
        errorMap.put("20305 ","窗帘设备控制失败");
        errorMap.put("20306 ","窗帘设备已离线");
    }
}
