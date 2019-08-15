package com.tcs.household.mgr.model.request;

import com.tcs.household.model.request.AbstractBaseRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class RoleRequest extends AbstractBaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4239346431058961391L;
	
	@NotNull(message="角色ID为空")
	private Integer roleId;
	
	@NotNull(message="角色名为空")
	@Pattern(regexp="^[a-zA-Z_0-9]*$")
	@Length(message="角色名长度最大32")
	private String roleName;
	
	@NotNull(message="角色昵称为空")
	@Length(message="角色昵称长度最大64")
	private String nickName;
	
	@Length(message="备注长度最大256")
	private String remark;
	
	private Integer[] permissions;

}
