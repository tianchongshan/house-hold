package com.tcs.household.mgr.security.model;

import com.tcs.household.mgr.persistent.entity.SystemPermissionInfo;
import com.tcs.household.persistent.entity.SystemUserInfo;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chongshan.tian01.
 */
public class MyUserFactory {

    private static final String CACHE_KEY="JWT_USER_";

    private MyUserFactory() {
    }

    public static UserAuthInfo createUserAuthInfo(SystemUserInfo user,
                                                  List<SystemPermissionInfo> listPermission){
        UserAuthInfo userAuthInfo=new UserAuthInfo(user.getId(),user.getLoginName(),
                user.getUserName(),user.getType(),mapToGrantedAuthorities(listPermission));
        return userAuthInfo;

    }
    /**
     *
     * @param listPermission
     * @return
     */
    private static List<MyGrantedAuthority> mapToGrantedAuthorities(final List<SystemPermissionInfo> listPermission) {
        final List<MyGrantedAuthority> list = new ArrayList<>();
        for (SystemPermissionInfo p : listPermission) {
            if (!StringUtils.isEmpty(p.getPermission()) && p.getPermission().indexOf(",") >= 0) {
                String[] perms = p.getPermission().split(",");
                for (String s : perms) {
                    list.add(new MyGrantedAuthority(p.getUrl(), p.getMethod(), s));
                }
            } else {
                list.add(new MyGrantedAuthority(p.getUrl(), p.getMethod(), p.getPermission()));
            }
        }
        return list;
    }
}
