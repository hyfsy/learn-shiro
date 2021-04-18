package com.hyf.shiro.web.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.web.env.EnvironmentLoader;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

/**
 * @author baB_hyf
 * @date 2021/04/18
 */
public class IniObjectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ServletContext servletContext = request.getServletContext();
        IniWebEnvironment environment = (IniWebEnvironment) servletContext.getAttribute(EnvironmentLoader.ENVIRONMENT_ATTRIBUTE_KEY);
        IniObject iniObject = environment.getObject("iniObject", IniObject.class);
        System.out.println(iniObject);

        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) environment.getFilterChainResolver();
        FilterChainManager filterChainManager = filterChainResolver.getFilterChainManager();
        filterChainManager.addFilter("custom", new CustomFilter(), false);
        filterChainManager.addToChain("/**", "custom", null);
        filterChainManager.setGlobalFilters(Collections.singletonList("custom"));

        // EhCache 缓存支持
        cacheSupport();
    }

    public void cacheSupport() {

        // notice CacheManagerAware effect

        // set CacheManager
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        EhCacheManager ehCacheManager = new EhCacheManager();
        securityManager.setCacheManager(ehCacheManager);

        // set SessionDAO
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionManager.setSessionDAO(sessionDAO);

        // custom config

        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");

        sessionDAO.setActiveSessionsCacheName("cacheName");
    }
}
