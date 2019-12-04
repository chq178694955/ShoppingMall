package com.king.dao;

import com.king.sys.Oauth2Client;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
public interface ClientMapper {

    /** 根据clientId查询Client信息 */
    Oauth2Client findByClientId(String clientId);
    /** 根据clientSecret查询client信息 */
    Oauth2Client findByClientSecret(String clientSecret);

}
