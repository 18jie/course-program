package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * @author fengjie
 * @date 2019/5/8 17:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseQuestion extends BaseDO {

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "question_no")
    private Integer questionNo;

    /**
     * 题目类型：
     * 1.选择题
     * 2.编程题
     */
    private Integer type;

    private Integer level;

    private String title;

    private String info;

    /**
     * 当时选择题时使用这个保存答案
     */
    private String answer;

    private String exampleAnswer;

    @Column(name = "system_in")
    private String systemIn;

    @Column(name = "system_out")
    private String systemOut;

    private Integer totalTried;

    private Integer totalPassed;

    private BigDecimal passRate;
}
