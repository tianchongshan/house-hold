package com.tcs.household.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by chongshan.tian01.
 */
@AllArgsConstructor
@Getter
public enum LoginUserTypeEnums  {

    admin(1,"管理员"),
    customer(2,"顾客");


    private Integer type;

    private String desc;

}
