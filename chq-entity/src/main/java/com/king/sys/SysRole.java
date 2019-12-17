package com.king.sys;

import java.util.Set;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public class SysRole {

    private Long id;

    private String name;

    private Set<Long> resourceIds;

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

    public Set<Long> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
