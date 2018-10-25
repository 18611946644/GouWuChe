package com.bwie.gouwuche.goshoppingcar.bean;

/**
 * 最外层的封装类
 * Created by DELL on 2018/10/23.
 */

public class MessageBean<T> {

    private String msg;
    private String code;
    private T data;//这是一个泛型 代表的是list<T>


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
