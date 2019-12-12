package com.king.utils;

public class RandomUtil {

    public static int generatorRandom(int num){
        String zeroStr = "1";
        num = num - 1;
        if(num > 0){
            for(int i=0;i<num;i++){
                zeroStr += "0";
            }
        }

        int zero = Integer.valueOf(zeroStr);
        return (int)((Math.random()*9+1)*zero);
    }

    public static void main(String[] args) {
        int s = RandomUtil.generatorRandom(1);
        System.out.println(s);
    }

}
