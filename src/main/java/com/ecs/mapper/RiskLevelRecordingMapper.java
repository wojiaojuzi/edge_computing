package com.ecs.mapper;

import com.ecs.model.RiskLevel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Zhaoone on 2019/10/31
 **/
public interface RiskLevelRecordingMapper {
    @Insert("INSERT INTO risk_level_recording(prisoner_id,create_at,risk_value) " +
            "VALUES(#{prisonerId},#{createAt},#{riskValue});")
    void createRiskLevel(@Param("prisonerId") String prisonerId, @Param("createAt") Timestamp createAt,
                         @Param("riskValue") String riskValue);

    @Insert("INSERT INTO risk_level_recording(prisoner_id,create_at,high_risk_sign,comment) " +
            "VALUES(#{prisonerId},#{createAt},#{highRiskSign},#{comment});")
    void createRecord(@Param("prisonerId") String prisonerId, @Param("createAt") Timestamp createAt,
                      @Param("highRiskSign") Boolean highRiskSign, @Param("comment") String comment);

    @Insert("INSERT INTO risk_level_recording(prisoner_id,create_at,risk_value,high_risk_sign) " +
            "VALUES(#{prisonerId},#{createAt},#{riskValue},#{highRiskSign});")
    void createHighRiskLevel(@Param("prisonerId") String prisonerId, @Param("createAt") Timestamp createAt,
                             @Param("riskValue") String riskValue, @Param("highRiskSign") Boolean highRiskSign);

    @Select("SELECT * FROM risk_level_recording WHERE high_risk_sign=#{highRiskSign};")
    List<RiskLevel> getByHighRiskSign(@Param("highRiskSign") Boolean highRiskSign);

    @Select("SELECT * FROM risk_level_recording WHERE high_risk_sign=#{highRiskSign} AND prisoner_id=#{prisonerId};")
    List<RiskLevel> getByHighRiskSignAndPrisonerId(@Param("highRiskSign") Boolean highRiskSign,
                                      @Param("prisonerId") String prisonerId);

    @Update("UPDATE risk_level_recording SET deal_state=#{dealState} WHERE id =#{id};")
    void updateDealStateById(@Param("dealState") Boolean dealState, @Param("id") String id);

    @Update("UPDATE risk_level_recording SET misdeclaration=#{misdeclaration} WHERE id =#{id};")
    void updateMisdeclarationById(@Param("misdeclaration") Boolean misdeclaration, @Param("id") String id);

    @Update("UPDATE risk_level_recording SET comment=#{comment} WHERE id =#{id};")
    void updateCommentById(@Param("comment") String comment, @Param("id") String id);

    @Select("SELECT * FROM risk_level_recording\n" +
            "WHERE create_at =\n" +
            "  (SELECT MAX(create_at) FROM risk_level_recording\n" +
            "   WHERE prisoner_id = #{prisonerId}) AND prisoner_id=#{prisonerId};")
    RiskLevel getByPrisonerId(@Param("prisonerId") String prisonerId);
}
