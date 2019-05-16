package com.fengjie.courseprogram.model.queryvo;

import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.ProgramAnswer;
import lombok.Data;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:16
 */
@Data
public class CourseQuestionAnswerVO extends CourseQuestion {

    private List<ProgramAnswer> answers;

}
