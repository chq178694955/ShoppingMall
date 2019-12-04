package com.king.oauth.service.impl;

import com.king.dao.ClientMapper;
import com.king.oauth.service.AuthorizeService;
import com.king.sys.Oauth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public boolean checkClientId(String clientId) {
        Oauth2Client client = clientMapper.findByClientId(clientId);
        return client != null ? true : false;
    }

    @Override
    public void addAuthCode(String authCode, String username) {

    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return false;
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return false;
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        return null;
    }

    @Override
    public void addAccessToken(String accessToken, String username) {

    }

    @Override
    public long getExpireIn() {
        return 0;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return false;
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return null;
    }
}
