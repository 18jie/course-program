package com.fengjie.courseprogram.model.queryvo;

import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.Operation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OperationVO extends Operation {

    private String startTimeStr;

    private String endTimeStr;

    private List<CourseQuestionOperationVO> questionList;

}
