package com.king.enums;

public enum SexEnum implements BaseEnum<SexEnum,Integer> {

    MAN(1,"男"),
    WOM(2,"女"),
    NON(0,"未知");

    private final int value;

    private final String description;

    static {
        subClass.add(SexEnum.class);
    }

    SexEnum(int value, String description) {
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
        for(SexEnum se : SexEnum.values()) {
            if (se.getValue() == age) {
                return se.getDisplayName();
            }
        }
        return null;
    }

}
