package com.hyf.shiro.web.config;

import org.springframework.context.annotation.Bean;

/**
 * @author baB_hyf
 * @date 2021/04/18
 */
public class OtherConfiguration {

    @Bean
    public CustomFilter customFilter() {
        return new CustomFilter();
    }

    @Bean
    public IniObjectFilter iniObjectFilter() {
        return new IniObjectFilter();
    }
}
