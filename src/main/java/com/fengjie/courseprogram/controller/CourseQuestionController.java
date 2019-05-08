package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.param.Page;
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
        map.addAttribute("active","operation");
        map.addAttribute("sideActive","questions");

        Course course = LoginUserContext.getCourse();
        Page page;
        if (StringUtils.isEmpty(current)) {
            page = new Page(1, 12);
        }
        page = new Page(Integer.parseInt(current), 12);
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
    public String addChoiceQuestion(ModelMap map){
        map.addAttribute("active","operation");
        map.addAttribute("sideActive","questions");
        return "teacher/teacherOperationAddChoiceQuestion";
    }

    @GetMapping("/question/saveChoiceQuestion")
    public String addProgramQuestion(ModelMap map){
        map.addAttribute("active","operation");
        map.addAttribute("sideActive","questions");
        return "teacher/teacherOperationAddProgramQuestion";
    }

    @PostMapping("/question/doSaveQuestion")
    public @ResponseBody
    RestResponse saveQuesion(ModelMap map,CourseQuestion courseQuestion){
        int i = courseQuestionService.saveQuestion(courseQuestion);
        if(i == 1){
            return RestResponse.success();
        }
        return RestResponse.fail();
    }



}
