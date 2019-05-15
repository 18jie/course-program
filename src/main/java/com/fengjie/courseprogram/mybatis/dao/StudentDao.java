package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fengjie
 * @date 2019/5/6 14:27
 */
@Repository
public class StudentDao extends BaseDao<Student, StudentMapper> {

    @Autowired
    private StudentMapper mapper;

    public void batchInsert(List<Student> students){
        mapper.batchInsert(students);
    }

}
