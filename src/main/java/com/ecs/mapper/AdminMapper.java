package com.ecs.mapper;

import com.ecs.model.Admin;
import com.ecs.model.Car;
import com.ecs.model.Prisoner;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Zhaoone on 2019/10/22
 **/
public interface AdminMapper {
    @Select("SELECT * FROM admin WHERE admin_id=#{adminId} AND password=#{password};")
    Admin getByAdminIdAndPassword(@Param("adminId") String adminId, @Param("password") String password);

    @Update("UPDATE admin SET token_create_at=#{tokenCreateTime} WHERE id =#{id};")
    void updateTokenCreateTimeById(@Param("tokenCreateTime") String tokenCreateTime, @Param("id") String id);

    @Select("SELECT token_create_at FROM admin WHERE id =#{id};")
    String getTokenCreateTime(@Param("id") String id);

    @Select("SELECT * FROM admin WHERE admin_id =#{adminId};")
    Admin getByadminId(@Param("adminId") String adminId);
}
