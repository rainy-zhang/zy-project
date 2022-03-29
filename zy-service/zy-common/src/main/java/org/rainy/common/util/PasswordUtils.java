package org.rainy.common.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class PasswordUtils {

    public static String encrypt(String str) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = str.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
                chars[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            log.error("generate md5 error, {}", str, e);
            return null;
        }
    }

}
