package com.ecs.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaoone on 2019/4/2
 **/
public class NetworkConditions {

    @ApiModelProperty(value = "网络时延")
    private String delay;

    @ApiModelProperty(value = "网络丢包率")
    private String packet_loss;

    public String getDelay() {
        return delay;
    }

    public String getPacket_loss() {
        return packet_loss;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public void setPacket_loss(String packet_loss) {
        this.packet_loss = packet_loss;
    }

    @Override
    public String toString() {
        return "Network: {delay: " + delay + ", packet_loss: " + packet_loss + "}";
    }

}
