package com.tcs.household.mgr.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628703048704590900L;
	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 登录名
	 */
	private String loginName;
	
	/**
	 * 邮件
	 */
	private String email;
	
	/**
	 * 手机号
	 */
	private String mobileNo;
	
	/**
	 * 用户类型 1--公司用户 2--外部用户
	 */
	private Integer userType;
	
	/**
	 * 冻结
	 */
	private Integer freeze;
	
	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;
	
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 所有权限
	 */
	private String roles;

}
