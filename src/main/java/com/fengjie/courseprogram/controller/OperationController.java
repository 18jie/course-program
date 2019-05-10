package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.model.param.OperationQuestionParam;
import com.fengjie.courseprogram.server.OpeartionService;
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
 * @date 2019/5/8 17:39
 */
@Controller
@RequestMapping("/teacher/operation")
public class OperationController {
    @Autowired
    private OpeartionService opeartionService;

    @GetMapping("/operations")
    public String listOperations(ModelMap map, HttpSession session) {
        Course course = (Course) session.getAttribute("course");
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "operations");
        List<Operation> operations =
                opeartionService.listOperations(course.getId(), Constants.UNDELETE);
        map.addAttribute("operations", operations);
        return "teacher/teacherOperationOperations";
    }

    @GetMapping("/rubbishOperations")
    public String listRubbishOperations(ModelMap map, HttpSession session) {
        Course course = (Course) session.getAttribute("course");
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "rubbishOperations");
        List<Operation> operations =
                opeartionService.listOperations(course.getId(), Constants.DELETEED);
        map.addAttribute("operations", operations);
        return "teacher/teacherOperationRubbish";
    }

    @GetMapping("/logicDeleteOperation")
    public @ResponseBody
    RestResponse logicDeleteOperation(String operationId) {
        int i = opeartionService.logicDeleteOperation(operationId);
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @GetMapping("/deleteOperation")
    public @ResponseBody
    RestResponse deleteOperation(String operationId) {
        int i = opeartionService.deleteOperation(operationId);
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @GetMapping("/rebirthOperation")
    public @ResponseBody
    RestResponse rebirthOperation(String operationId) {
        int i = opeartionService.rebirthOperation(operationId);
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @GetMapping("/addOperation")
    public String addOperation(ModelMap map) {
        Course course = LoginUserContext.getCourse();
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "addOperation");
        List<CourseQuestion> questions = opeartionService.getAllCourseQuestionByCourseId(course.getId());
        map.addAttribute("questions", questions);
        return "teacher/teacherOperationAddOperation";
    }

    @PostMapping("/submitCheckedQuestions")
    public @ResponseBody
    RestResponse submitCheckedQuestion(OperationQuestionParam operationQuestionParam) {
        boolean b = opeartionService.saveOperationQuestionsTemp(operationQuestionParam);
        if (b) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @GetMapping("/operationNextStep")
    public String operationNextStep(String uuid,ModelMap map){
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "addOperation");
        return null;
    }

}
