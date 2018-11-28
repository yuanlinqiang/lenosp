package test;

import com.len.Application;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author zhuxiaomeng
 * @date 2018/1/20.
 * @email 154040976@qq.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ActivitiVariable {

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
  public void leaveStart(){
    //使用deploy方法发布流程
    Deployment deployment=repositoryService.createDeployment()
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
    query.processDefinitionKey("studentLeaveProcess");//通过key获取
    List<ProcessDefinition> pds = query.list();
    for (ProcessDefinition pd : pds) {
      System.out.println("ID:"+pd.getId()+"\nNAME:"+pd.getName()+"\nKEY:"+pd.getKey()+"\nVERSION:"+pd.getVersion()+""
          + "\nRESOURCE_NAME:"+pd.getResourceName()+",DGRM_RESOURCE_NAME:"+pd.getDiagramResourceName());
    }
  }

  /**
   * 发布流程
   */
  @Test
  public void startFlow(){
    ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("studentLeaveProcess");
    System.out.println("流程实例id:"+processInstance.getId());
    System.out.println("流程定义id:"+processInstance.getProcessDefinitionId());

  }

  @Test
  public void queryTask(){
    List<Task> taskList=taskService.createTaskQuery().taskAssignee("李四")
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
    String taskId = "55002";
    //完成请假申请任务
    taskService.complete(taskId );
  }

  @Test
  public void setMsg(){
    Map<String,Object> objectMap=new HashMap<>();
    objectMap.put("day",5);
    objectMap.put("date",new Date());
    objectMap.put("reason","测试请假");
    taskService.setVariables("47504",objectMap);
  }

  /**
   * 运行时设置任务变量 taseService.setVariables
   * 也可以设置流程变量  runtimeService.setVariables 效果一样
   * 流程启动设置流程变量
   *
   */
  @Test
  public void getMsg(){
    Map<String,Object> objectMap=taskService.getVariables("55002");
    //runtimeService.setVariables();
    System.out.println(objectMap.get("day"));
    System.out.println(objectMap.get("date"));
    System.out.println(objectMap.get("reason"));
  }
}
