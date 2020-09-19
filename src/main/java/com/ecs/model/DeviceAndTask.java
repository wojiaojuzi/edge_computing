package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaoone on 2019/10/24
 **/
public class DeviceAndTask {
    @ApiModelProperty(value = "手持机信息")
    private Device device;

    @ApiModelProperty(value = "手环信息")
    private Vervel vervel;

    @ApiModelProperty(value = "脚环信息")
    private Bracelet bracelet;

    @ApiModelProperty(value = "任务信息")
    private Task task;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Vervel getVervel() {
        return vervel;
    }

    public void setVervel(Vervel vervel) {
        this.vervel = vervel;
    }

    public Bracelet getBracelet() {
        return bracelet;
    }

    public void setBracelet(Bracelet bracelet) {
        this.bracelet = bracelet;
    }
}
