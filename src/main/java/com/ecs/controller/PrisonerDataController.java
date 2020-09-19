package com.ecs.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecs.mapper.CarMapper;
import com.ecs.mapper.EscapeGpsMapper;
import com.ecs.model.*;
import com.ecs.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhaoone on 2019/10/14
 **/
@RestController
@RequestMapping(path = "/prisonerData")
@EnableAutoConfiguration
@Api(tags = "PrisonerData", description = "查询犯人生理数据以及风险值")
public class PrisonerDataController {
    private final PhysiologyDataReaderService physiologyDataReaderService;
    private final RiskAssessmentService riskAssessmentService;
    private final BracketConnectivityService bracketConnectivityService;
    private final PrisonerDataService prisonerDataService;
    private final PrisonerService prisonerService;
    private final UserService userService;
    private final TaskService taskService;
    private final CarMapper carMapper;
    private final CloudService cloudService;
    private final EscapeGpsMapper escapeGpsMapper;
    private final AdminService adminService;

    @Autowired
    public PrisonerDataController(PhysiologyDataReaderService physiologyDataReaderService, RiskAssessmentService riskAssessmentService,
                                  BracketConnectivityService bracketConnectivityService, PrisonerDataService prisonerDataService, PrisonerService prisonerService,
                                  UserService userService, TaskService taskService, CarMapper carMapper, CloudService cloudService, EscapeGpsMapper escapeGpsMapper,
                                  AdminService adminService) {
        this.physiologyDataReaderService = physiologyDataReaderService;
        this.riskAssessmentService = riskAssessmentService;
        this.bracketConnectivityService = bracketConnectivityService;
        this.prisonerDataService = prisonerDataService;
        this.prisonerService = prisonerService;
        this.userService = userService;
        this.taskService = taskService;
        this.carMapper = carMapper;
        this.cloudService = cloudService;
        this.escapeGpsMapper = escapeGpsMapper;
        this.adminService = adminService;
    }

    @ApiOperation(value = "手持机上传")
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam(value = "prisonerId") String prisonerId,
                         @RequestParam(value = "heartbeat") String heartbeat,
                         @RequestParam(value = "height" ,defaultValue = "0") String height) {

        prisonerDataService.uploadHeartbeat(prisonerId, heartbeat, height);
        cloudService.physiologyData(prisonerId, heartbeat, height);
        return "上传成功";
    }

    @ApiOperation(value = "手持机位置上传")
    @RequestMapping(path = "/upload2", method = RequestMethod.POST)
    public String uploadByPad(@RequestParam(value = "userId") String userId,
                         @RequestParam(value = "longitude") String longitude,
                         @RequestParam(value = "latitude") String latitude) throws IOException {
        String carNo = taskService.getTaskByUserName((userService.getByUserId(userId).getUserName())).getCarNo();
        prisonerDataService.uploadGps(userId, longitude, latitude, carNo);
        cloudService.GpsData(userId,longitude, latitude);
        return "上传成功";
    }

    @ApiOperation(value = "获取犯人心率、风险预警值")
    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    public List<Object> prisonerAllData(@RequestHeader(value="token") String token) throws Exception {
        String userId = adminService.getUserIdFromToken(token);
        List<Prisoner> prisoners = physiologyDataReaderService.getAllPrisoners();
        List<Object> prisonerDataList = new ArrayList<>();
//        List<PrisonerData> prisonerDataList = new ArrayList<>();
        for(int i = 0; i < prisoners.size(); i++){
            /*
            * 加入对手环连接情况的判定
            */
            String uid = bracketConnectivityService.getUidByUserName(prisoners.get(i).getUserName());
            System.out.println(uid);
            //TODO isConnectivity判断方式修改
            Boolean isConnectivity = bracketConnectivityService.getBracketConnectivity(uid);
            if(isConnectivity == true){
                String heart_beat = prisonerDataService.getLastestHeartbeat(prisoners.get(i).getPrisonerId());
                String risk_level = riskAssessmentService.prisonerRisk(prisoners.get(i).getPrisonerId());
                PrisonerData prisonerData = new PrisonerData();
                prisonerData.setPrisonerId(prisoners.get(i).getPrisonerId());
                prisonerData.setHeart_rate(heart_beat);
                prisonerData.setRisk_level(risk_level);
                prisonerDataList.add(prisonerData);
            }
            else {
                BraceletRecord bracketRecord = new BraceletRecord();
                bracketRecord.setPrisonerId(prisoners.get(i).getPrisonerId());
                bracketRecord.setRecord("手环断开连接");
                prisonerDataList.add(bracketRecord);
            }
        }
        return prisonerDataList;
    }

    @ApiOperation(value = "获取单个犯人心率、风险预警值")
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public RiskLevel prisonerData(@RequestParam(value = "PrisonerId") String PrisonerId, @RequestHeader(value="token") String token) throws Exception {
        String userId = adminService.getUserIdFromToken(token);
        return riskAssessmentService.getByPrisonerId(PrisonerId);
    }

    @ApiOperation(value = "获取单个视频的识别结果")
    @RequestMapping(path = "/getVideoType", method = RequestMethod.GET)
    public VideoDetection videoDetectionData(@RequestParam(value = "CarNo") String CarNo, @RequestHeader(value="token") String token) throws Exception {
        String userId = adminService.getUserIdFromToken(token);
        return riskAssessmentService.getByCarNo(CarNo);
    }

