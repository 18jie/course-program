package com.fengjie.courseprogram.model.entity;

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
