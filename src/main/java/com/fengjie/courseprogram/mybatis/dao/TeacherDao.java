package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.Teacher;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.TeacherMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengjie
 * @date 2019:05:04
 */
@Repository
public class TeacherDao extends BaseDao<Teacher, TeacherMapper> {
}
