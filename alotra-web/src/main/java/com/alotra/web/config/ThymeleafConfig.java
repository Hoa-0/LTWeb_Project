package com.alotra.web.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

    // Enable Thymeleaf Layout Dialect so layout:decorate/layout:fragment works
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
