package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.mybatis.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fengjie
 * @date 2019/5/5 15:22
 */
@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    public List<Course> getCoursesByTeacherId(String teacherId){
        Course course = new Course();
        course.setTeacherId(teacherId);
        course.setDeleteFlag(0);
        return courseDao.select(course);
    }

    public Course getCourse(String courseId){
        return courseDao.selectByPrimaryKey(courseId);
    }

}
