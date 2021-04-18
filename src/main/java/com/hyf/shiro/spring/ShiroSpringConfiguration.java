package com.hyf.shiro.spring;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author baB_hyf
 * @date 2021/04/18
 */
public class ShiroSpringConfiguration {

    @Autowired
    private ShiroFilterChainDefinition shiroFilterChainDefinition;

    @Qualifier("defaultAdvisorAutoProxyCreator")
    @Autowired
    private DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator;

    @PostConstruct
    public void initSecurityManager() {
        customShiroFilterChainDefinition();

        // Shiro使用新的ProxyCreator，启用CGLIB需要自己重新配置
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
    }

    public void customShiroFilterChainDefinition() {
        shiroFilterChainDefinition.getFilterChainMap().clear();
        DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition = (DefaultShiroFilterChainDefinition) shiroFilterChainDefinition;
        defaultShiroFilterChainDefinition.addPathDefinition("/permission/**", "anon");
        defaultShiroFilterChainDefinition.addPathDefinition("/login.jsp", "anon");
        defaultShiroFilterChainDefinition.addPathDefinition("/**", "authc");
    }

    /**
     * 必须要有一个Realm
     */
    @Bean
    public Realm realm() {
        return new Realm() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean supports(AuthenticationToken token) {
                return false;
            }

            @Override
            public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                return null;
            }
        };
    }

    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }
}
