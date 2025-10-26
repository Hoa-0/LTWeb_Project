package com.alotra.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
<<<<<<< HEAD
import org.springframework.security.core.userdetails.UserDetailsService;
=======
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
>>>>>>> e66511f5f76c02a6c7a92993afb90a3d8655037c
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService; // Now uses CompositeUserDetailsService (@Primary)

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Legacy-aware encoder: supports bcrypt and plaintext/noop without extra files
        return new PasswordEncoder() {
            private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            @Override public String encode(CharSequence rawPassword) { return bcrypt.encode(rawPassword); }
            @Override public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (encodedPassword == null) return false;
                String stored = encodedPassword.trim();
                String raw = rawPassword == null ? "" : rawPassword.toString();
                if (stored.startsWith("{bcrypt}")) {
                    return bcrypt.matches(raw, stored.substring("{bcrypt}".length()));
                }
                if (stored.startsWith("{noop}")) {
                    return raw.equals(stored.substring("{noop}".length()));
                }
                if (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$")) {
                    return bcrypt.matches(raw, stored);
                }
                return raw.equals(stored); // legacy/plaintext
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
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
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
<<<<<<< HEAD
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Allow both VENDOR and ADMIN to access vendor pages
                .requestMatchers("/vendor/**").hasAnyRole("VENDOR", "ADMIN")
                .requestMatchers("/shipper/**").hasRole("SHIPPER")
                .requestMatchers("/account/**", "/checkout/**", "/cart/**").authenticated()
                .anyRequest().permitAll()
=======
                .requestMatchers("/", "/login", "/register", "/register/**",
                                 "/NhanVien/**", // Cho phép truy cập tự do vào NhanVien (sử dụng session riêng)
                                 "/api/debug/**", // Cho phép truy cập debug API
                                 "/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**", "/favicon.ico", "/error").permitAll()
                .anyRequest().authenticated()
>>>>>>> e66511f5f76c02a6c7a92993afb90a3d8655037c
            )
            // Bỏ qua CSRF cho khu vực NhanVien vì đang dùng Interceptor session-based và API POS gọi từ fetch
            .csrf(csrf -> csrf.ignoringRequestMatchers("/NhanVien/**"))
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureUrl("/login?error")
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