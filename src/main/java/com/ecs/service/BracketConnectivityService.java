package com.ecs.service;

import com.ecs.mapper.BraceletMapper;
import com.ecs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Zhaoone on 2019/11/5
 **/
@Service
public class BracketConnectivityService {

    private final BraceletMapper braceletMapper;
    private final UserMapper userMapper;

    @Autowired
    public BracketConnectivityService(BraceletMapper braceletMapper, UserMapper userMapper) {
        this.braceletMapper = braceletMapper;
        this.userMapper = userMapper;
    }

    public Boolean getBracketConnectivity(String uid){
        return braceletMapper.getDeviceStatusByUid(uid);
    }

    public String getUidByUserName(String UserName){
        return userMapper.getUserIdByUserName(UserName);
    }
}
