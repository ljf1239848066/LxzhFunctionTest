package com.lxzh123.funcdemo.interview.singleton;

/**
 * description 懒汉式 缺点，每次获取单例都要获取锁，很耗费资源
 * author      Created by lxzh
 * date        2018/9/3
 */
public class Singleton2 {
    private static Singleton2 instance;
    private Singleton2(){

    }
    public static synchronized Singleton2 getInstance(){
        if(instance==null){
            instance=new Singleton2();
        }
        return instance;
    }
}
