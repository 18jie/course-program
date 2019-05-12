package com.fengjie.courseprogram.model.queryvo;

import com.fengjie.courseprogram.model.entity.CourseQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fengjie
 * @date 2019:05:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseQuestionOperationVO extends CourseQuestion {

    private Integer OperationNo;

    private Integer singleGrade;

}
