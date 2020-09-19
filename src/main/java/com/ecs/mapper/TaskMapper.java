package com.ecs.mapper;


import com.ecs.model.Task;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Zhaoone on 2019/10/22
 **/
public interface TaskMapper {

    @Select("SELECT * FROM task WHERE task_no=#{taskNo};")
    List<Task> getByTaskNo(@Param("taskNo") String taskNo);

    @Select("SELECT * FROM task WHERE car_no=#{carNo};")
    List<Task> getByCarNo(@Param("carNo") String carNo);

    @Select("SELECT * FROM task WHERE prisoner_name=#{prisonerName};")
    Task getByPrisonerName(@Param("prisonerName") String prisonerName);

    @Select("SELECT * FROM task WHERE user_name=#{userName};")
    Task getByUserName(@Param("userName") String userName);

    @Select("SELECT car_no FROM task WHERE prisoner_name=#{prisonerName};")
    String getCarNoByPrisonerName(@Param("prisonerName") String prisonerName);

}
