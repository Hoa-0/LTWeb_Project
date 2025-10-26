package com.alotra.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.payment")
@Data
public class PaymentProperties {
    
    private Webhook webhook = new Webhook();
    private String settleAccount;
    
    @Data
    public static class Webhook {
        private String secret;
        private String successKeywords;
    }
}