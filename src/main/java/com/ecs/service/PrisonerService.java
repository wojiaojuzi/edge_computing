package com.ecs.service;

import com.ecs.mapper.BraceletMapper;
import com.ecs.mapper.DeviceMapper;
import com.ecs.mapper.PrisonerMapper;
import com.ecs.mapper.UserMapper;
import com.ecs.model.Prisoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jojo
 * @Description:
 * @Date: Created on 2019/5/6 21:23
 */
@Service
public class PrisonerService {

    private final PrisonerMapper prisonerMapper;
    private final DeviceMapper deviceMapper;
    private final UserMapper userMapper;
    private final BraceletMapper braceletMapper;

    @Autowired
    public PrisonerService(PrisonerMapper prisonerMapper, DeviceMapper deviceMapper, UserMapper userMapper, BraceletMapper braceletMapper) {
        this.prisonerMapper = prisonerMapper;
        this.deviceMapper = deviceMapper;
        this.userMapper = userMapper;
        this.braceletMapper = braceletMapper;
    }

    public Prisoner getById(String prisonerId) {
        return prisonerMapper.getByPrisonerId(prisonerId);
    }

    public List<Prisoner> getAll() {
        return prisonerMapper.getAll();
    }

    public double[] getFeatureByPrisonerId(String prisonerId){
        String feature = prisonerMapper.getFeatureByPrisonerId(prisonerId);
        String[] features = feature.split(",");
        double[] array = new double[14];
        for(int i = 0; i < features.length; i++){
            array[i] = Double.valueOf(features[i]);
        }
        return array;
    }

    public String getPrisonerNameByPrisonerId(String priosnerId){
        return prisonerMapper.getByPrisonerId(priosnerId).getPrisonerName();
    }

    public void updateUserNameByBraceletNo(String deviceNo, String braceletNo){
        String uid = deviceMapper.getByDeviceNo(deviceNo).getUid();
        String userName = userMapper.getUserNameByUid(uid);
        String prisonerId = braceletMapper.getPrisonerIdByBraceletNo(braceletNo);
//        String prisonerName = prisonerMapper.getByPrisonerId(prisonerId).getPrisonerName();
        prisonerMapper.updateUserNameByPrisonerId(userName, prisonerId);
    }

    public String getPrisonerFeatureByPrisonerId(String prisonerId){
        return prisonerMapper.getFeatureByPrisonerId(prisonerId);
    }

    public Prisoner getByPrisonerName(String prisonerName){
        return prisonerMapper.getByPrisonerName(prisonerName);
    }
}
