package com.tcs.household.mgr.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuItemInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7443047971298757949L;

	/**
	 * 菜单ID
	 */
	private Integer id;
	
	/**
	 * 菜单名
	 */
	private String menuName;
	
	/**
	 * 菜单名
	 */
	private String menuDesc;
	
	/**
	 * 权限字符串
	 */
	private String permission;
	
	/**
	 * url
	 */
	private String url;
	
	/**
	 * 菜单图标
	 */
	private String icon;
	
	/**
	 * 类型 1-- 菜单 2--按钮
	 */
	private Integer type;
	
	/**
	 * 父菜单Id
	 */
	private Integer pId;
	
	/**
	 * 请求方法
	 */
	private String method;
	
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 绑定角色
	 */
	private List<Integer> lisRole;
}
