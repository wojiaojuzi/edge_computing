package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaoone on 2019/10/21
 **/
public class Car {
    @ApiModelProperty(value = "押解车编号")
    private String carNo;

    @ApiModelProperty(value = "任务编号")
    private String taskNo;

    @ApiModelProperty(value = "押解车型号")
    private String type;

    @ApiModelProperty(value = "押解车颜色")
    private String color;

    @ApiModelProperty(value = "车内警察人数")
    private String userSum;

    @ApiModelProperty(value = "押解车等级")
    private String level;

    @ApiModelProperty(value = "车内犯人人数")
    private String prisonerSum;

    @ApiModelProperty(value = "相片地址")
    private String CarPhotoUri;

    @ApiModelProperty(value = "视频地址")
    private String CarVideoUri;

    public String getCarVideoUri() {
        return CarVideoUri;
    }

    public void setCarVideoUri(String carVideoUri) {
        CarVideoUri = carVideoUri;
    }

    public String getCarPhotoUri() {
        return CarPhotoUri;
    }

    public void setCarPhotoUri(String carPhotoUri) {
        CarPhotoUri = carPhotoUri;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUserSum() {
        return userSum;
    }

    public void setUserSum(String userSum) {
        this.userSum = userSum;
    }

    public String getPrisonerSum() {
        return prisonerSum;
    }

    public void setPrisonerSum(String prisonerSum) {
        this.prisonerSum = prisonerSum;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
