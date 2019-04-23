package com.fengjie.courseprogram.constants.context;

import com.fengjie.courseprogram.model.entity.User;

/**
 * @author fengjie
 * @date 2019:04:23
 */
public class LoginUserContext {

    private final static ThreadLocal<User> LOCAL_USER = new ThreadLocal<>();

    public static void setUser(User user){
        LOCAL_USER.set(user);
    }

    public static User getUser(){
         return LOCAL_USER.get();
    }

}
