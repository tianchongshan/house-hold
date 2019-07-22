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
    housekeeper(2,"家政人员"),
    customer(3,"顾客");



    private Integer type;

    private String desc;

}
