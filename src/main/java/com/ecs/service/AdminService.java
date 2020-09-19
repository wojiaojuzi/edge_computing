package com.ecs.service;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecs.mapper.AdminMapper;
import com.ecs.model.Admin;
import com.ecs.model.Exception.EdgeComputingServiceException;
import com.ecs.model.Response.ResponseEnum;
import com.ecs.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Zhaoone on 2019/10/22
 **/
@Service
public class AdminService {
    private static final String mysqlSdfPatternString = "yyyy-MM-dd HH:mm:ss";

    private final AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper, UserService userService) {
        this.adminMapper = adminMapper;
    }

    public Admin userLogin(String adminId, String password) throws Exception {
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        Admin admin = adminMapper.getByAdminIdAndPassword(adminId, password);
        if(admin == null) {
            throw new EdgeComputingServiceException(ResponseEnum.LOGIN_FAILED.getCode(), ResponseEnum.LOGIN_FAILED.getMessage());
        } else {
            //采用jwt获得token
            Date createTime = new Date();
            //每七天需要重新登录(可以直接将过期时间写入token，解析token时即可判断是否过期，而无需在代码中判断)
            Date expireTime = new Date(createTime.getTime() + 1000 * 60 * 60 * 24 * 7);
            Map<String, String> content = new HashMap<>();
            content.put("uid", admin.getId());
            String token = CommonUtil.createJWT(content, "EdgeComputingService", createTime, expireTime);
            //更新数据库token_create_at，之后每次鉴权需要查看数据库查看自己的token是否是最新的
            adminMapper.updateTokenCreateTimeById(mysqlSdf.format(createTime), admin.getId());
            //将token返回作为登录凭证
            admin.setLoginToken(token);
            admin.setPassword(null);
            admin.setTokenCreateAt(null);
        }
        return admin;
    }

    public void userLogout(String token) throws Exception {
        String adminId = getUserIdFromToken(token);
        adminMapper.updateTokenCreateTimeById(null, adminId);
    }

    public Admin aboutInformation(String adminId){
        return adminMapper.getByadminId(adminId);
    }

    public String getUserIdFromToken(String token) throws Exception {
        SimpleDateFormat mysqlSdf = new java.text.SimpleDateFormat(mysqlSdfPatternString);
        if (Objects.equals(token, "noToken")) {
            throw new EdgeComputingServiceException(ResponseEnum.DO_NOT_LOGIN.getCode(), ResponseEnum.DO_NOT_LOGIN.getMessage());
        }
        Date now = new Date();
        DecodedJWT jwt = CommonUtil.phraseJWT(token, "EdgeComputingService", ResponseEnum.INVALID_USER_TOKEN.getMessage());
        String adminId = JSONObject.parseObject(jwt.getSubject()).getString("uid");
        String tokenCreateTime = adminMapper.getTokenCreateTime(adminId);
        //数据库中token创建时间字段为空，说明用户已经注销登陆
        if (tokenCreateTime == null)
            throw new EdgeComputingServiceException(ResponseEnum.DO_NOT_LOGIN.getCode(), ResponseEnum.DO_NOT_LOGIN.getMessage());
        else if (jwt.getIssuedAt().getTime() != mysqlSdf.parse(tokenCreateTime).getTime())
            throw new EdgeComputingServiceException(ResponseEnum.ALREADY_LOGIN.getCode(), ResponseEnum.ALREADY_LOGIN.getMessage());
        else if (jwt.getExpiresAt().getTime() < now.getTime())
            throw new EdgeComputingServiceException(ResponseEnum.EXPIRED_USER_TOKEN.getCode(), ResponseEnum.EXPIRED_USER_TOKEN.getMessage());
        else return adminId;
    }
}
