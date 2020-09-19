package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by Zhaoone on 2019/10/31
 **/
public class AbnormalCondition {

    @ApiModelProperty(value = "异常id")
    private String id;

    @ApiModelProperty(value = "犯人id")
    private String prisonerId;

    @ApiModelProperty(value = "犯人姓名")
    private String prisonerName;

    @ApiModelProperty(value = "记录时间")
    private Timestamp createAt;

    @ApiModelProperty(value = "所属车辆")
    private String carNo;

    @ApiModelProperty(value = "异常处理情况")
    private String dealState;

    @ApiModelProperty(value = "风险数值")
    private String riskValue;

    @ApiModelProperty(value = "误报情况")
    private Boolean misdeclaration;

    @ApiModelProperty(value = "详情")
    private String comment;

    public void setMisdeclaration(Boolean misdeclaration) {
        this.misdeclaration = misdeclaration;
    }

    public Boolean getMisdeclaration() {
        return misdeclaration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(String riskValue) {
        this.riskValue = riskValue;
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getDealState() {
        return dealState;
    }

    public void setDealState(String dealState) {
        this.dealState = dealState;
    }
}
