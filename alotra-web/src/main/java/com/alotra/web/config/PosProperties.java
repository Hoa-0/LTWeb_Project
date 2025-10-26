package com.alotra.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.pos")
@Data
public class PosProperties {
    
    private Integer guestCustomerId;
}