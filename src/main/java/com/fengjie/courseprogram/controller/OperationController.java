package com.fengjie.courseprogram.controller;

import com.alibaba.fastjson.JSON;
import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.Class;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.model.param.OperationQuestionParam;
import com.fengjie.courseprogram.model.param.SubmitQuestionParam;
import com.fengjie.courseprogram.model.queryvo.OperationVO;
import com.fengjie.courseprogram.server.OpeartionService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
        List<OperationVO> operations = opeartionService.transferToVO(opeartionService.listOperations(course.getId(), Constants.UNDELETE));
        map.addAttribute("operations", operations);
        return "teacher/teacherOperationOperations";
    }

    @GetMapping("/rubbishOperations")
    public String listRubbishOperations(ModelMap map, HttpSession session) {
        Course course = (Course) session.getAttribute("course");
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "rubbishOperations");
        List<OperationVO> operations = opeartionService.transferToVO(opeartionService.listOperations(course.getId(), Constants.DELETEED));
        map.addAttribute("operations", operations);
        return "teacher/teacherOperationRubbish";
    }

    @PostMapping("/logicDeleteOperation")
    public @ResponseBody
    RestResponse logicDeleteOperation(Operation operation) {
        int i = opeartionService.logicDeleteOperation(operation.getId());
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @PostMapping("/deleteOperation")
    public @ResponseBody
    RestResponse deleteOperation(Operation operation) {
        int i = opeartionService.deleteOperation(operation.getId());
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @PostMapping("/rebirthOperation")
    public @ResponseBody
    RestResponse rebirthOperation(Operation operation) {
        int i = opeartionService.rebirthOperation(operation.getId());
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

    /**
     * 暂时有bug
     *
     * @param operationQuestionParam
     * @return
     */
    @PostMapping("/submitCheckedQuestions")
    public @ResponseBody
    RestResponse submitCheckedQuestion(@RequestBody OperationQuestionParam operationQuestionParam) {
        boolean b = opeartionService.saveOperationQuestionsTemp(operationQuestionParam);
        if (b) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    /**
     * 暂时有bug
     *
     * @param uuid
     * @param map
     * @return
     */
    @GetMapping("/operationNextStep")
    public String operationNextStep(String uuid, ModelMap map) {
        Course course = LoginUserContext.getCourse();
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "addOperation");
        List<CourseQuestion> checkedQuestions = opeartionService.getCheckedQuestions(uuid);
        List<Class> classes = opeartionService.getClassesByCourseId(course.getId());
        map.addAttribute("questions", checkedQuestions);
        map.addAttribute("classes", classes);
        return "teacher/teacherOperationNextStep";
    }

    @PostMapping("/submitQuestionMsg")
    public @ResponseBody
    RestResponse submitQuestionMessage(Operation operation) {
        Course course = LoginUserContext.getCourse();
        operation.setCourseId(course.getId());
        int i = opeartionService.submitQuestionMsg(operation);
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @PostMapping("/saveOperation")
    public @ResponseBody
    RestResponse saveOperation(Operation operation) {
        int i = opeartionService.saveOpeartion(operation);
        if (i == 1) {
            RestResponse.success();
        }
        return RestResponse.fail();
    }

    @PostMapping("/getSingleOperation")
    public @ResponseBody
    RestResponse getSingleOperation(Operation operation) {
        Operation operationById = opeartionService.getOperationById(operation.getId());
        OperationVO operationVO = opeartionService.transferToVO(operationById);
        if (null != operationVO) {
            return RestResponse.success(operationVO);
        }
        return RestResponse.fail();
    }


}
