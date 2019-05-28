package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentAnswer extends BaseDO {

    private String operationId;

    private String questionId;

    private String studentId;

    private String answer;

    private String answerStatus;

}
