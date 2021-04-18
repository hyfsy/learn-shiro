package com.hyf.shiro.local.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.event.Subscribe;

/**
 * @author baB_hyf
 * @date 2021/04/13
 */
public class CustomEventBusRegister {

    @Subscribe
    public void event(Object event) {
        System.out.println("--> CustomEventBusRegister --> event: " + event);
    }

    @Subscribe
    public void event2(AuthenticationInfo event) {
        System.out.println("--> CustomEventBusRegister --> event2: " + event);
    }
}
