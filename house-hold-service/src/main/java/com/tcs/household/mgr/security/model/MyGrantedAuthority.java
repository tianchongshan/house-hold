package com.tcs.household.mgr.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created by chongshan.tian01.
 */
@Data
@AllArgsConstructor
public class MyGrantedAuthority implements GrantedAuthority{

    private static final long serialVersionUID = 3762117654077834058L;

    private String url;

    private String method;

    private String permission;


    @Override
    public String getAuthority() {
        return permission;
    }
}
