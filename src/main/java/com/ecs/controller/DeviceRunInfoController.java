package com.ecs.controller;

import com.ecs.model.Device;
import com.ecs.model.DeviceRunInfo;
import com.ecs.service.DeviceRunInfoService;
import com.ecs.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhaoone on 2019/11/4
 **/
@RestController
@RequestMapping(path = "/deviceRunInfo")
@EnableAutoConfiguration
@Api(tags = "DeviceRunInfo", description = "设备资源信息")
public class DeviceRunInfoController {
    private final DeviceRunInfoService deviceRunInfoService;
    private final DeviceService deviceService;

    @Autowired
    public DeviceRunInfoController(DeviceRunInfoService deviceRunInfoService, DeviceService deviceService) {
        this.deviceRunInfoService = deviceRunInfoService;
        this.deviceService = deviceService;
    }

    @ApiOperation(value = "上传设备信息")
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadByDeviceNo(@RequestParam("deviceNo") String deviceNo,
                              @RequestParam("cpuUsageRate") String cpuUsageRate,
                              @RequestParam("memoryUsageRate") String memoryUsageRate,
                              @RequestParam("dumpEnergyRate") String dumpEnergyRate,
                              @RequestParam("token") String token) {
        Timestamp uploadTime = new Timestamp(new Date().getTime());
        DeviceRunInfo deviceRunInfo = new DeviceRunInfo();
        deviceRunInfo.setDeviceNo(deviceNo);
        deviceRunInfo.setCpuUsageRate(cpuUsageRate);
        deviceRunInfo.setDumpEnergyRate(dumpEnergyRate);
        deviceRunInfo.setMemoryUsageRate(memoryUsageRate);
        deviceRunInfo.setUploadTime(uploadTime);
        deviceRunInfoService.insertDeviceRunInfo(deviceRunInfo);
        return "上传成功";
    }

//    @ApiOperation(value = "获取最新的设备信息")
//    @RequestMapping(path = "/latestRunInfo", method = RequestMethod.GET)
//    public Object getByDeviceNo(@RequestParam("deviceNo") String deviceNo){
//        Boolean device_status = deviceService.getDeviceStatusByDeviceNo(deviceNo);
//        if(device_status == true)
//            return deviceRunInfoService.getLastestDeviceRunInfoByDeviceNo(deviceNo);
//        else return "设备断开连接";
//    }
//
    @ApiOperation(value = "获取最新的设备信息")
    @RequestMapping(path = "/latestRunInfo", method = RequestMethod.GET)
    public Object getByDeviceNo(@RequestParam("deviceNo") String deviceNo, @RequestHeader(value="token") String token){
            return deviceRunInfoService.getLastestDeviceRunInfoByDeviceNo(deviceNo);
    }

    @ApiOperation(value = "获取全部设备信息")
    @RequestMapping(path = "/getAllRunInfo", method = RequestMethod.GET)
    public List<DeviceRunInfo> getAllRunInfo(@RequestHeader(value="token") String token){
        List<Device> devices = deviceService.getAll();
        List<DeviceRunInfo> deviceRunInfoList = new ArrayList<>();
        for(int i = 0; i < devices.size(); i++){
            deviceRunInfoList.add(deviceRunInfoService.getLastestDeviceRunInfoByDeviceNo(devices.get(i).getDeviceNo()));
        }
        return deviceRunInfoList;
    }

}
