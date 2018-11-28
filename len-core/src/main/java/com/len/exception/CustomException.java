package com.len.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhuxiaomeng
 * @date 2017/12/8.
 * @email 154040976@qq.com
 */
@Slf4j
public class CustomException implements HandlerExceptionResolver {

  @Override
  public ModelAndView resolveException(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o, Exception e) {
    ModelAndView mv=new ModelAndView("/error/error");
    if(e instanceof UnauthorizedException){
      //处理拦截shiro 无权限
      mv.setViewName("/login");
      return mv;
    }
   e.printStackTrace();
    MyException myExecption=null;
    if(e instanceof MyException){
      myExecption=(MyException)e;

    }else{
      myExecption=new MyException("未知错误");
    }

    //错误信息
    String message=myExecption.getMessage();

    ModelAndView modelAndView=new ModelAndView();

    //将错误信息传到页面
    modelAndView.addObject("message",message);

    //指向到错误界面
    return mv;
  }
}
