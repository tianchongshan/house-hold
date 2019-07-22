package com.tcs.household.mgr.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Context用用户对象
 * Created by chongshan.tian01.
 */
@Getter
@AllArgsConstructor
public class UserAuthInfo implements UserDetails {

    private static final long serialVersionUID = 6283526017150049698L;

    private Integer id;

    private String loginName;

    private String userName;

    private Integer userType;

    private Collection<?extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
