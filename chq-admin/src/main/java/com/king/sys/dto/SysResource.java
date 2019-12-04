package com.king.sys.dto;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public class SysResource {

    private Long id;

    private String name;

    private String permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
