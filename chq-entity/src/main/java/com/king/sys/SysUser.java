package com.king.sys;

import com.king.enums.AccountStateEnum;

import java.io.Serializable;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 6485138304347447022L;

    private Long id;

    private String username;

    private String password;

    private String name;

    private String idCardNum;

    private AccountStateEnum accountStateEnum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public AccountStateEnum getAccountStateEnum() {
        return accountStateEnum;
    }

    public void setAccountStateEnum(AccountStateEnum accountStateEnum) {
        this.accountStateEnum = accountStateEnum;
    }
}
