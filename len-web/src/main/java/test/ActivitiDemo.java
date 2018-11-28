package test;

import com.len.Application;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author zhuxiaomeng
 * @date 2018/1/11.
 * @email 154040976@qq.com
 *
 * 本demo 根据官网doc教程 address：http://www.mossle.com/docs/activiti/index.html
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ActivitiDemo {

  @Autowired
  RuntimeService runtimeService;

  @Autowired
  RepositoryService repositoryService;

  @Autowired
  TaskService taskService;

  @Autowired
  HistoryService historyService;


  /**
   * 发布流程
   */
  @Test
  public void test(){
    repositoryService.createDeployment()
        .name("demo.bar")
        .addClasspathResource("bpmn/demo.xml")

        .deploy();
    System.out.println(repositoryService.createDeploymentQuery().count());
    /*long count = runtimeService.createProcessInstanceQuery()
        .count();
    System.out.println(count);*/
  }

  /**
   * 启动一个流程
   */
  @Test
  public void startUp(){
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("employeeName", "张三");//张三
    variables.put("numberOfDays", new Integer(4));//请假四天
    variables.put("vacationMotivation", "太累了");//原因
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);
    System.out.println( runtimeService.createProcessInstanceQuery().count());
  }

  /**
   * 完成流程
   */
  @Test
  public void complete(){
    List<Task> taskList=taskService.createTaskQuery().taskCandidateGroup("management").list();
    for(Task task:taskList){
      System.out.println(task.getId());
      System.out.println(task.getName());
      System.out.println(task.getAssignee());
    }
    Task task=taskList.get(0);
    Map<String, Object> taskVariables = new HashMap<String, Object>();
    taskVariables.put("vacationApproved", "false");
    taskVariables.put("managerMotivation", "我们时间很紧！");
    taskService.complete(task.getId(), taskVariables);
  }

  /**
   * 激活、挂起
   */
  @Test
  public void gq(){
    repositoryService.suspendProcessDefinitionByKey("vacationRequest");
    try {
      runtimeService.startProcessInstanceByKey("vacationRequest");
    } catch (ActivitiException e) {
      e.printStackTrace();
    }
  }

  /**---------请假流程------------*/
  /**
   * 发布流程
   */
  @Test
  public void leaveStart(){
    //InputStream in = this.getClass().getClassLoader().getResourceAsStream("bpmn/leave.zip");
   // ZipInputStream zipInputStream = new ZipInputStream(in);
    //使用deploy方法发布流程
    Deployment deployment=repositoryService.createDeployment()
        ///.addZipInputStream(zipInputStream)
        .addClasspathResource("bpmn/leave.bpmn")
        .addClasspathResource("bpmn/leave.png")
        .name("请假流程")
        .deploy();
    System.out.println("流程id:"+deployment.getId());
    System.out.println("流程名称:"+deployment.getName());
  }

  /**
   * 获取流程详细信息
   */
  @Test
  public void queryProcdef(){
    //创建查询对象
    ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
    //添加查询条件
    query.processDefinitionKey("process");//通过key获取
//     .processDefinitionName("Myleave");//通过name获取
    // .orderByProcessDefinitionId()//根据ID排序
    //执行查询获取流程定义明细
    List<ProcessDefinition> pds = query.list();
    for (ProcessDefinition pd : pds) {
      System.out.println("ID:"+pd.getId()+"\nNAME:"+pd.getName()+"\nKEY:"+pd.getKey()+"\nVERSION:"+pd.getVersion()+""
          + "\nRESOURCE_NAME:"+pd.getResourceName()+",DGRM_RESOURCE_NAME:"+pd.getDiagramResourceName());
    }
  }

  /**
   * 发布流程 根据流程定义表的key 也就是 xml中的 id
   *
   * 绑定 userid 并且获取流程定义id 插入到请假表中
   */
  @Test
  public void startFlow(){
    Map<String, Object> variables=new HashMap<>();
    variables.put("userId","2211fec3e17c11e795ed201a068c6482");
    variables.put("userRole","0ea934e5e55411e7b983201a068c6482");
    variables.put("hrRole","2619a672e53811e7b983201a068c6482");
    variables.put("msg","请假一天");
    ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("process",variables);
    //System.out.println("id:"+processInstance.getId()+",activitiId:"+processInstance.getActivityId());
    System.out.println("流程实例id:"+processInstance.getId());
    System.out.println("流程定义id:"+processInstance.getProcessDefinitionId());

  }

  @Test
  public void queryTask(){
    List<Task> taskList=taskService.createTaskQuery().taskAssignee("张三")
        .list();
    for(Task task:taskList){
      System.out.println("ID:"+task.getId()+"\n姓名:"+task.getName()+"\n接收人:"+task.getAssignee()+"\n开始时间:"+task.getCreateTime());
    }

  }

  /**
   * 完成审批
   */
  @Test
  public void startTask(){
    //taskId 就是查询任务中的 ID
    String taskId = "30002";
    //完成请假申请任务
    taskService.complete(taskId );
  }

  @Test
  public void queryTaskBoss(){
    List<Task> taskList=taskService.createTaskQuery().taskAssignee("老板")
        .list();
    for(Task task:taskList){
      System.out.println("ID:"+task.getId()+",姓名:"+task.getName()+",接收人:"+task.getAssignee()+",开始时间:"+task.getCreateTime());
    }

  }
  /**
   * 完成审批
   */
  @Test
  public void startTaskBoss(){
    //taskId 就是查询任务中的 ID
    String taskId = "82502";
    //完成请假申请任务
    taskService.complete(taskId );
  }

  @Test
  public void isRun(){
    ProcessInstance instance= runtimeService.createProcessInstanceQuery()
        .processInstanceId("25001").singleResult();
    if(instance!=null){
      System.out.println("流程正在执行");
    }else{
      System.out.println("流程执行完毕");

    }
  }

  @Test
  public void history(){
   List<HistoricTaskInstance> instances= historyService.createHistoricTaskInstanceQuery()//创建历史实例查询
        .processInstanceId("25001") //流程实例id
        .finished().list();//已经完成流程实例
    for(HistoricTaskInstance hTask:instances){
      System.out.println("历史id:"+hTask.getId());
      System.out.println("流程实例id:"+hTask.getProcessInstanceId());
      System.out.println("任务名称:"+hTask.getName());
      System.out.println("办理人:"+hTask.getAssignee());
    }
  }

  @Test
  public void getDay(){

    Map<String,Object> map =taskService.getVariables("80008");
    System.out.println(map.get("userId"));
    System.out.println(map.get("msg"));
  }

  @Test
  public void getTask(){
    List<Task> taskList=taskService
        .createTaskQuery().taskCandidateUser("acfc0e9232f54732a5d9ffe9071bf572").list();//.taskAssignee("王五").list();

    for(Task task:taskList){
      System.out.println("任务id:"+task.getId());
      System.out.println("任务名称:"+task.getName());
      System.out.println("创建时间:"+task.getCreateTime());
      System.out.println("委派人:"+task.getAssignee());
      System.out.println("流程实例id:"+task.getProcessInstanceId());
    }
  }

  @Test
  public void getMsg(){
   /* Map<String,Object> map =taskService.getVariables("70002");
    System.out.println(map.get("mesage"));*/
    /*List<Task> taskList=taskService.createTaskQuery()
        .taskCandidateOrAssigned("2211fec3e17c11e795ed201a068c6482").orderByTaskCreateTime().desc().list();
    System.out.println(taskList.size());*/
    /*List<HistoricTaskInstance> instances=historyService.createHistoricTaskInstanceQuery()
      .taskCandidateUser("acfc0e9232f54732a5d9ffe9071bf572")
      .list();
    System.out.println(instances.size());*/
   /* ProcessInstance pi = runtimeService//表示正在执行的流程实例和执行对象
        .createProcessInstanceQuery()//创建流程实例查询
        .processInstanceId("67501")//使用流程实例ID查询
        .singleResult();
    if(pi!=null){
      System.out.println("还在运行");
    }else{
      System.out.println("已经结束");
    }
    HistoricActivityInstance hai=historyService.createHistoricActivityInstanceQuery()//
        .processInstanceId("67501")//
        .unfinished()//未完成的活动(任务)
        .singleResult();
    System.out.println(hai);*/
   String deploymentId="90004";

    ProcessDefinition processDefinition=repositoryService
        .createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
    ProcessDefinitionEntity processDefinitionEntity=(ProcessDefinitionEntity)((RepositoryServiceImpl) repositoryService)
        .getDeployedProcessDefinition(processDefinition.getId());
    List<ActivityImpl> activityList=processDefinitionEntity.getActivities();




    for(ActivityImpl activiti:activityList) {
      System.out.println("节点ID:"+activiti.getId());
      System.out.println("节点名称:"+activiti.getProperty("name"));
    }
}

  /**
   * 根据 流程部署id和资源文件名获取图片
   * @throws Exception
   */
  @Test
  public void imageById()throws Exception{
    InputStream inputStream=repositoryService.getResourceAsStream("12501","bpmn/leave.png");
    FileOutputStream outputStream=new FileOutputStream("F:/a.png");
    byte[] b=new byte[1024];
    int red=inputStream.read(b);
    while(red!=-1){
      outputStream.write(b,0,red);
      red=inputStream.read(b);
    }
    outputStream.write(b);
    inputStream.close();
    outputStream.close();
  }

  @Test
  public void del(){
   repositoryService.deleteDeployment("137534", true); // 默认是false true就是级联删除
    System.out.println("刪除流程定义");
  }
  @Autowired
  ProcessEngineFactoryBean processEngine;

  @Autowired
  ProcessEngineConfiguration processEngineConfiguration;
  @Test
  public void image() throws Exception {
    String processInstanceId="147540";
    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
    List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
    processEngineConfiguration = processEngine.getProcessEngineConfiguration();
    Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
    ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
    List<String> activeIds = this.runtimeService.getActiveActivityIds(processInstance.getId());

    InputStream imageStream=diagramGenerator.generateDiagram(
        bpmnModel, "png",
        activeIds, Collections.<String>emptyList(),
        processEngine.getProcessEngineConfiguration().getActivityFontName(),
        processEngine.getProcessEngineConfiguration().getLabelFontName(),
        "宋体",
        null, 1.0);
    byte[] b = new byte[1024];
    int len;
    FileOutputStream outputStream=new FileOutputStream("F:/1.png");
    while ((len = imageStream.read(b, 0, 1024)) != -1) {
      outputStream.write(b,0,len);
    }
  }




}
