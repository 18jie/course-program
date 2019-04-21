package com.fengjie.courseprogram.model.entity.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Date;

/**
 * @author fengjie
 * @date 2019:04:14
 */
@Data
public class BaseDTO {
    @Id
    private String id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_user")
    private String updateUser;

}
