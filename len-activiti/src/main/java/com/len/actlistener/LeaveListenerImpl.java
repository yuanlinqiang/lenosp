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

import com.len.entity.UserLeave;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;

/**
 * @author zhuxiaomeng
 * @date 2018/1/25.
 * @email 154040976@qq.com
 * <p>
 * 自定义请假流程 监听器
 */
public class LeaveListenerImpl extends ActNodeListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        super.notify(delegateTask);
        String taskId = delegateTask.getId();
        Map<String, Object> map = delegateTask.getVariables();
        UserLeave userLeave = (UserLeave) map.get("userLeave");
        delegateTask.addCandidateUser(userLeave.getUserId());
    }
}
