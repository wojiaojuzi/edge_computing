package com.ecs.service;

import com.ecs.mapper.GpsMapper;
import com.ecs.mapper.PrisonerHeartBeatMapper;
import com.ecs.mapper.RiskLevelRecordingMapper;
import com.ecs.model.GpsData;
import com.ecs.model.PrisonerOriginalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Zhaoone on 2019/11/13
 **/
@Service
public class PrisonerDataService {

    private final PrisonerHeartBeatMapper prisonerHeartBeatMapper;
    private final GpsMapper gpsMapper;
    private final RiskLevelRecordingMapper riskLevelRecordingMapper;

    @Autowired
    public PrisonerDataService(PrisonerHeartBeatMapper prisonerHeartBeatMapper, GpsMapper gpsMapper, RiskLevelRecordingMapper riskLevelRecordingMapper) {
        this.prisonerHeartBeatMapper = prisonerHeartBeatMapper;
        this.gpsMapper = gpsMapper;
        this.riskLevelRecordingMapper = riskLevelRecordingMapper;
    }

    public void uploadHeartbeat(String prisonerId, String heartbeat, String height){
        prisonerHeartBeatMapper.createHeartbeat(prisonerId, heartbeat, height);
    }

    public String getLastestHeartbeat(String prisonerId){
        return prisonerHeartBeatMapper.getLastestHeartbeatByPrisonerId(prisonerId);
    }

    public PrisonerOriginalData getLastest(String prisonerId){
        return prisonerHeartBeatMapper.getByLastestTime(prisonerId);
    }

    public GpsData getLastest2(String userId){      //genju userId
        return gpsMapper.getByLastestTime(userId);
    }

    public GpsData getLastest3(String carNo){    //genju carNo
        return gpsMapper.getByLastestTimeAndCarNo(carNo);
    }

    public void uploadGps(String userId, String longitude, String latitude, String carNo){
        gpsMapper.createGps(userId, longitude, latitude, carNo);
    }

    public void uploadOutRange(String prisonerId){
        Timestamp now = new Timestamp(new Date().getTime());
        riskLevelRecordingMapper.createRecord(prisonerId, now, true, "犯人位置超距");
    }
}
