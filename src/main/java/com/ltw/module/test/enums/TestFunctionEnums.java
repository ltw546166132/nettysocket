package com.ltw.module.test.enums;

import com.ltw.module.test.entity.TestUser;
import com.ltw.module.test.utils.IdCardUtil;
import lombok.Getter;
import java.util.List;
import java.util.function.Function;

@Getter
public enum TestFunctionEnums {
    AGE20("age20", "20岁以下", (list)->{
        return countNum(list, 20);
    }),
    AGE20_34( "age20-34", "20-34岁", (list)->{
        return countNum(list, 20, 34);
    });

    private String code;
    private String desc;
    private Function getCountNum;

    TestFunctionEnums(String code, String desc, Function<List<TestUser>, Integer> getCountNum){
        this.code = code;
        this.desc = desc;
        this.getCountNum = getCountNum;
    }

    private static Integer countNum(List<TestUser> list, Integer one){
        Integer num = 0;
        for (TestUser testUser: list) {
            String idCard = testUser.getIdCard();
            Integer age = IdCardUtil.getAgeByIdCard(idCard);
            if(age < one){
                num++;
            }
        }
        return num;
    }

    private static Integer countNum(List<TestUser> list, Integer one, Integer two){
        Integer num = 0;
        for (TestUser testUser: list) {
            String idCard = testUser.getIdCard();
            Integer age = IdCardUtil.getAgeByIdCard(idCard);
            if(age < two && age>=one){
                num++;
            }
        }
        return num;
    }
}
