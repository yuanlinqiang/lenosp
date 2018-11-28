package com.len.core.BootListener;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author zhuxiaomeng
 * @date 2018/1/6.
 * @email 154040976@qq.com
 * <p>
 * 通过监听，开辟线程，执行定时任务 当然 也可以执行其他
 */
@Component
@Slf4j
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("-------------bean初始化完毕-------------");
        /**
         * 通过线程开启数据库中已经开启的定时任务 灵感来自spring
         * spring boot继续执行 mythread开辟线程，延迟后执行
         */
        DataSourceJobThread myThread = (DataSourceJobThread) event.getApplicationContext().getBean(
                "dataSourceJobThread");
        myThread.start();
    }

}
