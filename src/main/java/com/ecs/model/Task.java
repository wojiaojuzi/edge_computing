package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaoone on 2019/10/22
 **/
public class Task {

    @ApiModelProperty(value = "任务编号")
    private String taskNo;

    @ApiModelProperty(value = "车牌号")
    private String carNo;

    @ApiModelProperty(value = "犯人姓名")
    private String prisonerName;

    @ApiModelProperty(value = "押解人姓名")
    private String userName;

    @ApiModelProperty(value = "任务等级")
    private String level;

    @ApiModelProperty(value = "任务详情")
    private String detail;

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
