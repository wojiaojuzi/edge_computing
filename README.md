# edge_computing_service
---
## 遇到的问题
### 吴姿颍  
* **运行项目报错： Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required**  
**解决方式：** 删除所有配置文件（.iml、.idea、.mvvm等），删除pom文件里的spring-test包，将spring版本由2.1.3改为1.5.7，重新运行项目，此时还是报错，但是错误变为找不到ibatis这个依赖。尝试把pom文件里的依赖顺序调换了一下，就不报错了。具体出错原因不明，解决方法仅供参考，后续有时间再尝试查找问题原因。  

* **用mybatis注解@Select查询数据库，返回数据部分字段为null，但是数据库里有值**  
**问题原因：** 查询出来的数据，与实体bean的字段不相匹配，导致null。实体bean的字段为驼峰形式，如deviceNo, 而数据库为下划线device_no,因此，匹配不上，导致映射失败。  
**解决方式：** 在application.properties里面配置mybatis，开启驼峰命名转换：mybatis.configuration.map-underscore-to-camel-case=true  

* **服务端利用webSocket向客户端主动推送消息，运行项目报错：javax.websocket.DecodeException: Failed to decode path parameter value [null] to expected type [long]**  
**解决方式：** 在网上找了一个人写的demo，将代码完全复制过来，能够直接跑通，但是两份代码几乎没有区别，所以具体原因并不清楚，百度，google也没有人遇到过类似错误。demo地址：[https://github.com/LuckyToMeet-Dian-N/springboot_Learn_9](https://github.com/LuckyToMeet-Dian-N/springboot_Learn_9)  

* **webSocket向指定客户端推送消息一直失败**  
**问题原因：** 用于记录指定客户端sessionId的属性为private String类型，不是静态属性  
**解决方式：** 将targetClientSessionId改为static静态属性  

* **@RequestBody注解导致android端请求失败，错误码400**  
**问题原因：** @RequestBody注解用于接收content-type为application/json的数据，而android客户端采用OkHttp发送请求，封装的请求方法采用默认的application/x-www-form-urlcoded编码  
**解决方式：** 将@RequestBody改为@RequestParam，android客户端post提交表单。参考CSDN博客：[https://blog.csdn.net/ljxbbss/article/details/74452326](https://blog.csdn.net/ljxbbss/article/details/74452326)，[https://blog.csdn.net/SmileorSilence/article/details/82996105](https://blog.csdn.net/SmileorSilence/article/details/82996105)，[https://blog.csdn.net/blueheart20/article/details/45174399](https://blog.csdn.net/blueheart20/article/details/45174399)  

* **查询数据库中的记录，返回的数据的create_at字段为null**  
**问题原因：** 实体类中的属性名一般为驼峰命名，而在数据库中一般以_连接单词来命名，由于在properties文件中打开了驼峰命名转换，因此在查询数据时就无法查询到实体类中以‘_’连接命名的属性值  
**解决方式：** 将实体类中所有属性名都改为以驼峰命名法来命名.参考CSDN博客：[https://blog.csdn.net/puhaiyang/article/details/79183638](https://blog.csdn.net/puhaiyang/article/details/79183638)  