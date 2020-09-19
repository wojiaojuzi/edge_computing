package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

public class Device {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "设备编号")
    private String deviceNo;

    @ApiModelProperty(value = "设备连接状态")
    private boolean deviceStatus;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "设备创建时间")
    private Timestamp createAt;

    @ApiModelProperty(value = "所属用户信息")
    private User user;

    @ApiModelProperty(value = "所属用户id")
    private String uid;

    @ApiModelProperty(value = "设备是否连接手环")
    private Boolean deviceConnectivityStatus;

    public Boolean getDeviceConnectivityStatus() {
        return deviceConnectivityStatus;
    }

    public void setDeviceConnectivityStatus(Boolean deviceConnectivityStatus) {
        this.deviceConnectivityStatus = deviceConnectivityStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public void setDeviceStatus(boolean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public boolean isDeviceStatus() {
        return deviceStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Device{" + "device_no=" + deviceNo + ", deviceStatus=" + deviceStatus + ", createAt=" + createAt + "}";
    }

}
