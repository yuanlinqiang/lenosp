package com.len.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuxiaomeng
 * @date 2018/1/4.
 * @email 154040976@qq.com
 * <p>
 * ApplicationContext会自动检查是否在定义文件中有实现了BeanPostProcessor接口的类，
 * 如果有的话，Spring容器会在每个Bean(其他的Bean)被初始化之前和初始化之后，
 * 分别调用实现了BeanPostProcessor接口的类的postProcessAfterInitialization()方法
 * 和postProcessBeforeInitialization()方法
 * 之所以用z开头是因为 初始化有顺序
 */
@Configuration
@Slf4j
public class ZBeanFactory implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        log.info("对象---" + beanName + "---初始化开始");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("对象---" + beanName + "---初始化成功");
        return bean;
    }
}
