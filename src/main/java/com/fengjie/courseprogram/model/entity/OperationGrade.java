package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;

/**
 * @author fengjie
 * @date 2019/5/8 17:26
 */
@Data
public class OperationGrade extends BaseDO {

    private String  operationId;

    private String studentId;

    private Integer grade;

}
