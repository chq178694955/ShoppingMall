package com.king.oauth.service;


import com.king.sys.Oauth2Client;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
public interface ClientService {
    /** 根据clientId查询Client信息 */
    public Oauth2Client findByClientId(String clientId);
    /** 根据clientSecret查询client信息 */
    public Oauth2Client findByClientSecret(String clientSecret);
}
