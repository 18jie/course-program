package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.CourseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengjie
 * @date 2019/5/5 15:17
 */
@Repository
public class CourseDao extends BaseDao<Course, CourseMapper> {
}
