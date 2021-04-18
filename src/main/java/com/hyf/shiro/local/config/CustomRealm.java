package com.hyf.shiro.local.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * @author baB_hyf
 * @date 2021/04/13
 */
public class CustomRealm implements Realm {

    private final String realmName = "custom_realm";

    @Override
    public String getName() {
        System.out.println("--> CustomRealm --> getName");
        return realmName;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        System.out.println("--> CustomRealm --> supports");
        return true;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("--> CustomRealm --> getAuthenticationInfo");
        SimplePrincipalCollection collection = new SimplePrincipalCollection();
        collection.add(token.getPrincipal(), realmName);

        SimpleAccount simpleAccount = new SimpleAccount(collection, token.getCredentials());
        // return simpleAccount;
        return null;
    }
}
