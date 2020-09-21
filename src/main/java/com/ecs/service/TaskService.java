package com.ecs.service;

import com.ecs.mapper.*;
import com.ecs.model.*;
import com.ecs.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Zhaoone on 2019/10/21
 **/
@Service
public class TaskService {
    private final UserMapper userMapper;
    private final CarMapper carMapper;
    private final TaskMapper taskMapper;
    private final DeviceMapper deviceMapper;
    private final PrisonerMapper prisonerMapper;
    private final BraceletMapper braceletMapper;

    @Autowired
    public TaskService(UserMapper userMapper, CarMapper carMapper, TaskMapper taskMapper, DeviceMapper deviceMapper, PrisonerMapper prisonerMapper, BraceletMapper braceletMapper) {
        this.userMapper = userMapper;
        this.carMapper = carMapper;
        this.taskMapper = taskMapper;
        this.deviceMapper = deviceMapper;
        this.prisonerMapper = prisonerMapper;
        this.braceletMapper = braceletMapper;
    }
    //    public List<User> getByTaskNo(String taskNo){
//        return userMapper.getByTaskNo(taskNo);
//    }
//
//    public Car getByCarNo(String carNo){
//        return carMapper.getByCarNo(carNo);
//    }
//
//    public List<Car> getAllCars(){
//        return carMapper.getAll();
//    }
//
//    public List<User> getMembersInCar(String carNo){
//        return userMapper.getMembersByCarNo(carNo);
//    }

    public List<Car> getAllCars(){
        return carMapper.getAll();
    }

    public List<Task> getTask(String TaskNo){
        return taskMapper.getByTaskNo(TaskNo);
    }

    public List<Task> getByCarNo(String CarNo){
        return taskMapper.getByCarNo(CarNo);
    }

    public Car getCarByCarNo(String CarNo){
        return carMapper.getByCarNo(CarNo);
    }

    public Task getPrisonerCar(String prisonerName){
        return taskMapper.getByPrisonerName(prisonerName);
    }

    public Task getTaskByUserName(String userName){
        return taskMapper.getByUserName(userName);
    }

    public String getPrisonerNameByUserName(String userName){
        //从username获取其id，获取设备NO，判断bracelet表中是否含有，如果有取烦人id，
        // 再取烦人名字
        String id = userMapper.getByUserName(userName).getId();
        System.out.println(id);
//        Device device = deviceMapper.getByUid(id);  //加入一体机之后会有两个值
        List<Device> devices = deviceMapper.getByUid(id);
//        System.out.println(device);
        String deviceNo = devices.get(0).getDeviceNo();
        Bracelet bracelet = braceletMapper.getBraceletByDeviceNo(deviceNo);
        if(bracelet != null){
            String prisonerName = prisonerMapper.getByPrisonerId(bracelet.getPrisonerId()).getPrisonerName();
            return prisonerName;
        }
        return null;
    }

    public String getPrisonerIdByUserId(String userId){
        String userName = userMapper.getByUserId(userId).getUserName();
        String prisonerName = taskMapper.getByUserName(userName).getPrisonerName();
        return prisonerMapper.getByPrisonerName(prisonerName).getPrisonerId();
    }

    public void inputTasks() throws SQLException, ClassNotFoundException {
        SqlUtil.mybatisExec2();
        SqlUtil.mybatisExec1();
    }

    public void dbTest() {
        //deviceMapper.getAll();
    }
}
