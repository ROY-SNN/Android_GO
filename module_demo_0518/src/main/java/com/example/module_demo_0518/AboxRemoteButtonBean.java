package com.example.module_demo_0518;

/**
 * 【AboxRemoteButtonBean类】：作为更新数据库、更新本地集合的载体。
 */
public class AboxRemoteButtonBean {
    private int position;          // 对应网格中30个按键的位置(0~29)
    private String keyId;          // 表中字段0：id值
    private String keyName;        // 表中字段1：遥控的功能名

    public AboxRemoteButtonBean(int position, String keyName, String keyId) {
        this.position = position;
        this.keyName = keyName;
        this.keyId = keyId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public int getPosition() {
        return position;
    }

    public String getKeyId() {
        return keyId;
    }

    public String getKeyName() {
        return keyName;
    }
}

