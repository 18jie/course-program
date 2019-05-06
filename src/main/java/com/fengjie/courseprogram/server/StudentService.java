package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.mybatis.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
