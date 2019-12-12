package com.king.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Base64Utils {

    private static Logger logger = LoggerFactory.getLogger(Base64Utils.class);

    public static final BASE64Decoder decoder = new BASE64Decoder();

    public static final BASE64Encoder encoder = new BASE64Encoder();

    public static final String defaultCode = "UTF-8";

    public static String encode(String str){
        String resStr = null;
        try {
            byte[] strBytes = str.getBytes(defaultCode);
            resStr = encoder.encode(strBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("不支持的编码");
        }
        return resStr;
    }

    public static String decode(String str){
        String resStr = null;
        try {
            resStr = new String(decoder.decodeBuffer(str),defaultCode);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IO异常");
        }
        return resStr;
    }

    public static void main(String[] args) throws Exception {
        String abc = "caohaoquan";
        Integer rand = RandomUtil.generatorRandom(4);
        String defaultTag = "CHQ";
        String strxxx = abc + "_" + defaultTag + "_" + rand;
        System.out.println("MD5：" + MD5Util.encode(strxxx));

        System.out.println("SHA1：" + SecuritySHA1Utils.shaEncode(MD5Util.encode(strxxx)));

        System.out.println("BASE64：" + Base64Utils.encode(SecuritySHA1Utils.shaEncode(MD5Util.encode(strxxx))));

        String encodeStr = Base64Utils.encode(abc);
        System.out.println("加码："+encodeStr);
        String decodeStr = Base64Utils.decode(encodeStr);
        System.out.println("解码："+decodeStr);
    }

}
