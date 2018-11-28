package test;

import com.len.Application;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
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
 * @date 2018/1/11.
 * @email 154040976@qq.com
 *
 * 本demo 根据官网doc教程 address：http://www.mossle.com/docs/activiti/index.html
 *
 * 网关
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ActivitiGateWay {

  @Autowired
  RuntimeService runtimeService;

  @Autowired
  RepositoryService repositoryService;

  @Autowired
  TaskService taskService;

  @Autowired
  HistoryService historyService;


  /**---------请假流程------------*/
  /**
   * 发布流程
   */
  @Test
  public void leaveStart(){
    //使用deploy方法发布流程
    Deployment deployment=repositoryService.createDeployment()
        .addClasspathResource("bpmn/leave2.bpmn")
        .addClasspathResource("bpmn/leave2.png")
        .name("请假流程2")
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
    query.processDefinitionKey("studentLeaveProcess2");//通过key获取
    //执行查询获取流程定义明细
    List<ProcessDefinition> pds = query.list();
    for (ProcessDefinition pd : pds) {
      System.out.println("ID:"+pd.getId()+"\nNAME:"+pd.getName()+"\nKEY:"+pd.getKey()+"\nVERSION:"+pd.getVersion()+""
          + "\nRESOURCE_NAME:"+pd.getResourceName()+",DGRM_RESOURCE_NAME:"+pd.getDiagramResourceName());
    }
  }

  /**
   * 发布流程 根据流程定义表的key 也就是 xml中的 id
   */
  @Test
  public void startFlow(){
    ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("studentLeaveProcess2");
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
    String taskId = "22502";
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

  /**
   * 根据 流程部署id和资源文件名获取图片
   * @throws Exception
   */
  @Test
  public void imageById()throws Exception{
    InputStream inputStream=repositoryService.getResourceAsStream("12501","bpmn/leave2.png");
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

}
