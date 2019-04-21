package com.fengjie.courseprogram.model.param;

import lombok.Data;

/**
 * @author fengjie
 * @date 2019:04:15
 */
@Data
public class RegisterParam {

    private String username;

    private String email;

    private String password;

    private String repeatPassword;

    private String emailCode;

}
