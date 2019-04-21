package com.fengjie.courseprogram.util;

import java.util.Date;

public class DateKit {

	public static long nowUnix() {
		Date date = new Date();
		return date.getTime();
	}

}
