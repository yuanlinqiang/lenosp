package com.len.core.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class MySchedulerListener {
      
    @Autowired  
    MyJobFactory myJobFactory;

      
    @Bean(name ="schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setJobFactory(myJobFactory);
        bean.setConfigLocation(new ClassPathResource("quartz.properties"));
        return bean;  
    }  
  
}  