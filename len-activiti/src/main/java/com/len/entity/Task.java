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
package com.len.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhuxiaomeng
 * @date 2018/1/21.
 * @email 154040976@qq.com
 *
 * 流程任务
 */

@Getter
@Setter
@ToString
public class Task {
  private String id;
  private String name;
  private Date createTime;
  private String assignee;
  private String processInstanceId;//流程实例id
  private String processDefinitionId;//流程定义id
  private String description;
  private String category;

  private String userName;
  private String reason;
  private String urlpath;
  public Task() {
  }
  public Task(org.activiti.engine.task.Task t) {
    this.id=t.getId();
    this.name=t.getName();
    this.createTime=t.getCreateTime();
    this.assignee=t.getAssignee();
    this.processInstanceId=t.getProcessInstanceId();
    this.processDefinitionId=t.getProcessDefinitionId();
    this.description=t.getDescription();
    this.category=t.getCategory();
  }
}
