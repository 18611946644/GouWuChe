package com.bwie.gouwuche.utils;

import android.os.Handler;

import com.bwie.gouwuche.R;
import com.bwie.gouwuche.inter.ICallBack;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求工具类
 * Created by DELL on 2018/10/23.
 */

public class OKHttpUtils {

    //使用单例模式
    //第一步
    private static volatile OKHttpUtils instanse;

    //初始化OKHttpClient  和 一个handler
    private OkHttpClient client;
    private Handler handler = new Handler();

    //第二步私有化一个方法
    private OKHttpUtils(){
        //初始化Client
        client = new OkHttpClient();
    }

    //第三步 提供一个对外的构造方法
    public static OKHttpUtils getInstanse(){
       if(instanse==null){
           synchronized (OKHttpUtils.class){
               if(null == instanse){
                   instanse = new OKHttpUtils();
               }
           }
       }
       return instanse;
    }


    //提供一个网络请求方法
    public void get(String url, final ICallBack callBack, final Type type){
       //使用一个请求方法
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        //通过client 和  request 得到一个call
        Call call = client.newCall(request);
        //通过call 使用一部请求方式请求数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //失败的时候  传回主线程一个原因
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.OnFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功的时候  得到数据  进行解析
                String string = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(string, type);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.OnSuccess(o);
                    }
                });
            }
        });
    }




}
