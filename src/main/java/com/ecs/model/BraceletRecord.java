package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaoone on 2019/11/5
 **/
public class BraceletRecord {

    @ApiModelProperty(value = "犯人id")
    private String PrisonerId;

    @ApiModelProperty(value = "手环数据")
    private String record;

    public String getPrisonerId() {
        return PrisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        PrisonerId = prisonerId;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
