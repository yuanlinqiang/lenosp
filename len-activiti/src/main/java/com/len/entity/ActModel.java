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
import org.activiti.engine.repository.Model;

/**
 * @author zhuxiaomeng
 * @date 2018/1/18.
 * @email 154040976@qq.com
 * 模型列表
 */
@Getter
@Setter
public class ActModel {

  private String id;
  private String name;
  private String key;
  private String category;
  private Date createTime;
  private Date lastUpdateTime;
  private Integer version;
  private String metaInfo;
  private String deploymentId;
  private String tenantId;
  private boolean hasEditorSource;


  public ActModel() {
  }

  public ActModel(Model model) {
    this.id = model.getId();
    this.name = model.getName();
    this.key = model.getKey();
    this.category = model.getCategory();
    this.createTime = model.getCreateTime();
    this.lastUpdateTime = model.getLastUpdateTime();
    this.version = model.getVersion();
    this.metaInfo = model.getMetaInfo();
    this.deploymentId = model.getDeploymentId();
    this.tenantId = model.getTenantId();
    this.hasEditorSource = model.hasEditorSource();
  }
}
