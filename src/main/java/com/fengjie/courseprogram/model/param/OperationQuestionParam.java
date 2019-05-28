package com.fengjie.courseprogram.model.param;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author fengjie
 * @date 2019/5/10 11:21
 */
@Data
public class OperationQuestionParam {

    private String uuid;


    private String questionIds;

}
