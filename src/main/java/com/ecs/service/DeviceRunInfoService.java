package com.ecs.service;

import com.ecs.mapper.DeviceRunInfoMapper;
import com.ecs.model.DeviceRunInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Zhaoone on 2019/11/5
 **/
@Service
public class DeviceRunInfoService {
    private final DeviceRunInfoMapper deviceRunInfoMapper;
    private final DeviceService deviceService;
    private final DeviceLogsService deviceLogsService;

    @Autowired
    public DeviceRunInfoService(DeviceRunInfoMapper deviceRunInfoMapper, DeviceService deviceService, DeviceLogsService deviceLogsService) {
        this.deviceRunInfoMapper = deviceRunInfoMapper;
        this.deviceService = deviceService;
        this.deviceLogsService = deviceLogsService;
}

    public void insertDeviceRunInfo(DeviceRunInfo deviceRunInfo){
        deviceRunInfoMapper.createDeviceRunInfo(deviceRunInfo);
    }

//    public DeviceRunInfo getByDeviceNo(String deviceNo){
//        return deviceRunInfoMapper.getByDeviceNo(deviceNo);
//    }

    /*
    * 选取最新的时间戳
    * */
    /*
    * 判断设备连接情况并修改连接状态
    * */
//    public Object getLastestDeviceRunInfoByDeviceNo(String deviceNo){
//        Boolean device_status = deviceService.getDeviceStatusByDeviceNo(deviceNo);
//        DeviceRunInfo deviceRunInfo = deviceRunInfoMapper.getLastestOneByDeviceNo(deviceNo);
//        Device device = deviceService.getByDeviceNo(deviceNo);
//        Timestamp now = new Timestamp(new Date().getTime());
//        if(device_status == true){
//            /*超过5s，修改连接，打出日志，返回断连
//             * 不超过，正常返回
//             * */
//            if(now.getTime() - deviceRunInfo.getUploadTime().getTime() > 5500){
//                deviceService.updateDeviceStatus(false,deviceNo );
//                /*
//                * 日志修改！！！！！
//                */
//                deviceLogsService.insertDeviceNewRecord(device,"设备掉线");
//                return "设备掉线";
//            }else{
//                return deviceRunInfo;
//            }
//        }else{
//            /*
//             *如果有10s内，重新连接，打出日志，返回数据
//             * 没有，返回断连
//             * */
//            if(now.getTime() - deviceRunInfo.getUploadTime().getTime() > 10500) {
//                return "设备掉线";
//            }else{
//                deviceService.updateDeviceStatus(true,deviceNo );
//                deviceLogsService.insertDeviceNewRecord(device,"设备重连");
//                return deviceRunInfo;
//            }
//
//        }
//    }

    public DeviceRunInfo getLastestDeviceRunInfoByDeviceNo(String deviceNo){
        return deviceRunInfoMapper.getLastestOneByDeviceNo(deviceNo);
    }
}
