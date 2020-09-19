package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaoone on 2019/10/25
 **/
public class PrisonerAndTask {

    @ApiModelProperty(value = "犯人")
    private Prisoner prisoner;

    @ApiModelProperty(value = "所属任务")
    private Task task;

    public Prisoner getPrisoner() {
        return prisoner;
    }

    public void setPrisoner(Prisoner prisoner) {
        this.prisoner = prisoner;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
