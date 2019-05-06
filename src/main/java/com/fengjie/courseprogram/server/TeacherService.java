package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.model.entity.Class;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.model.entity.Teacher;
import com.fengjie.courseprogram.model.param.LoginParam;
import com.fengjie.courseprogram.mybatis.dao.TeacherDao;
import com.fengjie.courseprogram.util.MD5Kit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:04
 */
@Service
public class TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService;

    public Teacher loginCheck(LoginParam loginParam) {
        Teacher teacher = new Teacher();
        teacher.setEmail(loginParam.getEmail());
        if (teacherDao.selectCount(teacher) == 0) {
            return null;
        }
        teacher.setPassword(MD5Kit.convertMD5(loginParam.getPassword()));
        return teacherDao.selectOne(teacher);
    }

    public List<Course> getCourses(String teacherId) {
        return courseService.getCoursesByTeacherId(teacherId);
    }

    public Course getCourseByCourseId(String courseId) {
        return courseService.getCourse(courseId);
    }

    public List<Class> getClassesByCourseId(String courseId) {
        return classService.getClassesByCourseId(courseId);
    }

    public List<Student> getStudentByClassId(String classId) {
        return studentService.getStudentsByClassId(classId);
    }


}
