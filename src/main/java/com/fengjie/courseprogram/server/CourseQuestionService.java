package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.param.Page;
import com.fengjie.courseprogram.mybatis.dao.CourseQuestionDao;
import com.fengjie.courseprogram.util.DateKit;
import com.fengjie.courseprogram.util.ObjectId;
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
        criteria.andEqualTo("deleteFlag", Constants.UNDELETE);
        criteria.andEqualTo("courseId", courseId);
        return PageHelper.startPage(page.getPageNum(), page.getPageSize())
                .doSelectPageInfo(() -> courseQuestionDao.selectByExample(example));
    }

    public int saveQuestion(CourseQuestion courseQuestion) {
        if (StringUtils.isEmpty(courseQuestion.getId())) {
            return this.addQuestion(courseQuestion);
        }
        return this.updateQuestion(courseQuestion);
    }

    public int updateQuestion(CourseQuestion courseQuestion) {
        DateKit.teacherUpdate(courseQuestion);
        return courseQuestionDao.updateByPrimaryKeySelective(courseQuestion);
    }

    public int addQuestion(CourseQuestion courseQuestion) {
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

}
