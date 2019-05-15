package com.fengjie.courseprogram.model.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fengjie
 * @date 2019:05:14
 */
@Data
public class StudentBatchAddParam {

    private String classId;

    private MultipartFile file;

}
