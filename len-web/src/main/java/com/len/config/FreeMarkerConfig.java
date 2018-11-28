package com.len.config;

import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.Properties;
/**
 * @author zhuxiaomeng
 * @date 2018/1/2.
 * @email 154040976@qq.com
 */
@Configuration
public class FreeMarkerConfig{


  @Bean
  public ViewResolver viewResolverFtl() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setCache(false);
    resolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
    resolver.setRequestContextAttribute("re");
    resolver.setExposeRequestAttributes(true);
    resolver.setExposeSessionAttributes(true);
    resolver.setSuffix(".ftl");
    resolver.setContentType("text/html;charset=UTF-8");
    resolver.setOrder(0);
    return resolver;
  }

  @Bean
  public ViewResolver viewResolverHtml() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setCache(false);
    resolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
    resolver.setRequestContextAttribute("re");
    resolver.setExposeRequestAttributes(true);
    resolver.setExposeSessionAttributes(true);
    resolver.setOrder(1);
    resolver.setSuffix(".html");
    resolver.setContentType("text/html;charset=UTF-8");
    return resolver;
  }


  @Bean
  public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
    FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
    factory.setTemplateLoaderPath("classpath:/ftl/");
    factory.setDefaultEncoding("UTF-8");
    factory.setPreferFileSystemAccess(false);
    FreeMarkerConfigurer result = new FreeMarkerConfigurer();
    freemarker.template.Configuration configuration = factory.createConfiguration();
    configuration.setClassicCompatible(true);
    result.setConfiguration(configuration);
    Properties settings = new Properties();
    settings.put("template_update_delay", "0");
    settings.put("default_encoding", "UTF-8");
    settings.put("number_format", "0.######");
    settings.put("classic_compatible", true);
    settings.put("template_exception_handler", "ignore");
    result.setFreemarkerSettings(settings);
    return result;
  }

}