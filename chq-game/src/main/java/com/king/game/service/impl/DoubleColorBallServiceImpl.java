package com.king.game.service.impl;

import com.king.game.service.IDoubleColorBallService;
import com.king.utils.DoubleColorBallUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2020/1/8
 * @描述
 */
@Service
public class DoubleColorBallServiceImpl implements IDoubleColorBallService {

    @Override
    public Map<String, List<String>> getOne() {
        return DoubleColorBallUtil.createDoubleColorBallResult();
    }

}
