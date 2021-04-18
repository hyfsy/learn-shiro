package com.hyf.shiro.spring;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注解功能需要AOP支持
 *
 * @author baB_hyf
 * @date 2021/04/18
 * @see org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor
 * @see org.apache.shiro.authz.aop.AuthorizingAnnotationHandler
 * @see ShiroSpringConfiguration CGLIB配置修改
 */
@ConditionalOnBean(ShiroEnableConfiguration.class)
@RestController
@RequestMapping("permission")
public class ShiroAnnotationController {

    /**
     * 需要认证过
     */
    @RequiresAuthentication
    @RequestMapping("1")
    public void requiresAuthentication() {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new AuthenticationException("");
        }
    }

    /**
     * 需要没有认证过
     */
    @RequiresGuest
    @RequestMapping("2")
    public void requiresGuest() {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals != null && !principals.isEmpty()) {
            //known identity - not a guest:
            throw new AuthenticationException("");
        }
    }

    /**
     * 需要能识别为用户，包括认证、记住我
     */
    @RequiresUser
    @RequestMapping("3")
    public void RequiresUser() {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals == null || principals.isEmpty()) {
            //no identity - they're anonymous, not allowed:
            throw new AuthenticationException("");
        }
    }

    /**
     * 需要指定权限
     */
    @RequiresPermissions(value = {"admin:all", "user:add"}, logical = Logical.OR)
    @RequestMapping("4")
    public void requiresPermissions() {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isPermitted("account:create")) {
            throw new AuthorizationException("");
        }
    }

    /**
     * 需要指定角色
     */
    @RequiresRoles("admin")
    @RequestMapping("5")
    public void requiresRoles() {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.hasRole("administrator")) {
            throw new AuthorizationException("");
        }
    }
}
