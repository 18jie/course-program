package com.fengjie.courseprogram.model.queryvo;

import lombok.Data;

@Data
public class GradeAnalysisVO {

    private Integer highestScore;

    private Integer lowestScore;

    private Double average;

    private Integer totalSubmit;

}
