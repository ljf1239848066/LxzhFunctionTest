package com.lxzh123.funcdemo.util.sign;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SymCrypto {

    /**
     * 获取密钥
     *
     * @return 密钥
     * @throws NoSuchAlgorithmException
     */
    public static byte[] InitAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); //192,256
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }
    /**
     * 参数：”AES/ECB/PKCS5Padding”在加密和解密时必须相同，可以直接写”AES”,这样就是使用默认模式
     * （C#和java默认的模式不一样，C#中默认的是这种,java的默认待研究）。
     * 分别的意思为：AES是加密算法，ECB是工作模式，PKCS5Padding是填充方式。
     * AES是分组加密算法，也称块加密。每一组16字节。这样明文就会分成多块。当有一块不足16字节时就会进行填充。
     * 一共有四种工作模式：
     *
     * ECB 电子密码本模式：相同的明文块产生相同的密文块，容易并行运算，但也可能对明文进行攻击。
     * CBC 加密分组链接模式：一块明文加密后和上一块密文进行链接，不利于并行，但安全性比ECB好，是SSL,IPSec的标准。
     * CFB 加密反馈模式：将上一次密文与密钥运算，再加密。隐藏明文模式，不利于并行，误差传递。
     * OFB 输出反馈模式：将上一次处理过的密钥与密钥运算，再加密。隐藏明文模式，不利于并行，有可能明文攻击，误差传递。
     */
    /**使用AES对字符串加密
     * @param str utf8编码的字符串
     * @param key 密钥（16字节）
     * @return 加密结果
     * @throws Exception
     */
    public static byte[] AesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        SecretKey secretKey = new SecretKeySpec(key.getBytes(Common.ENCODING), "AES");
        Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(str.getBytes(Common.ENCODING));
        return  bytes;
    }
    /**使用AES对数据解密
     * @param bytes utf8编码的二进制数据
     * @param key 密钥（16字节）
     * @return 解密结果
     * @throws Exception
     */
    public static String AesDecrypt(byte[] bytes, String key) throws Exception {
        if (bytes == null || key == null) return null;
        SecretKey secretKey = new SecretKeySpec(key.getBytes(Common.ENCODING), "AES");
        Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, Common.ENCODING);
    }

    /**使用DES对字符串加密
     * @param str utf8编码的字符串
     * @param key 密钥（56位，7字节）
     * @return 加密结果
     * @throws Exception
     */
    public static byte[] DesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        SecretKey secretKey = new SecretKeySpec(key.getBytes(Common.ENCODING), "DES");
        Cipher cipher = Cipher.getInstance("DES/CFB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(str.getBytes(Common.ENCODING));
        return  bytes;
    }
    /**使用DES对数据解密
     * @param bytes utf8编码的二进制数据
     * @param key 密钥（16字节）
     * @return 解密结果
     * @throws Exception
     */
    public static String DesDecrypt(byte[] bytes, String key) throws Exception {
        if (bytes == null || key == null) return null;
        SecretKey secretKey = new SecretKeySpec(key.getBytes(Common.ENCODING), "DES");
        Cipher cipher = Cipher.getInstance("DES/CFB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, Common.ENCODING);
    }
}
