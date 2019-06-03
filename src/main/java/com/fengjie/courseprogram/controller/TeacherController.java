package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.model.entity.Class;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.model.entity.Teacher;
import com.fengjie.courseprogram.model.param.LoginParam;
import com.fengjie.courseprogram.model.param.UserModifyParam;
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
            session.removeAttribute("course");
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

    @GetMapping("/examination")
    public String teacherExamination() {
        return "teacher/teacherExamination";
    }

    @GetMapping("/logout")
    public String teacherLogout(HttpSession session) {
        session.removeAttribute("teacher");
        return "redirect:/teacher/teacherLogin";
    }

    @GetMapping("/teacherMsg")
    public String teacherMsg(ModelMap map) {
        map.addAttribute("active","userMsg");
        return "teacher/teacherMsg";
    }

    @PostMapping("/modifyTeacher")
    @ResponseBody
    public RestResponse updateTeacher(UserModifyParam userModifyParam) {
        int i = teacherService.updateUser(userModifyParam);
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @GetMapping("/enterStudentClasses")
    public String enterStudentSide(ModelMap map, HttpSession session) {
        Course course = (Course) session.getAttribute("course");
        map.addAttribute("active", "enterStudent");
        List<Class> classesByCourseId = teacherService.getClassesByCourseId(course.getId());
        map.addAttribute("classes", classesByCourseId);
        return "teacher/teacherEnterStudentClasses";
    }

    @GetMapping("/enterStudents")
    public String enterStudents(ModelMap map, String classId) {
        map.addAttribute("active", "enterStudent");
        List<Student> studentByClassId = teacherService.getStudentByClassId(classId);
        map.addAttribute("students", studentByClassId);
        return "teacher/teacherEnterStudentStudents";
    }

    @GetMapping("/doEnterStudent")
    public String doEnterStudent(String studentId, HttpSession session) {
        Student studentById = teacherService.getStudentById(studentId);
        session.setAttribute("student", studentById);
        return "redirect:/student/studentIndex";
    }

}
