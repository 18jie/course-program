package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author fengjie
 * @date 2019/5/5 14:25
 */
@Data
public class Course extends BaseDO {

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_info")
    private String courseInfo;

    @Column(name = "pre_course")
    private String preCourse;

    /**
     * 教学计划
     */
    @Column(name = "course_plan")
    private String coursePlan;

    /**
     * 参考书目
     */
    @Column(name = "bibliography")
    private String bibliography;

    /**
     * 课件地址id
     */
    @Column(name = "courseware")
    private String courseware;
}
