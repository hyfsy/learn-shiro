package com.hyf.shiro.local.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.event.EventBus;
import org.apache.shiro.event.EventBusAware;
import org.apache.shiro.realm.Realm;

/**
 * @author baB_hyf
 * @date 2021/04/13
 */
public class CustomEventBusRealmHelper implements EventBusAware, Realm {

    private EventBus eventBus;

    @Override
    public String getName() {
        return "EventBusHelperRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        eventBus.publish(token);
        System.out.println("--> CustomEventBusRealmHelper --> supports -> EventBus publish");
        return true;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        eventBus.publish(token);
        System.out.println("--> CustomEventBusRealmHelper --> getAuthenticationInfo -> EventBus publish");
        return null;
    }

    @Override
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
