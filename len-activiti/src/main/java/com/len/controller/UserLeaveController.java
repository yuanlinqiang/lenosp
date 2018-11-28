/**
 * Copyright 2018 lenos
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.len.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.base.CurrentUser;
import com.len.core.shiro.ShiroUtil;
import com.len.entity.BaseTask;
import com.len.entity.LeaveOpinion;
import com.len.entity.SysRoleUser;
import com.len.entity.UserLeave;
import com.len.exception.MyException;
import com.len.service.RoleUserService;
import com.len.service.UserLeaveService;
import com.len.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.HMProcessDiagramGenerator;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

/**
 * @author zhuxiaomeng
 * @date 2018/1/21.
 * @email 154040976@qq.com
 * <p>
 * 请假流程示例
 */
@Controller
@RequestMapping("/leave")
public class UserLeaveController extends BaseController {

    @Autowired
    UserLeaveService leaveService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    IdentityService identityService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ProcessEngineFactoryBean processEngine;

    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    RoleUserService roleUserService;

    private String leaveOpinionList = "leaveOpinionList";


    @GetMapping(value = "showMain")
    public String showMain(Model model) {
        return "/dashboard/dashboard";
    }


    @GetMapping(value = "showMain2")
    public String showMain2(Model model) {
        return "/dashboard/dashboard2";
    }

    @GetMapping(value = "showLeave")
    public String showUser(Model model) {
        return "/act/leave/leaveList";
    }

