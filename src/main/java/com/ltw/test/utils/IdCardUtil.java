package com.ltw.test.utils;

import java.util.Calendar;

public class IdCardUtil {
    public static Integer getAgeByIdCard(String IdCard){
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH)+1;
        int dayNow = cal.get(Calendar.DATE);
        int year = Integer.valueOf(IdCard.substring(6, 10));
        int month = Integer.valueOf(IdCard.substring(10,12));
        int day = Integer.valueOf(IdCard.substring(12,14));
        Integer age = 0;
        if ((month < monthNow) || (month == monthNow && day<= dayNow) ){
            age = yearNow - year;
        }else {
            age = yearNow - year - 1;
        }
        return age;
    }
}
