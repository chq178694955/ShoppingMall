package com.king.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.king.base.Page;
import com.king.dao.ClientMapper;
import com.king.sys.Oauth2Client;
import com.king.sys.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/9
 * @描述
 */
@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public Oauth2Client getClientById(Long id) {
        return null;
    }

    @Override
    public Oauth2Client getClientByKey(String key) {
        return clientMapper.findByClientId(key);
    }

    @Override
    public Oauth2Client getClientBySecret(String secret) {
        return clientMapper.findByClientSecret(secret);
    }

    @Override
    public Long addClient(Oauth2Client client) {
        clientMapper.add(client);
        return client.getId();
    }

    @Override
    public Long modifyClient(Oauth2Client client) {
        clientMapper.update(client);
        return client.getId();
    }

    @Override
    public void delClient(Long id) {
        clientMapper.del(id);
    }

    @Override
    public Page<Oauth2Client> find(Page<Oauth2Client> page, Map<String, String> params) {
        //分页设置放在查询之前
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<Oauth2Client> list = clientMapper.find(params);
        Long totalCount = clientMapper.findCount(params);
        page.setResults(list,totalCount);
        return page;
    }
}
