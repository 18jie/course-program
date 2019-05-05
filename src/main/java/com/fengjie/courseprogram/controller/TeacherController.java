package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.Teacher;
import com.fengjie.courseprogram.model.param.LoginParam;
import com.fengjie.courseprogram.server.TeacherService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String teacher() {
        return "teacher/teacherLogin";
    }

    @PostMapping("/teacherDoLogin")
    public @ResponseBody
    RestResponse teacherLogin(LoginParam loginParam, HttpSession session) {
        Teacher teacher = teacherService.loginCheck(loginParam);
        if (null != teacher) {
            session.setAttribute("teacher", teacher);
            return RestResponse.success();
        }
        return RestResponse.fail("账号或密码错误");
    }

    @GetMapping("/index")
    public String teacherIndex(ModelMap map, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        List<Course> courses = teacherService.getCourses(teacher.getId());
        session.setAttribute("courses", courses);
        map.addAttribute("active", "head");
        return "teacher/teacherIndex";
    }

    @GetMapping("/course")
    public String teacherCourseMsg(String courseId, ModelMap map, HttpSession session) {
        Course course = teacherService.getCourseByCourseId(courseId);
        session.setAttribute("course", course);
        return "teacher/teacherStudent";
    }

    //学生管理

    @GetMapping("/student")
    public String teacherStudent() {
        return "teacher/teacherStudent";
    }


    //作业管理

    @GetMapping("/operation")
    public String teacherOperation(){
        return "teacher/teacherOperation";
    }

    //考试管理

    @GetMapping("/examination")
    public String teacherExamination(){
        return "teacher/teacherExamination";
    }

    //成绩管理

    @GetMapping("/grade")
    public String teacherGrade(){
        return "teacher/teacherGrade";
    }

}
