package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.StudentAnswer;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.StudentAnswerMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentAnswerDao extends BaseDao<StudentAnswer, StudentAnswerMapper> {
}
