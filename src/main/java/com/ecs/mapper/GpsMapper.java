package com.ecs.mapper;

import com.ecs.model.GpsData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by DFL on 2019/11/17.
 */
public interface GpsMapper {
    @Insert("INSERT INTO gps(user_id,longitude,latitude,car_no) " +
            "VALUES(#{userId},#{longitude},#{latitude},#{carNo});")
    void createGps(@Param("userId") String userId,
                   @Param("longitude") String longitude,
                   @Param("latitude") String latitude,
                   @Param("carNo") String carNo);

    @Select("SELECT * FROM gps\n" +
            "WHERE upload_time =\n" +
            "  (SELECT MAX(upload_time) FROM gps\n" +
            "   WHERE user_id = #{userId}) AND user_id=#{userId};")
    GpsData getByLastestTime(@Param("userId") String userId);

    @Select("SELECT * FROM gps\n" +
            "WHERE upload_time =\n" +
            "  (SELECT MAX(upload_time) FROM gps\n" +
            "   WHERE car_no = #{carNo}) AND car_no = #{carNo};")
    GpsData getByLastestTimeAndCarNo(@Param("carNo") String carNo);
}
