package com.alotra.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Sử dụng BCrypt để mã hóa mật khẩu
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Cho phép tất cả mọi người truy cập vào các URL này
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/register").permitAll()
                // Tất cả các request khác đều cần phải xác thực (đăng nhập)
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                // Cấu hình trang đăng nhập
                .loginPage("/login")
                // URL xử lý đăng nhập, Spring Security sẽ tự động xử lý
                .loginProcessingUrl("/login")
                // Chuyển hướng đến trang chủ sau khi đăng nhập thành công
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            );

        return http.build();
    }
}