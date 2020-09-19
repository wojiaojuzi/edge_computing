package com.ecs.controller;

import com.ecs.model.AbnormalCondition;
import com.ecs.model.Request.ChangeStateRequest;
import com.ecs.model.Request.UpdateCommentRequest;
import com.ecs.model.RiskLevel;
import com.ecs.service.AbnormalConditionService;
import com.ecs.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Zhaoone on 2019/10/31
 **/

@RestController
@RequestMapping(path = "/exceptions")
@EnableAutoConfiguration
@Api(tags = "AbnormalCondition", description = "异常信息")
public class AbnormalConditionController {

    private final AbnormalConditionService abnormalConditionService;
    private final AdminService adminService;

    @Autowired
    public AbnormalConditionController(AbnormalConditionService abnormalConditionService, AdminService adminService) {
        this.abnormalConditionService = abnormalConditionService;
        this.adminService = adminService;
    }

    @ApiOperation(value = "全部异常")
    @RequestMapping(path = "/getAllExceptions", method = RequestMethod.GET)
    public List<AbnormalCondition> getAllExceptions(@RequestParam("token") String token) throws Exception{
            String userId = adminService.getUserIdFromToken(token);
            List<RiskLevel> list = abnormalConditionService.getAllHighRiskLevel();
            List<AbnormalCondition> abnormalConditions = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String prisonerId = list.get(i).getPrisonerId();
                String prisonerName = abnormalConditionService.getPrisonerName(prisonerId).getPrisonerName();
                Timestamp createAt = list.get(i).getCreateAt();
                String carNo = abnormalConditionService.getCar(prisonerName).getCarNo();
                String dealState = list.get(i).getDealState();
                AbnormalCondition abnormalCondition = new AbnormalCondition();
                abnormalCondition.setPrisonerId(prisonerId);
                abnormalCondition.setCreateAt(createAt);
                abnormalCondition.setDealState(dealState);
                abnormalCondition.setCarNo(carNo);
                abnormalCondition.setPrisonerName(prisonerName);
                abnormalCondition.setRiskValue(list.get(i).getRiskValue());
                abnormalCondition.setId(list.get(i).getId());
                abnormalCondition.setComment(list.get(i).getComment());
                abnormalCondition.setMisdeclaration(list.get(i).getMisdeclaration());


                abnormalConditions.add(abnormalCondition);
            }
            return abnormalConditions;
    }

    @ApiOperation(value = "处理情况修改")
    @RequestMapping(path = "/changeState", method = RequestMethod.POST)
    public String updateDealState(@RequestBody ChangeStateRequest changeStateRequest, @RequestHeader("token") String token){
        System.out.println(token);
        abnormalConditionService.updateDealState(changeStateRequest.getId());
        return "处理完毕";
    }

    @ApiOperation(value = "误报处理")
    @RequestMapping(path = "/misdeclaration", method = RequestMethod.POST)
    public String updateMisdeclaration(@RequestBody ChangeStateRequest changeStateRequest){
        abnormalConditionService.updateMisdeclaration(changeStateRequest.getId());
        return "处理完毕";
    }

    @ApiOperation(value = "详情")
    @RequestMapping(path = "/comment", method = RequestMethod.POST)
    public String updateComment(@RequestBody UpdateCommentRequest updateCommentRequest){
        abnormalConditionService.updateComment(updateCommentRequest.getId(), updateCommentRequest.getComment());
        return "处理完毕";
    }

    @ApiOperation(value = "单个犯人异常（一体机）")
    @RequestMapping(path = "/getException", method = RequestMethod.GET)
    public List<AbnormalCondition> getExceptionByPrisoner(@RequestParam(value = "prisonerId") String prisonerId, @RequestParam("token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        List<RiskLevel> list = abnormalConditionService.getHighRiskLevelAndPrisoner(prisonerId);
        List<AbnormalCondition> abnormalConditions = new ArrayList<>();
        for(int i = 0;i < list.size(); i++){

            String prisonerName = abnormalConditionService.getPrisonerName(prisonerId).getPrisonerName();
            Timestamp createAt = list.get(i).getCreateAt();
            String carNo = abnormalConditionService.getCar(prisonerName).getCarNo();
            String dealState = list.get(i).getDealState();
            AbnormalCondition abnormalCondition = new AbnormalCondition();
            abnormalCondition.setPrisonerId(prisonerId);
            abnormalCondition.setCreateAt(createAt);
            abnormalCondition.setDealState(dealState);
            abnormalCondition.setCarNo(carNo);
            abnormalCondition.setPrisonerName(prisonerName);
            abnormalCondition.setRiskValue(list.get(i).getRiskValue());
            abnormalCondition.setId(list.get(i).getId());
            abnormalCondition.setComment(list.get(i).getComment());
            abnormalCondition.setMisdeclaration(list.get(i).getMisdeclaration());


            abnormalConditions.add(abnormalCondition);
        }

        return search(abnormalConditions);
    }
    public List<AbnormalCondition> search(List<AbnormalCondition> logsList){
        Collections.sort(logsList, new Comparator<AbnormalCondition>() {
            @Override
            public int compare(AbnormalCondition o1, AbnormalCondition o2) {
                if ((o1.getCreateAt().getTime() > o2.getCreateAt().getTime())){
                    return -1;
                }
                if (o1.getCreateAt() == o2.getCreateAt()){
                    return 0;
                }
                return 1;
            }
        });
        return logsList;
    }
}
