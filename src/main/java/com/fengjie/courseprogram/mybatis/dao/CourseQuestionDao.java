package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.CourseQuestionMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengjie
 * @date 2019/5/8 17:28
 */
@Repository
public class CourseQuestionDao extends BaseDao<CourseQuestion, CourseQuestionMapper> {
}
