package com.fengjie.courseprogram.model.entity;

import com.fengjie.courseprogram.model.entity.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;

/**
 * @author fengjie
 * @date 2019:04:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseDTO {

    @Column(name = "username")
    private String username;

    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "info")
    private String info;

}
