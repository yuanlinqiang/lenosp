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

/**
 * @author zhuxiaomeng
 * @date 2018/1/26.
 * @email 154040976@qq.com
 *
 * 请假流程 审批信息
 */
@Getter
@Setter
public class LeaveOpinion implements Serializable{

  //审批人id
  private String opId;
  //审批人姓名
  private String opName;
  //审批意见
  private String opinion;
  //审批时间
  private Date createTime;
  //是否通过
  private boolean flag;
  //流程id
  private String taskId;

}
