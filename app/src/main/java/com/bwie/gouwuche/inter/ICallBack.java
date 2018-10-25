package com.bwie.gouwuche.inter;

/**
 * 接口与回调 的  一个  接口
 * Created by DELL on 2018/10/23.
 */

public interface ICallBack {

    //定义两个方法
    void OnSuccess(Object o);
    void OnFailed(Exception e);

}
