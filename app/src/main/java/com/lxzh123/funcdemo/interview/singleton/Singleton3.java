package com.lxzh123.funcdemo.interview.singleton;

/**
 * description 静态内部类，第一次调用时才创建一个实例，
 * author      Created by lxzh
 * date        2018/9/3
 */
public class Singleton3 {
    private Singleton3(){

    }

    private static class Singleton3Holder{
        private final static Singleton3 instance=new Singleton3();
    }

    public static Singleton3 getInstance(){
        return Singleton3Holder.instance;
    }
}
