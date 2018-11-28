package com.len.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhuxiaomeng
 * @date 2018/1/5.
 * @email 154040976@qq.com
 */
public class ApplicationContextUtil implements ApplicationContextAware {

  private static ApplicationContext applicationContext;
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ApplicationContextUtil.applicationContext=applicationContext;
  }

  public static ApplicationContext getContext(){
    return applicationContext;
  }

  public static Object getBean(String arg){
    return applicationContext.getBean(arg);
  }
}
