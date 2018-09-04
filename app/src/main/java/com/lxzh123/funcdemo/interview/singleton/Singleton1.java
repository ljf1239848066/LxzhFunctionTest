package com.lxzh123.funcdemo.interview.singleton;

/**
 * description 双重if判断，加锁同步
 * author      Created by lxzh
 * date        2018/9/3
 */
public class Singleton1 {
    private static Singleton1 instance;
    private Singleton1(){

    }
    public static Singleton1 getInstance(){
        if(instance==null){
            synchronized (Singleton1.class){
                if(instance==null){
                    instance=new Singleton1();
                }
            }
        }
        return instance;
    }
}
