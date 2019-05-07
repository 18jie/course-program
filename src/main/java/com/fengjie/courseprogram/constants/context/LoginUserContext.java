package com.fengjie.courseprogram.constants.context;

import com.fengjie.courseprogram.model.entity.*;

/**
 * @author fengjie
 * @date 2019:04:23
 */
public class LoginUserContext {

    private final static ThreadLocal<LoggedThreadParam> LOCAL_MESSAGE = new ThreadLocal<>();

    private static LoggedThreadParam getParam(){
        LoggedThreadParam loggedThreadParam = LOCAL_MESSAGE.get();
        if(null == loggedThreadParam){
            loggedThreadParam = new LoggedThreadParam();
            LOCAL_MESSAGE.set(loggedThreadParam);
        }
        return loggedThreadParam;
    }

    public static void setUser(User user){
        getParam().setUser(user);
    }

    public static User getUser(){
         return getParam().getUser();
    }

    public static void setCourse(Course course){
        getParam().setCourse(course);
    }

    public static Course getCourse(){
        return getParam().getCourse();
    }

    public static void setTeacher(Teacher teacher){
        getParam().setTeacher(teacher);
    }

    public static Teacher getTeacher(){
        return getParam().getTeacher();
    }

    public static void setStudent(Student student){
        getParam().setStudent(student);
    }

    public static Student getStudent(){
        return getParam().getStudent();
    }

    public static void remove(){
        LOCAL_MESSAGE.remove();
    }

}
