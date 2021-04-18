package com.hyf.shiro.web.config;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 必须在Shiro链中的才能执行
 *
 * @author baB_hyf
 * @date 2021/04/17
 */
public class CustomFilter extends AccessControlFilter {

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        // 链条执行后的处理工作，下面两个都是preHandle中的
        super.postHandle(request, response);
    }

    // 下面两个任意返回一个true即可

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 直接认证
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 尝试登录并验证
        return true;
    }
}
