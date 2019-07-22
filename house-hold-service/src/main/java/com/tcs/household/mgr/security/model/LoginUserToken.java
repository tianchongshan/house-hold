package com.tcs.household.mgr.security.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by chongshan.tian01.
 */
public class LoginUserToken extends UsernamePasswordAuthenticationToken{

    private static final long serialVersionUID = 5347042813724742050L;


    public LoginUserToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

}
