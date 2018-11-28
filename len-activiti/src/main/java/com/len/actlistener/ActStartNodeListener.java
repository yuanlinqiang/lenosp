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
package com.len.actlistener;

import com.len.entity.ActAssignee;
import com.len.entity.BaseTask;
import com.len.entity.UserLeave;
import com.len.service.ActAssigneeService;
import com.len.service.impl.ActAssigneeServiceImpl;
import com.len.util.AssigneeType;
import com.len.util.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaomeng
 * @date 2018/1/20.
 * @email 154040976@qq.com
 * <p>
 * 流程监听器 动态注入节点办理人
 */
public class ActStartNodeListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        //KEY
        String nodeId = delegateTask.getTaskDefinitionKey();

        Map<String, Object> variables = delegateTask.getVariables();
        BaseTask baseTask = (BaseTask) variables.get("baseTask");

        delegateTask.setAssignee(baseTask.getUserId());

    }
}
