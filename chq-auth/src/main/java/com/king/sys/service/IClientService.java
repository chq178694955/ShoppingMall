package com.king.sys.service;

import com.king.base.Page;
import com.king.sys.Oauth2Client;

import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/9
 * @描述
 */
public interface IClientService {

    Oauth2Client getClientById(Long id);

    Oauth2Client getClientByKey(String key);

    Oauth2Client getClientBySecret(String secret);

    Long addClient(Oauth2Client client);

    Long modifyClient(Oauth2Client client);

    void delClient(Long id);

    Page<Oauth2Client> find(Page<Oauth2Client> page, Map<String,String> params);

}
