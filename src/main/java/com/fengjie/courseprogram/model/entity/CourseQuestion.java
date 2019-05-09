package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author fengjie
 * @date 2019/5/8 17:08
 */
@Data
public class CourseQuestion extends BaseDO {

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "question_no")
    private Integer questionNo;

    private Integer type;

    private Integer level;

    private String title;

    private String info;

    /**
     * 当时选择题时使用这个保存答案
     */
    private String answer;

    @Column(name = "system_in")
    private String systemIn;

    @Column(name = "system_out")
    private String systemOut;
}
