package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.param.Page;
import com.fengjie.courseprogram.mybatis.dao.CourseQuestionDao;
import com.fengjie.courseprogram.util.DateKit;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author fengjie
 * @date 2019/5/8 17:33
 */
@Service
public class CourseQuestionService {

    @Autowired
    private CourseQuestionDao courseQuestionDao;

    public PageInfo<CourseQuestion> pageQuestions(Page page, String courseId) {
        Example example = new Example(CourseQuestion.class);
        example.setOrderByClause("question_no");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("delete_flag", 0);
        return PageHelper.startPage(page.getPageNum(), page.getPageSize())
                .doSelectPageInfo(() -> courseQuestionDao.selectCountByExample(example));
    }

    public int saveQuestion(CourseQuestion courseQuestion) {
        if (StringUtils.isEmpty(courseQuestion.getId())) {
            return this.addQuestion(courseQuestion);
        }
        return this.updateQuestion(courseQuestion);
    }

    public int updateQuestion(CourseQuestion courseQuestion) {
        DateKit.updateObject(courseQuestion);
        return courseQuestionDao.updateByPrimaryKeySelective(courseQuestion);
    }

    public int addQuestion(CourseQuestion courseQuestion) {
        //需要处理
        return courseQuestionDao.insertSelective(courseQuestion);
    }

    public int deleteQuestion(String questionId) {
        return courseQuestionDao.deleteByPrimaryKey(questionId);
    }

}
