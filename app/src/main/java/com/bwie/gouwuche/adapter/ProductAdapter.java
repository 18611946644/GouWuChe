package com.bwie.gouwuche.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.gouwuche.R;
import com.bwie.gouwuche.goshoppingcar.bean.Product;
import com.bwie.gouwuche.utils.StringUtils;
import com.bwie.gouwuche.widget.AddDecreaseView;

import java.util.List;

/**商品展示
 * Created by DELL on 2018/10/23.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    //3 初始化Context 和 list
    private Context context;
    private List<Product> list;

    /**
     * 4 定义一个接口  二级列表的点击监听
      */
    public interface OnProductClickListener{
        void onProductClick(int position, boolean isChecked);
    }
    //提供一个接口对象
    public OnProductClickListener productClickListener;
    //对外提供一个传递对象的接口
    public void setOnProductClickListener(OnProductClickListener listener){
        this.productClickListener = listener;
    }

    /**
     *  5 加减器发生变化的监听   定义接口
     */
    public interface  OnAddDecreaseProductListener{
        //定义一个发生变化的方法
        void onChange(int position,int num);
    }
    //提供一个接口对象
    private OnAddDecreaseProductListener productListener;
    //对外提供一个传递接口对象的方法
    public void setOnAddDecreaseProductListener(OnAddDecreaseProductListener Listener){
        this.productListener = Listener;
    }

    //3.1
    public ProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 2  实现三个方法
      */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建ViewHolder
        View v = View.inflate(context,R.layout.item_product,null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //绑定的时候
        //得到数据
        final Product product = list.get(position);
        //得到图片
        String images = product.getImages();
        //商品图片进行判断  剪切  赋值
        if(!TextUtils.isEmpty(images)){//得到数据不为空
            //进行剪切  得到一个数组
            String[] strings = images.split("\\|");
            //进行赋值
            if(strings.length>0){
                Glide.with(context).load(StringUtils.httpstoHttp(strings[0]))
                        .into(holder.imgProduct);
            }
        }
        //为商品名  和  价格赋值
        holder.txtProductName.setText(product.getTitle());
        holder.txtSinglePriice.setText(String.valueOf(product.getPrice()));

        /**
         * 5.1 加减器添加点击事件
         * */
        holder.advProduct.setOnOnAddDecreaseClickListener(new AddDecreaseView.OnAddDecreaseClickListener() {
            @Override
            public void add(int num) {
                product.setNum(num);
                if(productListener!=null){
                    productListener.onChange(position,num);
                }
            }

            @Override
            public void decrease(int num) {
                product.setNum(num);
                if(productListener!=null){
                    productListener.onChange(position,num);
                }
            }
        });

        /**
         * 6.商品的复选框
         * */
        holder.cbProduct.setOnCheckedChangeListener(null);//设置默认
        holder.cbProduct.setChecked(product.isChecked());
        holder.cbProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                product.setChecked(isChecked);
                if(productClickListener != null){
                    //如果不为空  就调用点击方法
                    productClickListener.onProductClick(position,isChecked);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     *1  定义一个RecyclerView.Adapter<>泛型
     * */
    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbProduct;
        private ImageView imgProduct;
        private TextView txtProductName;
        private TextView txtSinglePriice;
        private AddDecreaseView advProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            //实现方法   初始化控件
            cbProduct = itemView.findViewById(R.id.cb_product);
            imgProduct = itemView.findViewById(R.id.img_product);
            txtSinglePriice = itemView.findViewById(R.id.txt_single_price);
            advProduct = itemView.findViewById(R.id.adv_product);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
        }
    }

}
