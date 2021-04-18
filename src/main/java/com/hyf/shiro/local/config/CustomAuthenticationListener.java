package com.hyf.shiro.local.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author baB_hyf
 * @date 2021/04/13
 */
public class CustomAuthenticationListener implements AuthenticationListener {
    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("--> CustomAuthenticationListener --> onSuccess");
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
        System.out.println("--> CustomAuthenticationListener --> onFailure");
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        System.out.println("--> CustomAuthenticationListener --> onLogout");
    }
}
