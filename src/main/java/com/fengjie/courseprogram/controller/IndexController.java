package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.model.param.EmailParam;
import com.fengjie.courseprogram.model.param.LoginParam;
import com.fengjie.courseprogram.model.param.RegisterParam;
import com.fengjie.courseprogram.server.EmailService;
import com.fengjie.courseprogram.server.UserService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author fengjie
 * @date 2019:04:13
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index(ModelMap map) {
        map.addAttribute("active", "head");
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/email_code")
    public @ResponseBody
    RestResponse<String> sendEmailCode(EmailParam email) {
        User user = new User();
        user.setEmail(email.getEmail());
        if (userService.isExitUser(user)) {
            return RestResponse.fail("邮箱已被注册");
        }
        emailService.sendTextEmail(email);
        return RestResponse.success();
    }

    @PostMapping("/do_register")
    public @ResponseBody
    RestResponse doRegister(RegisterParam registerParam) {
        if (!emailService.checkEmailCode(registerParam.getEmail(), registerParam.getEmailCode())) {
            return RestResponse.fail("验证码错误");
        }
        try {
            userService.registerUser(registerParam);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.fail("注册失败");
        }
        return RestResponse.success();
    }

    @PostMapping("do_login")
    public @ResponseBody
    RestResponse login(LoginParam loginParam, HttpSession session) {
        User user = userService.loginCheck(loginParam);
        if (user != null) {
            session.setAttribute("user", user);
            return RestResponse.success();
        }
        return RestResponse.fail("账号或密码错误");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "index";
    }

}
