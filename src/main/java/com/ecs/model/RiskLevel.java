package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by Zhaoone on 2019/10/31
 **/
public class RiskLevel {

    @ApiModelProperty(value = "记录id")
    private String id;

    @ApiModelProperty(value = "犯人id")
    private String prisonerId;

    @ApiModelProperty(value = "记录时间")
    private Timestamp createAt;

    @ApiModelProperty(value = "风险等级")
    private String riskValue;

    @ApiModelProperty(value = "处理情况")
    private String dealState;

    @ApiModelProperty(value = "是否错报")
    private Boolean misdeclaration;

    @ApiModelProperty(value = "详情")
    private String comment;

    @ApiModelProperty(value = "是否高危风险")
    private Boolean highRiskSign;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(String riskValue) {
        this.riskValue = riskValue;
    }

    public String getDealState() {
        return dealState;
    }

    public void setDealState(String dealState) {
        this.dealState = dealState;
    }

    public Boolean getMisdeclaration() {
        return misdeclaration;
    }

    public void setMisdeclaration(Boolean misdeclaration) {
        this.misdeclaration = misdeclaration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getHighRiskSign() {
        return highRiskSign;
    }

    public void setHighRiskSign(Boolean highRiskSign) {
        this.highRiskSign = highRiskSign;
    }
}
