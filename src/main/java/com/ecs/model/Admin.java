package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaoone on 2019/10/22
 **/
public class Admin {

    @ApiModelProperty(value = "管理员id")
    private String id;

    @ApiModelProperty(value = "管理员账号")
    private String uid;

    @ApiModelProperty(value = "管理员密码")
    private String password;

    @ApiModelProperty(value = "管理员姓名")
    private String adminName;

    @ApiModelProperty(value = "登录token")
    private String LoginToken;

    @ApiModelProperty(value = "登录token")
    private String tokenCreateAt;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "隶属部门")
    private String ministry;

    @ApiModelProperty(value = "座机电话")
    private String landline;

    @ApiModelProperty(value = "移动电话")
    private String phone;

    @ApiModelProperty(value = "E-mail")
    private String email;

    @ApiModelProperty(value = "相片地址")
    private String adminPhotoUri;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMinistry() {
        return ministry;
    }

    public void setMinistry(String ministry) {
        this.ministry = ministry;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminPhotoUri() {
        return adminPhotoUri;
    }

    public void setAdminPhotoUri(String adminPhotoUri) {
        this.adminPhotoUri = adminPhotoUri;
    }

    public String getTokenCreateAt() {
        return tokenCreateAt;
    }

    public void setTokenCreateAt(String tokenCreateAt) {
        this.tokenCreateAt = tokenCreateAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginToken() {
        return LoginToken;
    }

    public void setLoginToken(String loginToken) {
        this.LoginToken = loginToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
