package com.len.exception;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhuxiaomeng
 * @date 2018/5/6.
 * @email 154040976@qq.com
 */
@ControllerAdvice
public class CustomErrorViewResolver implements ErrorViewResolver {

    static final String PAGE_500 = "/error/500";
    static final String PAGE_404 = "/error/404";
    static final String PAGE_403 = "/error/403";
    static final String OTHER_ERROR = "/error/error";

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request,
                                         HttpStatus status, Map<String, Object> model) {
        boolean isServerError = status.is5xxServerError();
        ModelAndView andView = new ModelAndView();
        andView.addObject("message", model.get("message"));

        if ("404".equals(status.value())) {
            andView.setViewName(PAGE_404);
        } else if ("403".equals(status.value())) {
            andView.setViewName(PAGE_403);
        } else if (isServerError) {
            andView.setViewName(PAGE_500);
        } else {
            andView.addObject("status", status.value());
            andView.setViewName(OTHER_ERROR);
        }
        return andView;

    }

}