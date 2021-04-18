package com.hyf.shiro.spring;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author baB_hyf
 * @date 2021/04/18
 */
@ConditionalOnBean(ShiroEnableConfiguration.class)
@Component
public class SecurityManagerUtil {

    @Autowired
    private SecurityManager securityManager;

    @PostConstruct
    public void initSecurityManager() {
        System.out.println("init SecurityManager: " + securityManager);
        SecurityUtils.setSecurityManager(securityManager);
        // now can get the current user: SecurityUtils.getSubject();
    }
}
