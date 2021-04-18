package com.hyf.shiro.spring;

import org.apache.shiro.spring.config.ShiroAnnotationProcessorConfiguration;
import org.apache.shiro.spring.config.ShiroBeanConfiguration;
import org.apache.shiro.spring.web.config.ShiroRequestMappingConfig;
import org.apache.shiro.spring.web.config.ShiroWebConfiguration;
import org.apache.shiro.spring.web.config.ShiroWebFilterConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author baB_hyf
 * @date 2021/04/18
 */
@ConditionalOnNotWebApplication // 测试SpringBoot时关闭
// SpringBoot环境不要此声明 Controller 需要使用 CGLIB 代理
// @EnableAspectJAutoProxy(proxyTargetClass = false)
@Configuration
@Import({ShiroBeanConfiguration.class, // Shiro可选Bean配置，被ShiroConfiguration导入
        // ShiroConfiguration.class,
        ShiroWebConfiguration.class, // Web环境配置，包含ShiroConfiguration所有功能，不包含默认导入功能
        ShiroWebFilterConfiguration.class, // ShiroFilter 支持
        ShiroAnnotationProcessorConfiguration.class, // 注解AOP实现
        ShiroRequestMappingConfig.class, // 替换 UrlPathHelper
        ShiroSpringConfiguration.class, // 自定义Shiro配置
})
public class ShiroEnableConfiguration {
}
