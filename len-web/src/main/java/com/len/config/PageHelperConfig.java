package com.len.config;

import com.github.pagehelper.PageHelper;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuxiaomeng
 * @date 2018/1/2.
 * @email 154040976@qq.com
 *
 * 此方案可替代 application.yml中 pagehelper配置
 */
//@Configuration
public class PageHelperConfig {


  //@Bean
  public PageHelper getPageHelper(){
    PageHelper pageHelper=new PageHelper();
    Properties properties=new Properties();
    properties.setProperty("helperDialect","mysql");
    properties.setProperty("reasonable","true");
    properties.setProperty("supportMethodsArguments","true");
    properties.setProperty("params","count=countSql");
    pageHelper.setProperties(properties);
    return pageHelper;
  }

}
