package com.hyf.shiro.local.config;

import org.apache.shiro.event.support.EventListener;

/**
 * @author baB_hyf
 * @date 2021/04/13
 */
public class CustomEventListener implements EventListener {
    @Override
    public boolean accepts(Object o) {
        System.out.println("--> CustomEventListener --> accepts");
        return true;
    }

    @Override
    public void onEvent(Object o) {
        System.out.println("--> CustomEventListener --> onEvent");
    }
}
