package com.ecs.service;

import com.ecs.mapper.*;
import com.ecs.model.Device;
import com.ecs.model.DeviceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhaoone on 2019/11/4
 **/
@Service
public class DeviceLogsService {
    private final DeviceLogsMapper deviceLogsMapper;
    private final UserMapper userMapper;
    private final BraceletMapper braceletMapper;
    private final VervelMapper vervelMapper;
    private final PrisonerMapper prisonerMapper;

    @Autowired
    public DeviceLogsService(DeviceLogsMapper deviceLogsMapper, UserMapper userMapper, BraceletMapper braceletMapper, VervelMapper vervelMapper, PrisonerMapper prisonerMapper) {
        this.deviceLogsMapper = deviceLogsMapper;
        this.userMapper = userMapper;
        this.braceletMapper = braceletMapper;
        this.vervelMapper = vervelMapper;
        this.prisonerMapper = prisonerMapper;
    }

    public List<DeviceLog> getAllLogs(){
        return deviceLogsMapper.getAll();
    }

    public List<DeviceLog> getLogByDeviceNo(String deviceNo){
        return deviceLogsMapper.getByDeviceNo(deviceNo);
    }

    public void createNewLog(DeviceLog deviceLog){
        deviceLogsMapper.createPrisonerLog(deviceLog);
    }

//    public void insertDeviceNewRecord(Device device, String record){
//        Timestamp now = new Timestamp(new Date().getTime());
//
//        DeviceLog deviceLogPhone = getUserAndPrisonerNameByDeviceUid(device.getUid());
//        deviceLogPhone.setDealTime(now);
//        deviceLogPhone.setDeviceNo(device.getDeviceNo());
//        deviceLogPhone.setDeviceName("phone"+device.getId());
//        deviceLogPhone.setDeviceType("phone");
//        deviceLogPhone.setRecord(record);
//        createNewLog(deviceLogPhone);
//
//        DeviceLog deviceLogBracelet = getUserAndPrisonerNameByDeviceUid(device.getUid());
//        deviceLogBracelet.setDealTime(now);
//        deviceLogBracelet.setRecord(record);
//        deviceLogBracelet.setDeviceNo(braceletMapper.getBraceletNoByUid(device.getUid()));
//        deviceLogBracelet.setDeviceName("bracelet"+device.getId());
//        deviceLogBracelet.setDeviceType("bracelet");
//        createNewLog(deviceLogBracelet);
//
//        DeviceLog deviceLogVervel = getUserAndPrisonerNameByDeviceUid(device.getUid());
//        deviceLogVervel.setDealTime(now);
//        deviceLogVervel.setRecord(record);
//        deviceLogVervel.setDeviceNo(vervelMapper.getVervelNoByUid(device.getUid()));
//        deviceLogVervel.setDeviceName("vervel"+device.getId());
//        deviceLogVervel.setDeviceType("vervel");
//        createNewLog(deviceLogVervel);
//    }

//    public void insertNewRecord(Device device, String record){
//        Timestamp now = new Timestamp(new Date().getTime());
//        DeviceLog deviceLog = getUserAndPrisonerNameByDeviceUid(device.getUid());
//        deviceLog.setDealTime(now);
//        deviceLog.setDeviceName("手环"+device.getId());
//        deviceLog.setDeviceType("bracelet");
//        deviceLog.setRecord(record);
//        createNewLog(deviceLog);
//    }

    /*
    *   device传绑定手、脚环的一体机或手持机；deviceType标注这条日志的来源
    */
    public void insertRecord(Device device, String deviceType, String record){
        Timestamp now = new Timestamp(new Date().getTime());
        DeviceLog deviceLog = getUserByDeviceUid(device.getUid());

        //prisonerName从手环的prisonerId->到prisoner表的prisonerName

        String prisonerId = braceletMapper.getPrisonerIdByUid(device.getUid());
        if(prisonerId != null) {
            String prisonerName = prisonerMapper.getByPrisonerId(prisonerId).getPrisonerName();
            deviceLog.setPrisonerName(prisonerName);
        }else{
        deviceLog.setPrisonerName("---");
        }

        deviceLog.setDealTime(now);
        deviceLog.setRecord(record);
        System.out.println(deviceType+"1");
        if(deviceType.equals("phone")){
            deviceLog.setDeviceNo(device.getDeviceNo());
            System.out.println(deviceType+"2");
            deviceLog.setDeviceName("手持机"+device.getId());
            deviceLog.setDeviceType("phone");
        }else if(deviceType.equals("pad")){
            System.out.println(deviceType);
            deviceLog.setDeviceNo(device.getDeviceNo());
            deviceLog.setDeviceName("一体机"+device.getId());
            deviceLog.setDeviceType("pad"+"3");
        }else if(deviceType.equals("bracelet")) {
            deviceLog.setDeviceNo(braceletMapper.getBraceletNoByUid(device.getUid()));
            deviceLog.setDeviceName("手环"+braceletMapper.getBraceletIdByUid(device.getUid()));
            deviceLog.setDeviceType("bracelet");
        }else if(deviceType.equals("vervel")){
            deviceLog.setDeviceNo(vervelMapper.getVervelNoByUid(device.getUid()));
            deviceLog.setDeviceName("脚环"+vervelMapper.getVervelIdByUid(device.getUid()));
            deviceLog.setDeviceType("vervel");
        }
        createNewLog(deviceLog);
    }

    /*
     *   根据设备信息直接读出用户名字
     */
    public DeviceLog getUserByDeviceUid(String uid){
        System.out.println(uid);
        String userName = userMapper.getById(uid).getUserName();
        DeviceLog deviceLog = new DeviceLog();
        deviceLog.setUserName(userName);
        return deviceLog;
    }
}
