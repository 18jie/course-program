package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.Grade;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.model.param.LoginParam;
import com.fengjie.courseprogram.model.queryvo.CourseQuestionOperationVO;
import com.fengjie.courseprogram.model.queryvo.OperationVO;
import com.fengjie.courseprogram.server.StudentService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

/**
 * 学生端
 *
 * @author fengjie
 * @date 2019:05:15
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/studentLogin")
    public String studentLogin() {
        return "student/studentLogin";
    }

    @PostMapping("/studentDoLogin")
    @ResponseBody
    public RestResponse studentDoLogin(LoginParam loginParam, HttpSession session) {
        Student student = studentService.loginCheck(loginParam);
        if (null != student) {
            session.setAttribute("student", student);
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @GetMapping("/studentIndex")
    public String studentIndex(ModelMap map) {
        map.addAttribute("active", "head");
        return "student/studentIndex";
    }

    @GetMapping("/operations")
    public String studentOperations(ModelMap map) {
        Student student = LoginUserContext.getStudent();
        List<Operation> operations = studentService.getStudentOperations(student);
        map.addAttribute("active", "operations");
        map.addAttribute("sideActive", "operations");
        map.addAttribute("operations", operations);
        return "student/studentOperations";
    }

    @GetMapping("/questions")
    public String studentQuestions(ModelMap map, String operationId, HttpSession session) {
        if (!StringUtils.isEmpty(operationId)) {
            session.removeAttribute("operationId");
            session.setAttribute("operationId", operationId);
        } else {
            operationId = (String) session.getAttribute("operationId");
        }

        Student student = LoginUserContext.getStudent();

        map.addAttribute("active", "operations");
        map.addAttribute("sideActive", "operations");

        OperationVO operationDetail = studentService.getOperationDetail(operationId);
        List<CourseQuestionOperationVO> questionList = operationDetail.getQuestionList();
        questionList.sort(Comparator.naturalOrder());

        String answered = studentService.getAnsweredOfStudent(student.getId(), operationId);
        questionList.forEach(q -> q.setAnswered(answered.contains(q.getId())));

        map.addAttribute("questions", questionList);

        return "student/studentQuestions";
    }

    @GetMapping("/choiceQuestion")
    public String studentChoiceQuestion(String questionId, ModelMap map) {
        map.addAttribute("active", "operations");
        map.addAttribute("sideActive", "operations");

        CourseQuestion question = studentService.getQuestionById(questionId);
        map.addAttribute("question", question);
        return "student/studentChoiceQuestion";
    }

    @GetMapping("/programQuestion")
    public String studentProgramQuestion(String questionId, ModelMap map) {
        map.addAttribute("active", "operations");
        map.addAttribute("sideActive", "operations");

        CourseQuestion question = studentService.getQuestionById(questionId);
        map.addAttribute("question", question);
        return "student/studentProgramQuestion";
    }

    @PostMapping("/submitQuestion")
    @ResponseBody
    public RestResponse submitQuestion(CourseQuestion courseQuestion, HttpSession session) {
        String operationId = (String) session.getAttribute("operationId");
        int i = studentService.handleAnswer(courseQuestion, operationId);
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @GetMapping("/grade")
    public String studentGrade(ModelMap map) {
        Student student = LoginUserContext.getStudent();
        map.addAttribute("active", "grade");
        map.addAttribute("sideActive", "grade");

        List<Operation> operationGrades = studentService.getStudentOperationGrades(student);
        map.addAttribute("operations", operationGrades);
        return "student/studentGrade";
    }

    @PostMapping("/getSingleGrade")
    @ResponseBody
    public RestResponse getSingleGrade(Operation operation) {
        Student student = LoginUserContext.getStudent();
        Grade grade = studentService.getGradeOfStudent(student.getId(), operation.getId());
        if (null != grade) {
            return RestResponse.success(grade);
        }
        grade = new Grade();
        grade.setGrade(0);
        return RestResponse.success(grade);
    }

}
