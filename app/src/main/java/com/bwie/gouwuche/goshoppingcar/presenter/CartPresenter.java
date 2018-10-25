package com.bwie.gouwuche.goshoppingcar.presenter;

import com.bwie.gouwuche.goshoppingcar.bean.MessageBean;
import com.bwie.gouwuche.goshoppingcar.bean.Product;
import com.bwie.gouwuche.goshoppingcar.bean.Shopper;
import com.bwie.gouwuche.goshoppingcar.model.CartModel;
import com.bwie.gouwuche.goshoppingcar.view.IView;
import com.bwie.gouwuche.inter.ICallBack;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 购物车
 * Created by DELL on 2018/10/23.
 */

public class CartPresenter {

    private IView iv;
    private CartModel model;

    //定义一个方法   将vp层关联
    public void attach(IView iv){
        this.iv = iv;
        model=new CartModel();
    }

    //定义一个网络请求的方法
    public void getData(){
        //接口
        String url="http://www.zhaoapi.cn/product/getCarts?uid=1538";
        //type泛型
        Type type = new TypeToken<MessageBean<List<Shopper<List<Product>>>>>(){}.getType();

        //使用 M层 进行调用请求数据方法
        model.getData(url, new ICallBack() {
            @Override
            public void OnSuccess(Object o) {
                //请求成功后得到数据  进行强zhuan  并使用成功的方法回调
                MessageBean<List<Shopper<List<Product>>>> data = (MessageBean<List<Shopper<List<Product>>>>) o;
                iv.success(data);
            }

            @Override
            public void OnFailed(Exception e) {
               //请求失败
                iv.failed(e);
            }
        },type);

    }


    //最后进行 解绑
    public void detach(){
        if(iv!=null){
            iv=null;
        }
    }

}
