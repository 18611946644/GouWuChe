package com.bwie.gouwuche.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bwie.gouwuche.R;
import com.bwie.gouwuche.goshoppingcar.bean.Product;
import com.bwie.gouwuche.goshoppingcar.bean.Shopper;

import java.util.List;

/**
 * 商家的适配器
 * //一级列表  商家展示控件
 * Created by DELL on 2018/10/23.
 */

public class ShopperAdapter extends RecyclerView.Adapter<ShopperAdapter.ViewHolder> {

    /**
     * 3 首先初始化一个context  和 list
      */
    private Context context;
    private List<Shopper<List<Product>>> list;

    public ShopperAdapter(Context context, List<Shopper<List<Product>>> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 4 一级列表  发生变化的接口
     */
    public interface OnShopperClickListener {
        void onShopperClick(int position, boolean isCheck);
    }

    private OnShopperClickListener shopperClickListener;

    public void setOnShopperClickListener(OnShopperClickListener listener) {
        this.shopperClickListener = listener;
    }

    /**
     * 5 二级列表的加减器监听  接口定义
      */
    private ProductAdapter.OnAddDecreaseProductListener productListener;
    //提供一个接口对象的传递方法
    public void setOnAddDecreaseProductListener(ProductAdapter.OnAddDecreaseProductListener listener) {
        this.productListener = listener;
    }



    /**
     *2 实现三个方法
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建
        //2.3 初始化一个View视图   (布局中有一个选框  一个txtName  一个RecyclerView)
        View v = View.inflate(context,R.layout.item_shopper,null);
        //2.2 初始化一个ViewHolder
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //绑定的时候
        //2.4 得到数据进行 赋值
        final Shopper<List<Product>> Shopper = list.get(position);
        holder.txtShopperName.setText(Shopper.getSellerName());

        /**
         *6 产品的二级列表
         */
        //6.1 布局
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(context);
        holder.rvProduct.setLayoutManager(pLayoutManager);
        //初始化适配器  添加List 中的商品列表内容
        final ProductAdapter adapter = new ProductAdapter(context,Shopper.getList());
        //6.2 给二级商品列表添加一个加减器监听器
        if(productListener != null){
            adapter.setOnAddDecreaseProductListener(productListener);
        }
        /**
         * 7 二级列表  商品复选框的点击事件
         * */
        adapter.setOnProductClickListener(new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(int position, boolean isChecked) {
                //当前商品未选中  商家（一级列表） 也就未选中
                if(!isChecked){
                    Shopper.setChecked(false);
                    //只要当前条目未选中  全选也就未选中
                    shopperClickListener.onShopperClick(position,false);
                }else{
                    //否则  当前条目如果选中  就需要遍历商家所有的商品是否选中
                    // 循环遍历之前先设置一个true标志位，只要有一条商品没有被选中，商家也就没有选中，标志位变成false
                    boolean isAllProductSelected = true;
                    for (Product product : Shopper.getList()) {
                        if(!product.isChecked()){
                          isAllProductSelected = false;
                          break;//跳出循环
                        }
                    }
                    //重新为商家是否选中赋值
                    Shopper.setChecked(isAllProductSelected);
                    // 当前商品选中时，需要循环遍历所有的商家是否被选中来确认外部全选复选框的状态
                    shopperClickListener.onShopperClick(position,true);
                }
                //数据发生变化之后  要刷新适配器
                notifyDataSetChanged();
                productListener.onChange(0,0);
            }
        });

        //设置适配器
        holder.rvProduct.setAdapter(adapter);
        //先取消掉 checked自带的默认 点击变化监听  在后面设置自己的监听器
        holder.cbSHopper.setOnCheckedChangeListener(null);
        //设置好初始化的状态
        holder.cbSHopper.setChecked(Shopper.isChecked());

        /**
         * 8  设置我们自己的监听
         * */
        holder.cbSHopper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //设置商家选框
                Shopper.setChecked(isChecked);
                //1.商家被选中的时候，子类所有的商品应该被选中
                List<Product> productList = Shopper.getList();
                //遍历商家里的所有子类 并设置选框是否选中
                for (Product product : productList) {
                   product.setChecked(isChecked);
                }
                //子类商品的适配器刷新
                adapter.notifyDataSetChanged();
                //点击一级条目的时候，外部的全选按钮状态发生变化
                if(shopperClickListener != null){
                    shopperClickListener.onShopperClick(position,isChecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * 1 定义RecyclerView.Adapter<>的泛型类
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbSHopper;
        private TextView txtShopperName;
        private RecyclerView rvProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            //找控件
            cbSHopper = itemView.findViewById(R.id.cb_shopper);//选框
            txtShopperName = itemView.findViewById(R.id.txt_shopper_name);//商品名称
            rvProduct = itemView.findViewById(R.id.rv_product);//商品价格
        }
    }

}
