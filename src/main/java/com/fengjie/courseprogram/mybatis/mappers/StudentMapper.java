package com.fengjie.courseprogram.mybatis.mappers;

import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.mybatis.tkbase.GenericMapper;

import java.util.List;

/**
 * @author fengjie
 * @date 2019/5/6 14:27
 */
public interface StudentMapper extends GenericMapper<Student> {

    void batchInsert(List<Student> students);

}
