package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.exceptions.BusinessException;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.ProgramAnswer;
import com.fengjie.courseprogram.model.param.Page;
import com.fengjie.courseprogram.model.queryvo.CourseQuestionAddVO;
import com.fengjie.courseprogram.model.queryvo.CourseQuestionAnswerVO;
import com.fengjie.courseprogram.mybatis.dao.CourseQuestionDao;
import com.fengjie.courseprogram.util.DateKit;
import com.fengjie.courseprogram.util.ObjectId;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author fengjie
 * @date 2019/5/8 17:33
 */
@Service
public class CourseQuestionService {

    @Autowired
    private CourseQuestionDao courseQuestionDao;

    @Autowired
    private ProgramAnswerService programAnswerService;

    public PageInfo<CourseQuestion> pageQuestions(Page page, String courseId) {
        Example example = new Example(CourseQuestion.class);
        example.setOrderByClause("question_no");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleteFlag", Constants.UNDELETE);
        criteria.andEqualTo("courseId", courseId);
        return PageHelper.startPage(page.getPageNum(), page.getPageSize())
                .doSelectPageInfo(() -> courseQuestionDao.selectByExample(example));
    }

    @Transactional(rollbackFor = Exception.class)
    public int saveQuestion(CourseQuestion courseQuestion) {
        if (courseQuestion.getType() == 1) {
            //选择题
            if (StringUtils.isEmpty(courseQuestion.getId())) {
                return this.addChoiceQuestion(courseQuestion);
            }
            return this.updateChoiceQuestion(courseQuestion);
        } else {
            if (StringUtils.isEmpty(courseQuestion.getId())) {
                //新增编程题
                return this.addProgramQuestion(courseQuestion);
            }
            return this.updateProgramQuestion(courseQuestion);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public int addProgramQuestion(CourseQuestion courseQuestion) {
        CourseQuestionAddVO questionVO = (CourseQuestionAddVO) courseQuestion;
        List<ProgramAnswer> programAnswers = this.handleProgramAnswer(questionVO.getAnswer());

        programAnswers.forEach(p -> p.setQuestionId(courseQuestion.getId()));


        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateProgramQuestion(CourseQuestion courseQuestion) {

        return 0;
    }

    /**
     * 拆分编程题的测试用例（需要测试）
     * @param answer
     * @return
     */
    private List<ProgramAnswer> handleProgramAnswer(String answer) {
        String token1 = "*&^%$%$";
        String token2 = "!@#$%^&*";

        List<String> answers = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(answer, token2);
        while (tokenizer.hasMoreElements()) {
            answers.add(tokenizer.nextToken());
        }

        List<ProgramAnswer> programAnswers = new ArrayList<>();
        for (String ans : answers) {
            StringTokenizer stringTokenizer = new StringTokenizer(ans, token1);
            List<String> systems = new ArrayList<>();
            while (stringTokenizer.hasMoreElements()) {
                systems.add(stringTokenizer.nextToken());
            }
            if (systems.size() != 2) {
                throw new BusinessException("上传的数据错误");
            }
            ProgramAnswer pro = new ProgramAnswer();
            pro.setId(ObjectId.get().toString());
            pro.setSystemIn(systems.get(0));
            pro.setSystemOut(systems.get(1));
            programAnswers.add(pro);
        }
        return programAnswers;
    }

    public int updateChoiceQuestion(CourseQuestion courseQuestion) {
        DateKit.teacherUpdate(courseQuestion);
        return courseQuestionDao.updateByPrimaryKeySelective(courseQuestion);
    }

    public int addChoiceQuestion(CourseQuestion courseQuestion) {
        //需要处理
        courseQuestion.setId(ObjectId.get().toString());
        DateKit.teacherAdd(courseQuestion);
        return courseQuestionDao.insertSelective(courseQuestion);
    }

    public int deleteQuestion(String questionId) {
        return courseQuestionDao.deleteByPrimaryKey(questionId);
    }

    public CourseQuestion getQuestionById(String questionId) {
        CourseQuestion courseQuestion = new CourseQuestion();
        courseQuestion.setId(questionId);
        courseQuestion.setDeleteFlag(Constants.UNDELETE);
        return courseQuestionDao.selectOne(courseQuestion);
    }

    public CourseQuestion getSingleQuestionById(String id, String courseId) {
        CourseQuestion courseQuestion = new CourseQuestion();
        courseQuestion.setId(id);
        courseQuestion.setCourseId(courseId);
        courseQuestion.setDeleteFlag(Constants.UNDELETE);
        return courseQuestionDao.selectOne(courseQuestion);
    }

    public List<CourseQuestion> getAllQuestion(String courseId) {
        CourseQuestion courseQuestion = new CourseQuestion();
        courseQuestion.setCourseId(courseId);
        courseQuestion.setDeleteFlag(Constants.UNDELETE);
        return courseQuestionDao.select(courseQuestion);
    }

    public List<CourseQuestion> getQuestionsByIds(List<String> ids) {
        Example example = new Example(CourseQuestion.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        return courseQuestionDao.selectByExample(example);
    }

    public CourseQuestionAnswerVO transferToVO(CourseQuestion courseQuestion) {
        CourseQuestionAnswerVO courseQuestionAnswerVO = new CourseQuestionAnswerVO();
        List<ProgramAnswer> answers = programAnswerService.getAnswersByQuestionId(courseQuestion.getId());

        BeanUtils.copyProperties(courseQuestion, courseQuestionAnswerVO);
        courseQuestionAnswerVO.setAnswers(answers);
        return courseQuestionAnswerVO;
    }

}