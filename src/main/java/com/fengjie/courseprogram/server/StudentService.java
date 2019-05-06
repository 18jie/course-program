package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.model.param.Page;
import com.fengjie.courseprogram.mybatis.dao.StudentDao;
import com.fengjie.courseprogram.util.MD5Kit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * @author fengjie
 * @date 2019/5/6 14:29
 */
@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public List<Student> getStudentsByClassId(String classId) {
        Student student = new Student();
        student.setClassId(classId);
        return studentDao.select(student);
    }

    public int deleteStudentsByClassId(String classId){
        Student student = new Student();
        student.setClassId(classId);
        return studentDao.delete(student);
    }

    public Student getStudentById(String studentId){
        return studentDao.selectByPrimaryKey(studentId);
    }

    public int updateStudentById(Student student){
        if(!StringUtils.isEmpty(student.getPassword())){
            student.setPassword(MD5Kit.convertMD5(student.getPassword()));
        }
        return studentDao.updateByPrimaryKeySelective(student);
    }

    public int deleteStudentById(String id){
        return studentDao.deleteByPrimaryKey(id);
    }

}
