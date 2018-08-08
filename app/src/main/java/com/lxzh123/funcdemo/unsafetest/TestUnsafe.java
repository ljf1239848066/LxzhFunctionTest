package com.lxzh123.funcdemo.unsafetest;

import android.util.Log;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class TestUnsafe {

    private final static String TAG="TestUnsafe";

//    private static final Unsafe unsafe=Unsafe
    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            return null;
        }

    }

    public static String Test(){
        Log.d(TAG,"Test-0");
        StringBuffer rst=new StringBuffer();
        Log.d(TAG,"Test-1");
        Unsafe unsafe=getUnsafe();
        Log.d(TAG,"Test-2");
        Class clazz=TestModel.class;
        Log.d(TAG,"Test-3");
        try{
            System.out.print(TAG+":0:");
            TestModel model=(TestModel) unsafe.allocateInstance(clazz);
            Log.d(TAG,"Test-4");
            model.setTime(10);
            Log.d(TAG,"Test-5");
            rst.append(TAG+":1:"+model.getDistance());
            Log.d(TAG,"Test-6");
            System.out.print(TAG+":1:"+model.getDistance());
            Log.d(TAG,"Test-7-"+model.getTime());
            Log.d(TAG,"Test-7-"+model.getSpeed());
            Log.d(TAG,"Test-7-"+model.getDistance());
            //计算变量 value 在类对象中的偏移量
            long valueOffset = unsafe.objectFieldOffset(TestModel.class.getDeclaredField("speed"));
            unsafe.getAndSetInt(model, valueOffset, 100);
            Log.d(TAG,"Test-8");
            rst.append(TAG+":2:"+model.getDistance());
            Log.d(TAG,"Test-9-"+model.getDistance());
            System.out.print(TAG+":2:"+model.getDistance());
        }catch (Exception ex){
            Log.d(TAG,"Test-10");
        }
        return rst.toString();
    }
}
