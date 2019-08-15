package com.tcs.household.mgr.facade;

import com.tcs.household.enums.MessageCode;
import com.tcs.household.exception.BizException;
import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.mgr.dao.SystemPermissionInfoDao;
import com.tcs.household.mgr.dao.SystemRolePermissionDao;
import com.tcs.household.mgr.model.request.MenuRequest;
import com.tcs.household.mgr.model.response.MenuInfo;
import com.tcs.household.mgr.model.response.MenuItemInfo;
import com.tcs.household.mgr.persistent.entity.SystemPermissionInfo;
import com.tcs.household.mgr.security.model.UserAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chongshan.tian01.
 */
@Service
public class MenuFacadeService {

    @Autowired
    private SysCommonDao cmnDao;

    @Autowired
    private SystemPermissionInfoDao systemPermissionInfoDao;

    @Autowired
    private SystemRolePermissionDao rolePermDao;


    /**
     * 取得所有权限（用户为admin）
     * @return
     */
    public List<MenuInfo> getAllPermission() {
        List<SystemPermissionInfo> listPerm = null;
        UserAuthInfo UserInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        listPerm = cmnDao.getAllPermissions(); //cmnDao.getPermissonByLoginName(UserInfo.getUsername());
        return convertMenu(listPerm);
    }

    /**
     * 菜单转换
     * @param listPerm
     * @return
     */
    private List<MenuInfo> convertMenu(List<SystemPermissionInfo> listPerm) {
        List<MenuInfo> retList = new ArrayList<>();
        Map<Integer, MenuInfo> mapMenuInfo = new HashMap<>();
        for (SystemPermissionInfo p : listPerm) {
            if (p.getPid() == 0) {
                MenuInfo m = createMenuItem(p);
                retList.add(m);
                mapMenuInfo.put(p.getId(), m);
            } else {
                if (mapMenuInfo.containsKey(p.getPid())) {
                    MenuInfo m = createMenuItem(p);
                    mapMenuInfo.get(p.getPid()).getChildren().add(m);
                    mapMenuInfo.put(p.getId(), m);
                }
            }
        }
        return retList;
    }

    private MenuInfo createMenuItem(SystemPermissionInfo p) {
        MenuInfo m = new MenuInfo();
        m.setId(p.getId());
        m.setMenuName(p.getPermName());
        m.setMenuDesc(p.getRemark());
        m.setUrl(p.getUrl());
        m.setIcon(p.getIcon());
        m.setPermission(p.getPermission());
        m.setPId(p.getId());
        m.setType(p.getType());
        m.setMethod(p.getMethod());
        m.setSort(p.getSort());
        m.setChildren(new ArrayList<MenuInfo>());
        return m;
    }

    /**
     * 添加新菜单
     * @param menu
     */
    @Transactional
    public void addNewMenu(MenuRequest menu) {
        SystemPermissionInfo perm = new SystemPermissionInfo();
        perm.setPermName(menu.getMenuName());
        perm.setRemark(menu.getMenuDesc());
        perm.setUrl(menu.getUrl());
        perm.setIcon(menu.getIcon());
        perm.setPermission(menu.getPermission());
        perm.setPid(menu.getPId());
        perm.setSort(menu.getSort());
        perm.setType(menu.getMenuType());
        perm.setMethod(menu.getMethod());
        systemPermissionInfoDao.addNewMenu(perm);
        if (menu.getLisRole() != null && menu.getLisRole().size() > 0) {
            rolePermDao.addPermRole(perm.getId(), menu.getLisRole());
        }
    }

    /**
     * 修改菜单
     * @param menu
     */
    @Transactional
    public void modifyMenu(MenuRequest menu) {
        SystemPermissionInfo perm = systemPermissionInfoDao.getPermById(menu.getMenuId());
        perm.setPermName(menu.getMenuName());
        perm.setRemark(menu.getMenuDesc());
        perm.setUrl(menu.getUrl());
        perm.setIcon(menu.getIcon());
        perm.setPermission(menu.getPermission());
        perm.setSort(menu.getSort());
        perm.setMethod(menu.getMethod());
        systemPermissionInfoDao.modifyPerm(perm);
        rolePermDao.removePermissionByPermId(menu.getMenuId());
        if (menu.getLisRole() != null && menu.getLisRole().size() > 0) {
            rolePermDao.addPermRole(menu.getMenuId(), menu.getLisRole());
        }
    }

    /**
     * 取得菜单详情
     * @param menuId
     * @return
     */
    public MenuItemInfo getMenuById(Integer menuId) {
        MenuItemInfo menu = new MenuItemInfo();
        SystemPermissionInfo perm = systemPermissionInfoDao.getPermById(menuId);
        if (perm == null) {
            throw new BizException(MessageCode.Menu_not_Exists.getCode(),
                    MessageCode.Menu_not_Exists.getMessage());
        }
        menu.setIcon(perm.getIcon());
        menu.setId(perm.getId());
        menu.setMenuDesc(perm.getRemark());
        menu.setMenuName(perm.getPermName());
        menu.setMethod(perm.getMethod());
        menu.setPId(perm.getPid());
        menu.setType(perm.getType());
        menu.setUrl(perm.getUrl());
        menu.setPermission(perm.getPermission());
        menu.setMethod(perm.getMethod());
        menu.setSort(perm.getSort());
        menu.setLisRole(rolePermDao.getRoleByPermId(menu.getId()));
        return menu;
    }

    /**
     * 删除菜单
     * @param menu
     */
    @Transactional
    public void removeMenu(MenuRequest menu) {
        if (systemPermissionInfoDao.hasChild(menu.getMenuId())) {
            throw new BizException(MessageCode.Menu_has_child.getCode(),
                    MessageCode.Menu_has_child.getMessage());
        }
        systemPermissionInfoDao.removePerm(menu.getMenuId());
        rolePermDao.removePermissionByPermId(menu.getMenuId());
    }
}
