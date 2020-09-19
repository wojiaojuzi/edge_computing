package com.ecs.mapper;


import com.ecs.model.EscapeGps;
import com.ecs.model.GpsData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Zhaoone on 2019/12/20
 **/
public interface EscapeGpsMapper {

    @Insert("INSERT INTO escape_gps(escape_num,upload_time,longitude,latitude) " +
            "VALUES(#{escapeNum},#{uploadTime},#{longitude},#{latitude});")
    void createEscapeGps(@Param("escapeNum") String escapeNum,
                         @Param("uploadTime") Timestamp uploadTime,
                         @Param("longitude") String longitude,
                         @Param("latitude") String latitude);


    @Select("SELECT * FROM escape_gps")
    List<Object> isExisted();


    @Select("SELECT * FROM escape_gps\n"+
            "WHERE upload_time = \n"+
            "  (SELECT MAX(upload_time) FROM escape_gps)")
    List<EscapeGps> getLastest();
}
