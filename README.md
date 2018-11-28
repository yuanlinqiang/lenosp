## 个人微博
[一枚搬运工](http://weibo.com/u/6232732364)
## 团队成员 
[一枚码农](https://gitee.com/bweird)   [JamesZBL](https://gitee.com/zbl1996)    [zhong](https://gitee.com/guokeyunman) [sunhaoyuan](https://gitee.com/ys.re)
## 演示地址

[www.lenosp.cn](http://www.lenosp.cn)  admin 123456

## lenos收录到layui官方2018年度最佳案例名单中
![图片说明](http://ww4.sinaimg.cn/large/0060lm7Tly1fnjfv8d366j310x0hsjwr.jpg "图片说明")

## 交流群
* lenos开源脚手架群① 137738503 您有疑问，我们解答，您有建议，我们吸取，您有idea 我们欢迎
- 群<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=2c71822be7b8c061087a94647663a742a274626a846b76647743ed556a24cabc"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="开源脚手架lenos交流群" title="开源脚手架lenos交流群①"></a>


## 普通版
- 地址：[lenos](https://gitee.com/bweird/lenos) 

## 项目说明
- lenos(p为spring boot版本扩展名)一款快速开发模块化脚手架，采用spring boot 2.0.1+spring+SpringMvc+mybatis+shiro+swagger+ehcache+quartz+freemarker+layui技术开发；实现功能有系统模块：菜单管理、用户管理、角色管理，系统监控：系统日志、接口api、sql监控。本项目会一直维护并集成新的技术，给您的开发节约时间成本，本项目拥有非boot版本。
## 未来蓝图
![图片说明](http://ww2.sinaimg.cn/large/0060lm7Tly1fnw762j02vj30rs0vtjt7.jpg "图片说明")

## 功能说明
- 项目目前拥有
- **系统管理**：_菜单管理、用户管理、角色管理_
- 统一查询 pagehelper分页，查询调用BaseServiceImpl show model set数据，传入T，配合xml编写，即可自定义查询。
- 采用shiro技术，可配置化权限管理，精确到按钮(也可以是某一元素)功能分配
- 登录次数校验，超出定义次数后冻结一段时间账号  
  
- **系统监控**：_系统日志、接口api、系统监控、可配置定时任务_
- 前端可配置化定时任务。
- 采用swagger可视化出实时方法格式以及数据属性，采用阿里druid监控sql。  
  
- **工作流程管理**：完全实现模块化，无侵入。监听用户角色信息到引擎数据表中
- **流程管理**：动态给发布流程分配角色，无需手动写死，灵活可变
- **模块列表**：新建、编辑、发布流程，前端实现完全可配置化建立流程图
- **请假流程**：提供一个请假示例，让您很快熟悉流程
- **待办任务**：个人需要办理的流程任务  
  
- 如果不喜欢工作流，可以直接删除 len-activiti模块  
并在len-web/pom.xml、父 pom.xml 删除依赖以及模块，删除application.java 中对activiti的扫描即可完全删除工作流模块。

## 更新说明
- 18/1/28 **添加工作流模块，前端页面优化，更加有线条**
- 18/4/21 **增加菜单编辑、选择图标功能，前端js封装，修复工作流程现存bug**
- 18/4/23 **修复头像上传**
- 18/5/06 **升级spring boot 到2.0.1**
- 18/6/12 **增加sqlserver支持，脚本在db文件夹下，
只需要在application.yml 切换下active 即可切换数据源**
- 18/6/19 **集成丰富持久化插件tkmapper**
## 头像说明
```
修改 application.yml imagePath 路径 把image文件夹图片赋值进路径，即可正常展示头像
```

## 启动说明

```bash
git clone https://gitee.com/bweird/lenosp.git
mvn clean package
mvn package
java -jar len-web.jar
```
- db使用mysql，项目数据库在 根目录db文件夹下，
导入数据库后 设定数据库用户名密码 在文件lenosp\len-web\src\main\resources\application.yml中
项目开始会报实体类 get set错误，这是正常的，因为本项目entity使用的是 lombok 大大简化了代码量
您可以直接运行，项目可以正常启动。
如何消除？
如果您使用的为idea 只需要file -> setting->plugins->Browse Repositeories 输入 lombok 集成插件重启idea即可消除错误
如果您使用 eclipse 需要下载 lombk jar包 手动集成。

## 技术
* jdk：1.8
* 核心框架：spring boot 2.0.1.RELEASE
* 安全框架：Apache Shiro
* 工作流引擎：Activiti
* 数据库连接池：druid
* 视图框架：spring mvc
* 持久层框架：MyBatis
* 模板引擎：freemarker
* 缓存：ehcache
* 定时：quartz 2.3.0
* 前端页面：layui

## ps
- lenos承诺永久开源，全部免费，无任何收费地方
- 如果您喜欢lenos，可以clone下来使用，您的star将是本人前进的动力，本项目无丝毫保留开源，如果您有技术疑问，可以加群交流。
- 如果lenos对您有一点帮助，您可以点个star，就是对作者最大的支持了。
* lenos脚手架会一直更新下去，我们的征途是星辰大海

## 项目图片
* 登录账号：admin 密码：123456   
  
![图片说明](http://ww1.sinaimg.cn/large/0060lm7Tly1fn2bsi2kexj311y0hsdmw.jpg "图片说明")
![图片说明](http://ww2.sinaimg.cn/large/0060lm7Tly1fn2c1yaqrjj311y0hvdhj.jpg "图片说明")
   
* 完全模块化工作流引擎，可视化建立编辑，动态分配节点处理人
* 监听器监听系统用户、角色实时同步到工作流引擎表
* 提供请假流程示例   
  
![图片说明](http://ww4.sinaimg.cn/large/0060lm7Tly1fnvohtrdglj311y0gggn2.jpg "图片说明")
![图片说明](http://ww3.sinaimg.cn/large/0060lm7Tly1fnvokv38fwj311y0gddgj.jpg "图片说明")
![图片说明](https://s1.ax2x.com/2018/07/14/qvSGY.png "图片说明")
   
* 菜单管理分为一级菜单 二级菜单 按钮(也可以是元素)权限   
  
![图片说明](http://ww2.sinaimg.cn/large/0060lm7Tly1fn2c2l057sj311y0hu767.jpg "图片说明")
   
* 用户可以上传头像   
  
![图片说明](http://ww2.sinaimg.cn/large/0060lm7Tly1fn2c33qyvrj311y0hv40e.jpg "图片说明")
![图片说明](http://ww2.sinaimg.cn/large/0060lm7Tly1fn2c3m4b77j311y0hpq4b.jpg "图片说明")
   
* 自定义定时类，实现Job，前端配置定时类，即可控制任务类，已实现定时类获取spring上下文，
* 项目启动加载完bean后利用spring boot监听开启一个线程，检测已启动的定时任务，进行开启。  
   
![图片说明](http://ww1.sinaimg.cn/large/0060lm7Tly1fn873a0sqnj311y0gc0tr.jpg "图片说明")
![图片说明](http://ww1.sinaimg.cn/large/0060lm7Tly1fn876ntgczj30t707xdgf.jpg "图片说明")
   
* 日志监控 利用aop 自定义拦截日志持久化到数据库并对数据进行监控<br>
  
![图片说明](http://ww3.sinaimg.cn/large/0060lm7Tly1fn8793d3llj311y0gxq4s.jpg "图片说明")
   
* 接口   
 
![图片说明](http://ww2.sinaimg.cn/large/0060lm7Tly1fn2c4swdjrj311y0hptam.jpg "图片说明")
![图片说明](http://ww2.sinaimg.cn/large/0060lm7Tly1fn2c5ev8tgj30w50e7wfs.jpg "图片说明")
![图片说明](http://ww2.sinaimg.cn/large/0060lm7Tly1fn2dvrcl9lj30wd0e6gmd.jpg "图片说明")

如果对你有帮助，可以打赏请作者喝杯咖啡。

由于群成员满员，众筹升级群人数上限，十分感谢以下lenos群网友的捐助和支持，以下为捐助名单(qq昵称，账号，顺序随机)：
```
我有一块大腹肌(18****256) 35.54元
江湖百晓生 (26****417) 5元
有道(10*****838) 1.5元
24岁程序员不想加班(10*****286) 0.56元
水兮若龙(73****965) 1元
旭阳(13*****126) 6.6元
你是一个人物(32*****869) 1元
笨蛋~猪头]_[(23*****266) 0.45元
人丑脾气坏(35****702) 0.5元
襄州绿毛龟(18****933) 0.5元
小码*(53****051) 0.5元
AspectJ(13*****013) 0.5元
不以物喜，不以己悲 (11*****011) 0.5元
Lotus(37****374) 0.5元
书山有路(50****945) 0.5元
欧阳(24****748) 0.5元
π(12*****085) 0.5元
laughing(89****060)0.5元
十八画生(10*****400) 0.5元
YS.RE(27****583) 0.5元
小翼(40****384) 20元
远方(16*****408) 1元
像我这样的人(41****236) 0.46元
磊哥(87****537) 12元
tj(64****183) 20元
fenghaotong（码云）(83****531) 10元
```
