package com.tcs.household.mgr.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RoleInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6884105719565086577L;
	
	private Integer roleId;
	
	private String roleName;
	
	private String nickName;
	
	private String remark;
	
	private List<Integer> permissions;

}
