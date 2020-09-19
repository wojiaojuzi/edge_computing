package com.ecs.controller;

import com.ecs.model.DeviceLog;
import com.ecs.service.AdminService;
import com.ecs.service.DeviceLogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Zhaoone on 2019/11/4
 **/

@RestController
@RequestMapping(path = "/logs")
@EnableAutoConfiguration
@Api(tags = "DevicesLog", description = "设备日志")
public class DeviceLogsController {
    private final DeviceLogsService deviceLogsService;
    private final AdminService adminService;

    @Autowired
    public DeviceLogsController(DeviceLogsService deviceLogsSerivce, AdminService adminService) {
        this.deviceLogsService = deviceLogsSerivce;
        this.adminService = adminService;
    }

    @ApiOperation(value = "获取全部设备日志")
    @RequestMapping(path = "/getLogs", method = RequestMethod.GET)
    public List<DeviceLog> getAllLogs(@RequestParam("token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        return deviceLogsService.getAllLogs();
    }



    @ApiOperation(value = "获取单个设备日志")
    @RequestMapping(path = "/getLog", method = RequestMethod.GET)
    public List<DeviceLog> getLogByDeviceNo(@RequestParam("deviceNo") String deviceNo, @RequestParam("token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        return deviceLogsService.getLogByDeviceNo(deviceNo);
    }
}
