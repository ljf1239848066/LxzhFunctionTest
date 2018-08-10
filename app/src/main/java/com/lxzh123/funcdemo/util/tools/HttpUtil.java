package com.lxzh123.funcdemo.util.tools;

import android.os.Build;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description: 网络请求类
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class HttpUtil {

    /**
     * 同步get请求指定url内容
     * @param client    OkHttpClient
     * @param url       请求的url
     * @return          指定url的内容
     * @throws IOException
     */
    public static String SyncRequestGet(OkHttpClient client, String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        if(Build.VERSION.SDK_INT>=19){
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }else{
            try{
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (IOException ioe){
                throw ioe;
            }
        }
    }
}
