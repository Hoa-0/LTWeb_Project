package com.alotra.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class NhanVienAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        
        // Các đường dẫn không cần xác thực
        if (requestURI.equals("/NhanVien/login") || 
            requestURI.startsWith("/css/") || 
            requestURI.startsWith("/js/") || 
            requestURI.startsWith("/images/") ||
            requestURI.equals("/favicon.ico") ||
            requestURI.startsWith("/NhanVien/check-session")) {
            return true;
        }
        
        // Kiểm tra session nhân viên
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInNhanVien") != null) {
            log.debug("NhanVien authenticated for path: {}", requestURI);
            return true;
        }
        
        log.warn("NhanVien not authenticated, redirecting to login. Path: {}", requestURI);
        response.sendRedirect("/NhanVien/login");
        return false;
    }
}