//    @ApiOperation(value = "获取单个视频的识别结果")
//    @RequestMapping(path = "/getVideoType", method = RequestMethod.GET)
//    public VideoDetection videoDetectionData(@RequestParam(value = "CarNo") String CarNo) throws IOException {
//        return riskAssessmentService.getByCarNo(CarNo);
//    }


    @ApiOperation(value = "获取单个犯人心率")
    @RequestMapping(path = "/getSinglePrisonerHeartbeat", method = RequestMethod.GET)
    public String singlePrisonerHeartbeat(@RequestParam(value = "PrisonerId") String PrisonerId, @RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        return prisonerDataService.getLastestHeartbeat(PrisonerId);
    }

    @ApiOperation(value = "（云中心）获取全部犯人生理数据")
    @RequestMapping(path = "/getPrisonerData", method = RequestMethod.GET)
    public List<PrisonerOriginalData> getAll(){
        List<PrisonerOriginalData> prisonerOriginalDataList = new ArrayList<>();
        List<Prisoner> prisoners = prisonerService.getAll();
        for(int i = 0; i < prisoners.size(); i++){
            PrisonerOriginalData prisonerOriginalData =
                    prisonerDataService.getLastest(prisoners.get(i).getPrisonerId());
            prisonerOriginalDataList.add(prisonerOriginalData);
        }
        return prisonerOriginalDataList;
    }

    @ApiOperation(value = "（云中心）获取全部GPS数据")
    @RequestMapping(path = "/getGPS", method = RequestMethod.GET)
    public List<GpsData> getAllGps(){
        List<GpsData> gpsDataList = new ArrayList<>();
        List<User> users = userService.getAll();
        for(int i = 0; i < users.size(); i++){
             GpsData gpsData =
                    prisonerDataService.getLastest2(users.get(i).getUserId());
            gpsDataList.add(gpsData);
        }
        return gpsDataList;
    }

    @ApiOperation(value = "（前端轨迹展示）获取全部GPS数据")
    @RequestMapping(path = "/getCarGPS", method = RequestMethod.GET)
    public List<GpsData> getAllGps2(@RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        List<GpsData> gpsDataList = new ArrayList<>();
        List<Car> cars = carMapper.getAll();
        for(int i = 0; i < cars.size(); i++){
            GpsData gpsData =
                    prisonerDataService.getLastest3(cars.get(i).getCarNo());
            gpsDataList.add(gpsData);
        }
        return gpsDataList;
    }

    @ApiOperation(value = "犯人超出距离上报")
    @RequestMapping(path = "/outrange", method = RequestMethod.POST)
    public String outRange(@RequestParam("prisonerId") String prisonerId){
        prisonerDataService.uploadOutRange(prisonerId);
        return "上传成功";
    }

    @ApiOperation(value = "逃逸犯人GPS")
    @RequestMapping(path = "/escapedPrisonerGPS", method = RequestMethod.GET)
    public List<EscapeGps> getEscapePrisonerGPS(@RequestHeader(value="token") String token){
        List<EscapeGps>  escapeGps = escapeGpsMapper.getLastest();
        return escapeGps;
    }
}
