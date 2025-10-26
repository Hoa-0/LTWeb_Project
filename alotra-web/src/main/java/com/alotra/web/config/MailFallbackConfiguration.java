package com.alotra.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Provide a simple fallback JavaMailSender bean for local/dev when no mail
 * configuration is provided. This prevents application startup failure while
 * allowing EmailService to be autowired.
 */
@Configuration
public class MailFallbackConfiguration {

    @Bean
    @ConditionalOnMissingBean(JavaMailSender.class)
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl impl = new JavaMailSenderImpl();
        // Local defaults — set to localhost:25 so the bean exists. Adjust via
        // application.properties (spring.mail.*) in production.
        impl.setHost("localhost");
        impl.setPort(25);
        return impl;
    }
}
