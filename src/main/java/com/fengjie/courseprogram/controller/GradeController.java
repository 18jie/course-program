package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.model.queryvo.OperationVO;
import com.fengjie.courseprogram.model.queryvo.StudentVO;
import com.fengjie.courseprogram.server.GradeService;
import com.fengjie.courseprogram.server.OpeartionService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:17
 */
@Controller
@RequestMapping("/teacher/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private OpeartionService opeartionService;

    //成绩管理
    @GetMapping("")
    public String teacherGrade(ModelMap map) {
        Course course = LoginUserContext.getCourse();
        map.addAttribute("active", "grade");
        map.addAttribute("sideActive", "unFinishedOperations");
        List<OperationVO> operations = opeartionService.transferToVO(gradeService.getAllUnfinishedOperations(course.getId()));
        map.addAttribute("operations", operations);
        return "teacher/teacherGrade";
    }

    @PostMapping("/checkGrade")
    public RestResponse checkGrade(Operation operation) {
        gradeService.checkGrade(operation.getId());
        return RestResponse.success();
    }

    @GetMapping("/finishedOperations")
    public String finishedOperations(ModelMap map) {
        Course course = LoginUserContext.getCourse();

        map.addAttribute("active", "grade");
        map.addAttribute("sideActive", "finishedOperations");
        List<OperationVO> operations = opeartionService.transferToVO(gradeService.getAllFinishedOperations(course.getId()));
        map.addAttribute("operations", operations);
        return "teacher/teacherFinishedOperations";
    }

    @GetMapping("/studentsGrade")
    public String studentsGrade(String operationId,ModelMap map){
        map.addAttribute("active", "grade");
        map.addAttribute("sideActive", "finishedOperations");
        List<StudentVO> studentVOS = gradeService.getallStudentOfOperation(operationId);
        map.addAttribute("students",studentVOS);
        return "teacher/teacherStudentsGrades";
    }

}
