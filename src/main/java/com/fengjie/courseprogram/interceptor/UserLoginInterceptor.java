package com.fengjie.courseprogram.interceptor;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.model.entity.Teacher;
import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fengjie
 * @date 2019:04:13
 */
@Slf4j
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        log.debug(uri);

        if(request.getSession().getAttribute("course") != null){
            LoginUserContext.setCourse((Course) request.getSession().getAttribute("course"));
        }

        if(StringUtils.isStaticResources(uri)) {
            return true;
        }

        if(uri.startsWith("/teacher")){
            //教师模块拦截
            if(uri.equals("/teacher/teacherLogin") || uri.equals("/teacher/teacherDoLogin")){
                return true;
            }

            if(request.getSession().getAttribute("teacher") != null){
                LoginUserContext.setTeacher((Teacher) request.getSession().getAttribute("teacher"));
                return true;
            }else{
                response.sendRedirect(request.getContextPath() +"/teacher/teacherLogin");
                return false;
            }
        }else if(uri.startsWith("/student")){
            //学生模块拦截
            if(uri.equals("/student/studentLogin") || uri.equals("/student/studentDoLogin")){
                return true;
            }

            if(request.getSession().getAttribute("student") != null){
                LoginUserContext.setStudent((Student) request.getSession().getAttribute("student"));
                return true;
            }else{
                response.sendRedirect(request.getContextPath() + "/student/studentLogin");
                return false;
            }
        }

        //其他
        if(request.getSession().getAttribute("user") != null) {
            LoginUserContext.setUser((User) request.getSession().getAttribute("user"));
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
        LoginUserContext.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
