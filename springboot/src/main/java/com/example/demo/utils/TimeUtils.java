package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    //获取当前年份
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    //获取当前月份
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    //获取年月日
    public static String getYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(new Date());
        System.out.println("格式化后的日期：" + dateNowStr);
        return dateNowStr;
    }

    /*
     * 获取当月第一天的时间
     * */
    public static Date getFisrtDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //String firstDayOfMonth = sdf.format(cal.getTime());
        return cal.getTime();
    }

    /*
     * 获取当月最后一天的时间
     * */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //String lastDayOfMonth = sdf.format(cal.getTime());
        return cal.getTime();
    }


    /*
     * date转string
     * */
    public static String dateToString(Date value) {
        Date sqlDate = new java.sql.Date(value.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(sqlDate);
    }

}
