package com.king.enums;

public enum AccountStateEnum implements BaseEnum<AccountStateEnum,Integer> {

    NORMAL(0,"正常"),
    LOCK(1,"锁定");

    private final int value;

    private final String description;

    static {
        subClass.add(AccountStateEnum.class);
    }

    AccountStateEnum(int value, String description) {
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
        for(AccountStateEnum se : AccountStateEnum.values()) {
            if (se.getValue() == age) {
                return se.getDisplayName();
            }
        }
        return null;
    }

}
