package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;

/**
 * @author fengjie
 * @date 2019:05:15
 */
@Data
public class Grade extends BaseDO {

    private String studentId;

    private String operationId;

    private String answered;

    private int status;

}
