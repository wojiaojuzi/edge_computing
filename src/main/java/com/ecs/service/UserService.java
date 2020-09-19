package com.ecs.service;

        import com.alibaba.fastjson.JSONObject;
        import com.auth0.jwt.interfaces.DecodedJWT;
        import com.ecs.mapper.PrisonerMapper;
        import com.ecs.mapper.UserMapper;
        import com.ecs.model.Exception.EdgeComputingServiceException;
        import com.ecs.model.Request.LoginRequest;
        import com.ecs.model.Response.ResponseEnum;
        import com.ecs.model.User;
        import com.ecs.utils.CommonUtil;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.text.SimpleDateFormat;
        import java.util.*;

/**
 * @Author: zhaoone
 * @Description:添加功能
 * @Date: Created on 2019/10/14
 */
@Service
public class UserService {

    private static final String mysqlSdfPatternString = "yyyy-MM-dd HH:mm:ss";

    private final UserMapper userMapper;
    private final PrisonerMapper prisonerMapper;

    @Autowired
    public UserService(UserMapper userMapper, PrisonerMapper prisonerMapper) {
        this.userMapper = userMapper;
        this.prisonerMapper = prisonerMapper;
    }

    public User getById(String id) {
        return userMapper.getById(id);
    }

    public User getByUserName(String userName) {
        return userMapper.getByUserName(userName);
    }

    public User userLogin(LoginRequest loginRequest) throws Exception {
        SimpleDateFormat mysqlSdf = new java.text.SimpleDateFormat(mysqlSdfPatternString);
        User user = userMapper.getByUserIdAndPassword(loginRequest.getUserName(), loginRequest.getPassword());
        if(user == null) {
            throw new EdgeComputingServiceException(ResponseEnum.LOGIN_FAILED.getCode(), ResponseEnum.LOGIN_FAILED.getMessage());
        }else {
            //采用jwt获得token
            Date createTime = new Date();
            //每七天需要重新登录(可以直接将过期时间写入token，解析token时即可判断是否过期，而无需在代码中判断)
            Date expireTime = new Date(createTime.getTime() + 1000 * 60 * 60 * 24 * 7);
            Map<String, String> content = new HashMap<>();
            content.put("uid", user.getId());
            String token = CommonUtil.createJWT(content, "EdgeComputingService", createTime, expireTime);
            //更新数据库token_create_at，之后每次鉴权需要查看数据库查看自己的token是否是最新的
            userMapper.updateTokenCreateTimeById(mysqlSdf.format(createTime), user.getId());

//            /*更新prisonerId mapper
//             * 2019/10/14
//             */
//            userMapper.updatePrisonerIdById(loginRequest.getPrisonerId(), user.getId());
//            userMapper.updatePrisonerNameById(prisoner.getPrisonerName(), user.getId());

            /*
             *  更新deviceMapper，在登陆时写入用户和犯人姓名字段
             */
//            deviceMapper.updateUserName(loginRequest.getUserName(),deviceNo);
//            String prisonerName = prisonerMapper.getPrisonerNameByPrisonerId(loginRequest.getPrisonerId()).getPrisonerName();
//            deviceMapper.updatePrisonerName(prisonerName,deviceNo);

            //将token返回作为登录凭证
            user.setLoginToken(token);
            user.setPassword(null);
            user.setTokenCreateAt(null);
        }
        return user;
    }

    public void userLogout(String token) throws Exception {
        String userId = getUserIdFromToken(token);
        userMapper.updateTokenCreateTimeById(null, userId);

//        /*更新prisonerId mapper
//         * 2019/10/14
//         */
//        userMapper.updatePrisonerIdById(null, userId);
//        userMapper.updatePrisonerNameById(null, userId);
        /*
         *  更新deviceMapper，在登出时
         */
//        deviceMapper.updateUserName(null,deviceNo);
//        deviceMapper.updatePrisonerName(null,deviceNo);
    }

    public void createUser(User user) {
        userMapper.createUser(user);
    }

    public String getUserIdFromToken(String token) throws Exception {
        SimpleDateFormat mysqlSdf = new java.text.SimpleDateFormat(mysqlSdfPatternString);
        if (Objects.equals(token, "noToken")) {
            throw new EdgeComputingServiceException(ResponseEnum.DO_NOT_LOGIN.getCode(), ResponseEnum.DO_NOT_LOGIN.getMessage());
        }
        Date now = new Date();
        DecodedJWT jwt = CommonUtil.phraseJWT(token, "EdgeComputingService", ResponseEnum.INVALID_USER_TOKEN.getMessage());
        String userId = JSONObject.parseObject(jwt.getSubject()).getString("uid");
        String tokenCreateTime = userMapper.getTokenCreateTime(userId);
        //数据库中token创建时间字段为空，说明用户已经注销登陆
        if (tokenCreateTime == null)
            throw new EdgeComputingServiceException(ResponseEnum.DO_NOT_LOGIN.getCode(), ResponseEnum.DO_NOT_LOGIN.getMessage());
        else if (jwt.getIssuedAt().getTime() != mysqlSdf.parse(tokenCreateTime).getTime())
            throw new EdgeComputingServiceException(ResponseEnum.ALREADY_LOGIN.getCode(), ResponseEnum.ALREADY_LOGIN.getMessage());
        else if (jwt.getExpiresAt().getTime() < now.getTime())
            throw new EdgeComputingServiceException(ResponseEnum.EXPIRED_USER_TOKEN.getCode(), ResponseEnum.EXPIRED_USER_TOKEN.getMessage());
        else return userId;
    }

    public List<User> getAll(){
        return userMapper.getAll();
    }

    public User getByUserId(String userId){
        return userMapper.getByUserId(userId);
    }
}
