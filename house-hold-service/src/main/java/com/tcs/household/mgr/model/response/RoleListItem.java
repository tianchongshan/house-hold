package com.tcs.household.mgr.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoleListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1730147350690630436L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 角色标识名
	 */
	private String roleName;
	
	/**
	 * 角色昵称（中文）
	 */
	private String nickName;
	
	/**
	 * 备注
	 */
	private String remark;
}
