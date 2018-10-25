package com.bwie.gouwuche.goshoppingcar.view;

import com.bwie.gouwuche.goshoppingcar.bean.MessageBean;
import com.bwie.gouwuche.goshoppingcar.bean.Product;
import com.bwie.gouwuche.goshoppingcar.bean.Shopper;

import java.util.List;

/**
 * 定义一个接口  V层的接口
 * Created by DELL on 2018/10/23.
 */

public interface IView {

    //两个方法
    void success(MessageBean<List<Shopper<List<Product>>>> data);

    void failed(Exception e);


}
