package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;

/**
 * @author fengjie
 * @date 2019/5/6 14:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseDO {

    @Column(name = "class_id")
    private String classId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "password")
    private String password;

    @Column(name = "info")
    private String info;

}
