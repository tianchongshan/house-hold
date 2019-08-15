package com.tcs.household.mgr.dao;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tcs.household.dao.BaseDao;
import com.tcs.household.enums.LoginUserTypeEnums;
import com.tcs.household.enums.MessageCode;
import com.tcs.household.exception.BizException;
import com.tcs.household.mgr.model.request.UserListRequest;
import com.tcs.household.mgr.model.request.UserRequest;
import com.tcs.household.persistent.entity.SystemUserInfo;
import com.tcs.household.persistent.mapper.SystemUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("systemUserInfoDao")
public class SystemUserInfoDao extends BaseDao<SystemUserInfo> {

    @Autowired
    private SystemUserInfoMapper mapper;

    @Override
    protected Mapper<SystemUserInfo> getMapper() {
        return mapper;
    }

    /**
     *
     * @param userLoginName
     * @return
     */
    public SystemUserInfo getUserByLoginName(String userLoginName) {
        SystemUserInfo user = new SystemUserInfo();
        user.setLoginName(userLoginName);
        user.setFlag(0);
        return mapper.selectOne(user);
    }

    /**
     * 分页获取用户数据
     * @param param
     * @return
     */
    public Page<SystemUserInfo> getUserList(UserListRequest param) {
        Example example = new Example(SystemUserInfo.class);
        Example.Criteria c = example.createCriteria().andEqualTo("flag", 0);
        if (!StrUtil.isEmpty(param.getLoginName())) {
            c.andLike("loginName", param.getLoginName() + "%");
        }
        if (!StrUtil.isEmpty(param.getUserName())) {
            c.andLike("userName", param.getUserName() + "%");
        }
        if (!StrUtil.isEmpty(param.getMobileNo())) {
            c.andLike("mobileNo", param.getUserName() + "%");
        }
        c.andEqualTo("type", LoginUserTypeEnums.admin.getType());
        example.setOrderByClause("create_time desc");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        return (Page<SystemUserInfo>)mapper.selectByExample(example);
    }

    /**
     * 更新用户信息
     * @param u
     */
    public void updateUser(UserRequest user) {
        SystemUserInfo u = mapper.selectByPrimaryKey(user.getUserId());
        if (u == null || 1 == u.getFlag()) {
            throw new BizException(MessageCode.User_not_Exists.getCode(), MessageCode.User_not_Exists.getMessage());
        }
//        u.setEmail(user.getEmail());
        u.setUsername(user.getUsername());
        u.setLoginName(user.getLoginName());
        u.setMobileNo(user.getMobileNo());
        u.setUpdateTime(new Date());
        mapper.updateByPrimaryKey(u);
    }

    /**
     * 逻辑删除用户
     * @param userId
     */
    public void removeUser(Integer userId) {
        SystemUserInfo u = new SystemUserInfo();
        u.setFlag(1);
        u.setId(userId);
        mapper.updateByPrimaryKeySelective(u);
    }

    /**
     * 冻结用户
     * @param userId
     */
    public void freezeUser(Integer userId, Integer freezeFlg) {
        SystemUserInfo u = new SystemUserInfo();
        u.setIsFreeze(freezeFlg);;
        u.setId(userId);
        mapper.updateByPrimaryKeySelective(u);
    }


    /**
     * 保存用户登录信息
     */
    public void saveUserLoginInfo(String loginName,String requestIp){
        SystemUserInfo user=new SystemUserInfo();
        user.setLastLoginIp(requestIp);
        user.setLastLoginTime(new Date());
        Example example=new Example(SystemUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flag",0)
                .andEqualTo("loginName",loginName);
        mapper.updateByExampleSelective(user,example);
     mapper.saveUserLoginInfo(loginName,requestIp);
    }

    /**
     * 判断User是否存在
     * @return
     */
    public boolean userExistsForUpdate(Integer userId, String loginName) {
        Example example = new Example(SystemUserInfo.class);
        example.createCriteria().andEqualTo("loginName", loginName).andNotEqualTo("id", userId);
        return this.queryCount(example) > 0;
    }

    public Map<String, SystemUserInfo> getUserListByLoginName(List<String> userCodes) {
        Example example = new Example(SystemUserInfo.class);
        example.createCriteria().andIn("loginName", userCodes).andEqualTo("flag", 0);
        List<SystemUserInfo> list = this.query(example);
        Map<String, SystemUserInfo> map = new HashMap<>();
        for (SystemUserInfo user : list) {
            map.put(user.getLoginName(), user);
        }
        return map;
    }
}
