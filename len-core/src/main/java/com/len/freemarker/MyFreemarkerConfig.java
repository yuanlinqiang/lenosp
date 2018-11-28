package com.len.freemarker;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.IOException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * @author zhuxiaomeng
 * @date 2017/12/11.
 * @email 154040976@qq.com
 */
public class MyFreemarkerConfig extends FreeMarkerConfigurer {

  @Override
  public void afterPropertiesSet() throws IOException, TemplateException {
    super.afterPropertiesSet();
    Configuration configuration=this.getConfiguration();
    configuration.setSharedVariable("shiro",new ShiroTags());
    configuration.setNumberFormat("#");
  }
}
