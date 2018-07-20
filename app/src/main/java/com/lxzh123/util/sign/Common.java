package com.lxzh123.util.sign;

public class Common {
    public final static String ENCODING="UTF-8";

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    public static String Byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
