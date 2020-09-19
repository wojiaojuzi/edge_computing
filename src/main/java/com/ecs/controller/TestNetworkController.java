package com.ecs.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecs.service.TestNetworksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


/**
 * Created by Zhaoone on 2019/4/16
 **/

@Controller
@EnableAutoConfiguration
@Api(tags = "NetworkConditions", description = "查询当前网络状况")
public class TestNetworkController {
    private final TestNetworksService testNetworksService;

    @Autowired
    public TestNetworkController(TestNetworksService testNetworksService) {
        this.testNetworksService = testNetworksService;
    }


    @ApiOperation(value = "获得丢包时延")
    @RequestMapping(path = "/networkCondition",method = RequestMethod.GET)
    @ResponseBody
    public String networksCon(@RequestHeader(value="token") String token) throws IOException {
        JSONObject json = new JSONObject();
        json.put("delay",testNetworksService.getNetworkDelay());
//        json.put("packet_loss",testNetworksService.getNetworkPacketLoss());
        return json.toJSONString();
    }
}
