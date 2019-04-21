package com.fengjie.courseprogram.interceptor;

import com.fengjie.courseprogram.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fengjie
 * @date 2019:04:13
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        System.out.println(uri);
        if(StringUtils.isStaticResources(uri)) {
            return true;
        }

        if(request.getSession().getAttribute("user") != null) {
            return true;
        }

        if(uri.equals("") || uri.startsWith("/index") || uri.equals("/error")){
            return true;
        }

        response.sendRedirect(request.getContextPath() + "/index");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
