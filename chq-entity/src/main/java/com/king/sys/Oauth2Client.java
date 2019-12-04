package com.king.sys;

import java.io.Serializable;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
public class Oauth2Client implements Serializable {

    private static final long serialVersionUID = -4235768392151543633L;

    private Long id;

    private String clientName;

    private String clientId;

    private String clientSecret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
