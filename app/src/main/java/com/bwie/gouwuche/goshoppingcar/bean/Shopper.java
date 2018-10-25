package com.bwie.gouwuche.goshoppingcar.bean;

/**
 * 这是第二层数据的封装类
 * Created by DELL on 2018/10/23.
 */

public class Shopper<T> {

    private String sellerid;
    private String sellerName;
    private boolean isChecked;//用来判断是否选中的一个属性
    private T list;//一个泛型

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }
}
