package com.tcs.household.mgr.facade;

import com.github.pagehelper.Page;
import com.tcs.household.enums.MessageCode;
import com.tcs.household.exception.BizException;
import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.mgr.dao.SystemRoleDao;
import com.tcs.household.mgr.dao.SystemRolePermissionDao;
import com.tcs.household.mgr.model.request.RoleListRequest;
import com.tcs.household.mgr.model.request.RoleRequest;
import com.tcs.household.mgr.model.response.RoleInfo;
import com.tcs.household.mgr.model.response.RoleListItem;
import com.tcs.household.mgr.model.response.RoleListResponse;
import com.tcs.household.mgr.persistent.entity.SystemRole;
import com.tcs.household.mgr.security.model.UserAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chongshan.tian01.
 */
@Service
public class RoleFacadeService  {

    @Autowired
    private SystemRoleDao roleDao;

    @Autowired
    private SysCommonDao cmnDao;

    @Autowired
    private SystemRolePermissionDao rpDao;

    /**
     * 取得角色列表
     * @param req
     * @return
     */
    public RoleListResponse getRoleList(RoleListRequest req) {
        UserAuthInfo UserAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<SystemRole> lisRole = roleDao.getRoleList(req);
        RoleListResponse resp = new RoleListResponse();
        resp.copyPageInfo(lisRole);
        List<RoleListItem> list = new ArrayList<>();
        for (SystemRole r: lisRole) {
            RoleListItem item = new RoleListItem();
            item.setId(r.getId());
            item.setNickName(r.getNickName());
            item.setRoleName(r.getRoleName());
            item.setRemark(r.getRemark());
            list.add(item);
        }
        resp.setList(list);
        return resp;
    }

    /**
     * 取得角色信息
     * @param roleId
     * @return
     */
    public RoleInfo getRoleInfo(Integer roleId) {
        RoleInfo r = new RoleInfo();
        SystemRole sysRole = roleDao.getRoleById(roleId);
        if (sysRole == null || sysRole.getFlag() != 0) {
            throw new BizException(MessageCode.Role_not_Exists.getMessage(),
                    MessageCode.Role_not_Exists.getMessage());
        }
        r.setNickName(sysRole.getNickName());
        r.setRoleId(sysRole.getId());
        r.setRoleName(sysRole.getRoleName());
        r.setRemark(sysRole.getRemark());
        r.setPermissions(rpDao.getPermByRoleId(roleId));
        return r;
    }

    /**
     * 新增角色
     * @param req
     */
    @Transactional
    public void addNewRole(RoleRequest req) {
        if (roleDao.roleExists(req.getRoleName())) {
            throw new BizException(MessageCode.Role_Exists.getCode(),
                    MessageCode.Role_Exists.getMessage());
        }
        UserAuthInfo UserAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Integer> lisPerm = new ArrayList<>();
        if (req.getPermissions() != null) {
            for (Integer p : req.getPermissions()) {
                if (!lisPerm.contains(p)) {
                    lisPerm.add(p);
                }
            }
        }
        // 处理二级菜单问题
        Map<Integer, Integer> mapSec = cmnDao.getSecondMenus();
        List<Integer> firstPerm = new ArrayList<>();
        for (Integer p : lisPerm) {
            if (mapSec.containsKey(p)) {
                Integer pId = mapSec.get(p);
                if (!lisPerm.contains(pId)
                        && !firstPerm.contains(pId)) {
                    firstPerm.add(pId);
                }
            }
        }
        lisPerm.addAll(firstPerm);


        SystemRole role = new SystemRole();
        role.setNickName(req.getNickName());
        role.setRemark(req.getRemark());
        role.setRoleName(req.getRoleName());
        Integer roleId = roleDao.saveNewRole(role);

        rpDao.addRolePerm(roleId, lisPerm);
    }

    /**
     * 修改角色
     * @param req
     */
    @Transactional
    public void removeRole(RoleRequest req) {
        roleDao.removeRole(req.getRoleId());
        rpDao.removePermissionByRole(req.getRoleId());
    }

    /**
     * 修改角色
     * @param req
     */
    @Transactional
    public void modifyRole(RoleRequest req) {
        if (roleDao.roleExists(req.getRoleId(), req.getRoleName())) {
            throw new BizException(MessageCode.Role_Exists.getCode(),
                    MessageCode.Role_Exists.getMessage());
        }
        UserAuthInfo UserAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Integer> lisPerm = new ArrayList<>();
        if (req.getPermissions() != null) {
            for (Integer p : req.getPermissions()) {
                if (!lisPerm.contains(p)) {
                    lisPerm.add(p);
                }
            }
        }
        // 处理二级菜单问题
        Map<Integer, Integer> mapSec = cmnDao.getSecondMenus();
        List<Integer> firstPerm = new ArrayList<>();
        for (Integer p : lisPerm) {
            if (mapSec.containsKey(p)) {
                Integer pId = mapSec.get(p);
                if (!lisPerm.contains(pId)
                        && !firstPerm.contains(pId)) {
                    firstPerm.add(pId);
                }
            }
        }
        lisPerm.addAll(firstPerm);

        roleDao.saveRoleData(req);

        rpDao.removePermissionByRole(req.getRoleId());
        rpDao.addRolePerm(req.getRoleId(), lisPerm);
    }
}
