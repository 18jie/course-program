package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.model.entity.ProgramAnswer;
import com.fengjie.courseprogram.model.entity.ProgramQuestion;
import com.fengjie.courseprogram.model.param.Page;
import com.fengjie.courseprogram.model.param.ProgramParam;
import com.fengjie.courseprogram.mybatis.dao.ProgramQuestionDao;
import com.fengjie.courseprogram.util.CustomStringJavaCompiler;
import com.fengjie.courseprogram.util.RestResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;

/**
 * @author fengjie
 * @date 2019:05:03
 */
@Service
@Slf4j
public class ProgramQuestionService {
    @Autowired
    private ProgramQuestionDao programQuestionDao;

    @Autowired
    private ProgramAnswerService answerService;

    public ProgramQuestion getQuestionById(String questionId) {
        return programQuestionDao.selectByPrimaryKey(questionId);
    }

    public PageInfo<ProgramQuestion> pageQuestions(Page page) {
        Example example = new Example(ProgramQuestion.class);
        example.setOrderByClause("question_no");
        return PageHelper.startPage(page.getPageNum(), page.getPageSize())
                .doSelectPageInfo(() -> programQuestionDao.selectByExample(example));
    }

    /**
     * 做的不好，将太多东西耦合在一起
     *
     * @param program
     * @param in
     * @param out
     * @return
     */
    public RestResponse handleProgram(String program, String in, String out) {
        CustomStringJavaCompiler compiler = new CustomStringJavaCompiler(program);
        boolean res = compiler.compiler();
        if (res) {
            log.debug("编译成功" + "compilerTakeTime：" + compiler.getCompilerTakeTime());
            try {
                compiler.runMainMethod(StringUtils.isEmpty(in) ? "".getBytes() : in.getBytes());
                log.debug("runTakeTime：" + compiler.getRunTakeTime());
                if (compiler.getRunTakeTime() > 2000) {
                    return RestResponse.fail("运行超时");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RestResponse.fail("运行失败，请检查程序");
            }
            String runResult = compiler.getRunResult();
            if (runResult.endsWith("\r\n")) {
                runResult = runResult.substring(0, runResult.length() - 2);
            }
            if (!out.trim().equals(runResult.trim())) {
                return RestResponse.fail("某些测试用例运行结果不正确");
            }
            log.debug("runResult = " + runResult + "  " + "out = " + out);
            log.debug("诊断信息：" + compiler.getCompilerMessage());
        } else {
            log.debug("编译失败");
            log.debug(compiler.getCompilerMessage());
            return RestResponse.fail(compiler.getCompilerMessage());
        }
        return RestResponse.success("恭喜你通过所有的测试用例");
    }

    public RestResponse exeProgram(ProgramParam programParam, String questionId) {
        ProgramAnswer answer = answerService.getAnswerByQuestionId(questionId);
        RestResponse restResponse = handleProgram(programParam.getProgram(), answer.getSystemIn(), answer.getSystemOut());
        this.questionExecAfter(questionId, restResponse.isSuccess());
        return restResponse;
    }

    private void questionExecAfter(String questionId, boolean isPassed) {
        ProgramQuestion question = programQuestionDao.selectByPrimaryKey(questionId);
        question.setTotalTried(question.getTotalTried() + 1);
        if (isPassed) {
            question.setTotalPassed(question.getTotalPassed() + 1);
        }
        float tried = Float.valueOf(String.valueOf(question.getTotalTried()));
        float rate = question.getTotalPassed() / tried;
        BigDecimal passRate = new BigDecimal(rate);
        question.setPassRate(passRate);
        programQuestionDao.updateByPrimaryKeySelective(question);
    }


}
