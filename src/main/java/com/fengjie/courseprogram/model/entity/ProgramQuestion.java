package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author fengjie
 * @date 2019:04:29
 */
@Data
@Table(name = "program_questions")
public class ProgramQuestion extends BaseDO {

    @Column(name = "title")
    private String title;

    @Column(name = "program_no")
    private Integer programNo;

    @Column(name = "info")
    private String info;

    /**
     * 1-简单；2-中等；3-困难
     */
    @Column(name = "level")
    private Integer level;

    @Column(name = "total_tried")
    private Integer totalTried;

    @Column(name = "total_passed")
    private Integer totalPassed;

}
