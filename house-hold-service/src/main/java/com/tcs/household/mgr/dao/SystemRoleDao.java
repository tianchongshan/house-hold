package com.tcs.household.mgr.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tcs.household.dao.BaseDao;
import com.tcs.household.enums.MessageCode;
import com.tcs.household.exception.BizException;
import com.tcs.household.mgr.model.request.RoleListRequest;
import com.tcs.household.mgr.model.request.RoleRequest;
import com.tcs.household.mgr.persistent.entity.SystemRole;
import com.tcs.household.mgr.persistent.mapper.SystemRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;


/**
 * 系统角色表
 */
@Repository
public class SystemRoleDao extends BaseDao<SystemRole> {

    @Autowired
    private SystemRoleMapper mapper;
    @Override
    protected Mapper<SystemRole> getMapper() {
        return mapper;
    }

    /**
     * 取得所有可用的Role
     * @return
     */
    public Page<SystemRole> getRoleList(RoleListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        SystemRole record = new SystemRole();
        record.setFlag(0);
        return (Page<SystemRole>)mapper.select(record);
    }

    /**
     *
     * @param roleId
     * @return
     */
    public SystemRole getRoleById(Integer roleId) {
        return mapper.selectByPrimaryKey(roleId);
    }

    /**
     * 判断Role是否存在
     * @param roleName
     * @return
     */
    public boolean roleExists(String roleName) {
        SystemRole record = new SystemRole();
        record.setRoleName(roleName);
        return mapper.selectCount(record) > 0;
    }

    /**
     * 判断Role是否存在
     * @param roleName
     * @return
     */
    public boolean roleExists(Integer roleId, String roleName) {
        Example example = new Example(SystemRole.class);
        example.createCriteria().andEqualTo("roleName", roleName).andNotEqualTo("id", roleId);
        return mapper.selectCountByExample(example) > 0;
    }

    /**
     * 保存Role
     * @param role
     * @return
     */
    public Integer saveNewRole(SystemRole role) {
        Date now = new Date();
        role.setFlag(0);
        role.setCreateTime(now);
        role.setUpdateTime(now);
        mapper.insertSelective(role);
        return role.getId();
    }

    /**
     * 保存Role
     * @param req
     * @return
     */
    public void saveRoleData(RoleRequest req) {
        SystemRole role = mapper.selectByPrimaryKey(req.getRoleId());
        if (role == null) {
            throw new BizException(MessageCode.Role_not_Exists.getCode(),
                    MessageCode.Role_not_Exists.getMessage());
        }
        role.setFlag(0);
        role.setNickName(req.getNickName());
        role.setRemark(req.getRemark());
        role.setRoleName(req.getRoleName());
        role.setUpdateTime(new Date());
        mapper.updateByPrimaryKey(role);
    }

    /**
     * 删除角色
     * @param roleId
     */
    public void removeRole(Integer roleId) {
        SystemRole role = new SystemRole();;
        role.setId(roleId);
        role.setFlag(1);
        mapper.updateByPrimaryKeySelective(role);
    }
}
