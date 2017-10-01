package com.hg.mylifetomorrow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MLTUtil {
	
	public final static String DT_FORMAT="yyyy-MM-dd HH:mm:ss a";
	public final static String DT_FORMAT3="yyyy-MM-dd";
	public final static String DT_FORMAT2="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private final static SimpleDateFormat sdf =new SimpleDateFormat();
	
	public static String getCurrentDateTime(final String pattern){
		java.util.Date dt = new java.util.Date();
		sdf.applyPattern(pattern);
		return sdf.format(dt);
	}
	
	public static String getDateInDisplayFormat(final String date) throws ParseException{
		final SimpleDateFormat ft = new SimpleDateFormat(DT_FORMAT, Locale.getDefault());
		final java.util.Date t=ft.parse(date);
		ft.applyPattern("MMM dd, yyyy hh:mm:ss a");
		return ft.format(t);
	}
	
	public static String getDateToString(final Date date) throws ParseException{
		
		final SimpleDateFormat sdf = new SimpleDateFormat(DT_FORMAT, Locale.getDefault());
		sdf.applyPattern(DT_FORMAT3);
		return sdf.format(date); 
	}
	
	public static String getDateToString2(final Date date) throws ParseException{
		
		final SimpleDateFormat sdf = new SimpleDateFormat(DT_FORMAT2, Locale.getDefault());
		sdf.applyPattern(DT_FORMAT3);
		return sdf.format(date); 
	}
	

	
	public static Date getStringToDate(final String date) throws ParseException{
		
		final SimpleDateFormat sdf = new SimpleDateFormat(DT_FORMAT3,  Locale.getDefault());
		sdf.applyPattern(DT_FORMAT3);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));           
		return sdf.parse(date);
	}
	
	public static void main(String a[]) throws ParseException{
		System.out.println(getStringToDate("1980-05-27"));
	}

}
