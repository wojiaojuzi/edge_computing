package com.ecs.mapper;

import com.ecs.model.DeviceLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Zhaoone on 2019/11/4
 **/
public interface DeviceLogsMapper {
    @Select("SELECT * FROM device_log;")
    List<DeviceLog> getAll();

    @Insert("INSERT INTO device_log(device_type,device_name,deal_time,user_name,prisoner_name,record,device_no) " +
            "VALUES(#{deviceType},#{deviceName},#{dealTime},#{userName},#{prisonerName},#{record},#{deviceNo});")
    void createPrisonerLog(DeviceLog deviceLog);


    @Select("SELECT * FROM device_log WHERE device_no=#{deviceNo};")
    List<DeviceLog> getByDeviceNo(@Param("deviceNo") String deviceNo);
}
