package com.alotra.web.config;

import com.alotra.web.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.httpFirewall(httpFirewall());
    }

    @Bean
    public HttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true); // Cho phép semicolon trong URL (jsessionid)
        return firewall;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Dùng UserDetailsService của bạn (tránh phải tự tạo DaoAuthenticationProvider bị warning deprecated)
            .userDetailsService(customUserDetailsService)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/register/**",
                                 "/NhanVien/**", // Cho phép truy cập tự do vào NhanVien (sử dụng session riêng)
                                 "/api/debug/**", // Cho phép truy cập debug API
                                 "/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**", "/favicon.ico", "/error").permitAll()
                .anyRequest().authenticated()
            )
            // Bỏ qua CSRF cho khu vực NhanVien vì đang dùng Interceptor session-based và API POS gọi từ fetch
            .csrf(csrf -> csrf.ignoringRequestMatchers("/NhanVien/**"))
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}