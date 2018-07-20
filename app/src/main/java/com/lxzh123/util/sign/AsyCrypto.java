package com.lxzh123.util.sign;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AsyCrypto {
    /**
     * 字符串MD5加密
     * @param text 待加密的字符串
     * @return MD5加密后的字符串
     */
    public static String MD5Encrypt(String text){
        try {
            MessageDigest a = MessageDigest.getInstance("md5");
            byte[] b = a.digest(text.getBytes(Common.ENCODING));
            return Common.Byte2Hex(b);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /***
     * 字节流数据加密
     * @param in 待加密字节流
     * @return MD5加密后的字符串
     */
    public static String MD5Encrypt(InputStream in) {
        try {
            MessageDigest a = MessageDigest.getInstance("MD5");
            byte[] bb = new byte[8192];
            int bc;
            while ((bc = in.read(bb)) > 0) {
                a.update(bb, 0, bc);
            }
            byte[] b = a.digest();

            return Common.Byte2Hex(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
        }
        return null;
    }

    /**
     * MD5加密 生成32位md5码
     */
    public static String MD5_1Encrypt(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        return Common.Byte2Hex(md5Bytes).toUpperCase();
    }
    /**
     * SHA1加密
     * @param str
     * @return
     */
    public static String SHA1Encrypt(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes(Common.ENCODING));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  利用Apache的工具类实现SHA-256加密
     *  所需jar包下載 http://pan.baidu.com/s/1nuKxYGh
     * @param str 加密后的报文
     * @return
     */
    public static String SHA256Encrypt(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes(Common.ENCODING));
            encdeStr=Common.Byte2Hex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }
    //---------
    /**
     * 利用java原生的摘要实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    public static String SHA256EncryptByJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(Common.ENCODING));
            encodeStr = Common.Byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
}

