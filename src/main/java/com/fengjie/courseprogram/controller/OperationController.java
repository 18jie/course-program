package com.fengjie.courseprogram.controller;

import com.alibaba.fastjson.JSON;
import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.Class;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.model.param.OperationQuestionParam;
import com.fengjie.courseprogram.model.param.Page;
import com.fengjie.courseprogram.model.param.SubmitQuestionParam;
import com.fengjie.courseprogram.model.queryvo.CourseQuestionCheckedVO;
import com.fengjie.courseprogram.model.queryvo.CourseQuestionOperationVO;
import com.fengjie.courseprogram.model.queryvo.OperationVO;
import com.fengjie.courseprogram.server.CourseQuestionService;
import com.fengjie.courseprogram.server.OpeartionService;
import com.fengjie.courseprogram.util.RestResponse;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author fengjie
 * @date 2019/5/8 17:39
 */
@Controller
@RequestMapping("/teacher/operation")
public class OperationController {
    @Autowired
    private OpeartionService opeartionService;

    @Autowired
    private CourseQuestionService courseQuestionService;

    @Autowired
    @Qualifier("emailCache")
    private CaffeineCache cache;

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

    /**
     * 添加和修改作业都用到这个
     *
     * @param map
     * @param current     当前页面
     * @param search      搜索田间
     * @param level       难度
     * @param type        类型
     * @param uuid        唯一标记
     * @param operationId 作业号（这个变量只有在编辑作业的时候才用得到）
     * @return
     */
    @GetMapping("/addOperation")
    public String addOperation(ModelMap map, String current, String search, String level, String type, String uuid, String operationId) {
        Course course = LoginUserContext.getCourse();
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "addOperation");

        //为了兼容updateOperation
        if (!StringUtils.isEmpty(operationId)) {
            map.addAttribute("operationId", operationId);
        }
        if (StringUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
        }
        map.addAttribute("uuid", uuid);

        if (!StringUtils.isEmpty(search)) {
            map.addAttribute("searchCondition", search);
        }
        if (!StringUtils.isEmpty(level)) {
            map.addAttribute("level", level);
        }
        if (!StringUtils.isEmpty(type)) {
            map.addAttribute("type", type);
        }
        Page page;
        if (StringUtils.isEmpty(current)) {
            page = new Page(1, 12);
        } else {
            page = new Page(Integer.parseInt(current), 12);
        }
        PageInfo<CourseQuestion> questions = courseQuestionService.pageQuestions(page, course.getId(), search, level, type);
        map.addAttribute("questions", questions);
        if (questions.getPages() > 0) {
            List<Integer> pageNums = IntStream.rangeClosed(1, questions.getPages()).boxed().collect(Collectors.toList());
            map.addAttribute("pageNums", pageNums);
        }
        List<String> checkedQuestions = opeartionService.getCheckedQuestionIds(uuid);

        PageInfo<CourseQuestionCheckedVO> checkedVOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questions, checkedVOPageInfo);
        List<CourseQuestionCheckedVO> checkedQuestionVOS = new ArrayList<>();
        questions.getList().forEach(p -> {
            CourseQuestionCheckedVO questionCheckedVO = new CourseQuestionCheckedVO();
            BeanUtils.copyProperties(p, questionCheckedVO);
            if (checkedQuestions.contains(p.getId())) {
                questionCheckedVO.setChecked(true);
            }
            checkedQuestionVOS.add(questionCheckedVO);
        });
        checkedVOPageInfo.setList(checkedQuestionVOS);

        map.addAttribute("questions", checkedVOPageInfo);
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
    public String operationNextStep(String uuid, String operationId, ModelMap map) {
        Course course = LoginUserContext.getCourse();
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "addOperation");
        List<CourseQuestion> checkedQuestions = opeartionService.getCheckedQuestions(uuid);
        List<CourseQuestion> choiceQuestions = new ArrayList<>();
        List<CourseQuestion> programQuestions = new ArrayList<>();
        checkedQuestions.forEach(p -> {
            if (p.getType() == 1) {
                choiceQuestions.add(p);
            } else {
                programQuestions.add(p);
            }
        });

        List<Class> classes = opeartionService.getClassesByCourseId(course.getId());
        map.addAttribute("choiceQuestions", choiceQuestions);
        map.addAttribute("programQuestions", programQuestions);
        map.addAttribute("programStart", choiceQuestions.size());
        map.addAttribute("classes", classes);

        if (!StringUtils.isEmpty(operationId)) {
            Operation operationById = opeartionService.getOperationById(operationId);
            OperationVO operationVO = opeartionService.transferToVO(operationById);
            map.addAttribute("operation", operationVO);
        }
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

    @GetMapping("/singleOperationDetail")
    public String singleOperationDetail(ModelMap map, String operationId) {
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "operations");

        Operation operationById = opeartionService.getOperationById(operationId);
        OperationVO operationVO = opeartionService.transferToVO(operationById);

        map.addAttribute("operation", operationVO);
        return "teacher/teacherOperationOperationDetail";
    }

    @GetMapping("/updateOperation")
    public String updateOperation(ModelMap map, String operationId) {
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "operations");

        Operation operationById = opeartionService.getOperationById(operationId);
        OperationVO operationVO = opeartionService.transferToVO(operationById);

        List<CourseQuestionOperationVO> choiceQuestions = new ArrayList<>();
        List<CourseQuestionOperationVO> programQuestions = new ArrayList<>();

        operationVO.getQuestionList().sort(Comparator.naturalOrder());
        operationVO.getQuestionList().forEach(q -> {
            if (q.getType() == 1) {
                choiceQuestions.add(q);
            } else {
                programQuestions.add(q);
            }
        });
        map.addAttribute("operation", operationVO);
        map.addAttribute("choiceQuestions", choiceQuestions);
        map.addAttribute("programQuestions", programQuestions);

        String uuid = opeartionService.saveQuestionToCache(operationById);
        map.addAttribute("uuid", uuid);

        List<Class> classes = opeartionService.getClassesByCourseId(operationById.getCourseId());
        map.addAttribute("classes", classes);

        return "teacher/teacherOperationUpdate";
    }


}
