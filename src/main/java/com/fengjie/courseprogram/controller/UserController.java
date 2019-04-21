package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
