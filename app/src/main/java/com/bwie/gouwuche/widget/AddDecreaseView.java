package com.bwie.gouwuche.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.gouwuche.R;

/**
 * 有关加减器的类
 * Created by DELL on 2018/10/23.
 */

public class AddDecreaseView extends RelativeLayout implements View.OnClickListener {

    private TextView txtAdd;
    private TextView txtDecrease;
    private TextView txtNum;

    private int num;

    //2 第二步 定义一个接口回调
    public interface OnAddDecreaseClickListener {
        //两个加减方法
        void add(int num);
        void decrease(int num);
    }

    //2.1 定义一个接口对象
    public OnAddDecreaseClickListener listener;

    //2.2 对外提供一个接口对象的方法
    public void setOnOnAddDecreaseClickListener(OnAddDecreaseClickListener listener){
        this.listener = listener;
    }



    //1 第一步 继承RelativeLayout类  实现三个方法
    public AddDecreaseView(Context context) {
        this(context,null);
    }

    public AddDecreaseView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public AddDecreaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.3  定义一个方法  用来初始化控件
        init(context);
    }

    //1.3  定义一个方法  用来初始化控件
    private void init(Context context) {
       //1.3.1 引入加减布局
        View.inflate(context, R.layout.item_add_decrease,this);
        txtAdd = findViewById(R.id.txt_add);
        txtDecrease = findViewById(R.id.txt_decrease);
        txtNum = findViewById(R.id.txt_num);

        //设置 商品件数的默认值
        txtNum.setText("1");

        //1.3.3 设置加减点击事件
        txtAdd.setOnClickListener(this);
        txtDecrease.setOnClickListener(this);

    }

   //1.3.4 为num设置get  set 方法
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        //得到之后  重新赋值
        txtNum.setText(num+"");
    }

    //1.3.3 设置加减点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_add://点击加号
                num++;//点击加1
                txtNum.setText(num+"");//重新为展示商品件数的控件赋值
                //判断 回调的是否为空
                if(listener!=null){
                    listener.add(num);//回调添加的方法  进行加1
                }
                break;

            case R.id.txt_decrease://点击减号
                if(num>1){
                   num--;//判断num最少为1  只有大于1的时候 才会执行减1 的方法
                }
                //减完之后  重新赋值
                txtNum.setText(num+"");
                if(listener!=null){
                    listener.decrease(num);
                }
                break;
        }
    }

}
