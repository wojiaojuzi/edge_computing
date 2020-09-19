package com.ecs.controller;

import com.ecs.model.Request.UserRegisterRequest;
import com.ecs.model.Response.HttpResponseContent;
import com.ecs.model.Request.LoginRequest;
import com.ecs.model.Response.ResponseEnum;
import com.ecs.model.User;
import com.ecs.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: zhaoone
 * @Description:添加功能
 * @Date: Created on 2019/10/14
 */
@RestController
@RequestMapping(path = "/users")
@EnableAutoConfiguration
@Api(tags = "User", description = "用户相关的操作")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @ApiOperation(value = "获取用户信息")
//    @RequestMapping(path = "/get", method = RequestMethod.GET)
//    public HttpResponseContent getById(@RequestParam("id") String id) {
//        HttpResponseContent response = new HttpResponseContent();
//        User user = userService.getById(id);
//        if(user == null) {
//            response.setCode(ResponseEnum.NO_CONTENT.getCode());
//            response.setMessage(ResponseEnum.NO_CONTENT.getMessage());
//        } else {
//            response.setCode(ResponseEnum.SUCCESS.getCode());
//            response.setMessage(ResponseEnum.SUCCESS.getMessage());
//            response.setData(user);
//        }
//        return response;
//    }

    @ApiOperation(value = "用户登陆")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public HttpResponseContent userLogin(@RequestParam("userName") String userName,
                                         @RequestParam("password") String password) throws Exception {
        HttpResponseContent response = new HttpResponseContent();
        LoginRequest loginRequest = new LoginRequest(userName, password);
        User user = userService.userLogin(loginRequest);
        if(user == null) {
            response.setCode(ResponseEnum.LOGIN_FAILED.getCode());
            response.setMessage(ResponseEnum.LOGIN_FAILED.getMessage());
        } else {
            response.setCode(ResponseEnum.SUCCESS.getCode());
            response.setMessage(ResponseEnum.SUCCESS.getMessage());
            response.setData(user);
        }
        return response;
    }

    @ApiOperation(value = "用户注销登录")
    @RequestMapping(path = "/logout", method = RequestMethod.DELETE)
    public HttpResponseContent userLogout(@RequestParam(value = "token", defaultValue = "noToken") String token) throws Exception {
        String userId = userService.getUserIdFromToken(token);
        HttpResponseContent response = new HttpResponseContent();
        userService.userLogout(token);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        return response;
    }

    @ApiOperation(value = "新用户注册")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public HttpResponseContent createUser(@RequestParam("userId") String userId,
                                          @RequestParam("password") String password,
                                          @RequestParam("confirmPwd") String confirmPwd,
                                          @RequestParam("userName") String userName,
                                          @RequestParam("idCard") String idCard) {
        HttpResponseContent response = new HttpResponseContent();
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(userName, password, confirmPwd, userId, idCard);
        if(userService.getByUserName(userRegisterRequest.getUserName()) != null) {
            response.setCode(ResponseEnum.USERNAME_EXIST.getCode());
            response.setMessage(ResponseEnum.USERNAME_EXIST.getMessage());
        } else if(userRegisterRequest.getPassword().length() < 6) {
            response.setCode(ResponseEnum.PASSWORD_TOO_SHORT.getCode());
            response.setMessage(ResponseEnum.PASSWORD_TOO_SHORT.getMessage());
        } else if(!userRegisterRequest.getPassword().equals(userRegisterRequest.getConfirmPassword())) {
            response.setCode(ResponseEnum.PASSWORD_CONFIRM_ERROR.getCode());
            response.setMessage(ResponseEnum.PASSWORD_CONFIRM_ERROR.getMessage());
        } else {
            User user = new User();
            user.setUserName(userRegisterRequest.getUserName());
            user.setPassword(userRegisterRequest.getPassword());
            user.setUserId(userRegisterRequest.getUserId());
            user.setIdCard(userRegisterRequest.getIdCard());
            userService.createUser(user);
            response.setCode(ResponseEnum.SUCCESS.getCode());
            response.setMessage(ResponseEnum.SUCCESS.getMessage());
            response.setData(user);
        }
        return response;
    }

}
