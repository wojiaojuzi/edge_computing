package com.ecs.service;

import com.ecs.mapper.*;
import com.ecs.model.RiskLevel;
import com.ecs.model.VideoDetection;
import com.ecs.utils.RiskAssessmentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * Created by Zhaoone on 2019/10/17
 **/
@Service
public class RiskAssessmentService {
    private final RiskLevelRecordingMapper riskLevelRecordingMapper;
    private final VideoRiskMapper videoRiskMapper;
    private final PhysiologyDataReaderService physiologyDataReaderService;
    private final PrisonerService prisonerService;
    private final PrisonerHeartBeatMapper prisonerHeartBeatMapper;
    private final TaskMapper taskMapper;


    @Autowired
    public RiskAssessmentService(RiskLevelRecordingMapper riskLevelRecordingMapper, VideoRiskMapper videoRiskMapper, PhysiologyDataReaderService physiologyDataReaderService, PrisonerService prisonerService, PrisonerHeartBeatMapper prisonerHeartBeatMapper, TaskMapper taskMapper) {
        this.riskLevelRecordingMapper = riskLevelRecordingMapper;
        this.videoRiskMapper = videoRiskMapper;
        this.physiologyDataReaderService = physiologyDataReaderService;
        this.prisonerService = prisonerService;
        this.prisonerHeartBeatMapper = prisonerHeartBeatMapper;
        this.taskMapper = taskMapper;
    }

    public String getPython(){
        /*
         *   读取配置文件
         */
        String pythonPath = System.getProperty("user.dir");  //获取当前文件夹
        /*
        *   读取参数
        **/
//        try {
//            File file = new File(pythonPath + "config.ini");
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while((line = br.readLine()) != null) {
//                /*占位*/
//            }
//            br.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        /*
         *    运行python脚本
         */
        StringBuilder sb = new StringBuilder();
//        String[] strings  = new String[]{"python", pythonPath+"RiskAssessment.py"};
//        String information = "25.0,2.0,0.0,175.3,113.4,1.0,2.0,1.0,2.0,1.0,1.0,11.0,14.0,0.0,0.964,104.0";
//        String information “前面数据库”;
//        String carNo = getByPrisonerId();//从task里取
//        String videoRisk = getVideoRisk(carNo);
//        String heartRate = physiologyDataReaderService.prisonerHeartRate(prisonerId);
//        information += videoRisk+heartRate;
        String[] strings  = new String[]{"python", pythonPath+"\\RiskAssessment"+"\\RiskAssessment.py"};
        try{
            Process p = Runtime.getRuntime().exec(strings);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            sb.append(line);
            p.waitFor();
            reader.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String prisonerRisk(String prisonerId) throws Exception {
        Timestamp now = new Timestamp(new Date().getTime());
//        String riskValue = getPython();
        String pathxml = System.getProperty("user.dir");
        pathxml += "/RiskAssessment/model_r.pmml";        //打成jar包以后，地址是保留的打jar包时的
//        pathxml = "/home/zjb/model_r.pmml";
//        double[] array = {36.0,1.0,0.0,175.3,88.5,0.0,3.0,2.0,0.0,1.0,0.0,11.0,11.0,0.0,0.203,95.0};
        double[] array = prisonerService.getFeatureByPrisonerId(prisonerId);
        String prisonerName = prisonerService.getPrisonerNameByPrisonerId(prisonerId);
        String carNo = taskMapper.getCarNoByPrisonerName(prisonerName);
        double videoRiskLevel = Double.valueOf(videoRiskMapper.getLastestVideoRiskLevelByCarNo(carNo));
        double heartbeat = Double.valueOf(prisonerHeartBeatMapper.getLastestHeartbeatByPrisonerId(prisonerId));
        double[] newarray = new double[16];
        for(int i=0; i < 14;i++){
            newarray[i] = array[i];
        }
        newarray[14] = videoRiskLevel;
        newarray[15] = heartbeat;
        String riskLevel = RiskAssessmentUtil.predictLrHeart(newarray, pathxml);
        String riskValue;
        Random random = new Random();
        int offset = random.nextInt(25);
        if(riskLevel.equals("0")){
            riskValue = String.valueOf(offset);
        }else if(riskLevel.equals("1")){
            riskValue = String.valueOf(offset+25);
        }else if(riskLevel.equals("2")){
            riskValue = String.valueOf(offset+50);
        }else{
            riskValue = String.valueOf(offset+75);
        }

        RiskLevel riskLevelClass = new RiskLevel();
        riskLevelClass.setPrisonerId(prisonerId);
        riskLevelClass.setCreateAt(now);
        riskLevelClass.setRiskValue(riskValue);
        /*
        *   在插入数据库时，做判断，如果过大，标志标注；
        *   取异常事件以标注为关键；
        *   误报修改
        *
        */
        if(Integer.parseInt(riskValue) < 75 ){
            insertRiskLevel(riskLevelClass);
        }
        else{
            riskLevelClass.setHighRiskSign(true);
            insertHighRiskLevel(riskLevelClass);
        }
        return riskValue;
    }

    public void insertRiskLevel(RiskLevel riskLevel){
        riskLevelRecordingMapper.createRiskLevel(riskLevel.getPrisonerId(),
                riskLevel.getCreateAt(), riskLevel.getRiskValue());
        System.out.println("now:"+riskLevel.getCreateAt());
    }

    public void insertHighRiskLevel(RiskLevel riskLevel){
        riskLevelRecordingMapper.createHighRiskLevel(riskLevel.getPrisonerId(), riskLevel.getCreateAt(),
                riskLevel.getRiskValue(), riskLevel.getHighRiskSign());
    }

    public RiskLevel getByPrisonerId(String prisonerId){
        return riskLevelRecordingMapper.getByPrisonerId(prisonerId);
    }
    public VideoDetection getByCarNo(String carNo) {return videoRiskMapper.getLastestVideoDetectionTypeByCarNo(carNo);}

    public String getVideoRisk(String carNo){
        return videoRiskMapper.getLastestVideoRiskLevelByCarNo(carNo);
    }
}
