package com.fengjie.courseprogram.util;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.model.entity.base.BaseDO;

import java.util.Date;

public class DateKit {

	public static long nowUnix() {
		Date date = new Date();
		return date.getTime();
	}

	public static void updateObject(BaseDO baseDO){
		baseDO.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
		baseDO.setUpdateUser(LoginUserContext.getUser().getUsername());
	}

	public static void addObject(BaseDO baseDO){
		baseDO.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
		User user = LoginUserContext.getUser();
		baseDO.setCreateUser(user == null ? "" : user.getUsername());
	}

}
