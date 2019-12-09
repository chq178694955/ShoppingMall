package com.king.sys.service;

import com.king.sys.Oauth2Client;

/**
 * @创建人 chq
 * @创建时间 2019/12/9
 * @描述
 */
public interface IClientService {

    Oauth2Client getClientById(Long id);

    Oauth2Client getClientBySecret(String secret);

    Long addClient(Oauth2Client client);

    Long modifyClient(Oauth2Client client);

    void delClient(Long id);

}
