package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaoone on 2019/10/25
 **/
public class PrisonerData {

    @ApiModelProperty(value = "犯人id")
    private String prisonerId;

    @ApiModelProperty(value = "犯人心跳")
    private String heart_rate;

    @ApiModelProperty(value = "用户风险值")
    private String risk_level;

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }
}
