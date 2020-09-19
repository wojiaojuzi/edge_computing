package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by DFL on 2019/11/17.
 */
public class PrisonerOriginalData
{
    @ApiModelProperty(value = "犯人id")
    private String prisonerId;

    @ApiModelProperty(value = "数据时间")
    private Timestamp uploadTime;

    @ApiModelProperty(value = "犯人心跳")
    private String heartbeat;

    @ApiModelProperty(value = "犯人高度")
    private String height;

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(String heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
