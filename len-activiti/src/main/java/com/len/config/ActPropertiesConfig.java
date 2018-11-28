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
package com.len.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhuxiaomeng
 * @date 2018/1/28.
 * @email 154040976@qq.com
 *
 * 可以自定义一些配置类
 */
@Configuration
@PropertySource("classpath:properties\\activiti.properties")
public class ActPropertiesConfig {

  @Value("${modelId}")
  private String modelId;

  public String getModelId() {
    return modelId;
  }

  public void setModelId(String modelId) {
    this.modelId = modelId;
  }

  @Bean
  public ActPropertiesConfig getActPropertiesConfig(){
    return new ActPropertiesConfig();
  }
}
