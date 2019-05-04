package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.model.entity.Teacher;
import com.fengjie.courseprogram.model.param.LoginParam;
import com.fengjie.courseprogram.server.TeacherService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author fengjie
 * @date 2019:05:04
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teacherLogin")
    public String teacher(){
        return "teacher/teacherLogin";
    }

    @PostMapping("/teacherDoLogin")
    public @ResponseBody
    RestResponse teacherLogin(LoginParam loginParam, HttpSession session){
        Teacher teacher = teacherService.loginCheck(loginParam);
        if(null != teacher){
            session.setAttribute("teacher",teacher);
            return RestResponse.success();
        }
        return RestResponse.fail("账号或密码错误");
    }

    @GetMapping("/index")
    public String teacherIndex(){
        return "comm/teacherHeader";
    }

}
