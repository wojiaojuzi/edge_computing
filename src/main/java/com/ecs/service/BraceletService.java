package com.ecs.service;

import com.ecs.mapper.BraceletMapper;
import com.ecs.mapper.VervelMapper;
import com.ecs.model.Bracelet;
import com.ecs.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Zhaoone on 2019/11/4
 **/
@Service
public class BraceletService {
    private final BraceletMapper braceletMapper;
    private final DeviceLogsService deviceLogsService;
    private final DeviceService deviceService;
    private final VervelMapper vervelMapper;

    @Autowired
    public BraceletService(BraceletMapper braceletMapper, DeviceLogsService deviceLogsService, DeviceService deviceService, VervelMapper vervelMapper) {
        this.braceletMapper = braceletMapper;
        this.deviceLogsService = deviceLogsService;
        this.deviceService = deviceService;
        this.vervelMapper = vervelMapper;
    }

    public void updateBraceletStatus(Boolean deviceStatus, String braceletNo){
        braceletMapper.updateDeviceStatusByBraceletNo(deviceStatus, braceletNo);
        Device device = deviceService.getByDeviceNo(getDeviceNoByBraceletNo(braceletNo));
        if(deviceStatus == true)
            deviceLogsService.insertRecord(device, "bracelet", "设备重连");
        else    deviceLogsService.insertRecord(device, "bracelet", "设备掉线");
    }

    public String getDeviceNoByBraceletNo(String braceletNo){
        return braceletMapper.getDeviceNoByBraceletNo(braceletNo);
    }

    public void updateDeviceNoAndUidByBraceletNo(String braceletNo, String deviceNo){
        String uid = deviceService.getByDeviceNo(deviceNo).getUid();   //根据deviceNo获得uid
        braceletMapper.updateDeviceNoAndUidByBraceletNo(deviceNo,uid ,true, braceletNo);//
        String prisonerId = braceletMapper.getPrisonerIdByBraceletNo(braceletNo);//根据braceletNo获得vervelNo
        String vervelNo = vervelMapper.getVervelNoByPrisonerId(prisonerId);
        vervelMapper.updateDeviceNoAndUidByVervelNo(deviceNo, uid, true, vervelNo);
    }

    public String getPrisonerIdByBraceletNo(String braceletNo){
        return braceletMapper.getPrisonerIdByBraceletNo(braceletNo);
    }

    public void updatePrisonerIdByBraceletNo(String braceletNo, String prisonerId){
        Bracelet bracelet= new Bracelet();
        bracelet.setBraceletNo(braceletNo);
        bracelet.setPrisonerId(prisonerId);
        braceletMapper.createBracelet(bracelet);
    }
}
