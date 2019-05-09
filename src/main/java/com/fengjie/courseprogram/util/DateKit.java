package com.fengjie.courseprogram.util;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.base.BaseDO;

import java.util.Date;

public class DateKit {

	public static long nowUnix() {
		Date date = new Date();
		return date.getTime();
	}

	public static void teacherUpdate(BaseDO baseDO){
        baseDO.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
        baseDO.setUpdateUser(LoginUserContext.getTeacher().getId());
    }

    public static void teacherAdd(BaseDO baseDO){
	    baseDO.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
	    baseDO.setCreateUser(LoginUserContext.getTeacher().getId());
    }

    public static void studentUpdate(BaseDO baseDO){
	    baseDO.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
	    baseDO.setUpdateUser(LoginUserContext.getStudent().getId());
    }

    public static void studentAdd(BaseDO baseDO){
	    baseDO.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
	    baseDO.setUpdateUser(LoginUserContext.getStudent().getId());
    }

	public static void updateObject(BaseDO baseDO){
		baseDO.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
		baseDO.setUpdateUser(LoginUserContext.getUser().getId());
	}

	public static void addObject(BaseDO baseDO){
		baseDO.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
		baseDO.setCreateUser(LoginUserContext.getUser().getId());
	}

}
