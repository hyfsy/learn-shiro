package com.hyf.shiro.local.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * @author baB_hyf
 * @date 2021/04/13
 */
public class CustomAtLeastOneSuccessfulStrategy extends AtLeastOneSuccessfulStrategy {
    @Override
    public AuthenticationInfo beforeAllAttempts(Collection<? extends Realm> realms, AuthenticationToken token) throws AuthenticationException {
        System.out.println("--> CustomAtLeastOneSuccessfulStrategy --> beforeAllAttempts");
        return super.beforeAllAttempts(realms, token);
    }

    @Override
    public AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        System.out.println("--> CustomAtLeastOneSuccessfulStrategy --> beforeAttempt");
        return super.beforeAttempt(realm, token, aggregate);
    }

    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
        System.out.println("--> CustomAtLeastOneSuccessfulStrategy --> afterAttempt");
        return super.afterAttempt(realm, token, singleRealmInfo, aggregateInfo, t);
    }

    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        System.out.println("--> CustomAtLeastOneSuccessfulStrategy --> afterAllAttempts");
        return super.afterAllAttempts(token, aggregate);
    }
}
