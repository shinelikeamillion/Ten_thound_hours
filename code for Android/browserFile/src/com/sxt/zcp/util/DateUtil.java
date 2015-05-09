package com.sxt.zcp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyÄêMMÔÂddÈÕ HH:mm:ss");
	public static String formatDate(Date date){
		return format.format(date);
	}
}
