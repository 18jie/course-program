package com.fengjie.courseprogram.model.param;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author fengjie
 * @date 2019:05:12
 */
@Data
public class SubmitQuestionParam {

    private String title;

    private String questionMsg;

    private LocalDate startTime;

    private LocalDate endTime;

}
