package com.alotra.web.config;

import com.alotra.web.interceptor.NhanVienAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    
    @Autowired
    private NhanVienAuthInterceptor nhanVienAuthInterceptor;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình để serve các file upload
        String uploadPath = Paths.get(uploadDir).toAbsolutePath().toString();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Thêm interceptor cho hệ thống nhân viên
        registry.addInterceptor(nhanVienAuthInterceptor)
                .addPathPatterns("/NhanVien/**")
                .excludePathPatterns("/NhanVien/login", "/NhanVien/check-session");
    }
}