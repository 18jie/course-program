package com.fengjie.courseprogram.model.param;

import lombok.Data;

/**
 * @author fengjie
 * @date 2019:04:23
 */
@Data
public class UserModifyParam {

    private String username;

    private String info;

    private String oldPassword;

    private String password;

    private String newPassword;

}
