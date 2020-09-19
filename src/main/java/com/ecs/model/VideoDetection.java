package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
public class VideoDetection {
    @ApiModelProperty(value = "唯一标识")
    private int id;

    @ApiModelProperty(value = "押解车编号")
    private String carNo;

//    @ApiModelProperty(value = "视频地址")
//    private String carVideoUri;

    @ApiModelProperty(value = "异常类型")
    private int abnormalType;

    @ApiModelProperty(value = "视频风险等级")
    private int videoRiskLevel;

    @ApiModelProperty(value = "记录时间")
    private Timestamp timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcarNo() {
        return carNo;
    }

    public void setcarNo(String carNo) {
        this.carNo = carNo;
    }

    public int getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(int abnormalType) {
        this.abnormalType = abnormalType;
    }

    public int getVideoRiskLevel() {
        return videoRiskLevel;
    }

    public void setVideoRiskLevel(int videoRiskLevel) {
        this.videoRiskLevel = videoRiskLevel;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp createAt) {
        this.timestamp = createAt;
    }


}
