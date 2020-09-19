package com.ecs.mapper;

import com.ecs.model.Prisoner;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: jojo
 * @Description:
 * @Date: Created on 2019/5/6 21:16
 */

/**
 * @Author: zhaoone
 * @Description:添加功能
 * @Date: Created on 2019/10/14
 */
public interface PrisonerMapper {

    /*
    *修改
    **/
    @Select("SELECT * FROM prisoner WHERE prisoner_id=#{prisonerId};")
    Prisoner getByPrisonerId(@Param("prisonerId") String prisonerId);

    /*
     *添加
     **/
    @Select("SELECT * FROM prisoner WHERE prisoner_id=#{prisonerId} AND username=#{userName};")
    Prisoner getByPrisonerIdAndUserName(@Param("prisonerId") String prisonerId, @Param("userName") String userName);

    @Select("SELECT * FROM prisoner;")
    List<Prisoner> getAll();

    @Select("SELECT feature FROM prisoner WHERE prisoner_id=#{prisonerId}")
    String getFeatureByPrisonerId(@Param("prisonerId") String prisonerId);

    @Update("UPDATE prisoner SET user_name=#{userName} WHERE prisoner_id =#{prisonerId};")
    void updateUserNameByPrisonerId(@Param("userName") String userName, @Param("prisonerId") String prisonerId);

    @Select("SELECT * FROM prisoner WHERE prisoner_name=#{prisonerName}")
    Prisoner getByPrisonerName(@Param("prisonerName") String prisonerName);
}
