package com.ecs.mapper;

import com.ecs.model.DeviceRunInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Zhaoone on 2019/11/5
 **/
public interface DeviceRunInfoMapper {
    @Insert("INSERT INTO device_run_info(device_no,upload_time,cpu_usage_rate,memory_usage_rate,dump_energy_rate) " +
            "VALUES(#{deviceNo},#{uploadTime},#{cpuUsageRate},#{memoryUsageRate},#{dumpEnergyRate});")
    void createDeviceRunInfo(DeviceRunInfo deviceRunInfo);


    @Select("SELECT * FROM device_run_info\n" +
            "WHERE upload_time =\n" +
            "  (SELECT MAX(upload_time) FROM device_run_info\n" +
            "   WHERE device_no = #{deviceNo}) AND device_no=#{deviceNo};")
    DeviceRunInfo getLastestOneByDeviceNo(@Param("deviceNo") String deviceNo);
}
