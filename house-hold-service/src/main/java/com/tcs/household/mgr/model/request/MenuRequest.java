package com.tcs.household.mgr.model.request;

import com.tcs.household.model.request.AbstractBaseRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
public class MenuRequest extends AbstractBaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3417490046587052325L;
	
	@NotNull(message="菜单ID为空")
	private Integer menuId;
	
	@NotNull(message="菜单名为空")
	@Length(message="菜单名长度最大32")
	private String menuName;
	
	@Length(message="描述长度最大256")
	private String menuDesc;
	
	@Length(message="URL长度最大1024")
	private String url;
	
	@Length(message="ICON长度最大64")
	private String icon;
	
	@Pattern(regexp="^[a-z:0-9]*$", message="权限标识格式不正确")
	@Length(message="权限标识长度最大250", max=250)
	private String permission;
	
	@NotNull(message="父菜单ID为空")
	private Integer pId;
	
	@NotNull(message="排序为空")
	private Integer sort;
	
	@NotNull(message="菜单类型为空")
	private Integer menuType;
	
	@NotNull(message="请求方法为空")
	private String method;
	
	/**
	 * 绑定角色
	 */
	private List<Integer> lisRole;
}
