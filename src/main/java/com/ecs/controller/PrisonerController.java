package com.ecs.controller;

import com.ecs.model.Prisoner;
import com.ecs.model.PrisonerAndTask;
import com.ecs.model.Task;
import com.ecs.service.AdminService;
import com.ecs.service.PrisonerService;
import com.ecs.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jojo
 * @Description:
 * @Date: Created on 2019/5/6 21:22
 */
@RestController
@RequestMapping(path = "/prisoners")
@EnableAutoConfiguration
@Api(tags = "Prisoner", description = "获取犯人信息")
public class PrisonerController {

    private final PrisonerService prisonerService;
    private final TaskService taskService;
    private final AdminService adminService;

    @Autowired
    public PrisonerController(PrisonerService prisonerService, TaskService taskService, AdminService adminService) {
        this.prisonerService = prisonerService;
        this.taskService = taskService;
        this.adminService = adminService;
    }



    @ApiOperation(value = "获取单个犯人信息")
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public Prisoner getById(@RequestParam("prisonerId") String prisonerId, @RequestParam("token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        return prisonerService.getById(prisonerId);
    }

    @ApiOperation(value = "获取所有犯人信息")
    @RequestMapping(path = "/get_all", method = RequestMethod.GET)
//    public List<Prisoner> getAll() {
//        return prisonerService.getAll();
//    }
    public List<PrisonerAndTask> getAll(@RequestParam("token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        List<Prisoner> prisoners = prisonerService.getAll();
        List<PrisonerAndTask> prisonerAndCarAndTaskList = new ArrayList<>();
        for(int i = 0; i < prisoners.size(); i++){
            Task task = taskService.getPrisonerCar(prisoners.get(i).getPrisonerName());
            PrisonerAndTask prisonerAndCarAndTask = new PrisonerAndTask();
            prisonerAndCarAndTask.setTask(task);
            prisonerAndCarAndTask.setPrisoner(prisoners.get(i));
            prisonerAndCarAndTaskList.add(prisonerAndCarAndTask);
        }
        return prisonerAndCarAndTaskList;
    }


}
