package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author fengjie
 * @date 2019/5/6 14:21
 */
@Data
public class Class extends BaseDO {

    @Column(name = "course_id")
    private  String courseId;

    private String name;

}
