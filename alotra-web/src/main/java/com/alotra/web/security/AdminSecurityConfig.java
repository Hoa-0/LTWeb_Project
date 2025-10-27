package com.alotra.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// PasswordEncoder bean đã được comment, giữ nguyên
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {

    @Autowired
    private CustomEmployeeDetailsService customEmployeeDetailsService;

    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(new AntPathRequestMatcher("/admin/**"))
            .authorizeHttpRequests(authorize -> authorize
                // Chỉ cần permitAll cho trang login trong context /admin/**
                .requestMatchers("/admin/login").permitAll()
                // Bỏ permitAll cho static resources ở đây, để cấu hình chung xử lý
                // .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()
                .anyRequest().hasAuthority("ROLE_ADMIN") // Mọi request /admin/** khác cần ROLE_ADMIN
            )
            .formLogin(form -> form
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin/dashboard", true)
                .failureUrl("/admin/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .userDetailsService(customEmployeeDetailsService);

        return http.build();
    }
}