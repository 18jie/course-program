package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.time.LocalDate;

/**
 * @author fengjie
 * @date 2019/5/8 17:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Operation extends BaseDO {

    @Column(name = "course_id")
    private String courseId;

    private String title;

    @Column(name = "finished_condition")
    private Integer finishedCondition;

    private String questions;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "end_time")
    private LocalDate endTime;

    /**
     * 发布状态：1-未发布；
     */
    private Integer status;

}
