package com.ecs.service;

import com.ecs.mapper.PrisonerMapper;
import com.ecs.mapper.RiskLevelRecordingMapper;
import com.ecs.mapper.TaskMapper;
import com.ecs.model.Prisoner;
import com.ecs.model.RiskLevel;
import com.ecs.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Zhaoone on 2019/10/31
 **/
@Service
public class AbnormalConditionService {
    private final RiskLevelRecordingMapper riskLevelRecordingMapper;
    private final PrisonerMapper prisonerMapper;
    private final TaskMapper taskMapper;

    @Autowired
    public AbnormalConditionService(RiskLevelRecordingMapper riskLevelRecordingMapper, PrisonerMapper prisonerMapper, TaskMapper carMapper) {
        this.riskLevelRecordingMapper = riskLevelRecordingMapper;
        this.prisonerMapper = prisonerMapper;
        this.taskMapper = carMapper;
    }




    public List<RiskLevel> getAllHighRiskLevel(){
        return riskLevelRecordingMapper.getByHighRiskSign(true);
    }

    public List<RiskLevel> getHighRiskLevelAndPrisoner(String prisonerId){
        return riskLevelRecordingMapper.getByHighRiskSignAndPrisonerId(true, prisonerId);
    }

    public void updateDealState(String id){
        riskLevelRecordingMapper.updateDealStateById(true, id);
    }

    public void updateMisdeclaration(String id){
        riskLevelRecordingMapper.updateMisdeclarationById(true, id);
    }

    public void updateComment(String id, String comment){
        riskLevelRecordingMapper.updateCommentById(comment, id);
    }

    public Prisoner getPrisonerName(String prisonerId){
        return prisonerMapper.getByPrisonerId(prisonerId);
    }

    public Task getCar(String prisonerName){
        return taskMapper.getByPrisonerName(prisonerName);
    }
}
