package com.ecs.service;


import com.ecs.constant.FileNames;
import com.ecs.mapper.PrisonerMapper;
import com.ecs.model.Prisoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**1
 * Created by Zhaoone on 2019/10/11
 **/

/*
*   用于没有物理设备
*/
@Service
public class PhysiologyDataReaderService {
    private final PrisonerMapper prisonerMapper;

    @Autowired
    public PhysiologyDataReaderService(PrisonerMapper prisonerMapper) {
        this.prisonerMapper = prisonerMapper;
    }

    public String readPhysiologicalData(File file){
        System.out.println(file.toString());
        if (!file.exists())
            throw new RuntimeException("Not File!");
        List list = new ArrayList();
        String heart_rate = "记录结束";

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null){
                if(Pattern.matches(".*\"bpm\".*",line)){
                    String str[] = line.split(": ");
                    StringBuilder sb = new StringBuilder();
                    sb = sb.append(str[1]);
                    sb.deleteCharAt(sb.length()-1);
                    heart_rate = sb.toString();

                    line = line.replace("bpm","bpm~");
                    list.add(line);
                    break;
                }
                list.add(line);
            }

            while((line = br.readLine()) != null){
                 list.add(line);
            }
                br.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        try{
            PrintWriter pw = new PrintWriter(file);
            for(int i = 0; i<list.size(); i++){
                String line = (String)list.get(i);
                pw.println(line);
            }
            pw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return heart_rate;
    }

    /*
    * TODO 等设备实时读取数据
    */
//    public String readPhysiologicalData(){
//
//    }

    public List<Prisoner> getAllPrisoners(){
        return prisonerMapper.getAll();
    }

    public String prisonerHeartRate(String prisonerId){
        File file = new File(FileNames.FILE_NAME_START + prisonerId + "\\" + FileNames.FILE_NAME_END);
        return readPhysiologicalData(file);
    }
}

