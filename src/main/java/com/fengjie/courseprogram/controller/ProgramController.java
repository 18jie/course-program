package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.model.entity.ProgramQuestion;
import com.fengjie.courseprogram.model.param.Page;
import com.fengjie.courseprogram.model.param.ProgramParam;
import com.fengjie.courseprogram.server.ProgramQuestionService;
import com.fengjie.courseprogram.util.RestResponse;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author fengjie
 * @date 2019:04:29
 */
@Controller
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramQuestionService questionService;

    @GetMapping("/pages")
    public String listPage(ModelMap map, String current, String search) {
        //需要使用ajax异步发送json类型的数据
        map.addAttribute("active", "program");
        Page page;
        if (StringUtils.isEmpty(current)) {
            page = new Page(1, 12);
        } else {
            page = new Page(Integer.parseInt(current), 12);
        }
        PageInfo<ProgramQuestion> questions = questionService.pageQuestions(page);
        if (questions.getPageSize() > 0) {
            List<Integer> pageNums = IntStream.rangeClosed(1, questions.getPageSize()).boxed().collect(Collectors.toList());
            map.addAttribute("pageNums", pageNums);
        }
        map.addAttribute("questions", questions);
        return "pageList";
    }

    @GetMapping("/question")
    public String question(ModelMap map, String questionId) {
        map.addAttribute("active", "program");
        ProgramQuestion question = questionService.getQuestionById(questionId);
        map.addAttribute("question", question);
        return "question";
    }

    @PostMapping("/submit")
    public @ResponseBody
    RestResponse submitProgram(@RequestBody ProgramParam programParam, @RequestParam String questionId){
        return questionService.exeProgram(programParam,questionId);
    }

}
