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

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.activiti.engine.repository.Deployment;

/**
 * @author zhuxiaomeng
 * @date 2018/1/15.
 * @email 154040976@qq.com
 * 流程部署表
 */
@Getter
@Setter
public class ActDeployment implements Serializable {
  private String id;
  private String name;
  private Date deploymentTime;
  private String category;
  private String tenantId;

  public ActDeployment() {
  }

  public ActDeployment(Deployment deployment) {
    this.id = deployment.getId();
    this.name = deployment.getName();
    this.deploymentTime = deployment.getDeploymentTime();
    this.category = deployment.getCategory();
    this.tenantId = deployment.getTenantId();
  }
}
