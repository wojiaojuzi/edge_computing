package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by Zhaoone on 2019/11/5
 **/
public class DeviceRunInfo {

    @ApiModelProperty(value = "设备编号")
    private String deviceNo;

    @ApiModelProperty(value = "记录时间")
    private Timestamp uploadTime;

    @ApiModelProperty(value = "cpu资源利用率")
    private String cpuUsageRate;

    @ApiModelProperty(value = "内存资源利用率")
    private String memoryUsageRate;

    @ApiModelProperty(value = "剩余电量百分比")
    private String dumpEnergyRate;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCpuUsageRate() {
        return cpuUsageRate;
    }

    public void setCpuUsageRate(String cpuUsageRate) {
        this.cpuUsageRate = cpuUsageRate;
    }

    public String getMemoryUsageRate() {
        return memoryUsageRate;
    }

    public void setMemoryUsageRate(String memoryUsageRate) {
        this.memoryUsageRate = memoryUsageRate;
    }

    public String getDumpEnergyRate() {
        return dumpEnergyRate;
    }

    public void setDumpEnergyRate(String dumpEnergyRate) {
        this.dumpEnergyRate = dumpEnergyRate;
    }
}
