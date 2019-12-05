package com.king.oauth.service.impl;

import com.king.dao.ClientMapper;
import com.king.oauth.service.AuthorizeService;
import com.king.sys.Oauth2Client;
import com.king.utils.RedisUtil;
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

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean checkClientId(String clientId) {
        Oauth2Client client = clientMapper.findByClientId(clientId);
        return client != null ? true : false;
    }

    @Override
    public void addAuthCode(String authCode, String username) {
        redisUtil.set(authCode,username,5 * 60);//将code存入redis，保存5分钟
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        Oauth2Client client = clientMapper.findByClientSecret(clientSecret);
        return client != null ? true : false;
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        String username = (String)redisUtil.get(authCode);
        return username != null ? true : false;
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        String username = (String)redisUtil.get(authCode);
        return username;
    }

    @Override
    public void addAccessToken(String accessToken, String username) {
        redisUtil.set(accessToken,username, 30 * 60);//将accessToken保存redis，时间30分钟
    }

    @Override
    public long getExpireIn() {
        //设置过期时间30分钟
        return 30 * 60;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        String username = (String)redisUtil.get(accessToken);
        return username != null ? true : false;
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        String username = (String)redisUtil.get(accessToken);
        return username;
    }
}
