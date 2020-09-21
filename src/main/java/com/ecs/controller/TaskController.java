package com.ecs.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecs.model.Car;
import com.ecs.model.PrisonerAndTask;
import com.ecs.model.Response.HttpResponseContent;
import com.ecs.model.Task;
import com.ecs.service.AdminService;
import com.ecs.service.PrisonerService;
import com.ecs.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Zhaoone on 2019/10/21
 **/
@RestController
@RequestMapping(path = "/task")
@EnableAutoConfiguration
@Api(tags = "Task", description = "任务相关操作")

/*
*
*/
public class TaskController {
    private final TaskService taskService;
    private final PrisonerService prisonerService;
    private final AdminService adminService;

    @Autowired
    public TaskController(TaskService taskService, PrisonerService prisonerService, AdminService adminService) {
        this.taskService = taskService;
        this.prisonerService = prisonerService;
        this.adminService = adminService;
    }

    @ApiOperation(value = "获取全部任务")
    @RequestMapping(path = "/getAllTasks", method = RequestMethod.GET)
    public List<Car> getAllCars(@RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        return taskService.getAllCars();
    }

    @ApiOperation(value = "获得车辆数量")
    @RequestMapping(path = "/getNumbersOfCars", method = RequestMethod.GET)
    public String getNumbersOfCars(@RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        return String.valueOf(taskService.getAllCars().size());
    }

    @ApiOperation(value = "获取单个任务信息")
    @RequestMapping(path = "/getTask", method = RequestMethod.GET)
    public String getTask(@RequestParam("taskNo") String taskNo, @RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        List<Task> list = taskService.getTask(taskNo);
        JSONObject json = new JSONObject();
        json.put("taskNo",taskNo);
        StringBuilder sb = new StringBuilder();
//        sb.append(list.get(0).getCarNo());
        for(int i = 0;i < list.size(); i++){
            if(list.get(i).getCarNo() != null){
                sb.append(list.get(i).getCarNo()+"、");
            }
        }
        StringBuilder sb1 = new StringBuilder();
//        sb1.append(list.get(0).getPrisonerName());
        for(int i = 0;i < list.size(); i++){
            if(list.get(i).getPrisonerName() != null){
            sb1.append(list.get(i).getPrisonerName()+"、");
            }
        }
        StringBuilder sb2 = new StringBuilder();
//        sb2.append(list.get(0).getUserName());
        for(int i = 0;i < list.size(); i++){
            if(list.get(i).getUserName() != null) {
                sb2.append( list.get(i).getUserName()+"、");
            }
        }
        json.put("carNo",(sb.substring(0, sb.length()-1)).toString());
        json.put("prisoner",(sb1.substring(0, sb1.length()-1)).toString());
        json.put("user",(sb2.substring(0, sb2.length()-1)).toString());
        json.put("level",list.get(0).getLevel());
        json.put("detail",list.get(0).getDetail());
        return json.toJSONString();
    }

    @ApiOperation(value = "获取车辆信息")
    @RequestMapping(path = "/getAboutCar", method = RequestMethod.GET)
    public String getCar(@RequestParam("carNo") String carNo, @RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        List<Task> list = taskService.getByCarNo(carNo);
        Car car = taskService.getCarByCarNo(carNo);
        JSONObject json = new JSONObject();
        json.put("taskNo",car.getTaskNo());
        json.put("carNo",carNo);
        json.put("color",car.getColor());
        json.put("type",car.getType());

        StringBuilder sb = new StringBuilder();
//        sb.append(list.get(0).getPrisonerName());
        for(int i = 0;i < list.size(); i++){
            if(list.get(i).getPrisonerName() != null)
                sb.append(list.get(i).getPrisonerName()+"、");
        }

        StringBuilder sb1 = new StringBuilder();
//        sb1.append(list.get(0).getUserName());
        for(int i = 0;i < list.size(); i++){
            if(list.get(i).getUserName() != null)
                sb1.append(list.get(i).getUserName()+"、");
        }
        json.put("prisoner",(sb.substring(0, sb.length()-1)).toString());
        json.put("user",(sb1.substring(0, sb1.length()-1)).toString());
        json.put("level",car.getLevel());
        json.put("carPhoto",car.getCarPhotoUri());
        return json.toJSONString();
    }

    @ApiOperation(value = "获取警察的任务信息（手持机）")
    @RequestMapping(path = "/deviceGetTasks", method = RequestMethod.GET)
    public PrisonerAndTask getByUserId(@RequestParam("userName") String userName, @RequestHeader(value="token") String token) {
        PrisonerAndTask prisonerAndTask = new PrisonerAndTask();
        Task task = taskService.getTaskByUserName(userName);
        prisonerAndTask.setTask(task);
        prisonerAndTask.setPrisoner(prisonerService.getByPrisonerName(task.getPrisonerName()));
        return prisonerAndTask;
    }

    /*
    * 从任务表中读取user对应犯人
    * */
    @ApiOperation(value = "获取警察绑定犯人信息（一体机）")
    @RequestMapping(path = "/getByUser", method = RequestMethod.GET)
    public String getPrisonerId(@RequestParam("userId") String userId, @RequestHeader(value="token") String token) throws Exception{
        String user_Id = adminService.getUserIdFromToken(token);
        return taskService.getPrisonerIdByUserId(userId);
    }

    @ApiOperation(value = "押解任务导入")
    @RequestMapping(path = "/inputTasks", method = RequestMethod.GET)
    public HttpResponseContent inputTasks() throws SQLException, ClassNotFoundException {
        taskService.inputTasks();
        return null;
    }

}
