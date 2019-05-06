package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.server.StudentService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:06
 */
@Controller
@RequestMapping("/teacher/student")
public class TeacherStudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/pages")
    public String teacherStudentPage(String classId, ModelMap map){
        map.addAttribute("sideActive", "classification");
        map.addAttribute("active", "student");
        List<Student> students = studentService.getStudentsByClassId(classId);
        map.addAttribute("students",students);
        return "teacher/teacherStudentPage";
    }

    @PostMapping("/get")
    public @ResponseBody
    RestResponse getStudentInfo(Student student){
        Student studentById = studentService.getStudentById(student.getId());
        if(null != studentById){
            return RestResponse.success(studentById);
        }
        return RestResponse.fail();
    }

    @PostMapping("/update")
    public @ResponseBody RestResponse updateStudent(Student student){
        int i = studentService.updateStudentById(student);
        if(i == 1){
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @PostMapping("/delete")
    public @ResponseBody RestResponse deleteStudent(Student student){
        int i = studentService.deleteStudentById(student.getId());
        if(i == 1){
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

}
