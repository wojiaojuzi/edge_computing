package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaoone on 2019/11/4
 **/
public class Bracelet {

    @ApiModelProperty(value = "手环编号")
    private String braceletNo;

    @ApiModelProperty(value = "设备状态")
    private Boolean deviceStatus;

    @ApiModelProperty(value = "绑定手持机编号")
    private String deviceNo;

    @ApiModelProperty(value = "绑定犯人id")
    private String prisonerId;

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getBraceletNo() {
        return braceletNo;
    }

    public void setBraceletNo(String braceletNo) {
        this.braceletNo = braceletNo;
    }

    public Boolean getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Boolean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
