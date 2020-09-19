package com.ecs.model;

/**
 * Created by Zhaoone on 2019/11/11
 **/
public enum PoliceTypesEnum {

    CIVIL_POLICE(1, "民警"),
    SPECIAL_POLICE(2, "特警"),
    ARMED_POLICE(3, "武警"),
    PLAINCLOTHES_POLICE(4, "便衣警察"),
    LEADER(5, "领队");

    private Integer code;
    private String message;

    PoliceTypesEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
