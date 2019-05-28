package com.fengjie.courseprogram.model.queryvo;

import com.fengjie.courseprogram.model.entity.CourseQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CourseQuestionCheckedVO extends CourseQuestion {

    private boolean checked = false;

}
