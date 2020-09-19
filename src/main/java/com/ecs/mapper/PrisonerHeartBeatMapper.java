package com.ecs.mapper;

import com.ecs.model.PrisonerOriginalData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;

/**
 * Created by Zhaoone on 2019/11/13
 **/
public interface PrisonerHeartBeatMapper {
    @Insert("INSERT INTO prisoner_heartbeat(prisoner_id,heartbeat,height) " +
            "VALUES(#{prisonerId},#{heartbeat},#{height});")
    void createHeartbeat(@Param("prisonerId") String prisonerId,
                         @Param("heartbeat") String heartbeat,
                         @Param("height") String height);

    @Select("SELECT heartbeat FROM prisoner_heartbeat\n" +
            "WHERE upload_time =\n" +
            "  (SELECT MAX(upload_time) FROM prisoner_heartbeat\n" +
            "   WHERE prisoner_id = #{prisonerId}) AND prisoner_id=#{prisonerId};")
    String getLastestHeartbeatByPrisonerId(@Param("prisonerId") String prisonerId);

    @Select("SELECT * FROM prisoner_heartbeat\n" +
            "WHERE upload_time =\n" +
            "  (SELECT MAX(upload_time) FROM prisoner_heartbeat\n" +
            "   WHERE prisoner_id = #{prisonerId}) AND prisoner_id=#{prisonerId};")
    PrisonerOriginalData getByLastestTime(@Param("prisonerId") String prisonerId);

    @Select("SELECT MAX(upload_time) FROM prisoner_heartbeat WHERE prisoner_id = #{prisonerId};")
    Timestamp getLastestTimeByPrisonerId(@Param("prisonerId") String prisonerId);

    @Update("UPDATE prisoner_heartbeat SET longitude=#{longitude},latitude=#{latitude}" +
            " WHERE upload_time = #{lastestTime}" +
            " AND prisoner_id =#{prisonerId};")
    void updateLongitudeAndLatitudeByPrisonerIdAndLastestTime(@Param("prisonerId") String prisonerId, @Param("lastestTime") Timestamp lastestTime, @Param("longitude") String longitude, @Param("latitude") String latitude);
}
