package com.king.oauth.controller;

import com.king.base.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/10
 * @描述
 */
public class BaseController {

    protected Page getPage(HttpServletRequest request){
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer rows = Integer.parseInt(request.getParameter("rows"));
        return new Page(page,rows);
    }

    protected <T> Map<String, Object> getGridData(Page<T> page) {
        Map<String, Object> map = new HashMap();
        map.put("rows", page.getDatas());
        map.put("total", page.getTotalCount());
        map.put("footer", page.getSummary());
        if (page.getTotalCount() == -1L) {
            int total = (page.getPageNo() - 1) * page.getPageSize() + (page.getDatas() != null ? page.getDatas().size() : 0);
            if (page.getDatas() != null && page.getDatas().size() == page.getPageSize()) {
                ++total;
            }

            map.put("total", total);
        }

        return map;
    }

}
