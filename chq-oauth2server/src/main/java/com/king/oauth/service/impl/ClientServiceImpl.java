package com.king.oauth.service.impl;

import com.king.dao.ClientMapper;
import com.king.oauth.service.ClientService;
import com.king.sys.Oauth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public Oauth2Client findByClientId(String clientId) {
        return clientMapper.findByClientId(clientId);
    }

    @Override
    public Oauth2Client findByClientSecret(String clientSecret) {
        return clientMapper.findByClientSecret(clientSecret);
    }
}
