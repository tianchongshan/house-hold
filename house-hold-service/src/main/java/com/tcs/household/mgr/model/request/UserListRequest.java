package com.tcs.household.mgr.model.request;

import com.tcs.household.model.request.AbstractPagingBaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * 取得用户列表
 * @author lenovo
 *
 */
@Getter
@Setter
public class UserListRequest extends AbstractPagingBaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4057249822211464583L;

	/**
	 * 用户名
	 */
	private String loginName;
	
	/**
	 * 密码
	 */
	private String userName;
	
	/**
	 * 手机号
	 */
	private String mobileNo;
	
}
