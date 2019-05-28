package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author fengjie
 * @date 2019/5/8 17:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Operation extends BaseDO {

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "class_id")
    private String classId;

    private String title;

    @Column(name = "finished_condition")
    private Integer finishedCondition;

    private String questions;

    private String excelPath;

    @Column(name = "start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    /**
     * 发布状态：1-未发布；
     */
    private Integer status;

}
