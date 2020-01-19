package com.king.utils;

import java.util.*;

/**
 * 双色球随机选球工具
 * @创建人 chq
 * @创建时间 2020/1/8
 * @描述
 */
public class DoubleColorBallUtil {

    private static final int MAX_RED = 33;
    private static final int MAX_BLUE = 16;
    private static final int MAX_RED_COUNT = 6;
    private static final int MAX_BLUE_COUNT = 1;

    public static final String KEY_RED = "KEY_RED";
    public static final String KEY_RED_SORT = "KEY_RED_SORT";
    public static final String KEY_BLUE = "KEY_BLUE";

    /**
     * 生成随机数字
     * @param max
     * @return
     */
    private static int createBall(int max){
        Random random = new Random();
        return random.nextInt(max + 1);
    }

    /**
     * 生成红球
     * @return
     */
    private static List<Integer> createRedBall(){
        List<Integer> result = new ArrayList<>();
        int index = 0;
        Map<Integer,Object> map = new HashMap<>();
        while(true){
            int num = createBall(MAX_RED);
            if(num != 0){
                if(map.containsKey(num)){
                    continue;
                }else{
                    map.put(num,null);
                    result.add(num);
                    index++;
                }
            }else{
                continue;
            }
            if(index == MAX_RED_COUNT){
                break;
            }
        }
        return result;
    }

    /**
     * 生成蓝球
     * @return
     */
    private static List<Integer> createBlueBall(){
        List<Integer> result = new ArrayList<>();
        int index = 0;
        Map<Integer,Object> map = new HashMap<>();
        while(true){
            int num = createBall(MAX_BLUE);
            if(num != 0){
                if(map.containsKey(num)){
                    continue;
                }else{
                    map.put(num,null);
                    result.add(num);
                    index++;
                }
            }else{
                continue;
            }
            if(index == MAX_BLUE_COUNT){
                break;
            }
        }
        return result;
    }

    /**
     * 数字排序
     * @param balls
     * @return
     */
    private static List<Integer> sortBalls(List<Integer> balls){
        Collections.sort(balls);
        return balls;
    }

    /**
     * 格式化数字
     * @param balls
     * @return
     */
    private static List<String> formatBalls(List<Integer> balls){
        List<String> newBalls = new ArrayList<>();
        for(Integer ball : balls){
            String str = String.format("%02d",ball);
            newBalls.add(str);
        }
        return newBalls;
    }

    /**
     * 生成双色球
     * @return
     */
    public static Map<String,List<String>> createDoubleColorBallResult(){
        Map<String,List<String>> map = new HashMap<>();
        List<Integer> redBalls = createRedBall();
        List<Integer> blueBalls = createBlueBall();

        map.put(KEY_RED,formatBalls(redBalls));
        map.put(KEY_RED_SORT,formatBalls(sortBalls(redBalls)));
        map.put(KEY_BLUE,formatBalls(blueBalls));
        return map;
    }

    public static void main(String[] args) {
//        Random random = new Random();
//        for(int i=0;i<100;i++){
//            System.out.println(random.nextInt(MAX_RED +1));
//        }

//        String str = String.format("%02d",133);
//        System.out.println(str);

//        List<Integer> redBalls = DoubleColorBallUtil.createRedBall();
//        for(int i=0;i<redBalls.size();i++){
//            System.out.println(redBalls.get(i));
//        }
//        System.out.println("---------------------------");
//        List<Integer> blueBalls = DoubleColorBallUtil.createBlueBall();
//        for(int i=0;i<blueBalls.size();i++){
//            System.out.println(redBalls.get(i));
//        }

//        Map<String,List<String>> map = createDoubleColorBallResult();
    }

}
