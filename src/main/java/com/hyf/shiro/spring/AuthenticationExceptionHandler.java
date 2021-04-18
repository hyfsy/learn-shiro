package com.hyf.shiro.spring;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author baB_hyf
 * @date 2021/04/18
 */
@ConditionalOnBean(ShiroEnableConfiguration.class)
@ControllerAdvice
public class AuthenticationExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public void authenticationHandler(AuthenticationException ae) {
        log.warn("authentication fail: {}", ae.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    public void authorizationException(AuthorizationException ae) {
        log.warn("authorization fail: {}", ae.getMessage());
    }
}
