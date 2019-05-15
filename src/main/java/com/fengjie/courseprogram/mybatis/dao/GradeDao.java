package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.Grade;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.GradeMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengjie
 * @date 2019:05:15
 */
@Repository
public class GradeDao extends BaseDao<Grade, GradeMapper> {
}
