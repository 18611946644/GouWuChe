package com.bwie.gouwuche.goshoppingcar.view;

import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.gouwuche.R;
import com.bwie.gouwuche.adapter.ProductAdapter;
import com.bwie.gouwuche.adapter.ShopperAdapter;
import com.bwie.gouwuche.goshoppingcar.bean.MessageBean;
import com.bwie.gouwuche.goshoppingcar.bean.Product;
import com.bwie.gouwuche.goshoppingcar.bean.Shopper;
import com.bwie.gouwuche.goshoppingcar.presenter.CartPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {

    private RecyclerView rvShopper;
    private CheckBox cbTotalSelect;
    private TextView txtTotlePrice;
    private Button btnJieSuan;
    private TextView txtBianJi;
    private ImageView imgBack;
    private List<Shopper<List<Product>>> list;
    private ShopperAdapter adapter;
    private CartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 初始化控件
        getinit();
        //2 设置适配器和创建list
        initData();
        //3 监听
        setListener();
        
    }

    /**
     * 3 监听
     * */
    private void setListener() {
        cbTotalSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义一个变量
                boolean isChecked = cbTotalSelect.isChecked();
                //遍历一级列表  和  下方的全选状态一致
                for (Shopper<List<Product>> listShopper : list) {
                    listShopper.setChecked(isChecked);
                    //遍历二级列表  和下方的全选状态一致
                    List<Product> products = listShopper.getList();
                    for (Product product : products) {
                        product.setChecked(isChecked);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 2 设置适配器和创建list
     * */
    private void initData() {
        //创建一个List
        list = new ArrayList<>();
        //商家的适配器初始化
        adapter = new ShopperAdapter(this, list);
        //添加一级条目  商家状态发生变化时
        adapter.setOnShopperClickListener(new ShopperAdapter.OnShopperClickListener() {
            @Override
            public void onShopperClick(int position, boolean isCheck) {
                // 为了效率考虑，当点击状态变成未选中时，全选按钮肯定就不是全选了，就不用再循环一次
                if(!isCheck){
                    cbTotalSelect.setChecked(false);
                }else{
                    // 如果是商家变成选中状态时，需要循环遍历所有的商家是否被选中
                    // 循环遍历之前先设置一个true标志位，只要有一个是未选中就改变这个标志位为false
                    boolean isAllShopperChecked = true;
                    for (Shopper<List<Product>> listShopper : list) {
                        //判断是否有未选中的
                        if(!listShopper.isChecked()){
                            //如果有未选中的 就将标识设置为FALSE
                            isAllShopperChecked = false;
                            //跳出循环
                            break;
                        }
                    }
                    //重新赋值
                    cbTotalSelect.setChecked(isAllShopperChecked);
                }
                //一级条目发生变化  计算总价  这是一个方法  在下面
                calculatePrice();
            }
        });

        //适配器
        adapter.setOnAddDecreaseProductListener(new ProductAdapter.OnAddDecreaseProductListener() {
            @Override
            public void onChange(int position, int num) {
                calculatePrice();
            }
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvShopper.setLayoutManager(layoutManager);
        //添加分割线
        rvShopper.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvShopper.setAdapter(adapter);
        //初始化出购物车的P层
        presenter = new CartPresenter();
        presenter.attach(this);
        presenter.getData();
    }

    /**
     * 计算总价的方法
     * */
    private void calculatePrice() {
        //遍历商家
        float totalPrice = 0;
        for (Shopper<List<Product>> listShopper : list) {
            //遍历商家商品
            List<Product> list = listShopper.getList();
            for (Product product : list) {
                //如果商品被选中
                if(product.isChecked()){
                    //计算   要买商品的数量  *  商品的价格 = 这类商品的总价格
                    totalPrice+=product.getNum()*product.getPrice();
                }
            }
        }
        //得出计算的总价   进行重新赋值
        txtTotlePrice.setText("总价:"+totalPrice);
    }

    /**
     * 1 控件初始化
     */
    private void getinit() {
        //顶部
        imgBack = findViewById(R.id.img_back);//返回
        txtBianJi = findViewById(R.id.txt_bianji);//编辑

        //底部
        cbTotalSelect = findViewById(R.id.cb_total_select);//全选
        txtTotlePrice = findViewById(R.id.txt_total_price);//总价
        btnJieSuan = findViewById(R.id.btn_jiesuan);//点击结算按钮

        //中部  RecyclerView
        rvShopper = findViewById(R.id.rv_shopper);
    }


    /**
     * 2 实现IView  接口后的实现的两个方法
     * */
    @Override
    public void success(MessageBean<List<Shopper<List<Product>>>> data) {
       if(data!=null){//如果得到的数据不为空
           //就获取商家列表
           List<Shopper<List<Product>>> shoppers = data.getData();
           if(shoppers!=null){
               list.clear();
               list.addAll(shoppers);
               adapter.notifyDataSetChanged();
           }
       }
    }

    @Override
    public void failed(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.detach();
        }
    }
}
