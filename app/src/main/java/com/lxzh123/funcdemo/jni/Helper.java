package com.lxzh123.funcdemo.jni;

import android.util.Log;

public class Helper {
    private final static String TAG = "Helper";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("jnilib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native String stringFromJNI();

    public static native String stringFromJNI1(int level);

    public static native String stringFromJNI2(int level);

    /**
     * called from jni
     *
     * @param param
     * @return
     */
    public static int callStaticMethod(int param) {
        Log.d(TAG, "callStaticMethod called from jni...");
        return param + 1;
    }

    public int callInstanceMethod(int param) {
        Log.d(TAG, "callInstanceMethod called from jni...");
        return param + 1;
    }
}
