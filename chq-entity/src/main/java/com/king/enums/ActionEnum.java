package com.king.enums;

public enum ActionEnum implements BaseEnum<ActionEnum,Integer> {

    QUERY(1,"查询"),
    ADD(2,"新增"),
    UPDATE(3,"修改"),
    DELETE(4,"删除");

    private final int value;

    private final String description;

    static{
        subClass.add(ActionEnum.class);
    }

    ActionEnum(int value,String description){
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDisplayName() {
        return this.description;
    }

    public static String getDesc(int value){
        for(ActionEnum se : ActionEnum.values()){
            if(se.getValue() == value){
                return se.getDisplayName();
            }
        }
        return null;
    }

    public static ActionEnum getDesc(String value){
        for(ActionEnum se : ActionEnum.values()){
            if(se.toString().toLowerCase().equals(value)){
                return se;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(ActionEnum.DELETE);
        System.out.println(ActionEnum.getDesc("add"));
    }
}
