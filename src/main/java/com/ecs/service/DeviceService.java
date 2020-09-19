package com.ecs.service;

import com.ecs.mapper.*;
import com.ecs.model.Bracelet;
import com.ecs.model.Device;
import com.ecs.model.DeviceRunInfo;
import com.ecs.model.Request.DeviceRegisterRequest;
import com.ecs.model.Vervel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class DeviceService {

    private final DeviceMapper deviceMapper;
    private final UserMapper userMapper;
    private final BraceletMapper braceletMapper;
    private final VervelMapper vervelMapper;
    private final DeviceRunInfoMapper deviceRunInfoMapper;
    private final DeviceLogsService deviceLogsService;

    @Autowired
    public DeviceService(DeviceMapper deviceMapper, UserMapper userMapper, BraceletMapper braceletMapper, VervelMapper vervelMapper, DeviceRunInfoMapper deviceRunInfoMapper, DeviceLogsService deviceLogsService) {
        this.deviceMapper = deviceMapper;
        this.userMapper = userMapper;
        this.braceletMapper = braceletMapper;
        this.vervelMapper = vervelMapper;
        this.deviceRunInfoMapper = deviceRunInfoMapper;
        this.deviceLogsService = deviceLogsService;
    }

    public Device getByDeviceNo(String deviceNo) {
        return deviceMapper.getByDeviceNo(deviceNo);
    }

    public List<Device> getAll() {
        return deviceMapper.getAll();
    }

    public Device createDevice(DeviceRegisterRequest deviceRegisterRequest) {
        if(deviceRegisterRequest.getUserId() == null || deviceRegisterRequest.getDeviceType() == null || deviceRegisterRequest.getDeviceNo() == null) {
            return null;
        } else {
            String uid = userMapper.getUidByUserId(deviceRegisterRequest.getUserId());
            String deviceType = deviceRegisterRequest.getDeviceType();
            String deviceNo = deviceRegisterRequest.getDeviceNo();
            if(deviceType.equals("phone") || deviceType.equals("pad")) {
                // 一个user只可能有一个phone和一个ipad
                if(deviceMapper.getByUserIdAndType(uid, deviceType) != null) {
                    return null;
                } else if(deviceMapper.getByDeviceNo(deviceNo) != null) {
                    return null;
                } else {
                    Device device = new Device();
//                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//                String date = df.format(new Date());
//                String deviceNo = deviceRegisterRequest.getDeviceType() + date;
                    device.setDeviceNo(deviceNo);
                    device.setDeviceType(deviceType);
                    device.setDeviceStatus(deviceRegisterRequest.isDeviceStatus());
                    device.setUid(uid);
                    deviceMapper.createDevice(device);
                    return deviceMapper.getByDeviceNo(deviceNo);
                }
            } else {
                return null;
            }
        }
    }

    public Integer deviceLogin(String deviceNo) {
        braceletMapper.updateDeviceStatusByDeviceNo(true, deviceNo);
        vervelMapper.updateDeviceStatusByDeviceNo(true, deviceNo);
        return deviceMapper.updateDeviceStatusByDeviceNo(true, deviceNo);
    }

    public Integer deviceLogout(String deviceNo) {
        braceletMapper.updateDeviceStatusByDeviceNo(false, deviceNo);
        vervelMapper.updateDeviceStatusByDeviceNo(false, deviceNo);
        return deviceMapper.updateDeviceStatusByDeviceNo(false, deviceNo);
    }

    public void deleteDevice(String deviceNo) {
//        braceletMapper.deleteByDeviceNo(deviceNo);
//        vervelMapper.deleteByDeviceNo(deviceNo);
        deviceMapper.deleteByDeviceNo(deviceNo);
    }



    public Integer updateDeviceStatus(Boolean deviceStatus, String deviceNo) {
        return deviceMapper.updateDeviceStatusByDeviceNo(deviceStatus, deviceNo);
    }

    public Bracelet getBracelet(String deviceNo){
        return braceletMapper.getBraceletByDeviceNo(deviceNo);
    }

    public Vervel getVervel(String deviceNo){
        return vervelMapper.getVervelByDeviceNo(deviceNo);
    }

    public Boolean getDeviceStatusByDeviceNo(String deviceNo) {
        return deviceMapper.getDeviceStatusByDeviceNo(deviceNo);
    }

    public Boolean rectifyDeviceStatus(String deviceNo){
        Boolean device_status = getDeviceStatusByDeviceNo(deviceNo);
        DeviceRunInfo deviceRunInfo = deviceRunInfoMapper.getLastestOneByDeviceNo(deviceNo);
        if(deviceRunInfo == null)   return false;
        Device device = getByDeviceNo(deviceNo);
        Timestamp now = new Timestamp(new Date().getTime());
        if(device_status == true){
            if(now.getTime() - deviceRunInfo.getUploadTime().getTime() > 5500){
                updateDeviceStatus(false,deviceNo );
                /*
                * 日志修改！！！！！
                */
                deviceLogsService.insertRecord(device,"phone","设备掉线");
                deviceLogsService.insertRecord(device,"bracelet","绑定手持机掉线");
                deviceLogsService.insertRecord(device,"vervel","绑定手持机掉线");
                return false;
            }else{
                return true;
            }
        }else{
            /*
             *如果有10s内，重新连接，打出日志，返回数据
             * 没有，返回断连
             * */
            if(now.getTime() - deviceRunInfo.getUploadTime().getTime() > 10500) {
                return false;
            }else{
                updateDeviceStatus(true,deviceNo );
                deviceLogsService.insertRecord(device, "phone","设备重连");
                deviceLogsService.insertRecord(device, "bracelet","设备重连");
                deviceLogsService.insertRecord(device, "vervel","设备重连");
                return true;
            }
        }
    }

    public void updateDeviceConnectivityStatusByDeviceNo(Boolean deviceConnectivityStatus, String deviceNo){
        deviceMapper.updateDeviceConnectivityStatusByDeviceNo(deviceConnectivityStatus, deviceNo);
    }
}

