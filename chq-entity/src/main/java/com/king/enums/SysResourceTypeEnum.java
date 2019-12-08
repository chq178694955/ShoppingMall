package com.king.enums;

public enum SysResourceTypeEnum implements BaseEnum<SysResourceTypeEnum,Integer> {

    MENU(0,"菜单"),
    TAB(1,"TAB页"),
    BUTTON(2,"按钮");

    private final int value;

    private final String description;

    static {
        subClass.add(SysResourceTypeEnum.class);
    }

    SysResourceTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDisplayName() {
        return description;
    }

    public static String getDesc(int age){
        for(SysResourceTypeEnum se : SysResourceTypeEnum.values()) {
            if (se.getValue() == age) {
                return se.getDisplayName();
            }
        }
        return null;
    }

}
