package com.bwie.gouwuche.goshoppingcar.model;

import com.bwie.gouwuche.inter.ICallBack;
import com.bwie.gouwuche.utils.OKHttpUtils;

import java.lang.reflect.Type;

/**
 * Model层  网络请求数据
 * Created by DELL on 2018/10/23.
 */

public class CartModel {

    //调用网络请求工具类  进行请求数据
    public void getData(String url, ICallBack callBack, Type type){
        OKHttpUtils.getInstanse().get(url,callBack,type);
    }

}
