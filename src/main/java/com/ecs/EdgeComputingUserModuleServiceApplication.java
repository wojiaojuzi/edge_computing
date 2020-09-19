package com.ecs;

import com.ecs.cmd.InitFront;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@MapperScan("com.ecs.mapper")
@EnableAsync
public class EdgeComputingUserModuleServiceApplication {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(EdgeComputingUserModuleServiceApplication.class);
        logger.debug("start application");
        SpringApplication.run(EdgeComputingUserModuleServiceApplication.class, args);
//        InitFront initFront = new InitFront();
//
//
//        try{
//            //创建一个服务器对象，端口8090
//            ServerSocket serverSocket=new ServerSocket(8090);
//            //创建一个客户端对象，这里的作用是用作多线程，必经服务器服务的不是一个客户端
//            Socket client=null;
//            boolean flag=true;
//
//            while(flag){
//                System.out.println("服务器已启动，等待客户端请求。。。。");
//                //accept是阻塞式方法，对新手来说这里很有可能出错，下面的注意事项我会说到
//                client=serverSocket.accept();
//                //创建一个线程，每个客户端对应一个线程
//                new Thread(new EchoThread(client)).start();
//            }
//            client.close();
//            serverSocket.close();
//            System.out.println("服务器已关闭。");
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//

    }
}

/*
*   2次联调
*   1、添加前端自动，cmd文件夹和主函数里的一行（Ubuntu部署代码修改，不在本代码内）
*   2、将云分开到cloudService，使用异步方式调用云上传接口（cloudService)
*   3、添加escapeGpsMapper（未完成）(EscapeGPSMapper)
*   4、添加烦人逃跑后GPS监控（未完成）(abnormalControl）
*/

/*
* 第一次联调
* 1、日志过长
* 2、devices get_all中prisonerName字段没有
* 3、获取烦人id（devices)
* 4、添加高度、管道等数据，添加get_all字段将数据推到云中心
* 5、添加端口监听
* */