    @GetMapping(value = "showLeaveList")
    @ResponseBody
    public ReType showLeaveList(Model model, UserLeave userLeave, String page, String limit) {
        String userId = CommonUtil.getUser().getId();
        userLeave.setUserId(userId);
        List<UserLeave> tList = null;
        Page<UserLeave> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            tList = leaveService.selectListByPage(userLeave);
            for (UserLeave leave : tList) {
                ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                        .processInstanceId(leave.getProcessInstanceId()).singleResult();
                //保证运行ing
                if (instance != null) {
                    Task task = this.taskService.createTaskQuery().processInstanceId(leave.getProcessInstanceId()).singleResult();
                    leave.setTaskName(task.getName());
                }
            }
        } catch (MyException e) {
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tList);
    }

    /**
     * 根据 执行对象id获取审批信息
     *
     * @param model
     * @param processId
     * @return
     */
    @GetMapping("leaveDetail")
    public String leaveDetail(Model model, String processId) {
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processId).singleResult();
        //保证运行ing
        List<LeaveOpinion> leaveList = null;
        List<HistoricActivityInstance> historicActivityInstanceList = new ArrayList<>();
        if (instance != null) {
            Task task = this.taskService.createTaskQuery().processInstanceId(processId).singleResult();
            Map<String, Object> variables = taskService.getVariables(task.getId());
            Object o = variables.get(leaveOpinionList);
            if (o != null) {
        /*获取历史审核信息*/
                leaveList = (List<LeaveOpinion>) o;
            }
        } else {
            leaveList = new ArrayList<>();
            List<HistoricDetail> list = historyService.createHistoricDetailQuery().
                    processInstanceId(processId).list();
            HistoricVariableUpdate variable = null;
            for (HistoricDetail historicDetail : list) {
                variable = (HistoricVariableUpdate) historicDetail;
                String variableName = variable.getVariableName();
                if (leaveOpinionList.equals(variable.getVariableName())) {
                    leaveList.clear();
                    leaveList.addAll((List<LeaveOpinion>) variable.getValue());
                }
            }
        }
        model.addAttribute("leaveDetail", JSON.toJSONString(leaveList));
        return "/act/leave/leaveDetail";
    }

    @GetMapping("addLeave")
    public String addLeave() {
        return "/act/leave/add-leave";
    }

    @GetMapping("updateLeave/{taskId}")
    public String updateLeave(Model model, @PathVariable String taskId) {
        Map<String, Object> variables = taskService.getVariables(taskId);
        BaseTask baseTask = (BaseTask) variables.get("baseTask");
        UserLeave leave = leaveService.selectByPrimaryKey(baseTask.getId());
        model.addAttribute("leave", leave);
        model.addAttribute("taskId", taskId);
        return "/act/leave/update-leave";
    }

    @PostMapping("updateLeave/updateLeave/{taskId}/{id}/{flag}")
    @ResponseBody
    public JsonUtil updateLeave(UserLeave leave, @PathVariable String taskId, @PathVariable String id, @PathVariable boolean flag) {
        JsonUtil j = new JsonUtil();
        try {
            UserLeave oldLeave = leaveService.selectByPrimaryKey(leave.getId());
            BeanUtil.copyNotNullBean(leave, oldLeave);
            leaveService.updateByPrimaryKeySelective(oldLeave);

            Map<String, Object> variables = taskService.getVariables(taskId);
//            UserLeave userLeave = (UserLeave) variables.get("userLeave");
            Map<String, Object> map = new HashMap<>();
            if (flag) {
                map.put("flag", true);
            } else {
                map.put("flag", false);
            }
            taskService.complete(taskId, map);
            j.setMsg("保存成功");
        } catch (MyException e) {
            j.setMsg("保存失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }


    @PostMapping("addLeave")
    @ResponseBody
    public JsonUtil addLeave(Model model, UserLeave userLeave) {
        JsonUtil j = new JsonUtil();
        if (userLeave == null) {
            return JsonUtil.error("获取数据失败");
        }
        userLeave.setDays(3);
        CurrentUser user = CommonUtil.getUser();
        userLeave.setUserId(user.getId());
        userLeave.setUserName(user.getUsername());
        userLeave.setProcessInstanceId("2018");//模拟数据
        leaveService.insertSelective(userLeave);
        Map<String, Object> map = new HashMap<>();
        userLeave.setUrlpath("/leave/readOnlyLeave/"+userLeave.getId());
        map.put("baseTask",(BaseTask) userLeave);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_leave", map);
        userLeave.setProcessInstanceId(processInstance.getId());
        UserLeave userLeave1 = leaveService.selectByPrimaryKey(userLeave.getId());
        BeanUtil.copyNotNullBean(userLeave, userLeave1);
        userLeave1.setUrlpath("/leave/readOnlyLeave/"+userLeave.getId());
        leaveService.updateByPrimaryKeySelective(userLeave1);
        if (processInstance == null) {
            return JsonUtil.error("未识别key");
        }
        j.setMsg("请假申请成功");
        return j;
    }

    @GetMapping("readOnlyLeave/{billId}")
    public String readOnlyLeave(Model model, @PathVariable String billId) {
        UserLeave leave = leaveService.selectByPrimaryKey(billId);
        model.addAttribute("leave", leave);
        return "/act/leave/update-leave-readonly";
    }

    /**
     * ---------我的任务---------
     */
    @GetMapping(value = "showTask")
    public String showTask(Model model) {
        return "/act/task/taskList";
    }

    @GetMapping(value = "showTaskList")
    @ResponseBody
    public String showTaskList(Model model, com.len.entity.Task task, String page, String limit) {
        CurrentUser user = CommonUtil.getUser();
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setUserId(user.getId());
        List<SysRoleUser> userRoles = roleUserService.selectByCondition(sysRoleUser);
        List<String> roleString = new ArrayList<String>();
        for(SysRoleUser sru:userRoles)
        {
            roleString.add(sru.getRoleId());
        }
        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(user.getId()).list();
        List<Task> assigneeList =taskService.createTaskQuery().taskAssignee(user.getId()).list();
        List<Task> candidateGroup =taskService.createTaskQuery().taskCandidateGroupIn(roleString).list();
        taskList.addAll(assigneeList);
        taskList.addAll(candidateGroup);
        List<com.len.entity.Task> tasks = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        com.len.entity.Task taskEntity = null;

        Map<String, Map<String, Object>> mapMap = new HashMap<>();
        Map<String, Object> objectMap = null;
        Set<String> taskSet = new HashSet<String>();
        for (Task task1 : taskList) {
            objectMap = new HashMap<>();
            String taskId = task1.getId();
            if(taskSet.contains(taskId))
            {
                continue;
            }

            map = taskService.getVariables(taskId);
            BaseTask userLeave = (BaseTask) map.get("baseTask");

            taskEntity = new com.len.entity.Task(task1);
            taskEntity.setUserName(userLeave.getUserName());
            taskEntity.setReason(userLeave.getReason());
            taskEntity.setUrlpath(userLeave.getUrlpath());
            /**如果是自己*/
            if (user.getId().equals(userLeave.getUserId()) ) {
                if( map.get("flag")!=null)
                {
                    if(!(boolean) map.get("flag"))
                    {
                        objectMap.put("flag", true);
                    }else
                    {
                        objectMap.put("flag", false);
                    }
                }else
                {
                    objectMap.put("flag", true);
                }
            } else {
                objectMap.put("flag", false);
            }
            mapMap.put(taskEntity.getId(), objectMap);
            tasks.add(taskEntity);
            taskSet.add(taskId);
        }
        return ReType.jsonStrng(taskList.size(), tasks, mapMap, "id");
    }

    @GetMapping("agent/{id}")
    public String agent(Model model, @PathVariable("id") String taskId) {
        Map<String, Object> variables = taskService.getVariables(taskId);
        BaseTask baseTask = (BaseTask) variables.get("baseTask");
//        UserLeave userLeave = leaveService.selectByPrimaryKey(baseTask.getId());
        model.addAttribute("leaveUrl", baseTask.getUrlpath());
        model.addAttribute("taskId", taskId);
        return "/act/task/task-agent-iframe";
    }

    @PostMapping("agent/complete")
    @ResponseBody
    public JsonUtil complete(LeaveOpinion op, HttpServletRequest request) {
        Map<String, Object> variables = taskService.getVariables(op.getTaskId());

        CurrentUser user = ShiroUtil.getCurrentUse();
        op.setCreateTime(new Date());
        op.setOpId(user.getId());
        op.setOpName(user.getRealName());
        JsonUtil j = new JsonUtil();
        Map<String, Object> map = new HashMap<>();
        map.put("flag", op.isFlag());

        //判断节点是否已经拒绝过一次了
        Object needend = variables.get("needend");
        if(needend!=null && (boolean ) needend &&  (!op.isFlag()) )
        {
            map.put("needfinish",-1); //结束
        }else
        {
            if(op.isFlag())
            {
                map.put("needfinish",1);//通过下一个节点
            }else
            {
                map.put("needfinish",0);//不通过
            }
        }
        //审批信息叠加
        List<LeaveOpinion> leaveList = new ArrayList<>();
        Object o = variables.get(leaveOpinionList);
        if (o != null) {
            leaveList = (List<LeaveOpinion>) o;
        }
        leaveList.add(op);
        map.put(leaveOpinionList, leaveList);
        j.setMsg("审核成功" + (op.isFlag() ? "<font style='color:green'>[通过]</font>" : "<font style='color:red'>[未通过]</font>"));
        taskService.complete(op.getTaskId(), map);
        return j;
    }

    @Autowired
    HistoryService historyService;

    /**
     * 追踪图片成图
     * 增加历史流程
     * @param request
     * @param resp
     * @param processInstanceId
     * @throws IOException
     */
    @GetMapping("getProcImage")
    public void getProcImage(HttpServletRequest request, HttpServletResponse resp, String processInstanceId)
            throws IOException {
        InputStream imageStream = generateStream(request,resp,processInstanceId,true);
        if(imageStream==null)
        {
            return;
        }
        InputStream imageNoCurrentStream = generateStream(request,resp,processInstanceId,false);
        if(imageNoCurrentStream==null)
        {
            return;
        }

        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.setTransparent(Color.BLACK);
        e.setRepeat(0);
        e.setQuality(19);
        e.start(resp.getOutputStream());

        BufferedImage current = ImageIO.read(imageStream); // 读入需要播放的jpg文件
        e.addFrame(current);  //添加到帧中

        e.setDelay(200); //设置播放的延迟时间
        BufferedImage nocurrent = ImageIO.read(imageNoCurrentStream); // 读入需要播放的jpg文件
        e.addFrame(nocurrent);  //添加到帧中

        e.finish();

//        byte[] b = new byte[1024];
//        int len;
//        while ((len = imageStream.read(b, 0, 1024)) != -1) {
//            resp.getOutputStream().write(b, 0, len);
//        }
    }

    //只读图片页面
    @GetMapping("shinePics/{processInstanceId}")
    public String shinePics(Model model, @PathVariable String processInstanceId) {
        model.addAttribute("processInstanceId", processInstanceId);
        return "/act/leave/shinePics";
    }

    @GetMapping("getShineProcImage")
    @ResponseBody
    public String getShineProcImage(HttpServletRequest request, HttpServletResponse resp, String processInstanceId)
            throws IOException {
        JSONObject result = new JSONObject();
        JSONArray shineProImages = new JSONArray();
        BASE64Encoder encoder  = new BASE64Encoder();
        InputStream imageStream = generateStream(request,resp,processInstanceId,true);
        if(imageStream!=null)
        {
            String  imageCurrentNode = Base64Utils.ioToBase64(imageStream);
            if(StringUtils.isNotBlank(imageCurrentNode))
            {
                shineProImages.add(imageCurrentNode);
            }
        }
        InputStream imageNoCurrentStream = generateStream(request,resp,processInstanceId,false);
        if(imageNoCurrentStream!=null)
        {
            String  imageNoCurrentNode = Base64Utils.ioToBase64(imageNoCurrentStream);
            if(StringUtils.isNotBlank(imageNoCurrentNode))
            {
                shineProImages.add(imageNoCurrentNode);
            }
        }
        result.put("id",UUID.randomUUID().toString());
        result.put("errorNo",0);
        result.put("images",shineProImages);
        return result.toJSONString();
    }



    public InputStream generateStream(HttpServletRequest request, HttpServletResponse resp, String processInstanceId,boolean needCurrent)
    {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = null;
        List<String> executedActivityIdList = new ArrayList<String>();
        List<String> currentActivityIdList = new ArrayList<>();
        List<HistoricActivityInstance> historicActivityInstanceList = new ArrayList<>();
        if (processInstance != null) {
            processDefinitionId = processInstance.getProcessDefinitionId();
            if(needCurrent)
            {
                currentActivityIdList = this.runtimeService.getActiveActivityIds(processInstance.getId());
            }
        }  if (historicProcessInstance != null) {
        processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        historicActivityInstanceList =
                historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();
        for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
            executedActivityIdList.add(activityInstance.getActivityId());
        }
    }

        if (StringUtils.isEmpty(processDefinitionId) || executedActivityIdList.isEmpty()) {
            return null;
        }

        //高亮线路id集合
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity,historicActivityInstanceList);

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        //List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        HMProcessDiagramGenerator diagramGenerator = (HMProcessDiagramGenerator) processEngineConfiguration.getProcessDiagramGenerator();
        //List<String> activeIds = this.runtimeService.getActiveActivityIds(processInstance.getId());

        InputStream imageStream = diagramGenerator.generateDiagram(
                bpmnModel, "png",
                executedActivityIdList,highLightedFlows,
                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                processEngine.getProcessEngineConfiguration().getLabelFontName(),
                "宋体",
                null, 1.0,currentActivityIdList);

        return imageStream;
    }

    /**
     * 获取需要高亮的线
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {

        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }


}
