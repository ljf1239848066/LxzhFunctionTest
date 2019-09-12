package com.lxzh123.funcdemo.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class Common {
    public final static String SDCARD_DIR="FunctionTest";

    /**
     * 检测是否有packagemanager
     * @param context
     * @return
     */
    public static boolean checkBasicConfiguration(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        final String packageName = context.getPackageName();

        if (packageManager == null || packageName == null) {
            Log.e("Common","Can't check  packageManager or packageName");
            return false;
        }
        return PackageManager.PERMISSION_GRANTED == packageManager.checkPermission("android.permission.INTERNET", packageName);

    }


    /**
     * bitmap 转 byteArr
     *
     * @param bitmap bitmap 对象
     * @param format 格式
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }
}
