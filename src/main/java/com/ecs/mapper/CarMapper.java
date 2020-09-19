package com.ecs.mapper;

import com.ecs.model.Car;
import com.ecs.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Zhaoone on 2019/10/21
 **/
public interface CarMapper {

    @Select("SELECT * FROM car;")
    List<Car> getAll();

    @Select("SELECT * FROM car WHERE car_no=#{carNo};")
    Car getByCarNo(@Param("carNo") String carNo);

}
