package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDO;
import lombok.Data;

/**
 * @author fengjie
 * @date 2019:05:04
 */
@Data
public class Teacher extends BaseDO {

    private String username;

    private String email;

    private String password;

    private String info;
}
