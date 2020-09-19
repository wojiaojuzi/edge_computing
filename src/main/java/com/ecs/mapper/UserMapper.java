package com.ecs.mapper;

import com.ecs.model.Car;
import com.ecs.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: zhaoone
 * @Description:添加功能
 * @Date: Created on 2019/10/14
 */
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id=#{id};")
    User getById(@Param("id") String id);

    @Select("SELECT * FROM user WHERE username=#{userName};")
    User getByUserName(@Param("userName") String userName);

    @Select("SELECT id FROM user WHERE username=#{userName};")
    String getUidByUserName(@Param("userName") String userName);

    @Select("SELECT username FROM user WHERE id=#{id};")
    String getUserNameByUid(@Param("id") String id);

    @Select("SELECT * FROM user WHERE username=#{userName} AND password=#{password};")
    User getByUserIdAndPassword(@Param("userName") String userName, @Param("password") String password);

    @Select("SELECT token_create_at FROM user WHERE id =#{id};")
    String getTokenCreateTime(@Param("id") String id);

    @Insert("INSERT INTO user(user_id,username,password,id_card,create_at,token_create_at) " +
            "VALUES(#{userId},#{userName},#{password},#{idCard},#{createAt},#{tokenCreateAt});")
    void createUser(User user);

    @Update("UPDATE user SET token_create_at=#{tokenCreateTime} WHERE id =#{id};")
    void updateTokenCreateTimeById(@Param("tokenCreateTime") String tokenCreateTime, @Param("id") String id);

    @Update("UPDATE user SET prisoner_id = #{prisonerId} WHERE id = #{id};")       //prisonerId修改
    void updatePrisonerIdById(@Param("prisonerId") String prisonerId, @Param("id") String id);

    @Update("UPDATE user SET prisoner_name = #{prisonerName} WHERE id = #{id};")       //prisonerName修改
    void updatePrisonerNameById(@Param("prisonerName") String prisonerName, @Param("id") String id);

    @Update("UPDATE user SET username=#{userName} WHERE id =#{id};")
    void updateUserNameById(@Param("userName") String userName, @Param("id") String id);

    @Update("UPDATE user SET password=#{password} WHERE id =#{id};")
    void updatePasswordById(@Param("password") String password, @Param("id") String id);

    @Update("UPDATE user SET phone=#{phone} WHERE id =#{id};")
    void updatePhoneById(@Param("phone") String phone, @Param("id") String id);

    @Delete("DELETE FROM user WHERE id=#{id};")
    void deleteById(@Param("id") String id);

    /*
    *   添加与2019/10/21
    */
    @Select("SELECT * FROM user WHERE task_no=#{taskNo};")
    List<User> getByTaskNo(@Param("taskNo") String taskNo);

    @Select("SELECT * FROM user WHERE car_no=#{carNo};")
    List<User> getMembersByCarNo(@Param("carNo") String carNo);

    @Select("SELECT id FROM user WHERE user_id=#{userId};")
    String getUidByUserId(@Param("userId") String userId);

    @Select("SELECT id FROM user WHERE username=#{userName};")
    String getUserIdByUserName(@Param("userName") String userName);

    @Select("SELECT * FROM user;")
    List<User> getAll();

    @Select("SELECT * FROM user WHERE user_id=#{userId};")
    User getByUserId(@Param("userId") String userId);
}
