package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.param.Page;
import com.fengjie.courseprogram.model.queryvo.CourseQuestionAddVO;
import com.fengjie.courseprogram.model.queryvo.CourseQuestionAnswerVO;
import com.fengjie.courseprogram.server.CourseQuestionService;
import com.fengjie.courseprogram.util.RestResponse;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author fengjie
 * @date 2019/5/8 17:37
 */
@Controller
@RequestMapping("/teacher/operation")
public class CourseQuestionController {

    @Autowired
    private CourseQuestionService courseQuestionService;

    @GetMapping("/questions")
    public String teacherOperation(ModelMap map, String current) {
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "questions");

        Course course = LoginUserContext.getCourse();
        Page page;
        if (StringUtils.isEmpty(current)) {
            page = new Page(1, 12);
        } else {
            page = new Page(Integer.parseInt(current), 12);
        }
        PageInfo<CourseQuestion> questions = courseQuestionService.pageQuestions(page, course.getId());
        map.addAttribute("questions", questions);
        if (questions.getPages() > 0) {
            List<Integer> pageNums = IntStream.rangeClosed(1, questions.getPages()).boxed().collect(Collectors.toList());
            map.addAttribute("pageNums", pageNums);
        }
        map.addAttribute("questions", questions);
        return "teacher/teacherOperation";
    }

    @GetMapping("/question/saveChoiceQuestion")
    public String addChoiceQuestion(ModelMap map, String questionId) {
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "questions");
        if (!StringUtils.isEmpty(questionId)) {
            CourseQuestion questionById = courseQuestionService.getQuestionById(questionId);
            map.addAttribute("question", questionById);
        }
        return "teacher/teacherOperationAddChoiceQuestion";
    }

    @GetMapping("/question/saveProgramQuestion")
    public String addProgramQuestion(ModelMap map, String questionId) {
        map.addAttribute("active", "operation");
        map.addAttribute("sideActive", "questions");
        map.addAttribute("model","<p>【问题描述】</p>\n" +
                "    <p>【输入形式】</p>\n" +
                "    <p>【输入样例】</p>\n" +
                "    <p>【输出样例】</p>\n" +
                "    <p>【样例说明】</p>\n" +
                "    <p>【评分标准】</p>");
        if (!StringUtils.isEmpty(questionId)) {
            CourseQuestion questionById = courseQuestionService.getQuestionById(questionId);
            CourseQuestionAnswerVO courseQuestionAnswerVO = courseQuestionService.transferToVO(questionById);
            map.addAttribute("question", courseQuestionAnswerVO);
        }
        return "teacher/teacherOperationAddProgramQuestion";
    }

    @PostMapping("/question/doSaveQuestion")
    public @ResponseBody
    RestResponse saveQuestion(CourseQuestionAddVO courseQuestion) {
        Course course = LoginUserContext.getCourse();
        courseQuestion.setCourseId(course.getId());
        int i = courseQuestionService.saveQuestion(courseQuestion);
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @PostMapping("/question/deleteQuestion")
    public @ResponseBody
    RestResponse deleteQuestion(CourseQuestion courseQuestion) {
        int i = courseQuestionService.deleteQuestion(courseQuestion.getId());
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @PostMapping("/question/getSingleQuestion")
    public @ResponseBody
    RestResponse getSingleQuestion(CourseQuestion courseQuestion) {
        Course course = LoginUserContext.getCourse();
        CourseQuestion question = courseQuestionService.getSingleQuestionById(courseQuestion.getId(), course.getId());
        if(null != question){
            return RestResponse.success(question);
        }
        return RestResponse.fail();
    }

    @PostMapping("/question/submitExample")
    @ResponseBody
    public RestResponse submitProgramExample(CourseQuestion courseQuestion){
        return courseQuestionService.handleExample(courseQuestion);
    }

}
