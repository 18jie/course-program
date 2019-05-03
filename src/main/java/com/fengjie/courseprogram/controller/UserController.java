package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.model.param.UserModifyParam;
import com.fengjie.courseprogram.server.UserService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fengjie
 * @date 2019:04:15
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user_message")
    public String getUser(String email){
        return "usermsg";
    }

    @PostMapping("/modify_user")
    public @ResponseBody RestResponse modifyUser(UserModifyParam userModifyParam){
        int i = userService.updateUser(userModifyParam);
        if(i == 1){
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

}
