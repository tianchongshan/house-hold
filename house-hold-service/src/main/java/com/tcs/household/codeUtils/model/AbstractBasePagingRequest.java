package com.tcs.household.codeUtils.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页查询请求基类
 * @author xiaoj
 *
 */
@Getter
@Setter
public abstract class AbstractBasePagingRequest extends AbstractBaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4069443595511841737L;

	/**
	 * 当前页面
	 */
	private Integer pageNum;
	
	/**
	 * 每页大小
	 */
	private Integer pageSize;
}
