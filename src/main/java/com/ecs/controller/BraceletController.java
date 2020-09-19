//package com.ecs.controller;
//
//import com.ecs.service.BraceletService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by Zhaoone on 2019/11/4
// **/
//@RestController
//@RequestMapping(path = "/bracelets")
//@EnableAutoConfiguration
//@Api(tags = "Bracelet", description = "手环模拟操作")
//public class BraceletController {
//    private final BraceletService braceletService;
//
//    @Autowired
//    public BraceletController(BraceletService braceletService) {
//        this.braceletService = braceletService;
//    }
//
//    @ApiOperation(value = "修改手环状态")
//    @RequestMapping(path = "/updateBraceletStatus", method = RequestMethod.GET)
//    public void getByDeviceNo(@RequestParam("deviceStatus") Boolean deviceStatus,
//                              @RequestParam("braceletNo") String braceletNo){
//        braceletService.updateBraceletStatus(deviceStatus, braceletNo);
//    }
//
//}
