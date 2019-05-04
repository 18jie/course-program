package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author fengjie
 * @date 2019:05:03
 */
@Data
public class ProgramAnswer extends BaseDO {

    @Column(name = "question_id")
    private String questionId;

    @Column(name = "system_in")
    private String systemIn;

    @Column(name = "system_out")
    private String systemOut;

}
