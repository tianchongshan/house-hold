package com.tcs.household.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerResponse implements Serializable {

    private static final long serialVersionUID = 3548498712722262811L;

    private String customCode;

    private String customName;

    private Integer CustomType;

    private String channel;

    private Date createTime;

    private Date updateTime;

}
