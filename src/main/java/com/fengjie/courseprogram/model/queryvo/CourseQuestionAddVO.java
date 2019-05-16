package com.fengjie.courseprogram.model.queryvo;

import com.fengjie.courseprogram.model.entity.CourseQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fengjie
 * @date 2019:05:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseQuestionAddVO extends CourseQuestion {

    private String systemStr;

}
