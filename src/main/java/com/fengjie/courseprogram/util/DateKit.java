package com.fengjie.courseprogram.util;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.model.entity.base.BaseDTO;

import java.util.Date;

public class DateKit {

	public static long nowUnix() {
		Date date = new Date();
		return date.getTime();
	}

	public static void updateObject(BaseDTO baseDTO){
		baseDTO.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
		baseDTO.setUpdateUser(LoginUserContext.getUser().getUsername());
	}

	public static void addObject(BaseDTO baseDTO){
		baseDTO.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
		User user = LoginUserContext.getUser();
		baseDTO.setCreateUser(user == null ? "" : user.getUsername());
	}

}
