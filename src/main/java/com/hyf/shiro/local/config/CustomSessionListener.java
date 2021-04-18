package com.hyf.shiro.local.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @author baB_hyf
 * @date 2021/04/13
 */
public class CustomSessionListener implements SessionListener {
    @Override
    public void onStart(Session session) {
        System.out.println("--> CustomSessionListener --> onStart");
    }

    @Override
    public void onStop(Session session) {
        System.out.println("--> CustomSessionListener --> onStop");
    }

    @Override
    public void onExpiration(Session session) {
        System.out.println("--> CustomSessionListener --> onExpiration");
    }
}
