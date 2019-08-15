package com.tcs.household.mgr.dao;

import com.tcs.household.dao.BaseDao;
import com.tcs.household.mgr.persistent.entity.SystemPermissionInfo;
import com.tcs.household.mgr.persistent.mapper.SystemPermissionInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

@Repository
public class SystemPermissionInfoDao extends BaseDao<SystemPermissionInfo> {
    @Autowired
    private SystemPermissionInfoMapper mapper;

    @Override
    protected Mapper<SystemPermissionInfo> getMapper() {
        return mapper;
    }

    /**
     * 保存新菜单
     * @param perm
     * @return
     */
    public Integer addNewMenu(SystemPermissionInfo perm) {
        Date now = new Date();
        perm.setCreateTime(now).setModifyTime(now).setFlag(0);
        mapper.insertSelective(perm);
        return perm.getId();
    }

    /**
     * 根据ID取得数据
     * @param permId
     * @return
     */
    public SystemPermissionInfo getPermById(Integer permId) {
        return mapper.selectByPrimaryKey(permId);
    }


    /**
     * 判断是否存在子项目
     * @param permId
     * @return
     */
    public boolean hasChild(Integer permId) {
        SystemPermissionInfo record = new SystemPermissionInfo();
        record.setFlag(0).setPid(permId);
        return mapper.selectCount(record) > 0;
    }

    /**
     * 更新数据
     * @param perm
     */
    public void modifyPerm(SystemPermissionInfo perm) {
        perm.setModifyTime(new Date());
        mapper.updateByPrimaryKey(perm);
    }

    /**
     * 逻辑删除数据
     * @param permId
     */
    public void removePerm(Integer permId) {
        SystemPermissionInfo perm = new SystemPermissionInfo();
        perm.setFlag(1);
        perm.setId(permId);
        mapper.updateByPrimaryKeySelective(perm);
    }
}
