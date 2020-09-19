package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by Zhaoone on 2019/11/4
 **/
public class DeviceLog {

    @ApiModelProperty(value = "设备类型")
    private String deviceType ;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "使用者")
    private String userName;

    @ApiModelProperty(value = "押解犯人")
    private String prisonerName;

    @ApiModelProperty(value = "操作时间")
    private Timestamp dealTime;

    @ApiModelProperty(value = "操作内容")
    private String record;

    @ApiModelProperty(value = "设备编号")
    private String deviceNo;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public Timestamp getDealTime() {
        return dealTime;
    }

    public void setDealTime(Timestamp dealTime) {
        this.dealTime = dealTime;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
