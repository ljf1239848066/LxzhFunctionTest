package com.lxzh123.funcdemo.unsafetest;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class TestUnsafe {

    private final static String TAG = "TestUnsafe";

    private static Unsafe unsafe;

    //    private static final Unsafe unsafe=Unsafe
    public static Unsafe getUnsafe() {
        if (unsafe == null) {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                unsafe = (Unsafe) f.get(null);
            } catch (Exception e) {
                return null;
            }
        }
        return unsafe;
    }

    public static String Test() {
        Log.d(TAG, "Test-0");
        StringBuffer rst = new StringBuffer();
        Log.d(TAG, "Test-1");
        Unsafe unsafe = getUnsafe();
        Log.d(TAG, "Test-2");
        Class clazz = TestModel.class;
        Log.d(TAG, "Test-3");
        try {
            System.out.print(TAG + ":0:");
            TestModel model = (TestModel) unsafe.allocateInstance(clazz);
            Log.d(TAG, "Test-4");
            model.setTime(10);
            Log.d(TAG, "Test-5");
            rst.append(TAG + ":1:" + model.getDistance());
            Log.d(TAG, "Test-6");
            System.out.print(TAG + ":1:" + model.getDistance());
            Log.d(TAG, "Test-7-" + model.getTime());
            Log.d(TAG, "Test-8-" + model.getSpeed());
            Log.d(TAG, "Test-9-" + model.getDistance());
            //计算变量 value 在类对象中的偏移量
            long valueOffset = unsafe.objectFieldOffset(TestModel.class.getDeclaredField("speed"));
            Log.d(TAG, "Test-10-" + valueOffset);
            unsafe.getAndSetInt(model, valueOffset, 100);
            Log.d(TAG, "Test-11");
            rst.append(TAG + ":2:" + model.getDistance());
            Log.d(TAG, "Test-12-" + model.getDistance());
            System.out.print(TAG + ":2:" + model.getDistance());
        } catch (Exception ex) {
            Log.d(TAG, "Test-13");
        }
        ClassLoader cl;

        return rst.toString();
    }

    public static void printDexFile(Context context) {
        try {
            ClassLoader parent = context.getClassLoader();
            Log.d(TAG, "BaseDexClassLoader classloader:" + parent);
            Class<?> DexPathListClass = Class.forName("dalvik.system.DexPathList");

            Class<?> PathClassLoaderClass = parent.getClass();
            Log.d(TAG, "BaseDexClassLoader PathClassLoaderClass:" + PathClassLoaderClass.toString());
            Class<?> OriginBaseClassLoaderClass = parent.getClass().getSuperclass();
            Log.d(TAG, "BaseDexClassLoader OriginBaseClassLoaderClass:" + OriginBaseClassLoaderClass.toString());
            Field pathListField = OriginBaseClassLoaderClass.getDeclaredField("pathList");

            pathListField.setAccessible(true);
            Object oldPathList = pathListField.get(parent);
            Log.d(TAG, "BaseDexClassLoader oldPathList:" + oldPathList.toString());

            Field dexElementsField = DexPathListClass.getDeclaredField("dexElements");
            Log.d(TAG, "BaseDexClassLoader dexElementsField:" + dexElementsField.toString());
            dexElementsField.setAccessible(true);
            Object oldDexElements = dexElementsField.get(oldPathList);
            Log.d(TAG, "BaseDexClassLoader oldDexElements:" + oldDexElements.toString());

            Object dexElementOne = ((Object[]) oldDexElements)[0];
//            Log.d(TAG, "dexElementOne addr:" + System.identityHashCode(dexElementOne));
            printAddresses("dexElementOne", dexElementOne);

            Class<?> ElementClass = dexElementOne.getClass();
            Field dexFileField = ElementClass.getDeclaredField("dexFile");
            dexFileField.setAccessible(true);
            Class<?> DexFileClass = Class.forName("dalvik.system.DexFile");

            Object dexFile = dexFileField.get(dexElementOne);
//            Log.d(TAG, "dexFile addr:" + System.identityHashCode(dexFile));
            printAddresses("dexFile", dexFile);

            Field mCookieField = DexFileClass.getDeclaredField("mCookie");
            mCookieField.setAccessible(true);
            Object mCookie = mCookieField.get(dexFile);
//            Log.d(TAG, "mCookie addr:" + System.identityHashCode(mCookie));
            printAddresses("mCookie", mCookie);

//            Class<?> ElementClass = Class.forName("dalvik.system.DexPathList$Element");
            Class<?> ElementArrayClass = oldDexElements.getClass();
            Log.d(TAG, "BaseDexClassLoader ElementArrayClass:" + ElementArrayClass.toString());

            int elementLen = Array.getLength(oldDexElements);
            Object newDexElements = Array.newInstance(ElementClass, elementLen + 1);
            for (int k = 0; k < elementLen; ++k) {
                Array.set(newDexElements, k, Array.get(oldDexElements, k));
            }
        } catch (Exception ex) {
            Log.e(TAG, "BaseDexClassLoader Exception:" + ex.toString());
            ex.printStackTrace();
        }
    }

    public static void printAddresses(String label, Object... objects) {
        System.out.print(label + ": 0x");
        long last = 0;
        int offset = unsafe.arrayBaseOffset(objects.getClass());
        int scale = unsafe.arrayIndexScale(objects.getClass());
        switch (scale) {
            case 4:
                long factor = 8;
                final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
                System.out.print(Long.toHexString(i1));
                last = i1;
                for (int i = 1; i < objects.length; i++) {
                    final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
                    if (i2 > last)
                        System.out.print(", +" + Long.toHexString(i2 - last));
                    else
                        System.out.print(", -" + Long.toHexString(last - i2));
                    last = i2;
                }
                break;
            case 8:
                throw new AssertionError("Not supported");
        }
        System.out.println();
    }
}
