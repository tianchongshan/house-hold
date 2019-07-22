package com.tcs.household.codeUtils.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author lushaozhong
 * @date 2019-02-14 10:15
 */
@Getter
@Setter
public class JsonResponse<T> implements Serializable {

	private static final long serialVersionUID = 167495444117952739L;

	private String code;

    private String msg;

    private String globalTicket;

    private String monitorTrackId = UUID.randomUUID().toString();

    private T data;

}
