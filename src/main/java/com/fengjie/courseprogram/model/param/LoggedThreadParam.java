package com.fengjie.courseprogram.model.param;

import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.model.entity.Teacher;
import com.fengjie.courseprogram.model.entity.User;
import lombok.Data;

/**
 * @author fengjie
 * @date 2019/5/7 14:31
 */
@Data
public class LoggedThreadParam {

    private User user;

    private Course course;

    private Teacher teacher;

    private Student student;
}
