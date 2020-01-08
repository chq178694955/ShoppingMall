package com.king.enums;

/**
 * @创建人 chq
 * @创建时间 2019/12/26
 * @描述
 */
public enum TaskEnum {

    READY(0,"准备"),
    START(1,"开始"),
    PAUSE(2,"暂停"),
    RESUME(3,"恢复"),
    FINISH(4,"结束");

    private final int value;

    private final String description;

    private TaskEnum(int value,String description){
        this.value = value;
        this.description = description;
    }

    public int getValue(){
        return value;
    }

    public String getDisplayName(){
        return description;
    }

    public static String getDesc(int val){
        for(TaskEnum t : TaskEnum.values()) {
            if (t.getValue() == val) {
                return t.getDisplayName();
            }
        }
        return null;
    }


}
