package com.hyf.shiro.web;

import com.hyf.shiro.web.config.OtherConfiguration;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContextListener;
import java.util.Collections;
import java.util.EnumSet;

/**
 * @author baB_hyf
 * @date 2021/04/14
 */
// @ConditionalOnNotWebApplication // 测试Spring时关闭
@Configuration
@Import(OtherConfiguration.class)
public class ShiroWebConfiguration {

    /**
     * 非 FilterRegistrationBean 的 Filter 注册，会在容器初始化完毕后，创建servlet容器时，
     * 将Filter适配成 FilterRegistrationBean，再获取所有的 ServletContextInitializer，统一调用onStartup()方法
     */
    @Bean
    // ServletContextInitializer
    public FilterRegistrationBean<ShiroFilter> shiroFilter() {
        // 在允许请求到达应用程序之前执行必要的身份和访问控制操作
        ShiroFilter shiroFilter = new ShiroFilter();

        FilterRegistrationBean<ShiroFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(shiroFilter);
        filterRegistrationBean.setName("shiroFilter");
        // 同一个JVM下多个Shiro环境可能会产生问题
        filterRegistrationBean.setInitParameters(Collections.singletonMap("staticSecurityManagerEnabled", "true"));
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        filterRegistrationBean.setOrder(1);
        EnumSet<DispatcherType> enumSet = EnumSet.of(
                DispatcherType.REQUEST,
                DispatcherType.FORWARD,
                DispatcherType.INCLUDE,
                DispatcherType.ERROR);
        filterRegistrationBean.setDispatcherTypes(enumSet);

        // 从环境中获取 FilterChainResolver，再获取该请求匹配的过滤链，进行过滤


        //---------------------------------------------------------------------
        //      Send -> F -> F -> ShiroFilter -> F -> F -> Handler
        //      < ----- < -- < - <    |         ^
        //                       |    |         |
        //                       ^    F -> F -> F     ProxiedFilterChain
        //                       |              |
        //                       ^--- < -- < -- <
        //---------------------------------------------------------------------
        return filterRegistrationBean;
    }

    @Bean
    public ServletContextListener environmentLoaderListener() {

        // 创建并加载环境，加载时，会创建 SecurityManager/FilterChainResolver 两个非常重要的东西
        // 创建 SecurityManager 时查找WEB-INF/shiro.ini文件以进行Shiro配置
        // FilterChainResolver 将 PatternMatcher 和 FilterChainManager 结合，
        // 进行【一条条】过滤链的创建，也需要用到shiro.ini，内置很多过滤器(DefaultFilter)

        return new EnvironmentLoaderListener();
    }
}
