package com.ecs.controller;

import com.ecs.model.*;
import com.ecs.model.Request.DeviceRegisterRequest;
import com.ecs.model.Response.HttpResponseContent;
import com.ecs.model.Response.ResponseEnum;
import com.ecs.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

@RestController
@RequestMapping(path = "/devices")
@EnableAutoConfiguration
@Api(tags = "Device", description = "设备相关的操作")
public class DeviceController {

    private final DeviceService deviceService;
    private final TaskService taskService;
    private final DeviceLogsService deviceLogsService;
    private final BraceletService braceletService;
    private final PrisonerService prisonerService;
    private final AdminService adminService;

    @Autowired
    public DeviceController(DeviceService deviceService, TaskService taskService, DeviceLogsService deviceLogsService, BraceletService braceletService, PrisonerService prisonerService, AdminService adminService) {
        this.deviceService = deviceService;
        this.taskService = taskService;
        this.deviceLogsService = deviceLogsService;
        this.braceletService = braceletService;
        this.prisonerService = prisonerService;
        this.adminService = adminService;
    }

    @ApiOperation(value = "获取设备信息")
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public HttpResponseContent getByDeviceNo(@RequestHeader(value="token") String token, @RequestParam("deviceNo") String deviceNo) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        HttpResponseContent response = new HttpResponseContent();
        Device device = deviceService.getByDeviceNo(deviceNo);
        if(device == null) {
            response.setCode(ResponseEnum.DEVICE_NOT_EXIST.getCode());
            response.setMessage(ResponseEnum.DEVICE_NOT_EXIST.getMessage());
        } else {
            response.setCode(ResponseEnum.SUCCESS.getCode());
            response.setMessage(ResponseEnum.SUCCESS.getMessage());
            response.setData(device);
        }
        return response;
    }

    @ApiOperation(value = "获取所有设备信息")
    @RequestMapping(path = "/get_all", method = RequestMethod.GET)
    public List<DeviceAndTask> getAll(@RequestHeader(value="token") String token) throws Exception{
        //System.out.println(headers.get("token").toString());
        //String token = StringUtils.strip(headers.get("token").toString(),"[]");
        System.out.println(token);
        String userId = adminService.getUserIdFromToken(token);
        List<DeviceAndTask> deviceAndTasks = new ArrayList<>();
        List<Device> devices = deviceService.getAll();
        for(int i = 0; i < devices.size(); i++){
            //根据device.user.username 读 task表，取出来，其中有taskno、carNo

            Device device = devices.get(i);
            device.setDeviceStatus(deviceService.rectifyDeviceStatus(device.getDeviceNo()));   //修改设备状态
            Task task = taskService.getTaskByUserName(device.getUser().getUserName());
            String prisonerName = taskService.getPrisonerNameByUserName(task.getUserName());
            task.setPrisonerName(prisonerName);
            Bracelet bracelet = deviceService.getBracelet(device.getDeviceNo());
            Vervel vervel = deviceService.getVervel(device.getDeviceNo());
            DeviceAndTask deviceAndTask = new DeviceAndTask();
            deviceAndTask.setDevice(device);
            deviceAndTask.setTask(task);
            deviceAndTask.setBracelet(bracelet);
            deviceAndTask.setVervel(vervel);
            deviceAndTasks.add(deviceAndTask);
        }
        return deviceAndTasks;
    }

    @ApiOperation(value = "注册新设备")    //未修改关于手环、脚环的部分
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public HttpResponseContent createDevice(@RequestParam("deviceType") String deviceType,
                                            @RequestParam("deviceNo") String deviceNo,
                                            @RequestParam("userId") String userId,
                                            @RequestParam(value = "deviceStatus" , defaultValue = "1") String deviceStatus) {
        HttpResponseContent response = new HttpResponseContent();
        boolean ds;
        if(deviceStatus.equals("true")) {
            ds = true;
        } else {
            ds = false;
        }
        DeviceRegisterRequest deviceRegisterRequest = new DeviceRegisterRequest(deviceType,
                userId, deviceNo, ds);
        Device device = deviceService.createDevice(deviceRegisterRequest);
        if(device == null) {
            response.setCode(ResponseEnum.DEVICE_REGISTER_FAIL.getCode());
            response.setMessage(ResponseEnum.DEVICE_REGISTER_FAIL.getMessage());
        } else {
            response.setCode(ResponseEnum.SUCCESS.getCode());
            response.setMessage(ResponseEnum.SUCCESS.getMessage());
            response.setData(device);
        }
        return response;
    }

    @ApiOperation(value = "设备登入")
    @RequestMapping(path = "/login", method = RequestMethod.PUT)
    public HttpResponseContent deviceLogin(@RequestParam("deviceNo") String deviceNo) {
        HttpResponseContent response = new HttpResponseContent();
        deviceService.deviceLogin(deviceNo);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        Device device = deviceService.getByDeviceNo(deviceNo);
        response.setData(device);

        /*
         *   插入日志
         *   需要userName、prisonerName，从数据库读
         */
//        Timestamp now = new Timestamp(new Date().getTime());
//        DeviceLog deviceLogPhone = deviceService.getUserAndPrisonerNameByDeviceUid(device.getUid());  //uid从哪里来？
//        deviceLogPhone.setDealTime(now);
//        deviceLogPhone.setDeviceName("手持机"+device.getId());
//        deviceLogPhone.setDeviceType("phone");
//        deviceLogPhone.setRecord("设备登入");
//        deviceLogsService.CreateNewLog(deviceLogPhone);
//        DeviceLog deviceLogBracelet = deviceLogPhone;
//        deviceLogBracelet.setDeviceName("手环"+device.getId());
//        deviceLogBracelet.setDeviceType("bracelet");
//        deviceLogsService.CreateNewLog(deviceLogBracelet);
//        System.out.println(device.getDeviceType());
        deviceLogsService.insertRecord(device,device.getDeviceType(), "设备登入");


        return response;
    }

    @ApiOperation(value = "设备登出")
    @RequestMapping(path = "/logout", method = RequestMethod.PUT)
    public HttpResponseContent deviceLogout(@RequestParam("deviceNo") String deviceNo) {
        HttpResponseContent response = new HttpResponseContent();
        deviceService.deviceLogout(deviceNo);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        Device device = deviceService.getByDeviceNo(deviceNo);
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setData(device);

        /*
         *   日志修改
         */
        deviceLogsService.insertRecord(device,device.getDeviceType(), "设备登出");

        return response;
    }

    @ApiOperation(value = "设备注销")
    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public HttpResponseContent deleteDevice(@RequestParam("deviceNo") String deviceNo) {
        HttpResponseContent response = new HttpResponseContent();

        Device device = deviceService.getByDeviceNo(deviceNo);
        /*
         *   日志修改
         */
        deviceLogsService.insertRecord(device,device.getDeviceType(),"设备删除");
        if(device.getDeviceConnectivityStatus() == true) {
            deviceLogsService.insertRecord(device, "bracelet", "绑定手持机删除");
            deviceLogsService.insertRecord(device, "vervel", "绑定手持机删除");
        }

        deviceService.deleteDevice(deviceNo);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());

        return response;
    }

    /*
    *添加于2019/10/16
    */
    @ApiOperation(value = "判断设备是否存在")
    @RequestMapping(path = "/judgeDeviceNo", method = RequestMethod.GET)
    public boolean judgeDeviceNo(@RequestParam("deviceNo") String deviceNo,@RequestParam("token") String token) {
        Device device = deviceService.getByDeviceNo(deviceNo);
        if(device == null)
            return false;
        return true;
    }

    @ApiOperation(value = "修改手持机状态")
    @RequestMapping(path = "/updateDeviceStatus", method = RequestMethod.GET)
    public void getByDeviceNo(@RequestParam("deviceStatus") Boolean deviceStatus,
                              @RequestParam("DeviceNo") String DeviceNo,
                              @RequestParam("token") String token){
        deviceService.updateDeviceStatus(deviceStatus, DeviceNo);
    }

    /*
    *   1、读取手环绑定手持机的no
    *   2、如果不空，解绑；如果空，建立新连接
    *   3、与一体机无关
    * */
    @ApiOperation(value = "修改连接设备")
    @RequestMapping(path = "/updateDeviceConnectivityStatus", method = RequestMethod.POST)
    public String updateDeviceConnectivityStatus(@RequestParam("deviceNo") String deviceNo,
                                                 @RequestParam("braceletNo") String braceletNo) {
        //找到原来的设备
        String lastDeviceNo = braceletService.getDeviceNoByBraceletNo(braceletNo);
        if(lastDeviceNo != null) {
            Device lastDevice = deviceService.getByDeviceNo(lastDeviceNo);

            //插入解绑日志
            deviceLogsService.insertRecord(lastDevice, "phone", "与手环脚环解绑");
            deviceLogsService.insertRecord(lastDevice, "bracelet", "与手持机解绑");
            deviceLogsService.insertRecord(lastDevice, "vervel", "与手持机解绑");

            //与旧设备解绑
            deviceService.updateDeviceConnectivityStatusByDeviceNo(false, lastDeviceNo);
        }
        //与新设备建立连接
        //TODO 修改user表中对应prisoner
        deviceService.updateDeviceConnectivityStatusByDeviceNo(true, deviceNo);
        prisonerService.updateUserNameByBraceletNo(deviceNo,braceletNo);
        braceletService.updateDeviceNoAndUidByBraceletNo(braceletNo, deviceNo);
        braceletService.updateBraceletStatus(true,braceletNo);

        //插入连接日志
        Device device = deviceService.getByDeviceNo(deviceNo);
        deviceLogsService.insertRecord(device,"phone", "与手环脚环连接");
        deviceLogsService.insertRecord(device,"bracelet", "与手持机连接");
        deviceLogsService.insertRecord(device,"vervel", "与手持机连接");

        return "修改成功";
    }

    @ApiOperation(value = "判断手环是否绑定犯人")
    @RequestMapping(path = "/braceletBind", method = RequestMethod.GET)
    public Boolean braceletBind(@RequestParam("braceletNo") String braceletNo){
        if(braceletService.getPrisonerIdByBraceletNo(braceletNo) == null)
            return false;
        else return true;
    }

    @ApiOperation(value = "手环绑定犯人")
    @RequestMapping(path = "/braceletBindPrisoner", method = RequestMethod.POST)
    public String braceletBindPrisoner(@RequestParam("braceletNo") String braceletNo,
                                                  @RequestParam("prisonerId") String prisonerId){
        braceletService.updatePrisonerIdByBraceletNo(braceletNo, prisonerId);
        return "绑定成功";
    }

    @ApiOperation(value = "获取犯人id和特征")
    @RequestMapping(path = "/prisonerId", method = RequestMethod.GET)
    public String getPrisonerIdByBracelet(@RequestParam("braceletNo") String braceletNo){
        return braceletService.getPrisonerIdByBraceletNo(braceletNo)+ ";" +prisonerService.getPrisonerFeatureByPrisonerId(braceletService.getPrisonerIdByBraceletNo(braceletNo));
    }

    @ApiOperation(value = "检测服务器是否可连")
    @RequestMapping(path = "/isConnectivity", method = RequestMethod.GET)
    public String isConnectivity(){
        return "true";
    }
}