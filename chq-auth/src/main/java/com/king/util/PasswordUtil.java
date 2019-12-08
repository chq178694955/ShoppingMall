package com.king.util;

import org.apache.shiro.crypto.hash.Sha384Hash;

/**
 * @创建人 chq
 * @创建时间 2019/12/8
 * @描述
 */
public class PasswordUtil {

    /**
     * shiro密码加密方式
     * @param data
     * @return
     */
    public static String encrypt(String data){
        String sha384Hex=new Sha384Hash(data).toBase64();
        return sha384Hex;
    }

}
